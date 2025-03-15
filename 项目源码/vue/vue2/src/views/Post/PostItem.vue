

<template>

  <div style="margin-top: 15px">
    <el-card class="me-area"
             :body-style="{ padding: '16px' }">
      <div class="me-article-header">
        <a @click="view(id)"
           class="me-article-title">{{title}}</a>
        <span class="me-pull-right me-article-count">
            <i class="el-icon-star-off"></i>&nbsp;{{favorites}}
          </span>
        <span class="me-pull-right me-article-count">
            <i class="el-icon-chat-dot-round"></i>&nbsp;{{comments}}
          </span>
        <span class="me-pull-right me-article-count">
            <img style="height:13px;width:13px" src="../../assets/good.png"></img>&nbsp;{{goods}}
          </span>

      </div>

      <div class="me-artile-description">
        {{description}}
      </div>
      <div class="preimg" v-if="img">
        <el-image
            style="object-fit: cover"
            :src="'http://localhost:8080/api/files/' + img"
            :preview-src-list="['http://localhost:8080/api/files/' + img]">
        </el-image>
      </div>
      <div class="me-article-footer">
          <span class="me-article-author" @click="personal(userid)">
            <i class="me-icon-author"></i>&nbsp;{{username}}
            <el-image :src="'http://localhost:8080/api/files/' + avatar" class="me-article-author-avatar"></el-image>
          </span>
        <div class="me-postCategory">
          分类：{{postCategoryName?postCategoryName:'未分类'}}
        </div>
        <div class="me-pull-right me-article-time">
          <span class="me-pull-right me-article-count">
            <i class="el-icon-time"></i>&nbsp;{{time}}
          </span>
        </div>
      </div>
    </el-card>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "PostItem",
  props:{
    id: Number,
    title: String,
    favorites: Number,
    comments: Number,
    goods: Number,
    description: String,
    img: String,
    userid: Number,
    time: String,
    postCategoryName: String,
  },
  data() {
    return {
      username: '',//用户的用户名,
      avatar:''
    }
  },
  created() {
    this.loaduser();
  },
  mounted() {
    this.loaduser();
  },
  methods:{
    loaduser(){
      request.get(`/admin/search/${this.userid}`).then(res => {
        if(res.code === "0"){
          this.username = res.data.username;
          this.avatar = res.data.avatar;
        }
        else{
          this.$message({
            type: 'error',
            message: res.msg
          });
        }
      })
    },
    personal(id){
      this.$router.push(`/user/${id}`)
    },
    view(id){
      this.$router.push(`/view/${id}`)
    },
  }
}
</script>
<style scoped>
.me-article-header {
  /*padding: 10px 18px;*/
  padding-bottom: 10px;
}

.me-article-title {
  font-weight: 600;
}

.me-article-icon {
  padding: 3px 8px;
}

.me-article-count {
  color: #a6a6a6;
  padding-left: 20px;
  font-size: 13px;
}

.me-pull-right {
  float: right;
}

.me-artile-description {
  font-size: 13px;
  line-height: 24px;
  margin-bottom: 10px;
}
.me-article-author {
  color: #a6a6a6;
  padding-right: 18px;
  font-size: 13px;
  cursor: pointer;
}
.me-postCategory{
  margin-left: 2px;
  font-size: 15px;
  color: #0271ff;
}
.me-article-author-img {
  width: 15px;
  height: 15px;
}
.me-article-author-avatar {
  object-fit: cover;
  width: 30px;
  height: 30px;
  vertical-align: text-bottom;
  border-radius: 50%;
}
.el-tag {
  margin-left: 6px;
}
.me-article-time {
  margin-top: 20px;
}
.leftimg {
  /* width:11%;
  height:11%; */
  width: 100px;
  height: 90px;
  float: left;
  margin-right: 20px;
}
img {
  width: 100%;
  height: 100%;
}
.preimg {
  width: 700px;
  height: 90px;
  margin-bottom: 10px;
  display: flex;
}
</style>
