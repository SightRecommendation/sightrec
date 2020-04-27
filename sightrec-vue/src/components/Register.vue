<template>
  <div class="register_container" :style="{'background': 'linear-gradient(to left bottom, hsl(' + leftColor + ', 100%, 85%) 0%,hsl(' + bottomColor + ', 100%, 85%) 100%)'}">
    <div class="register_box">
      <h1 style="font-family: 'Miriam Libre';">
        SightLens
      </h1>
      <!--      登陆表单区域-->
      <el-form ref="registerFormRef" :model="registerForm" :rules="registerFormRules" label-width="0px" class="register_form">
        <!--        用户名-->
        <el-form-item prop="name">
          <el-input placeholder="请输入长度为 3 - 15 的用户名" v-model="registerForm.name" prefix-icon="el-icon-user"></el-input>
        </el-form-item>
        <!--        密码-->
        <el-form-item prop="password">
          <el-input placeholder="请输入密码" v-model="registerForm.password" prefix-icon="el-icon-lock" show-password></el-input>
        </el-form-item>
        <!--        重复密码-->
        <el-form-item prop="password">
          <el-input placeholder="确认密码" v-model="registerForm.confirmPassword" prefix-icon="el-icon-lock" show-password></el-input>
        </el-form-item>
        <!--        按钮-->
        <el-form-item class="btns">
          <el-button type="primary" @click="register">注册</el-button>
          <el-button type="info" @click="resetregisterForm">重置</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
export default {
  data () {
    return {
      leftColor: 0,
      bottomColor: 203,
      registerForm: {
        name: 'user',
        password: '123456',
        confirmPassword: '123456'
      },
      registerFormRules: {
        name: [{ required: true, message: '请输入用户名', trigger: 'blur' },
          { min: 3, max: 15, message: '长度在 3 到 15 个字符', trigger: 'blur' }],
        password: [{ required: true, message: '请输入密码', trigger: 'blur' },
          { min: 6, max: 15, message: '长度在 6 到 15 个字符', trigger: 'blur' }]
      }
    }
  },
  created () {
    this.generateBackground()
  },
  methods: {
    generateBackground () {
      this.leftColor = parseInt(Math.random() * 256)
      this.bottomColor = parseInt(Math.random() * 256)
    },
    resetregisterForm () {
      this.$refs.registerFormRef.resetFields()
    },
    register () {
      this.$refs.registerFormRef.validate(async valid => {
        if (!valid) return
        if (this.registerForm.password !== this.registerForm.confirmPassword) { this.$message.error('两次输入密码不一致！'); return }
        const { data: result } = await this.$http.post('/users/register', this.registerForm)
        if (result.meta.status !== 200) this.$message.error(result.meta.msg)
        else this.$message.success('登录成功')
        console.log(result)
        window.sessionStorage.setItem('token', result.data.token)
        this.$router.push('/')
      })
    }
  }
}
</script>

<style lang="less" scoped>
  .register_container {
    height: 100%;
  }

  .register_box {
    width: 450px;
    height: 370px;
    backdrop-filter: blur(10px);
    background: rgba(255, 255, 255, 0.8);
    border-radius: 5px;
    position: absolute;
    left: 50%;
    top: 50%;
    transform: translate(-50%, -50%);
    >h1 {
      margin-top: 30px;
      text-align: center;
      font-weight: 400;
    }
  }
  .btns{
    display: flex;
    justify-content: flex-end;
  }
  .register_form{
    position: absolute;
    bottom: 5px;
    width: 100%;
    padding: 0 20px;
    box-sizing: border-box;
  }
</style>
