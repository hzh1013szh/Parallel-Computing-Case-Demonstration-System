
<template>
  <div class="me-view-card">
    <el-card>
      <div class="me-view-head-title">
        <h1 class="me-view-title">{{post.title}}</h1>
        <div style="flex:1">
          <el-button
              v-if="this.post.userid === this.user.id"
              @click="editArticle()"

              size="small"
              type="info"
              round
              icon="el-icon-edit">编辑</el-button>
          <el-popconfirm
              title="这篇文章确定删除吗？"
              v-if="this.post.userid === this.user.id"
              @confirm="removeArticle()">
            <el-button slot="reference"
                       size="small"
                       type="danger"
                       round
                       icon="el-icon-delete"
            >删除</el-button>
          </el-popconfirm>
        </div>
        <div class="me-view-author">
          <a class="">
            <el-image class="me-view-picture" :src="'http://localhost:8080/api/files/' + author.avatar" @click="toUser()"></el-image>
          </a>
          <div class="me-view-info">
            <span>{{post.author}}</span>
            <div class="me-view-meta">
              <span>{{post.time}}</span>
              <span>点赞数   {{post.goods}}</span>
              <span>评论   {{post.comments}}</span>
              <span>收藏数   {{post.favorites}}</span>
            </div>
          </div>
          <span v-if="this.post.userid !== this.user.id">
                <el-button v-if="!isfollow" @click="follow" type="primary" plain round class="follow" >关注  <i class="el-icon-plus"></i></el-button>
                <el-button v-if="isfollow" @click="follow" type="success" plain round class="follow">已关注  <i class="el-icon-check"></i></el-button>
          </span>
        </div>
      </div>
      <div>
        <span>分类：{{post.postCategoryName}}</span>
      </div>
    </el-card>
    <el-card>
      <span>描述:</span>
      <div>{{post.description}}</div>
    </el-card>
    <el-card>
      <div class="me-view-content">
        <div v-html="post.content"></div>
      </div>
    </el-card>
    <div class="me-view-end">
      <el-alert
          title="文章End..."
          type="success"
          center
          :closable="false">
      </el-alert>
    </div>
    <div style="display:flex;justify-content: space-between;">
      <div class="me-view-good">
        点赞<img v-if="!isgood" style="width:30px;height:30px;cursor: pointer;" src="@/assets/good.png" @click="good">
        <img v-if="isgood" style="width:30px;height:30px;cursor: pointer;" src="@/assets/isgood.png" @click="good">
        <span>{{post.goods}}</span>
      </div>
      <div class="me-view-collect">
        收藏<i :class="isFavorites?'el-icon-star-on':'el-icon-star-off'" style="font-size:30px;cursor: pointer;" @click="favorites"></i>
        <span>{{post.favorites}}</span>
      </div>
    </div>
    <div class="me-view-comment">
      <div class="me-view-comment-write">
        <el-row :gutter="20">
          <el-col :span="2">
            <a class="">
              <img class="me-view-picture" :src="'http://localhost:8080/api/files/' + user.avatar"></img>
            </a>
          </el-col>
          <el-col :span="22">
            <el-input
                type="textarea"
                :autosize="{ minRows: 2}"
                placeholder="你的评论..."
                class="me-view-comment-text"
                v-model="comment.content"
                resize="none">
            </el-input>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="2" :offset="22">
            <el-button type="text" @click="publishComment()">评论</el-button>
          </el-col>
        </el-row>
      </div>
      <div class="me-view-comment-title">
        <span>{{post.comments}} 条评论</span>
      </div>
      <CommentItem
          v-for="(c,index) in comments"
          :comment="c"
          :postid="post.id"
          @IncreasePostComment="IncreasePostComment"
          @getPost="getPost"
          :key="c.id">
      </CommentItem>
    </div>
    <div style="margin-top: 15px">
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="params.pageNum"
          :page-sizes="[5, 10, 15, 20]"
          :page-size="params.pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
      </el-pagination>
    </div>
    <div>
      <el-dialog title="请输入信息" :visible.sync="dialogFormVisible" width="80%">
        <el-form>
          <el-form ref="form" :model="form" label-width="80px" id="form">
            <el-form-item label="标题">
              <el-input v-model="form.title" placeholder="请输入标题"></el-input>
            </el-form-item>
            <el-form-item label="封面">
              <el-upload action="http://localhost:8080/api/files/upload" :on-success="HandlerSuccess">
                <el-button size="small" type="primary">点击上传</el-button>
              </el-upload>
            </el-form-item>
            <el-form-item label="描述">
              <el-input v-model="form.description" placeholder="请输入描述"></el-input>
            </el-form-item>
            <el-form-item label="内容">
              <div id="editor"></div>
            </el-form-item>
            <el-form-item label="分类">
              <el-select placeholder="请选择分类" v-model="form.postCategoryId">
                <el-option
                    v-for="category in categorys"
                    :key="category.id"
                    :label="category.name"
                    :value="category.id"
                ></el-option>
              </el-select>
            </el-form-item>
            <div style="margin-left: 80px">
              <el-button type="primary" @click="submit()">立即上传</el-button>
            </div>
          </el-form>
        </el-form>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";
