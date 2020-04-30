<template>
  <div class="settings">
    <!-- 面包屑导航区域 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: 'user/settings' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>我的收藏</el-breadcrumb-item>
    </el-breadcrumb>

    <el-card>
      <p>
        <i class="el-icon-shopping-cart-2"></i>
        <span> 我的收藏</span>
      </p><br />
      <el-row :gutter="10">
        <el-col :span="5"
                v-for="(sight, index1) in parsedFavoriteSightList"
                :key="index1"
                style="margin-left: 30px">
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
                             @click="removeSightFromFavorite(sight.id)">取消收藏</el-button>
                </div>
              </div>
            </el-card>
          </span>
        </el-col>
      </el-row>
    </el-card>
  </div>
</template>

<script>
export default {
  data () {
    return {
      loginUserName: window.sessionStorage.getItem('name'),
      loginUserId: window.sessionStorage.getItem('id'),
      favoriteSightIdList: [],
      favoriteSightList: [],
      parsedFavoriteSightList: []
    }
  },
  created () {
    this.getUserFavoriteSightId()
  },
  methods: {
    async getUserFavoriteSightId () {
      const { data: res } = await this.$http.get('favorites/userId', {
        params: { userId: this.loginUserId }
      })
      if (res.meta.status !== 200) {
        return this.$message.error(res.meta.msg)
      }
      this.favoriteSightIdList = []
      for (let i = 0; i < res.data.length; i++) {
        this.favoriteSightIdList[i] = res.data[i]
      }
      this.getUserFavoriteSightDetail()
    },
    async getUserFavoriteSightDetail () {
      for (let i = 0; i < this.favoriteSightIdList.length; i++) {
        const { data: res } = await this.$http.get(
          'sights/' + this.favoriteSightIdList[i].sightId)
        if (res.meta.status !== 200) {
          return this.$message.error(res.meta.msg)
        }
        this.favoriteSightList[i] = res.data
      }
      this.parsedFavoriteSightList = []
      this.parsedFavoriteSightList = this.favoriteSightList
      for (let i = 0; i < this.favoriteSightIdList.length; i++) {
        this.parsedFavoriteSightList[i].imageUrl = JSON.parse(this.parsedFavoriteSightList[i].imageUrl)
      }
    },
    async removeSightFromFavorite (id) {
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

      const { data: res } = await this.$http.delete('favorites/' + id + '/' + this.loginUserId)
      if (res.meta.status !== 200) {
        return this.$message.error(res.meta.msg)
      }
      this.$message.success('取消收藏成功！')
      // 取消收藏成功后，把对应的景点从景点列表中去掉
      var tempList = this.parsedFavoriteSightList
      this.parsedFavoriteSightList = []
      var index = 0
      for (let i = 0; i < tempList.length; i++) {
        if (tempList[i].id === id) {
          continue
        }
        this.parsedFavoriteSightList[index++] = tempList[i]
      }
      console.log(this.parsedFavoriteSightList)
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
.col-left > p {
  color: #909399;
  text-align: right;
  line-height: 40px;
}

.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409eff;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 89px;
  height: 89px;
  line-height: 89px !important;
  text-align: center;
}
.avatar {
  width: 89px;
  height: 89px;
  display: block;
}
</style>
