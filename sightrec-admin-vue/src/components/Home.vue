<template>
  <el-scrollbar class="home-scroll">
    <el-container class="home-container">
      <!-- 头部区域 -->
      <el-header>
        <div>
          <img src="https://cdn.jsdelivr.net/gh/JingqingLin/ImageHosting/img/592aef2edd2d3.png" width="48px" />
          <span>景点后台管理系统</span>
        </div>
        <el-button type="danger" size="small" @click="logout" plain>退出</el-button>
      </el-header>
      <!-- 页面主体区域 -->
      <el-container width="100vw">
        <!-- 侧边栏 -->
        <el-aside width="14vw">
          <!-- 侧边栏菜单区域 -->
          <el-menu router :default-active="activePath">
            <!-- 一级菜单 -->
            <el-menu-item :index="'/'+item.path" v-for="item in menulist" :key="item.id">
              <!-- 一级菜单的模板区域 -->
              <template slot="title">
                <!-- 图标 -->
                <i :class="iconsObj[item.id]"></i>
                <!-- 文本 -->
                <span>{{item.authName}}</span>
              </template>
            </el-menu-item>
          </el-menu>
        </el-aside>
        <!-- 右侧内容主体 -->
        <div class="main">
          <!-- 路由占位符 -->
          <router-view></router-view>
        </div>
      </el-container>
    </el-container>
  </el-scrollbar>
</template>

<script>
  export default {
    data() {
      return {
        // 左侧菜单数据
        menulist: [],
        iconsObj: {
          '1': 'iconfont icon-user',
          '22': 'iconfont icon-tijikongjian',
          '222': 'iconfont icon-shangpin',
          '3': 'iconfont icon-danju',
          '2': 'iconfont icon-baobiao'
        },
        // 被激活的链接地址
        activePath: ''
      }
    },
    created() {
      this.getMenuList()
      this.activePath = this.$route.path
    },
    methods: {
      logout() {
        window.sessionStorage.clear()
        this.$router.push('/login')
      },
      // 获取所有的菜单
      async getMenuList() {
        const { data: res } = await this.$http.get('menus')
        if (res.meta.status !== 200) return this.$message.error(res.meta.msg)
        this.menulist = res.data
        console.log(res)
      }
    }
  }
</script>

<!-- style 要全局，不能 scoped，否则滚动条不起作用 -->
<!-- https://blog.csdn.net/yiyueqinghui/article/details/89714124 -->
<style lang="less">
  .home-container {
    height: 100%;
  }

  .el-header {
    display: flex;
    justify-content: space-between;
    padding-left: 0;
    align-items: center;
    color: #303133;
    font-size: 20px;
    border: 0px solid #dcdfe6;
    border-top: none;
    border-left: none;
    border-right: none;
    box-shadow: rgba(0, 0, 0, 0.1) 0 0 6px;
    backdrop-filter: blur(6px);
    background: rgba(255, 255, 255, 0.7);
    z-index: 2;
    position: fixed;
    top: 0;
    width: 100%;

    >div {
      margin-left: 10px;
      display: flex;
      align-items: center;

      span {
        margin-left: 10px;
      }
    }
  }

  .el-aside {
    position: fixed;
    top: 60px;
    height: 100%;
    background-color: #fff;
    border: 0px solid #dcdfe6;
    border-top: none;
    border-left: none;
    border-bottom: none;
    box-shadow: rgba(0, 0, 0, 0.1) 0 0 6px;
    z-index: 1;

    .el-menu {
      border-right: none;
    }
  }

  .main {
    position: relative;
    top: 75px;
    left: 15.5vw;
    width: 82vw;
    background-color: #fff;
  }

  .iconfont {
    margin-right: 10px;
  }

  .home-scroll {
    height: 100%;
  }
</style>