<template>
  <div class="login_container" :style="{'background': 'linear-gradient(to left bottom, hsl(' + leftColor + ', 100%, 85%) 0%,hsl(' + bottomColor + ', 100%, 85%) 100%)'}">
    <div class="login_box">
      <h1>
        sightrec
      </h1>
      <!--      登陆表单区域-->
      <el-form ref="loginFormRef" :model="loginForm" :rules="loginFormRules" label-width="0px" class="login_form">
        <!--        用户名-->
        <el-form-item prop="name">
          <el-input v-model="loginForm.name" prefix-icon="el-icon-user"></el-input>
        </el-form-item>
        <!--        密码-->
        <el-form-item prop="password">
          <el-input v-model="loginForm.password" prefix-icon="el-icon-lock" type="password"></el-input>
        </el-form-item>
        <!--        按钮-->
        <el-form-item class="btns">
          <el-button type="primary" @click="login">登录</el-button>
          <el-button type="info" @click="resetLoginForm">重置</el-button>
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
      loginForm: {
        name: 'user',
        password: '123456'
      },
      loginFormRules: {
        name: [{ required: true, message: '请输入用户名', trigger: 'blur' },
          { min: 3, max: 10, message: '长度在 3 到 10 个字符', trigger: 'blur' }],
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
    resetLoginForm () {
      this.$refs.loginFormRef.resetFields()
    },
    login () {
      this.$refs.loginFormRef.validate(async valid => {
        if (!valid) return
        const { data: result } = await this.$http.post('/users/login', this.loginForm)
        // const response = await this.$http.post('/admins/login', this.loginForm)
        if (result.meta.status !== 200) this.$message.error('登录失败')
        else this.$message.success('登录成功')
        console.log(result)
        window.sessionStorage.setItem('token', result.data.token)
        this.$router.push('/home')
      })
    }
  }
}
</script>

<style lang="less" scoped>
  .login_container {
    height: 100%;
  }

  .login_box {
    width: 450px;
    height: 300px;
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
  .login_form{
    position: absolute;
    bottom: 5px;
    width: 100%;
    padding: 0 20px;
    box-sizing: border-box;
  }
</style>
