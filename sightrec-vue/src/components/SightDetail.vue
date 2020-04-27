<template>
  <el-scrollbar class="sight-scroll">
    <el-container class="sight-container">
      <!-- 头部区域 -->
      <el-header>
        <div>
          <el-link class="link-logo" :underline="false" href="../">
            <img src="https://cdn.jsdelivr.net/gh/JingqingLin/ImageHosting/img/592aef2edd2d3.png" width="48px" />
          </el-link>
          <span class="span-logo">SightLens</span>
        </div>
        <el-row :gutter="20">
          <el-col :span="8">
            <el-input placeholder="请输入景点名称">
              <el-button slot="append" icon="el-icon-search"></el-button>
            </el-input>
          </el-col>
        </el-row>
        <el-dropdown trigger="click">
          <el-badge :value="0">
            <el-button class="button-user" icon="el-icon-user" circle></el-button>
          </el-badge>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item icon="el-icon-setting">设置</el-dropdown-item>
            <el-dropdown-item icon="el-icon-delete">退出</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </el-header>
      <el-main>
        <el-card class="box-card">
          <el-row type="flex" :gutter="10" style="margin-left: -25px;">
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
                <div style="float: right;">
                  <!-- @mouseout 鼠标移开后：若用户打分，那肯定执行了 click，则显示用户评分；若未打分，则为 0 颗星 -->
                  <!-- <ul class="rate-stars">
                    <li><span style="font-size:small;position: relative;margin-right: 10px;">打分：</span></li>
                    <li v-for="(i, index) in starList" :key="index" @click="clickStars(index)" @mouseover="starNums=index+1" @mouseout="mouseOutStars(index)">
                      <img class="stars" :src="starNums>index?starLight:starGray" width="50%"/>
                    </li>
                  </ul> -->
                  <el-rate v-model="userRate" @change="changeRate"></el-rate>
                </div>
                <span style="font-size: small;"> {{ parsedsightDetail.level }}</span>
                <p style="font-size: 15px;color: #909399;">{{ parsedsightDetail.description }}</p><br />
                <p>
                  <i class="el-icon-location-outline"></i><span style="color: #909399;"> 位置：</span>
                  <span>{{ parsedsightDetail.location }}</span>
                </p>
                <p>
                  <i class="el-icon-s-data"></i><span style="color: #909399;"> 评分：</span>
                  <span style="font-size: 19px;color: #ff6600;">{{ parsedsightDetail.point }}</span><span> / 5 分</span>
                </p>
                <p>
                  <i class="el-icon-sunny"></i><span style="color: #909399;"> 热度：</span>
                  <span>{{ parsedsightDetail.heat }}</span>
                </p>
                <p>
                  <i class="el-icon-collection-tag"></i><span style="color: #909399;"> 标签：</span>
                  <!-- 防止 split 出现 undefined -->
                  <span v-for="item in (parsedsightDetail.subject || '').split(',')" :key="item">
                    <el-tag size="small" style="margin-right: 10px;">{{ item }}</el-tag>
                  </span>
                </p><br />
                <el-button style="position: relative;margin-right: 225px;" type="primary" icon="el-icon-shopping-cart-2">收藏景点</el-button>
                <div class="button-shared">
                  <div>
                    <i class="el-icon-share"></i><span style="color: #909399;margin-right: 70px;" > 分享：</span>
                  </div>
                  <i class="iconfont" style="color: #7ad238;font-size: 35px;">&#xe6df;</i>
                  <i class="iconfont" style="color: #1bc1fa;font-size: 35px;">&#xe6e0;</i>
                  <i class="iconfont" style="color: #e6152c;font-size: 35px;">&#xe6de;</i>
                  <i class="iconfont" style="color: #00b51d;font-size: 35px;">&#xe6dc;</i>
                </div>
              </el-row>
            </el-col>
          </el-row>
        </el-card>
        <el-divider></el-divider>
        <el-card>
          <el-tabs v-model="activeName">
            <el-tab-pane label="景点介绍" name="first">
              <span style="font-size: medium;" slot="label"><i class="el-icon-document"></i> 景点介绍</span>
              <p style="line-height: 30px;padding: 20px;">{{ parsedsightDetail.introduction }}</p>
            </el-tab-pane>
            <el-tab-pane label="用户评论" name="second">
              <span style="font-size: medium;" slot="label"><i class="el-icon-s-comment"></i> 用户评论</span>
              <div v-for="(item, index) in commentList" :key="index">
                <el-card class="card-comment" shadow="never">
                  <p>
                    <el-avatar src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"></el-avatar>
                    <span style="position: relative;margin-left: 10px;bottom: 13px;">匿名游客</span>
                    <span style="font-size: 12px;float: right;color: #909399;">{{ item.createdDate }}</span>
                  </p>
                  <p style="font-size: 14px;">{{ item.content }}</p>
                </el-card>
              </div>
              <el-card class="card-comment" shadow="never">
                <div style="display:flex">
                  <el-avatar style="margin-right: 10px;" src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"></el-avatar>
                  <el-input
                  type="textarea"
                  placeholder="你的想法 ~"
                  :autosize="{ minRows: 5}"
                  v-model="userComment"
                  maxlength="1200"
                  show-word-limit
                  >
                  </el-input>
                </div>
                <div align="right">
                  <el-button type="primary" icon="el-icon-check" style="position: relative;margin-top: 10px;">写好了</el-button>
                </div>
              </el-card>
              <!-- 分页区域 -->
              <el-pagination :hide-on-single-page="true" @size-change="handleSizeChange" @current-change="handleCurrentChange"
              :current-page="commentQueryInfo.pageNum" :page-sizes="[5, 10, 20]" :page-size="commentQueryInfo.pageSize"
              layout="total, sizes, prev, pager, next, jumper" :total="commentNum">
              </el-pagination>
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
      id: 2709,
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
      commentQueryInfo: {
        sightId: 2709,
        pageNum: 1,
        pageSize: 5
      },
      commentNum: 0,
      commentList: [],
      userList: [],
      userComment: '',
      similarList: [],
      parsedSimilarList: [],
      // starList: [0, 1, 2, 3, 4],
      // starNums: 0,
      // isClickedStar: false,
      userRate: 0,
      starGray: require('../assets/images/star_hollow_hover@2x.png'),
      starLight: require('../assets/images/star_onmouseover@2x.png')
    }
  },
  created () {
    this.getSightDetail(this.id)
    this.getSightCommentList()
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
    },
    async getSightCommentList () {
      const { data: res } = await this.$http.get('comments/sight', {
        params: this.commentQueryInfo
      })
      if (res.meta.status !== 200) {
        return this.$message.error('获取评论失败！')
      }
      this.commentList = res.data.comments
      this.commentNum = res.data.commentNum
    },
    async getSimilarSightList () {
      const { data: res } = await this.$http.get('sights', {
        params: this.similarQueryInfo
      })
      if (res.meta.status !== 200) {
        return this.$message.error('获取相似景点失败！')
      }
      this.similarList = res.data.sights
      this.parsedSimilarList = this.similarList
      for (let i = 0; i < this.similarList.length; i++) {
        this.parsedSimilarList[i].imageUrl = JSON.parse(this.parsedSimilarList[i].imageUrl)
      }
    },
    async getCommentUserList () {
      const { data: res } = await this.$http.get('users', {
        params: this.similarQueryInfo
      })
      if (res.meta.status !== 200) {
        return this.$message.error('获取相似景点失败！')
      }
      this.similarList = res.data.sights
      this.parsedSimilarList = this.similarList
      for (let i = 0; i < this.similarList.length; i++) {
        this.parsedSimilarList[i].imageUrl = JSON.parse(this.parsedSimilarList[i].imageUrl)
      }
    },
    // 监听 pageSize 改变的事件
    handleSizeChange (newSize) {
      this.commentQueryInfo.pageSize = newSize
      this.getSightCommentList()
    },
    // 监听 页码值 改变的事件
    handleCurrentChange (newPage) {
      this.commentQueryInfo.pageNum = newPage
      this.getSightCommentList()
    },
    changeRate () {
    }
    // clickStars (i) {
    //   this.starNums = i + 1
    //   this.isClickedStar = true
    //   this.userRate = i + 1
    // },
    // mouseOutStars (i) {
    //   if (this.isClickedStar === false) {
    //     this.starNums = 0
    //   } else {
    //     this.starNums = this.userRate
    //   }
    // }
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

    .el-input {
      margin-left: 30px;
      margin-right: 700px;
    }
  }

  .link-logo {
    display: flex;
    align-items: center;
  }

  .span-logo {
    font-family: 'Miriam Libre';
    margin-left: 10px;
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
    color: #909399;
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
  }

  .card-comment {
    padding-left: 15px;
    padding-right: 15px;
    padding-top: 0;
    padding-bottom: 0;
    margin-bottom: 10px !important;
  }

  .el-footer {
    background-color: #fff;
    color: #333;
    text-align: center;
  }

  .sight-scroll {
    height: 100%;
  }

  /* .rate-stars li{
    float: left;
    margin-right: -0.9rem;
    list-style-type: none;
    cursor: pointer;
  }
  .stars {
    position: relative;
    top: 3px;
  } */
</style>
