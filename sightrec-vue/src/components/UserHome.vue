<template>
  <el-scrollbar class="home-scroll">
    <el-container class="home-container">
      <!-- 头部区域 -->
      <el-header>
        <div>
          <el-link class="link-logo"
                   :underline="false"
                   href="../">
            <img src="https://cdn.jsdelivr.net/gh/JingqingLin/ImageHosting/img/592aef2edd2d3.png"
                 width="48px" />
          </el-link>
          <span class="span-user-logo">个人中心 - SightLens</span>
        </div>
        <el-button type="danger"
                   size="small"
                   @click="logout"
                   plain>退出</el-button>
      </el-header>
      <!-- 页面主体区域 -->
      <el-container width="100vw">
        <!-- 侧边栏 -->
        <el-aside width="14vw">
          <!-- 侧边栏菜单区域 -->
          <el-menu router
                   :default-active="activePath">
            <!-- 一级菜单 -->
            <el-menu-item index="/user/settings">
              <!-- 一级菜单的模板区域 -->
              <template slot="title">
                <!-- 图标 -->
                <i class="el-icon-user"></i>
                <!-- 文本 -->
                <span>基本资料</span>
              </template>
            </el-menu-item>
            <el-menu-item index="/user/comments">
              <template slot="title">
                <i class="el-icon-chat-dot-square"></i>
                <span>我的评论</span>
              </template>
            </el-menu-item>
            <el-menu-item index="/user/favorite">
              <template slot="title">
                <i class="el-icon-shopping-cart-2"></i>
                <span>我的收藏</span>
              </template>
            </el-menu-item>
          </el-menu>
        </el-aside>
        <!-- 右侧内容主体 -->
        <div class="user">
          <!-- 路由占位符 -->
          <router-view></router-view>
        </div>
      </el-container>
    </el-container>
  </el-scrollbar>
</template>

<script>
export default {
  data () {
    return {
      // 被激活的链接地址
      activePath: ''
    }
  },
  created () {
    this.activePath = this.$route.path
  },
  methods: {
    logout () {
      window.sessionStorage.clear()
      this.$router.push('/login')
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
  z-index: 15 !important;
  position: fixed;
  top: 0;
  width: 100%;
}

.link-logo {
  display: flex;
  align-items: center;
}

.span-user-logo {
  font-family: "Miriam Libre";
  margin-left: 10px;
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

.user {
  position: relative;
  top: 75px;
  left: 15.5vw;
  width: 82vw;
  background-color: #fff;
}

.home-scroll {
  height: 100%;
}
</style>
