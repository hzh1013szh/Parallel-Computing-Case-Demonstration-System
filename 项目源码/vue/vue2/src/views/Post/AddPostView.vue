
<template>
  <div>
    <el-card>
      <div slot="header">
        <span>发布帖子</span>
      </div>
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
          <el-button type="primary" @click="staging()">暂存</el-button>
          <el-button type="primary" @click="submit()">立即上传</el-button>
        </div>
      </el-form>
    </el-card>
  </div>
</template>
<script>
import request from "@/utils/request";
import E from 'wangeditor'
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
  name: "AddPostView",
  data() {
    return {
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
      form: {
        title: "",
        content: "",
        status: "",
        userid: "",
        img:'',
        role:'',
        username:'',
        description:'',
        postCategoryId:'',
      },
      categorys: [],
    };
  },
  created() {
    this.getCategory();
    this.getStaging();
  },
  mounted() {
    this.getStaging();
    this.$nextTick(() => {
      initEditor(""); // 确保 DOM 渲染后初始化
    });
  },
  methods: {
    getCategory() {
      request.get("/post/category").then((res) => {
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
    getStaging() {
      request.get("post/staging", { params: { userid: this.user.id } }).then((res) => {
        if (res.code === "0") {
          this.form = res.data;
          this.$nextTick(() => {
            initEditor(this.form.content || "");
          });
        } else {
          this.$message({
            type: "error",
            message: res.msg,
          });
        }
      });
    },
    staging(){
      this.form.content = editor.txt.html();
      this.form.status = "0";
      this.form.userid = this.user.id;
      this.form.role = this.user.role;
      this.form.username = this.user.username;
      request.post("/post",this.form).then(res => {
        if(res.code === "0"){
          this.$message({
            type: 'success',
            message: '暂存成功'
          });
          this.getStaging();
        }
        else{
          this.$message({
            type: 'error',
            message: res.msg
          });
        }
      })
    },
    submit(){
      this.form.content = editor.txt.html();
      this.form.status = "1";
      this.form.userid = this.user.id;
      this.form.role = this.user.role;
      this.form.username = this.user.username;
      request.post("/post",this.form).then(res => {
        if(res.code === "0"){
          this.$message({
            type: 'success',
            message: '发布成功'
          });
        }
        else{
          this.$message({
            type: 'error',
            message: res.msg
          });
        }
      })
    },
    HandlerSuccess(res){
      this.form.img = res.data;
    }
  }
}
</script>
<style scoped>
.content{
  height: 700px;
}
</style>
