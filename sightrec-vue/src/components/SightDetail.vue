<template>
  <el-scrollbar class="sight-scroll">
    <el-container class="sight-container">
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
        <el-card class="box-card">
          <el-row :gutter="20" style="margin-left: -25px;">
            <el-col :span="13">
              <el-carousel trigger="click" height="400px">
                <el-carousel-item v-for="item in parsedsightDetail.imageUrl" :key="item">
                  <img :src="item" alt="" width="120%"/>
                </el-carousel-item>
              </el-carousel>
            </el-col>
            <el-col :span="10">
              <el-row>
                <span style="font-size: 25px;font-weight: 400;color: #409eff;">{{ parsedsightDetail.name }}</span>
                <span> {{ parsedsightDetail.level }}</span>
                <p style="font-size: 15px;color: #999;">{{ parsedsightDetail.description }}</p><br />
                <p style="margin-top: 10px;">
                  <i class="el-icon-location-outline"></i><span style="color: #999;"> 位置：</span>
                  <span>{{ parsedsightDetail.location }}</span>
                </p>
                <p>
                  <i class="el-icon-s-data"></i><span style="color: #999;"> 评分：</span>
                  <span style="font-size: 19px;color: #ff6600;">{{ parsedsightDetail.point }}</span><span> / 5 分</span>
                </p>
                <p>
                  <i class="el-icon-sunny"></i><span style="color: #999;"> 热度：</span>
                  <span>{{ parsedsightDetail.heat }}</span>
                </p>
                <p>
                  <i class="el-icon-collection-tag"></i><span style="color: #999;"> 标签：</span>
                  <span v-for="(item, index) in parsedsightDetail.subject.split(',')" v-bind:key="index">
                    <el-tag size="small" style="margin-right: 10px;">{{ item }}</el-tag>
                  </span>
                </p><br />
                <div style="margin-top: 50px;" class="button-shared">
                  <el-button style="margin-right: 140px;" type="primary" icon="el-icon-shopping-cart-2">收藏景点</el-button>
                  <i class="el-icon-share"></i><span style="color: #999;" > 分享：</span>
                  <i class="iconfont" style="font-size: 35px;">&#xe6df;</i>
                  <i class="iconfont" style="font-size: 35px;">&#xe6e0;</i>
                  <i class="iconfont" style="font-size: 35px;">&#xe6de;</i>
                  <i class="iconfont" style="font-size: 35px;">&#xe6dc;</i>
                </div>
              </el-row>
            </el-col>
          </el-row>
        </el-card>
        <el-divider></el-divider>
        <el-card>
          <el-tabs v-model="activeName" @tab-click="handleClick">
            <el-tab-pane label="景点介绍" name="first">
              <span slot="label"><i class="el-icon-document"></i> 景点介绍</span>
              <p style="line-height: 30px;padding: 20px;">{{ parsedsightDetail.introduction }}</p>
            </el-tab-pane>
            <el-tab-pane label="用户评论" name="second">
              <span slot="label"><i class="el-icon-s-comment"></i> 用户评论</span>
            </el-tab-pane>
          </el-tabs>
        </el-card>
        <el-divider></el-divider>
        <h2>相似景点</h2>
        <el-row>
          <el-col :span="4" v-for="(sight, index1) in parsedSimilarList" :key="index1">
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
      sightDetail: {},
      parsedsightDetail: {},
      activeName: 'first',
      // 获取相似景点列表的参数对象
      similarQueryInfo: {
        query: '',
        // 当前的页数
        pageNum: 20,
        // 当前每页显示多少条数据
        pageSize: 5
      },
      similarList: [],
      parsedSimilarList: []
    }
  },
  created () {
    this.getSightDetail(2744)
    this.getSimilarSightList()
  },
  methods: {
    async getSightDetail (id) {
      const { data: res } = await this.$http.get('sights/' + id)

      if (res.meta.status !== 200) {
        return this.$message.error('获取景点信息失败！')
      }

      this.sightDetail = res.data
      this.parsedsightDetail = this.sightDetail
      this.parsedsightDetail.imageUrl = JSON.parse(this.parsedsightDetail.imageUrl)
      console.log(this.parsedsightDetail.imageUrl)
    },
    async getSimilarSightList () {
      const { data: res } = await this.$http.get('sights', {
        params: this.similarQueryInfo
      })
      if (res.meta.status !== 200) {
        return this.$message.error('获取景点列表失败！')
      }
      this.similarList = res.data.sights
      this.parsedSimilarList = this.similarList
      console.log('highrated' + res)
      for (let i = 0; i < this.similarList.length; i++) {
        this.parsedSimilarList[i].imageUrl = JSON.parse(this.parsedSimilarList[i].imageUrl)
      }
    }
  }
}
</script>

<style lang="less">
  .sight-container {
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
    z-index: 5;
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

  .button-shared {
    position: relative;
    text-align: right;
    bottom: 40px;
    right: 20px;
  }

  .iconfont {
    margin-top: 20px;
  }

  .el-footer {
    background-color: #fff;
    color: #333;
    text-align: center;
  }

  .sight-scroll {
    height: 100%;
  }
</style>
