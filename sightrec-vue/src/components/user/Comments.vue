<template>
  <div class="settings">
    <!-- 面包屑导航区域 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: 'user/settings' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>我的评论</el-breadcrumb-item>
    </el-breadcrumb>

    <el-card>
      <p>
        <i class="el-icon-chat-dot-square"></i>
        <span> 我的评论</span>
      </p><br />
      <el-timeline>
        <el-timeline-item v-for="(item, index) in commentAndSightList"
                          :key="index"
                          icon="el-icon-more"
                          type="primary"
                          size="large"
                          :timestamp=item.createdDate
                          placement="top">
          <el-card>
            <el-link class="span-sight"
                     style="font-size: 17px;font-weight: 600"
                     @click="jumpSightDetail(item.sightId)"> {{ item.sightName }} </el-link>
            <span style="font-size: 13px;font-weight: 400;color: #909399;position: relative;top: 3px"> {{ item.sightCity }} </span>
            <el-button type="danger"
                       icon="el-icon-delete"
                       style="float: right"
                       @click="removeCommentById(item.commentId)"
                       circle></el-button>
            <p>{{ item.content }}</p>
          </el-card>
        </el-timeline-item>
        <!-- 分页区域 -->
        <el-pagination @size-change="handleSizeChange"
                       @current-change="handleCurrentChange"
                       :current-page="commentQueryInfo.pageNum"
                       :page-sizes="[5, 10, 20]"
                       :page-size="commentQueryInfo.pageSize"
                       layout="total, sizes, prev, pager, next, jumper"
                       :total="commentNum">
        </el-pagination>
      </el-timeline>
    </el-card>
  </div>
</template>

<script>
export default {
  data () {
    return {
      userName: window.sessionStorage.getItem('name'),
      userId: window.sessionStorage.getItem('id'),
      commentQueryInfo: {
        userId: window.sessionStorage.getItem('id'),
        pageNum: 1,
        pageSize: 5
      },
      commentNum: 0,
      commentList: [],
      commentAndSightList: []
    }
  },
  created () {
    this.getUserCommentList()
  },
  methods: {
    async getUserCommentList () {
      const { data: res } = await this.$http.get('comments/user', {
        params: this.commentQueryInfo
      })
      if (res.meta.status !== 200) {
        return this.$message.error('获取评论失败！')
      }
      this.commentList = res.data.comments
      this.commentNum = res.data.commentNum

      var tempList = []
      for (let i = 0; i < this.commentList.length; i++) {
        const { data: res } = await this.$http.get('sights/' + this.commentList[i].sightId)
        if (res.meta.status !== 200) {
          return this.$message.error('获取景点信息失败！')
        }
        var commentAndSight = {
          sightId: 1,
          sightName: '景点名称',
          sightCity: '北京',
          commentId: 1,
          content: '评论内容...',
          createdDate: '2020-01-01 00:00:00'
        }
        commentAndSight.commentId = this.commentList[i].id
        commentAndSight.content = this.commentList[i].content
        commentAndSight.createdDate = this.commentList[i].createdDate
        commentAndSight.sightId = res.data.id
        commentAndSight.sightName = res.data.name
        commentAndSight.sightCity = res.data.city
        tempList[i] = commentAndSight
      }
      this.commentAndSightList = tempList
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
      this.getUserCommentList()
    },
    // 监听 pageSize 改变的事件
    handleSizeChange (newSize) {
      this.commentQueryInfo.pageSize = newSize
      this.getUserCommentList()
    },
    // 监听 页码值 改变的事件
    handleCurrentChange (newPage) {
      this.commentQueryInfo.pageNum = newPage
      this.$root.scrollEvent.$emit('trans')
      this.getUserCommentList()
    },
    jumpSightDetail (id) {
      this.$router.push({
        path: `/sight/${id}`
      })
    }
  }
}
</script>

<style lang="less">
.col-left > p {
  color: #909399;
  text-align: right;
  line-height: 40px;
}

.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409eff;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 89px;
  height: 89px;
  line-height: 89px !important;
  text-align: center;
}
.avatar {
  width: 89px;
  height: 89px;
  display: block;
}
</style>
