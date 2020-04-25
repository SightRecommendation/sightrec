<template>
  <el-scrollbar class="home-scroll">
    <el-container class="home-container">
      <!-- 头部区域 -->
      <el-header>
        <div>
          <img src="https://cdn.jsdelivr.net/gh/JingqingLin/ImageHosting/img/592aef2edd2d3.png" width="48px" />
          <span style="font-family: 'Miriam Libre';">SightLens</span>
        </div>
        <el-row :gutter="20">
          <el-col :span="8">
            <el-input placeholder="请输入景点名称">
              <el-button slot="append" icon="el-icon-search"></el-button>
            </el-input>
          </el-col>
        </el-row>
        <el-button class="button-user" icon="el-icon-user" circle></el-button>
      </el-header>
      <el-main>
        <h2>最受欢迎</h2>
        <el-row>
          <el-col :span="4" v-for="(sight, index1) in parsedPopularList" :key="index1">
            <span v-for="(imageUrl, index2) in sight.imageUrl" :key="index2">
              <!-- 只显示每个景点的第一张图片 -->
              <el-card shadow="hover" v-if="index2 < 1" :body-style="{ padding: '0px' }">
                <img :src="imageUrl" class="card-image">
                <div class="card-description">
                  <span>{{sight.name}}</span>
                  <div class="card-bottom clearfix">
                    <time class="card-time">{{sight.description}}</time>
                    <el-button type="text" class="card-button">更多</el-button>
                  </div>
                </div>
              </el-card>
            </span>
          </el-col>
          <el-button class="button-next" icon="el-icon-arrow-right" circle></el-button>
        </el-row>
        <el-divider></el-divider>
        <h2>好评最多</h2>
        <el-row>
          <el-col :span="4" v-for="(sight, index1) in parsedHighratedList" :key="index1">
            <span v-for="(imageUrl, index2) in sight.imageUrl" :key="index2">
              <!-- 只显示每个景点的第一张图片 -->
              <el-card shadow="hover" v-if="index2 < 1" :body-style="{ padding: '0px' }">
                <img :src="imageUrl" class="card-image">
                <div class="card-description">
                  <span>{{sight.name}}</span>
                  <div class="card-bottom clearfix">
                    <time class="card-time">{{sight.description}}</time>
                    <el-button type="text" class="card-button">更多</el-button>
                  </div>
                </div>
              </el-card>
            </span>
          </el-col>
          <el-button class="button-next" icon="el-icon-arrow-right" circle></el-button>
        </el-row>
        <el-divider></el-divider>
        <h2>为你推荐</h2>
        <el-row>
          <el-col :span="4" v-for="(sight, index1) in recommendedList" :key="index1">
            <span v-for="(imageUrl, index2) in sight.imageUrl" :key="index2">
              <!-- 只显示每个景点的第一张图片 -->
              <el-card shadow="hover" v-if="index2 < 1" :body-style="{ padding: '0px' }">
                <img :src="imageUrl" class="card-image">
                <div class="card-description">
                  <span>{{sight.name}}</span>
                  <div class="card-bottom clearfix">
                    <time class="card-time">{{sight.description}}</time>
                    <el-button type="text" class="card-button">更多</el-button>
                  </div>
                </div>
              </el-card>
            </span>
          </el-col>
          <el-button class="button-next" icon="el-icon-arrow-right" circle></el-button>
        </el-row>
        <el-divider></el-divider>
      </el-main>
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
      // 获取最受欢迎列表的参数对象
      popularQueryInfo: {
        query: '',
        // 当前的页数
        pageNum: 1,
        // 当前每页显示多少条数据
        pageSize: 5
      },
      // 获取最高评分列表的参数对象
      highratedQueryInfo: {
        query: '',
        // 当前的页数
        pageNum: 2,
        // 当前每页显示多少条数据
        pageSize: 5
      },
      // 获取最受欢迎列表的参数对象
      recommendedQueryInfo: {
        query: '',
        // 当前的页数
        pageNum: 3,
        // 当前每页显示多少条数据
        pageSize: 5
      },
      popularList: [],
      highratedList: [],
      recommendedList: [],
      parsedPopularList: [],
      parsedHighratedList: [],
      parsedrecommendedList: []
    }
  },
  created () {
    this.getPopularSightList()
    this.getHighratedSightList()
    this.getRecommendedSightList()
  },
  methods: {
    async getPopularSightList () {
      const { data: res } = await this.$http.get('sights', {
        params: this.popularQueryInfo
      })
      if (res.meta.status !== 200) {
        return this.$message.error('获取景点列表失败！')
      }
      this.popularList = res.data.sights
      this.parsedPopularList = this.popularList
      console.log('highrated' + res)
      for (let i = 0; i < this.popularList.length; i++) {
        this.parsedPopularList[i].imageUrl = JSON.parse(this.parsedPopularList[i].imageUrl)
      }
    },
    async getHighratedSightList () {
      const { data: res } = await this.$http.get('sights', {
        params: this.highratedQueryInfo
      })
      if (res.meta.status !== 200) {
        return this.$message.error('获取景点列表失败！')
      }
      this.highratedList = res.data.sights
      this.parsedHighratedList = this.highratedList
      console.log('popular' + res)
      for (let i = 0; i < this.highratedList.length; i++) {
        this.parsedHighratedList[i].imageUrl = JSON.parse(this.parsedHighratedList[i].imageUrl)
      }
    },
    async getRecommendedSightList () {
      const { data: res } = await this.$http.get('sights', {
        params: this.recommendedQueryInfo
      })
      if (res.meta.status !== 200) {
        return this.$message.error('获取景点列表失败！')
      }
      this.recommendedList = res.data.sights
      this.parsedrecommendedList = this.recommendedList
      console.log('highrated' + res)
      for (let i = 0; i < this.recommendedList.length; i++) {
        this.parsedrecommendedList[i].imageUrl = JSON.parse(this.parsedrecommendedList[i].imageUrl)
      }
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

    >div {
      margin-left: 10px;
      display: flex;
      align-items: center;

      span {
        margin-left: 10px;
      }
    }

    .el-input {
      margin-left: 30px;
      margin-right: 700px;
    }
  }

  .el-main {
    color: #333;
    margin-left: 10px;
    margin-right: 10px;
    margin-top: 50px;
    .el-row {
      margin-top: 10px;
      .el-col {
        margin-left: 25px;
      }
    }
  }

  .el-card {
    box-shadow: 0 1px 1px 0 rgba(0,0,0,.1);
  }
  .card-description {
    padding-bottom: 0px;
    padding-left: 14px;
    padding-top: 14px;
    padding-right: 14px;
  }
  .card-time {
    font-size: 13px;
    color: #999;
  }
  .card-bottom {
    margin-top: 13px;
    line-height: 12px;
  }
  .card-button {
    padding: 0;
    float: right;
  }
  .card-image {
    width: 200%;
    position: relative;
    right: 60px;
    display: block;
  }
  .clearfix:before,
  .clearfix:after {
    display: table;
    content: "";
  }
  .clearfix:after {
    clear: both
  }

  .button-next {
    position: relative;
    top: 125px;
    left: 30px;
  }

  .el-footer {
    background-color: #fff;
    color: #333;
    text-align: center;
  }

  .home-scroll {
    height: 100%;
  }
</style>
