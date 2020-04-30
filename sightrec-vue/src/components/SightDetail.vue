<template>
  <div class="sight-detail">
    <el-card class="box-card">
      <el-row type="flex"
              :gutter="10"
              style="margin-left: -25px;">
        <el-col :span="13">
          <el-carousel trigger="click"
                       height="400px">
            <el-carousel-item v-for="item in parsedsightDetail.imageUrl"
                              :key="item">
              <img :src="item"
                   alt=""
                   width="120%" />
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
              <el-rate v-model="userRate"
                       @change="changeRate"></el-rate>
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
              <span v-for="item in (parsedsightDetail.subject || '').split(',')"
                    :key="item">
                <el-tag size="small"
                        style="margin-right: 10px;">{{ item }}</el-tag>
              </span>
            </p><br />
            <el-button v-if="isInFavorite === false"
                       style="position: relative;margin-right: 225px;"
                       type='primary'
                       icon='el-icon-star-off'
                       @click="addSightToFavorite"> 收藏景点 </el-button>
            <el-popover v-if="isInFavorite === true"
                        trigger="hover"
                        placement="top-start"
                        width="120"
                        content="点击”已收藏“按钮以取消收藏">
              <el-button slot="reference"
                         style="position: relative;margin-right: 225px;"
                         type='success'
                         icon='el-icon-check'
                         @click="removeSightFromFavorite"> 已收藏 </el-button>
            </el-popover>
            <div class="button-shared">
              <div>
                <i class="el-icon-share"></i><span style="color: #909399;margin-right: 70px;"> 分享：</span>
              </div>
              <i class="iconfont"
                 style="color: #7ad238;font-size: 35px;">&#xe6df;</i>
              <i class="iconfont"
                 style="color: #1bc1fa;font-size: 35px;">&#xe6e0;</i>
              <i class="iconfont"
                 style="color: #e6152c;font-size: 35px;">&#xe6de;</i>
              <i class="iconfont"
                 style="color: #00b51d;font-size: 35px;">&#xe6dc;</i>
            </div>
          </el-row>
        </el-col>
      </el-row>
    </el-card>
    <el-divider></el-divider>
    <el-card>
      <el-tabs v-model="activeName">
        <el-tab-pane label="景点介绍"
                     name="first">
          <span style="font-size: medium;"
                slot="label"><i class="el-icon-reading"></i> 景点介绍</span>
          <p style="line-height: 30px;padding: 20px;">{{ parsedsightDetail.introduction }}</p>
        </el-tab-pane>
        <el-tab-pane label="用户评论"
                     name="second">
          <span style="font-size: medium;"
                slot="label"><i class="el-icon-chat-line-square"></i> 用户评论</span>
          <div v-for="(item, index) in commentList"
               :key="index">
            <el-card class="card-comment"
                     shadow="never">
              <p>
                <el-avatar :src="userList[index].headUrl"></el-avatar>
                <span style="position: relative;margin-left: 10px;bottom: 13px;">{{ userList[index].name }}</span>
                <span style="font-size: 12px;float: right;color: #909399;">{{ item.createdDate }}</span>
              </p>
              <p style="font-size: 14px;">{{ item.content }}</p>
            </el-card>
          </div>
          <el-card class="card-comment"
                   shadow="never">
            <div style="display:flex">
              <el-avatar style="margin-right: 10px;"
                         src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"></el-avatar>
              <el-input type="textarea"
                        placeholder="你的想法 ~"
                        :autosize="{ minRows: 5}"
                        v-model="myComment"
                        maxlength="1200"
                        show-word-limit>
              </el-input>
            </div>
            <div align="right">
              <el-button type="primary"
                         icon="el-icon-check"
                         style="position: relative;margin-top: 10px;">写好了</el-button>
            </div>
          </el-card>
          <!-- 分页区域 -->
          <el-pagination @size-change="handleSizeChange"
                         @current-change="handleCurrentChange"
                         :current-page="commentQueryInfo.pageNum"
                         :page-sizes="[5, 10, 20]"
                         :page-size="commentQueryInfo.pageSize"
                         layout="total, sizes, prev, pager, next, jumper"
                         :total="commentNum">
          </el-pagination>
        </el-tab-pane>
      </el-tabs>
    </el-card>
    <el-divider></el-divider>
    <h2>相似景点</h2>
    <el-row>
      <el-col :span="4"
              v-for="(sight, index1) in parsedSimilarList"
              :key="index1">
        <span v-for="(imageUrl, index2) in sight.imageUrl"
              :key="index2">
          <!-- 只显示每个景点的第一张图片 -->
          <el-card shadow="hover"
                   v-if="index2 < 1"
                   :body-style="{ padding: '0px' }">
            <img :src="imageUrl"
                 @click="jumpSightDetail(sight.id)"
                 class="card-image">
            <div class="card-description">
              <span>{{sight.name}}</span>
              <div class="card-bottom clearfix">
                <time class="card-time">{{sight.description}}</time>
                <el-button type="text"
                           class="card-button"
                           @click="jumpSightDetail(sight.id)">更多</el-button>
              </div>
            </div>
          </el-card>
        </span>
      </el-col>
      <el-button class="button-next"
                 icon="el-icon-arrow-right"
                 circle></el-button>
    </el-row>
  </div>
