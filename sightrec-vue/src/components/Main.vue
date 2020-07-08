<template>
  <div class="mainPage">
    <h2>最受欢迎</h2>
    <el-row>
      <el-col :span="4"
              v-for="(sight, index1) in parsedPopularList"
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
                 icon="el-icon-refresh"
                 circle
                 @click="getPopularSightList()"></el-button>
    </el-row>
    <el-divider></el-divider>
    <h2>好评最多</h2>
    <el-row>
      <el-col :span="4"
              v-for="(sight, index1) in parsedHighratedList"
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
                 icon="el-icon-refresh"
                 circle
                 @click="getHighratedSightList()"></el-button>
    </el-row>
    <el-divider></el-divider>
    <h2>为你推荐</h2>
    <el-row>
      <el-col :span="4"
              v-for="(sight, index1) in parsedrecommendedList"
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
                 icon="el-icon-refresh"
                 circle
                 @click="getRecommendedSightList()"></el-button>
    </el-row>
  </div>
</template>

<script>
export default {
  data () {
    return {
      // 获取最受欢迎列表的参数对象
      popularQueryInfo: {
        query: '',
        // 当前的页数
        pageNum: 4,
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
      const { data: res } = await this.$http.get('sights/popular', {
        params: this.popularQueryInfo
      })
      if (res.meta.status !== 200) {
        return this.$message.error('获取景点列表失败！')
      }
      this.popularList = res.data.sights
      this.parsedPopularList = this.popularList
      for (let i = 0; i < this.popularList.length; i++) {
        this.parsedPopularList[i].imageUrl = JSON.parse(this.parsedPopularList[i].imageUrl)
      }
    },
    async getHighratedSightList () {
      const { data: res } = await this.$http.get('sights/highrated', {
        params: this.highratedQueryInfo
      })
      if (res.meta.status !== 200) {
        return this.$message.error('获取景点列表失败！')
      }
      this.highratedList = res.data.sights
      this.parsedHighratedList = this.highratedList
      for (let i = 0; i < this.highratedList.length; i++) {
        this.parsedHighratedList[i].imageUrl = JSON.parse(this.parsedHighratedList[i].imageUrl)
      }
    },
    async getRecommendedSightList () {
      const { data: res } = await this.$http.get('sights/popular', {
        params: this.recommendedQueryInfo
      })
      if (res.meta.status !== 200) {
        return this.$message.error('获取景点列表失败！')
      }
      this.recommendedList = res.data.sights
      this.parsedrecommendedList = this.recommendedList
      for (let i = 0; i < this.recommendedList.length; i++) {
        this.parsedrecommendedList[i].imageUrl = JSON.parse(this.parsedrecommendedList[i].imageUrl)
      }
    },
    handleCommand (command) {
      if (command === 'logout') {
        window.sessionStorage.clear()
        this.$router.push('/login')
      }
    },
    jumpSightDetail (id) {
      this.$router.push({
        path: `/sight/${id}`
      })
    }
  }
}
</script>

<style lang="less">
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
</style>