import E from 'wangeditor'
import CommentItem from "@/views/Comment/CommentItem.vue";
let editor
function initEditor(content){ setTimeout(() => {
  if (editor) {
    editor.destroy(); // 销毁旧实例
    editor = null;
  }
  if(!editor){
    editor = new E('#editor')
    editor.config.placeholder = "请输入内容"
    editor.config.uploadFileName = "file"
    editor.config.uploadImgServer = "http://localhost:8080/api/files/wang/upload"
    editor.create();
  }
  editor.txt.html(content)
})
}
export default {
  name: "BolgView",
  components: {
    CommentItem,
  },
  data(){
    return {
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
      userdata:{

      },
      dialogFormVisible: false,
      isfollow: false,
      isgood: false,
      isFavorites: false,
      Myfans: [],
      Mygoods: [],
      Myfavorites: [],
      FollowForm: {
        userid:'',
        followid:'',
      },
      GoodForm: {
        userid:'',
        goodid:'',
      },
      FavoriteForm:{
        userid:'',
        favoriteid:'',
      },
      post:{

      },
      author:{
        id: '',
        avatar:'',
      },
      form:{

      },
      comment: {

      },
      comments: [],
      params:{
        pageNum: 1,
        pageSize: 5,
      },
      total: 0,
      categorys: [],
    }
  },
  created() {
    this.getCategories();
    this.getPost();
  },
  mounted() {
    this.getCategories();
    this.getPost();
  },
  methods:{
    getPost(){
      request.get(`/post/${this.$route.params.id}`).then(res => {
        if(res.code === "0"){
          this.post = res.data;
          console.log(res.data);
          this.getAuthor();
          this.getUserData();
          this.getMyRootComments();
        }
        else{
          this.$message({
            type: 'error',
            message: res.msg
          });
        }
      })
    },
    getCategories() {
      request.get("/post/category").then(res => {
        if (res.code === "0"){
          this.categorys = res.data;
        }else {
          this.$message({
            type: "error",
            message: res.msg,
          });
        }
      })
    },
    getAuthor(){
      request.get(`/admin/search/${this.post.userid}`).then(res => {
        if(res.code === "0"){
          this.author = res.data;
          this.getMyfans();
          this.getMygoods();
          this.getMyfavorites();
        }
        else{
          this.$message({
            type: 'error',
            message: res.msg
          });
        }
      })
    },
    getUserData(){
      request.get(`/admin/search/${this.user.id}`).then(res => {
        if(res.code === "0"){
          this.userdata = res.data;
        }else{
          this.$message({
            type: 'error',
            message: res.msg
          });
        }
      })
    },
    getMyfans(){
      request.get(`/follow/myfan/${this.author.id}`).then(res => {
        if(res.code === "0"){
          this.Myfans = res.data;
          if(this.Myfans.indexOf(this.user.id) > -1){
            this.isfollow = true;
          }
          else {
            this.isfollow = false;
          }
        }else{
          this.$message({
            type: 'error',
            message: res.msg
          });
        }
      })
    },
    getMygoods(){
      request.get(`/good/${this.post.id}`).then(res => {
        if(res.code === "0"){
          this.Mygoods = res.data;
          if(this.Mygoods.indexOf(this.user.id) > -1){
            this.isgood = true;
          }
          else {
            this.isgood = false;
          }
        }else{
          this.$message({
            type: 'error',
            message: res.msg
          });
        }
      })
    },
    getMyfavorites(){
      request.get(`/favorite/user/${this.post.id}`).then(res => {
        if(res.code === "0"){
          this.Myfavorites = res.data;
          if(this.Myfavorites.indexOf(this.user.id) > -1){
            this.isFavorites = true;
          }
          else {
            this.isFavorites = false;
          }
        }else{
          this.$message({
            type: 'error',
            message: res.msg
          });
        }
      })
    },
    getMyRootComments(){
      request.get(`/comment/search/${this.post.id}`,{params:this.params}).then(res => {
        if(res.code === "0"){
          this.comments = res.data.list;
          this.total = res.data.total;
          console.log(this.comments);
        }
        else{
          this.$message({
            type: 'error',
            message: res.msg
          });
        }
      })
    },
    publishComment(){
      if(!this.comment.content){
        return;
      }
      this.comment.userid = this.user.id;
      this.comment.postid = this.post.id;
      request.post("/comment",this.comment).then(res => {
        if(res.code === "0"){
          this.$message({
            type: 'success',
            message: '评论成功'
          });
          this.comment.content = '';
          this.IncreasePostComment();
        }
        else{
          this.$message({
            type: 'error',
            message: res.msg
          });
        }
      })
    },
    follow() {
      this.FollowForm.userid = this.user.id;
      this.FollowForm.followid = this.author.id;
      this.isfollow = this.Myfans.includes(this.user.id); // 使用 includes 方法更简洁


      const handleResponse = (res) => {
        if (res.code === "0") {
          this.$message({
            type: 'success',
            message: this.isfollow ? '取消关注成功' : '关注成功'
          });
          if (this.isfollow) {
            this.userdata.myfollow -= 1;
            this.author.myfan -= 1;
          } else {
            this.userdata.myfollow += 1;
            this.author.myfan += 1;
          }
          this.updateUser();
        } else {
          this.$message({
            type: 'error',
            message: res.msg
          });
        }
      };

      const handleError = (error) => {
        this.$message({
          type: 'error',
          message: '请求失败，请稍后再试'
        });
        console.error(error);
      };

      if (!this.isfollow) {
        request.post("/follow", this.FollowForm)
            .then(handleResponse)
            .catch(handleError);
      } else {
        request.delete("/follow", { data: this.FollowForm })
            .then(handleResponse)
            .catch(handleError);
      }
      this.getPost();
    },
    good(){
      this.GoodForm.userid = this.user.id;
      this.GoodForm.goodid = this.post.id;
      this.isgood = this.Mygoods.includes(this.user.id);
      const handleResponse = (res) => {
        if (res.code === "0") {
          this.$message({
            type: 'success',
            message: this.isgood ? '取消点赞成功' : '点赞成功'
          });
          if (this.isgood) {
            this.post.goods -= 1;
          } else {
            this.post.goods += 1;
          }
          this.updatePost();
        }
      };
      const handleError = (error) => {
        this.$message({
          type: 'error',
          message: '请求失败，请稍后再试'
        });
        console.error(error);
      };
      if (!this.isgood) {
        request.post("/good", this.GoodForm)
            .then(handleResponse)
            .catch(handleError);
      } else {
        request.delete("/good", { data: this.GoodForm })
            .then(handleResponse)
            .catch(handleError);
      }
      this.getPost();
    },
    favorites(){
      this.FavoriteForm.userid = this.user.id;
      this.FavoriteForm.favoriteid = this.post.id;
      this.isFavorites = this.Myfavorites.includes(this.user.id);
      const handleResponse = (res) => {
        if (res.code === "0") {
          this.$message({
            type: 'success',
            message: this.isFavorites ? '取消收藏成功' : '收藏成功'
          });
          if (this.isFavorites) {
            this.post.favorites -= 1;
          } else {
            this.post.favorites += 1;
          }
          this.updatePost();
        }
      };
      const handleError = (error) => {
        this.$message({
          type: 'error',
          message: '请求失败，请稍后再试'
        });
        console.error(error);
      };
      if (!this.isFavorites) {
        request.post("/favorite", this.FavoriteForm)
            .then(handleResponse)
            .catch(handleError);
      } else {
        request.delete("/favorite", { data: this.FavoriteForm })
            .then(handleResponse)
            .catch(handleError);
      }
      this.getPost();
    },
    updateUser(){
      request.post("/admin",this.userdata).then(res => {
        if(res.code === "0") {
        }
        else{
          this.$message({
            type: 'error',
            message: res.msg
          });
        }
      })
      request.post("/admin",this.author).then(res => {
        if(res.code === "0") {
        }
        else{
          this.$message({
            type: 'error',
            message: res.msg
          });
        }
      })
    },
    updatePost(){
      request.post("/post",this.post).then(res => {
        if(res.code === "0"){
          this.getPost();
        }
        else{
          this.$message({
            type: 'error',
            message: res.msg
          });
        }
      })
    },
    editArticle(){
      this.form = this.post;
      initEditor(this.form.content);
      this.dialogFormVisible = true;
    },
    submit(){
      this.form.content = editor.txt.html();
      request.post("/post",this.form).then(res => {
        if(res.code === "0"){
          this.$message({
            type: 'success',
            message: '修改成功'
          });
          this.dialogFormVisible = false;
          this.getPost();
        }
        else{
          this.$message({
            type: 'error',
            message: res.msg
          });
        }
      })
    },
    toUser(){
      this.$router.push(`/user/${this.author.id}`);
    },
    removeArticle(){
      request.delete(`/post/${this.post.id}`).then(res => {
        if(res.code === "0"){
          this.$message({
            type: 'success',
            message: '删除成功'
          });
          this.$router.push('/allpost');
        }
        else{
          this.$message({
            type: 'error',
            message: res.msg
          });
        }
      })
    },
    IncreasePostComment(){
      this.post.comments += 1;
      this.updatePost();
    },
    handleSizeChange(pageSize){
      this.params.pageSize = pageSize;
      this.getPost();
    },
    handleCurrentChange(currentPage){
      this.params.pageNum = currentPage;
      this.getPost();
    },
    HandlerSuccess(res){
      this.form.img = res.data;
    }
  }
}
</script>
<style scoped>
.me-view-body {
  /* margin-top: 100px; */
  padding: 30px;
}

