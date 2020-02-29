<template>
  <div class="login_container">
    <div class="login_box">
      <!--      登陆表单区域-->
      <el-form ref="loginFormRef" :model="loginForm" :rules="loginFormRules" label-width="0px" class="login_form">
        <!--        用户名-->
        <el-form-item prop="name">
          <el-input  v-model="loginForm.name" prefix-icon="el-icon-user"></el-input>
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
      loginForm: {
        name: 'admin',
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
  methods: {
    resetLoginForm () {
      this.$refs.loginFormRef.resetFields()
    },
    login () {
      this.$refs.loginFormRef.validate(async valid => {
        if (!valid) return
        //const { data: result } = await this.$http.post('/admins/login', this.loginForm)
        const response = await this.$http.post('/admins/login', this.loginForm)
        // 登录请求返回的 Json 中没有 status 属性，先注释
        // if (result.meta.status !== 200) this.$message.error('登陆失败')
        // else this.$message.success('登陆成功')
        // console.log(result)
        console.log(response);
        // window.sessionStorage.setItem('token', result.data.token)
        // this.$router.push('/home')
      })
    }
  }
}
</script>

<style lang="less" scoped>
  .login_container {
    background-color: #2b4b6b;
    height: 100%;
  }

  .login_box {
    width: 450px;
    height: 300px;
    background-color: #fff;
    border-radius: 3px;
    position: absolute;
    left: 50%;
    top: 50%;
    transform: translate(-50%, -50%);

  }
  .btns{
    display: flex;
    justify-content: flex-end;
  }
  .login_form{
    position: absolute;
    bottom: 30px;
    width: 100%;
    padding: 0 20px;
    box-sizing: border-box;
  }
</style>
