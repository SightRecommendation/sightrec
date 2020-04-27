<template>
  <el-scrollbar class="home-scroll" ref="myScrollbar">
    <el-container class="home-container">
      <!-- 头部区域 -->
      <el-header>
        <div style="display: flex;">
          <el-link class="link-logo" :underline="false" href="../">
            <img src="https://cdn.jsdelivr.net/gh/JingqingLin/ImageHosting/img/592aef2edd2d3.png" width="48px" />
          </el-link>
          <span class="span-logo">SightLens</span>
        </div>
        <el-row :gutter="20">
          <el-col :span="8">
            <el-autocomplete popper-class="my-autocomplete"
            v-model="sight" :fetch-suggestions="querySearchAsync"
            placeholder="输入景点名称以搜索" @select="handleSelect" clearable style="width: 70%;">
            <i
            class="el-icon-search el-input__icon"
            slot="suffix">
            </i>
            <template slot-scope="{ item }">
              <div class="name">{{ item.value }}</div>
              <span class="addr">{{ item.address }}</span>
            </template>
            </el-autocomplete>
          </el-col>
        </el-row>
        <el-dropdown @command="handleCommand" trigger="click">
          <el-button class="button-user" icon="el-icon-user" circle></el-button>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item command="settings" icon="el-icon-setting">设置</el-dropdown-item>
            <el-dropdown-item command="logout" icon="el-icon-delete">退出</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </el-header>
      <div class="main">
        <!-- 路由占位符 -->
        <router-view></router-view>
      </div>
      <el-footer>
        <p style="font-size: small;">
          京 ICP 备 1234567890-1 号
        </p>
        <p style="font-size: small;">
          &copy; 2020 SightLens 版权所有
        </p>
      </el-footer>
    </el-container>
  </el-scrollbar>
</template>

<script>
export default {
  data () {
    return {
      searchedSights: [],
      sight: '',
      timeout: null,
      // 获取搜索景点列表的参数
      searchQueryInfo: {
        query: '',
        pageSize: 20
      }
    }
  },
  created () {
  },
  beforeRouteUpdate (to, from, next) {
    // 页面跳转后，回顶部
    this.$refs.myScrollbar.wrap.scrollTop = 0
    next()
  },
  methods: {
    handleCommand (command) {
      if (command === 'logout') {
        window.sessionStorage.clear()
        this.$router.push('/login')
      } else if (command === 'settings') {
        this.$router.push('/user/settings')
      }
    },
    querySearchAsync (queryString, cb) {
      this.searchQueryInfo.query = queryString

      if (queryString === '') {
        var results = []
        cb(results)
      } else {
        this.$http.get('sights/search', {
          params: this.searchQueryInfo
        }).then((response) => {
          this.searchedSights = response.data.data
          cb(this.searchedSights)
        }).catch((error) => {
          console.log(error)
        })
      }
    },
    handleSelect (item) {
      this.sight = ''
      this.$router.push({
        path: `/sight/${item.id}`
      })
    }
  }
}
</script>

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
    backdrop-filter: blur(15px);
    background: rgba(255, 255, 255, 0.7);
    z-index: 2;
    position: fixed;
    top: 0;
    width: 100%;
  }

  .link-logo {
    display: flex;
    align-items: center;
  }

  .span-logo {
    font-family: 'Miriam Libre';
    position: relative;
    top: 11px;
  }

  .main {
    color: #333;
    position: relative;
    top: 75px;
    left: 3vw;
    width: 94vw;
    .el-row {
      margin-top: 10px;
      .el-col {
        margin-left: 25px;
      }
    }
  }

  .el-footer {
    position: relative;
    top: 75px;
    background-color: #fff;
    color: #333;
    text-align: center;
  }

  .my-autocomplete {
    li {
      line-height: normal !important;
      padding: 7px !important;
      padding-left: 17px !important;

      .name {
        text-overflow: ellipsis;
        overflow: hidden;
      }
      .addr {
        font-size: 12px;
        color: #b4b4b4;
      }
      .highlighted .addr {
        color: #ddd;
      }
    }
  }

  .el-autocomplete-suggestion__wrap {
    margin-bottom: 0px !important;
  }

  .home-scroll {
    height: 100%;
  }
</style>