.me-view-container {
  width: 1400px;
}

.el-main {
  position: relative;
  overflow: hidden;
}
.follow {
  float: right;
}
.me-view-card {
  margin-left: 5px;
}
.me-view-head-title {
  display: flex;
  /* justify-content: space-between; */
}
.me-view-title {
  /* font-size: 34px;
  font-weight: 800;
  line-height: 1.3; */
  font-size: 25px;
  line-height: 35px;
  margin: 0;
  flex: 4;
}
.me-view-author {
  /*margin: 30px 0;*/
  margin-top: 5px;
  vertical-align: middle;
  margin-bottom: 10px;
}

.me-view-picture {
  width: 50px;
  height: 50px;
  border: 1px solid #ddd;
  border-radius: 50%;
  vertical-align: top;
  background-color: #5fb878;
}

.me-view-info {
  display: inline-block;
  vertical-align: top;
  margin-left: 8px;
  font-size: 19px;
}

.me-view-info-img {
  width: 18px;
  height: 18px;
}

.me-view-info-v {
  font-size: 16px;
  color: #00c3ff;
}

.me-view-meta {

  font-size: 14px;
  color: #969696;
}

.me-view-meta > span {
  margin-right: 6px;
}
.me-view-end {
  margin-top: 20px;
}

.me-view-tag {
  margin-top: 20px;
  padding-left: 6px;
  border-left: 4px solid #c5cac3;
}

.me-view-complain {
  display: inline-block;
}

.me-view-complain-button {
  margin-top: -40px;
  width: 350px;
}

.me-view-complain-button > div {
  margin-top: 40px;
  margin-left: 40px;
  width: 100%;
  border-bottom: 2px solid #f0f0f0;
}

.me-view-tag-item {
  margin: 0 4px;
}

.me-view-good {
  margin-top: 20px;
}

.me-view-collect {
  margin-top: 20px;
  margin-left: 680px;
}

.me-view-comment {
  margin-top: 60px;
}

.me-view-comment-title {
  font-weight: 600;
  border-bottom: 1px solid #f0f0f0;
  padding-bottom: 20px;
}

.me-view-comment-write {
  margin-top: 20px;
}

.me-view-comment-text {
  font-size: 16px;
}

.v-show-content {
  padding: 8px 25px 15px 30px !important;
}

.v-note-wrapper .v-note-panel {
  box-shadow: none !important;
}

.v-note-wrapper .v-note-panel .v-note-show .v-show-content,
.v-note-wrapper .v-note-panel .v-note-show .v-show-content-html {
  background: #fff !important;
}
</style>