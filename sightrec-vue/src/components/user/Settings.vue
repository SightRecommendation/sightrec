<template>
  <div class="settings">
    <el-card>
      <el-form style="width: 45%;" :model="userForm" ref="userFormRef" :rules="userFormRules" label-width="100px">
        <!-- prop 为 userFormRules 中的验证规则 -->
        <el-form-item label="用户名" prop="userName">
          <el-input maxlength="12" show-word-limit v-model="userForm.userName" clearable></el-input>
        </el-form-item>
        <el-form-item label="头像">
          <el-tooltip class="item" effect="dark" content="点击图片更换头像" placement="left">
            <el-upload
            class="avatar-uploader"
            action="https://jsonplaceholder.typicode.com/posts/"
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload">
              <img v-if="userForm.userAvatar" :src="userForm.userAvatar" class="avatar">
              <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
          </el-tooltip>
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input
          placeholder="请输入邮箱"
          v-model="userForm.userEmail"
          clearable></el-input>
        </el-form-item>
        <el-form-item label="手机" prop="userPhone">
          <el-input
          placeholder="请输入手机号"
          v-model.number="userForm.userPhone"
          clearable></el-input>
        </el-form-item>
        <el-form-item label="修改密码">
          <el-input placeholder="请输入旧密码" v-model="userForm.userOriginPwd" show-password clearable></el-input>
          <span style="font-size: small;color: #909399;">旧密码</span>
          <el-input placeholder="请输入新密码" v-model="userForm.userNewPwd" show-password clearable></el-input>
          <span style="font-size: small;color: #909399;">新密码</span>
          <el-input placeholder="确认密码" v-model="userForm.userConfirmPwd" show-password clearable></el-input>
          <span style="font-size: small;color: #909399;">确认密码</span>
        </el-form-item>
        <el-form-item>
          <el-button type="primary">保存修改</el-button>
          <el-button @click="resetForm('userFormRef')">重 置</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script>
export default {
  data () {
    return {
      userForm: {
        userName: '荒唐的杰尼',
        userAvatar: 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png',
        userEmail: 'admin@sightrec.com',
        userPhone: '18888888888',
        userOriginPwd: '',
        userNewPwd: '',
        userConfirmPwd: ''
      },
      userFormRules: {
        userName: [
          { required: true, message: '请输入用户名', trigger: 'blur' }
        ],
        userPhone: [
          { required: true, message: '请输入手机号', trigger: 'blur' },
          {
            min: 13000000000,
            max: 19999999999,
            type: 'number',
            message: '请输入正确的手机号',
            trigger: 'blur'
          }
        ]
      }
    }
  },
  created () {
  },
  methods: {
    handleAvatarSuccess (res, file) {
      this.imageUrl = URL.createObjectURL(file.raw)
    },
    beforeAvatarUpload (file) {
      const isJPG = file.type === 'image/jpeg'
      const isLt2M = file.size / 1024 / 1024 < 2

      if (!isJPG) {
        this.$message.error('上传头像图片只能是 JPG 格式!')
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!')
      }
      return isJPG && isLt2M
    },
    resetForm (ruleForm) {
      this.$refs[ruleForm].resetFields()
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
    border-color: #409EFF;
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
