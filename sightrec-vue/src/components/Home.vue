<template>
  <el-scrollbar class="home-scrollbar">
    <el-container class="home-container">
      <!-- 头部区域 -->
      <el-header>
        <div>
          <img src="https://cdn.jsdelivr.net/gh/JingqingLin/ImageHosting/img/592aef2edd2d3.png" width="48px" />
          <span>gogo</span>
        </div>
        <el-row :gutter="20">
          <el-col :span="8">
            <el-input placeholder="请输入内容">
              <el-button slot="append" icon="el-icon-search"></el-button>
            </el-input>
          </el-col>
        </el-row>
      </el-header>
      <el-main>
        <el-row>
          <el-col :span="4" v-for="(sight, index1) in parsedSightList" :key="index1">
            <span v-for="(imageUrl, index2) in sight.imageUrl" :key="index2">
              <el-card v-if="index2 < 1" :body-style="{ padding: '0px' }">
              <img :src="imageUrl" class="image">
              <div style="padding: 14px">
                <span>好吃的汉堡</span>
                <div class="bottom clearfix">
                  <time class="time">hh</time>
                  <el-button type="text" class="button">操作按钮</el-button>
                </div>
              </div>
              </el-card>
            </span>
          </el-col>
        </el-row>
      </el-main>
    </el-container>
  </el-scrollbar>
</template>

<script>
export default {
  data () {
    return {
      // 获取用户列表的参数对象
      queryInfo: {
        query: '',
        // 当前的页数
        pageNum: 1,
        // 当前每页显示多少条数据
        pageSize: 5
      },
      sightList: [],
      parsedSightList: [],
      total: 0
    }
  },
  created () {
    this.getSightList()
  },
  methods: {
    async getSightList () {
      const { data: res } = await this.$http.get('sights', {
        params: this.queryInfo
      })
      if (res.meta.status !== 200) {
        return this.$message.error('获取景点列表失败！')
      }
      this.sightList = res.data.sights
      this.imageUrlToJsonObject()
      this.total = res.data.total
      console.log(res)
    },
    // imageUrl 由 JSON 字符串转为 JSON 对象
    imageUrlToJsonObject () {
      this.parsedSightList = this.sightList
      for (let i = 0; i < this.sightList.length; i++) {
        this.parsedSightList[i].imageUrl = JSON.parse(this.parsedSightList[i].imageUrl)
      }
      console.log(this.parsedSightList)
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

    .el-input {
      margin-left: 150px;
      margin-right: 700px;
    }
  }
  .el-main {
    color: #333;
    margin-left: 125px;
    margin-right: 0px;
    margin-top: 50px;
    .el-row {
      margin-top: 30px;
      .el-col {
        margin-left: 10px;
      }
    }
  }
  .el-footer {
    background-color: #B3C0D1;
    color: #333;
    text-align: center;
    line-height: 60px;
  }
  .time {
    font-size: 13px;
    color: #999;
  }

  .bottom {
    margin-top: 13px;
    line-height: 12px;
  }

  .button {
    padding: 0;
    float: right;
  }

  .image {
    width: 100%;
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
</style>
