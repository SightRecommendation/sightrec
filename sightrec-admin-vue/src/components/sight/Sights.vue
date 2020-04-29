<template>
  <div class="comments">
    <!-- 面包屑导航区域 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>景点管理</el-breadcrumb-item>
    </el-breadcrumb>

    <!-- 卡片视图区域 -->
    <el-card>
      <!-- 搜索与添加区域 -->
      <el-row :gutter="20">
        <el-col :span="8">
          <el-input placeholder="输入景点名称"
                    v-model="queryInfo.query"
                    clearable
                    @clear="getSightList">
            <el-button slot="append"
                       icon="el-icon-search"
                       @click="searchSights"></el-button>
          </el-input>
        </el-col>
        <!-- <el-col :span="4">
            <el-button type="primary" @click="addDialogVisible = true">添加景点</el-button>
          </el-col> -->
      </el-row>

      <!-- 景点列表区域 -->
      <el-table :data="parsedSightList"
                border
                stripe>
        <el-table-column type="expand">
          <template slot-scope="props">
            <el-form label-position="left"
                     inline
                     class="sight-table-expand">
              <el-form-item label="景点名称">
                <span>{{ props.row.name }}</span>
              </el-form-item>
              <el-form-item label="地址">
                <span>{{ props.row.location }}</span>
              </el-form-item>
              <el-form-item label="经纬度">
                <span>{{ props.row.coordinate }}</span>
              </el-form-item>
              <el-form-item label="缩略图">
                <span v-for="(item, index) in props.row.imageUrl"
                      v-bind:key="index">
                  <el-popover placement="top-start"
                              width="300"
                              trigger="hover">
                    <img :src="item"
                         alt=""
                         width="100%" />
                    <el-link slot="reference"
                             type="primary"
                             style="margin-right: 10px;font-size: 12px;">
                      图 {{index}}
                    </el-link>
                  </el-popover>
                </span>
              </el-form-item>
              <el-form-item label="标签">
                <span v-for="(item, index) in props.row.subject.split(',')"
                      v-bind:key="index">
                  <el-tag size="mini"
                          style="margin-right: 10px;">{{ item }}</el-tag>
                </span>
              </el-form-item>
              <el-form-item label="评分">
                <span>{{ props.row.point }}</span>
              </el-form-item>
              <el-form-item label="标语">
                <span>{{ props.row.description }}</span>
              </el-form-item>
              <el-form-item label="介绍">
                <span>{{ props.row.introduction }}</span>
              </el-form-item>
            </el-form>
          </template>
        </el-table-column>
        <el-table-column label="ID"
                         prop="id"
                         width="80px"></el-table-column>
        <el-table-column label="名称"
                         prop="name"
                         width="150px"></el-table-column>
        <el-table-column label="位置"
                         prop="location"></el-table-column>
        <el-table-column label="评分"
                         prop="point"
                         width="50px"></el-table-column>
        <el-table-column label="标语"
                         prop="description"></el-table-column>
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
                       @click="removeSightById(scope.row.id)">
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
                     :total="sightNum">
      </el-pagination>
    </el-card>

    <!-- 修改景点对话框 -->
    <el-dialog title="修改景点"
               :visible.sync="editDialogVisible"
               width="70%"
               @close="editDialogClosed">
      <el-form :model="editForm"
               :rules="editFormRules"
               ref="editFormRef"
               label-width="80px">
        <el-form-item label="景点名称"
                      prop="name">
          <el-input v-model="editForm.name"></el-input>
        </el-form-item>
        <el-form-item label="位置"
                      prop="location">
          <el-input v-model="editForm.location"></el-input>
        </el-form-item>
        <el-form-item label="图片链接"
                      prop="imageUrl">
          <el-input v-model="editForm.imageUrl"></el-input>
        </el-form-item>
        <el-form-item label="评分"
                      prop="point">
          <el-input-number type="number"
                           v-model="editForm.point"
                           :precision="1"
                           :step="0.1"
                           :min="0"
                           :max="5">
          </el-input-number>
        </el-form-item>
        <el-form-item label="标语"
                      prop="description">
          <el-input type="textarea"
                    autosize
                    v-model="editForm.description"></el-input>
        </el-form-item>
        <el-form-item label="介绍"
                      prop="introduction">
          <el-input type="textarea"
                    autosize
                    v-model="editForm.introduction"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer"
            class="dialog-footer">
        <el-button @click="editDialogVisible = false">取 消</el-button>
        <el-button type="primary"
                   @click="editSightInfo">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data () {
    return {
      // 获取景点列表的参数对象
      queryInfo: {
        query: '',
        // 当前的页数
        pageNum: 1,
        // 当前每页显示多少条数据
        pageSize: 7
      },
      sightList: [],
      parsedSightList: [],
      sightNum: 0,
      // 控制添加用户对话框的显示与隐藏
      addDialogVisible: false,
      // 控制修改用户对话框的显示与隐藏
      editDialogVisible: false,
      // 添加用户的表单数据
      // 修改表单的验证规则对象
      editFormRules: {
        name: [
          { required: true, message: '请输入内容', trigger: 'blur' }
        ],
        location: [
          { required: true, message: '请输入内容', trigger: 'blur' }
        ],
        imageUrl: [
          { required: true, message: '请输入内容', trigger: 'blur' }
        ],
        point: [
          { required: true, message: '请输入评分', trigger: 'blur' }
        ],
        description: [
          { required: true, message: '请输入内容', trigger: 'blur' }
        ],
        introduction: [
          { required: true, message: '请输入内容', trigger: 'blur' }
        ]
      },
      editForm: {},
      inputVisible: false,
      inputValue: '',
    }
  },
  created () {
    this.getSightList()
  },
  methods: {
    async getSightList () {
      const { data: res } = await this.$http.get('sights', {
        params: this.queryInfo
      })
      if (res.meta.status !== 200) {
        return this.$message.error('获取景点列表失败！')
      }
      this.sightList = res.data.sights
      this.imageUrlToJsonObject()
      this.sightNum = res.data.sightNum
      console.log(res)
    },
    // imageUrl 由 JSON 字符串转为 JSON 对象
    imageUrlToJsonObject () {
      this.parsedSightList = this.sightList
      for (let i = 0; i < this.sightList.length; i++) {
        this.parsedSightList[i].imageUrl = JSON.parse(this.parsedSightList[i].imageUrl)
      }
    },
    // 搜索景点，页码要置为 1
    searchSights () {
      this.queryInfo.pageNum = 1
      this.getSightList()
    },
    // 监听 pageSize 改变的事件
    handleSizeChange (newSize) {
      this.queryInfo.pageSize = newSize
      this.getSightList()
    },
    // 监听 页码值 改变的事件
    handleCurrentChange (newPage) {
      this.queryInfo.pageNum = newPage
      this.getSightList()
    },
    // 监听添加景点对话框的关闭事件
    addDialogClosed () {
      this.$refs.addFormRef.resetFields()
    },
    // 展示编辑景点的对话框
    async showEditDialog (id) {
      const { data: res } = await this.$http.get('sights/' + id)

      if (res.meta.status !== 200) {
        return this.$message.error('查询景点信息失败！')
      }

      this.editForm = res.data
      this.editDialogVisible = true
    },
    // 监听修改景点对话框的关闭事件
    editDialogClosed () {
      this.$refs.editFormRef.resetFields()
    },
    // 修改景点信息并提交
    editSightInfo () {
      this.$refs.editFormRef.validate(async valid => {
        if (!valid) return
        // 发起景点信息的数据请求
        const { data: res } = await this.$http.put(
          'sights/' + this.editForm.id, this.editForm
        )

        if (res.meta.status !== 200) {
          return this.$message.error('修改景点失败！')
        }

        // 关闭对话框
        this.editDialogVisible = false
        // 刷新数据列表
        this.getSightList()
        // 提示修改成功
        this.$message.success('修改景点成功！')
      })
    },
    async removeSightById (id) {
      // 弹框询问用户是否删除数据
      const confirmResult = await this.$confirm(
        '此操作将删除该条景点, 是否继续？',
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
      const { data: res } = await this.$http.delete('sights/' + id)

      if (res.meta.status !== 200) {
        return this.$message.error('删除景点失败！')
      }

      this.$message.success('删除景点成功！')
      this.getSightList()
    },
  }
}
</script>

<style lang="less">
.sight-table-expand label {
  font-size: 12px;
  line-height: 22px !important;
  width: 80px;
  color: #99a9bf;
}

.sight-table-expand .el-form-item__content {
  font-size: 12px;
  line-height: 22px !important;
}

.sight-table-expand .el-form-item {
  margin-right: 0;
  margin-bottom: 0;
  width: 100%;
}
</style>
