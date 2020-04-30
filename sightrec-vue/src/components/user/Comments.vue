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
    </el-card>
  </div>
</template>

<script>
export default {
  data () {
    return {
      userName: window.sessionStorage.getItem('name'),
      userId: window.sessionStorage.getItem('id')
    }
  },
  created () {
  },
  methods: {
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
