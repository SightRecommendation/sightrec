<template>
  <div class="comments">
    <!-- 面包屑导航区域 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>评论管理</el-breadcrumb-item>
    </el-breadcrumb>

    <!-- 卡片视图区域 -->
    <el-card>
      <!-- 搜索与添加区域 -->
      <el-row :gutter="20">
        <el-col :span="8">
          <el-input placeholder="输入评论内容"
                    v-model="queryInfo.query"
                    clearable
                    @clear="getCommentList">
            <el-button slot="append"
                       icon="el-icon-search"
                       @click="searchComments"></el-button>
          </el-input>
        </el-col>
        <el-col :span="4">
          <el-button type="primary"
                     @click="addDialogVisible = true">添加评论</el-button>
        </el-col>
      </el-row>

      <!-- 评论列表区域 -->
      <el-table :data="commentList"
                border
                stripe>
        <el-table-column type="index"></el-table-column>
        <el-table-column label="内容"
                         prop="content"></el-table-column>
        <el-table-column label="用户 ID"
                         prop="userId"
                         width="65px"></el-table-column>
        <el-table-column label="景点 ID"
                         prop="sightId"
                         width="65px"></el-table-column>
        <el-table-column label="时间"
                         prop="createdDate"
                         width="150px"></el-table-column>
        <el-table-column label="操作"
                         width="120px">
          <template slot-scope="scope">
            <!-- 修改按钮 -->
            <el-button type="primary"
                       icon="el-icon-edit"
                       size="mini"
                       @click="showEditDialog(scope.row.id)"></el-button>
            <!-- 删除按钮 -->
            <el-button type="danger"
                       icon="el-icon-delete"
                       size="mini"
                       @click="removeCommentById(scope.row.id)">
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页区域 -->
      <el-pagination @size-change="handleSizeChange"
                     @current-change="handleCurrentChange"
                     :current-page="queryInfo.pageNum"
                     :page-sizes="[7, 10, 20, 50]"
                     :page-size="queryInfo.pageSize"
                     layout="total, sizes, prev, pager, next, jumper"
                     :total="commentNum">
      </el-pagination>
    </el-card>

    <!-- 添加评论的对话框 -->
    <el-dialog title="添加评论"
               :visible.sync="addDialogVisible"
               width="50%"
               @close="addDialogClosed">
      <!-- 内容主体区域 -->
      <el-form :model="addForm"
               :rules="addFormRules"
               ref="addFormRef"
               label-width="80px">
        <el-form-item label="创建时间"
                      prop="createdDate">
          <el-date-picker v-model="addForm.createdDate"
                          type="datetime"
                          value-format="yyyy-MM-dd HH:mm:ss"
                          placeholder="选择日期时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="内容"
                      prop="content">
          <el-input v-model="addForm.content"></el-input>
        </el-form-item>
        <el-form-item label="用户 ID"
                      prop="userId">
          <el-input v-model.number="addForm.userId"></el-input>
        </el-form-item>
        <el-form-item label="景点 ID"
                      prop="sightId">
          <el-input v-model.number="addForm.sightId"></el-input>
        </el-form-item>
      </el-form>
      <!-- 底部区域 -->
      <span slot="footer"
            class="dialog-footer">
        <el-button @click="addDialogVisible = false">取 消</el-button>
        <el-button type="primary"
                   @click="addComment">确 定</el-button>
      </span>
    </el-dialog>

    <!-- 修改评论的对话框 -->
    <el-dialog title="修改评论"
               :visible.sync="editDialogVisible"
               width="50%"
               @close="editDialogClosed">
      <el-form :model="editForm"
               :rules="editFormRules"
               ref="editFormRef"
               label-width="80px">
        <el-form-item label="内容"
                      prop="content">
          <el-input type="textarea"
                    autosize
                    v-model="editForm.content"></el-input>
        </el-form-item>
        <el-form-item label="用户 ID">
          <el-input v-model="editForm.userId"
                    disabled></el-input>
        </el-form-item>
        <el-form-item label="景点 ID">
          <el-input v-model="editForm.sightId"
                    disabled></el-input>
        </el-form-item>
        <el-form-item label="创建时间">
          <el-input v-model="editForm.createdDate"
                    disabled></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer"
            class="dialog-footer">
        <el-button @click="editDialogVisible = false">取 消</el-button>
        <el-button type="primary"
                   @click="editCommentInfo">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data () {
    return {
      // 获取评论列表的参数对象
      queryInfo: {
        query: '',
        // 当前的页数
        pageNum: 1,
        // 当前每页显示多少条数据
        pageSize: 7
      },
      commentList: [],
      commentNum: 0,
      // 控制添加用户对话框的显示与隐藏
      addDialogVisible: false,
      // 控制修改用户对话框的显示与隐藏
      editDialogVisible: false,
      // 添加用户的表单数据
      // 修改表单的验证规则对象
      editFormRules: {
        content: [
          { required: true, message: '请输入内容', trigger: 'blur' }
        ]
      },
      addFormRules: {
        content: [
          { required: true, message: '请输入内容', trigger: 'blur' }
        ],
        userId: [
          { required: true, message: '请输入内容', trigger: 'blur' },
          {
            min: 1,
            max: 9999,
            type: 'number',
            message: '长度为 1 ~ 5 位数字',
            trigger: 'blur'
          }
        ],
        sightId: [
          { required: true, message: '请输入内容', trigger: 'blur' },
          {
            min: 1,
            max: 9999,
            type: 'number',
            message: '长度为 1 ~ 5 位数字',
            trigger: 'blur'
          }
        ],
        createdDate: [
          { required: true, message: '请选择日期', trigger: 'blur' }
        ]
      },
      addForm: {
        content: '',
        userId: '',
        sightId: '',
        createdDate: ''
      },
      // 查询到的评论信息对象
      editForm: {},
    }
  },
  created () {
    this.getCommentList()
  },
  methods: {
    async getCommentList () {
      const { data: res } = await this.$http.get('comments', {
        params: this.queryInfo
      })
      if (res.meta.status !== 200) {
        return this.$message.error('获取评论列表失败！')
      }
      this.commentList = res.data.comments
      this.commentNum = res.data.commentNum
      console.log(res)
    },
    // 搜索评论，页码要置为 1
    searchComments () {
      this.queryInfo.pageNum = 1
      this.getCommentList()
    },
    // 监听 pageSize 改变的事件
    handleSizeChange (newSize) {
      // console.log(newSize)
      this.queryInfo.pageSize = newSize
      this.getCommentList()
    },
    // 监听 页码值 改变的事件
    handleCurrentChange (newPage) {
      // console.log(newPage)
      this.queryInfo.pageNum = newPage
      this.getCommentList()
    },
    // 监听添加评论对话框的关闭事件
    addDialogClosed () {
      this.$refs.addFormRef.resetFields()
    },
    // 点击按钮，添加新评论
    addComment () {
      console.log(this.addForm.createdDate)
      this.$refs.addFormRef.validate(async valid => {
        if (!valid) return
        // 可以发起添加评论的网络请求
        const { data: res } = await this.$http.post('comments', this.addForm)
        if (res.meta.status !== 200) {
          this.$message.error('添加评论失败！')
        }
        this.$message.success('添加评论成功！')
        // 隐藏添加评论的对话框
        this.addDialogVisible = false
        // 重新获取评论列表数据
        this.getCommentList()
      })
    },
    // 展示编辑评论的对话框
    async showEditDialog (id) {
      // console.log(id)
      const { data: res } = await this.$http.get('comments/' + id)
      if (res.meta.status !== 200) {
        return this.$message.error('查询评论信息失败！')
      }
      this.editForm = res.data
      this.editDialogVisible = true
    },
    // 监听修改评论对话框的关闭事件
    editDialogClosed () {
      this.$refs.editFormRef.resetFields()
    },
    // 修改评论信息并提交
    editCommentInfo () {
      this.$refs.editFormRef.validate(async valid => {
        if (!valid) return
        // 发起修改评论信息的数据请求
        const { data: res } = await this.$http.put(
          'comments/' + this.editForm.id,
          {
            content: this.editForm.content
          }
        )
        if (res.meta.status !== 200) {
          return this.$message.error('修改评论失败！')
        }
        // 关闭对话框
        this.editDialogVisible = false
        // 刷新数据列表
        this.getCommentList()
        // 提示修改成功
        this.$message.success('修改评论成功！')
      })
    },
    // 根据 Id 删除对应的评论
    async removeCommentById (id) {
      // 弹框询问用户是否删除数据
      const confirmResult = await this.$confirm(
        '此操作将删除该评论, 是否继续？',
        '提示',
        {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }
      ).catch(err => err)
      // 如果用户确认删除，则返回值为字符串 confirm
      // 如果用户取消了删除，则返回值为字符串 cancel
      // console.log(confirmResult)
      if (confirmResult !== 'confirm') {
        return this.$message.info('取消删除')
      }
      const { data: res } = await this.$http.delete('comments/' + id)
      if (res.meta.status !== 200) {
        return this.$message.error('删除评论失败！')
      }
      this.$message.success('删除评论成功！')
      this.getCommentList()
    },
  }
}
</script>

<style lang="less">
</style>