</template>

<script>
export default {
  data () {
    return {
      id: this.$route.params.sightId,
      loginUserId: window.sessionStorage.getItem('id'),
      sightDetail: {},
      parsedsightDetail: {},
      activeName: 'first',
      // 获取相似景点列表的参数对象
      similarQueryInfo: {
        query: '',
        // 当前的页数
        pageNum: 1,
        // 当前每页显示多少条数据
        pageSize: 5
      },
      commentQueryInfo: {
        sightId: this.$route.params.sightId,
        pageNum: 1,
        pageSize: 5
      },
      userQueryInfo: {
        id: ''
      },
      commentNum: 0,
      commentList: [],
      userList: [
        { name: '匿名游客', headUrl: 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png' },
        { name: '匿名游客', headUrl: 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png' },
        { name: '匿名游客', headUrl: 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png' },
        { name: '匿名游客', headUrl: 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png' },
        { name: '匿名游客', headUrl: 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png' },
        { name: '匿名游客', headUrl: 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png' },
        { name: '匿名游客', headUrl: 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png' },
        { name: '匿名游客', headUrl: 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png' },
        { name: '匿名游客', headUrl: 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png' },
        { name: '匿名游客', headUrl: 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png' },
        { name: '匿名游客', headUrl: 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png' },
        { name: '匿名游客', headUrl: 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png' },
        { name: '匿名游客', headUrl: 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png' },
        { name: '匿名游客', headUrl: 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png' },
        { name: '匿名游客', headUrl: 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png' },
        { name: '匿名游客', headUrl: 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png' },
        { name: '匿名游客', headUrl: 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png' },
        { name: '匿名游客', headUrl: 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png' },
        { name: '匿名游客', headUrl: 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png' },
        { name: '匿名游客', headUrl: 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png' }
      ],
      myComment: '',
      similarList: [],
      parsedSimilarList: [],
      // starList: [0, 1, 2, 3, 4],
      // starNums: 0,
      // isClickedStar: false,
      userRate: 0,
      starGray: require('../assets/images/star_hollow_hover@2x.png'),
      starLight: require('../assets/images/star_onmouseover@2x.png'),
      isInFavorite: false
    }
  },
  created () {
    this.getSightDetail(this.id)
    this.getSightCommentList()
    this.isSightInFavorite()
  },
  beforeRouteUpdate (to, from, next) {
    this.id = to.params.sightId
    this.commentQueryInfo.sightId = to.params.sightId
    this.getSightDetail(this.id)
    this.getSightCommentList()
    this.isSightInFavorite()
    next()
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
      this.getSimilarSightList()
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
      this.getCommentUserList()
    },
    async getCommentUserList () {
      for (let i = 0; i < this.commentList.length; i++) {
        this.userQueryInfo.id = this.commentList[i].userId
        const { data: res } = await this.$http.get('users/', {
          params: this.userQueryInfo
        })
        if (res.meta.status !== 200) {
          return this.$message.error('获取评论失败！')
        }
        var tempUser = res.data
        this.userList[i] = tempUser
      }
    },
    async getSimilarSightList () {
      this.similarQueryInfo.query = await (this.sightDetail.subject || '').split(',')[0]
      const { data: res } = await this.$http.get('sights/similar', {
        params: this.similarQueryInfo
      })
      if (res.meta.status !== 200) {
        console.log(res)
        return this.$message.error('获取相似景点失败！')
      }
      this.similarList = res.data.sights
      this.parsedSimilarList = this.similarList
      for (let i = 0; i < this.similarList.length; i++) {
        this.parsedSimilarList[i].imageUrl = JSON.parse(this.parsedSimilarList[i].imageUrl)
      }
    },
    async isSightInFavorite () {
      const { data: res } = await this.$http.get('favorites/', {
        params: { sightId: this.id, userId: this.loginUserId }
      })
      if (res.meta.status !== 200) {
        return this.$message.error(res.meta.msg)
      }
      this.isInFavorite = res.data
    },
    async addSightToFavorite () {
      const { data: res } = await this.$http.post('favorites/', {
        sightId: this.id,
        userId: this.loginUserId
      })
      if (res.meta.status !== 200) {
        return this.$message.error(res.meta.msg)
      }
      this.$message.success('收藏成功！')
      this.isInFavorite = true
    },
    async removeSightFromFavorite () {
      // 弹框询问用户是否删除数据
      const confirmResult = await this.$confirm(
        '此操作将取消收藏此景点, 是否继续？',
        '提示',
        {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }
      ).catch(err => err)

      if (confirmResult !== 'confirm') {
        return this.$message.info('取消操作')
      }

      const { data: res } = await this.$http.delete('favorites/' + this.id + '/' + this.loginUserId)
      if (res.meta.status !== 200) {
        return this.$message.error(res.meta.msg)
      }
      this.$message.success('取消收藏！')
      this.isInFavorite = false
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
    },
    jumpSightDetail (id) {
      this.$router.push({
        path: `/sight/${id}`
      })
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
  font-family: "Miriam Libre";
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
  box-shadow: 0 1px 1px 0 rgba(0, 0, 0, 0.1);
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
  cursor: pointer;
}
.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}
.clearfix:after {
  clear: both;
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
