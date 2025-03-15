<template>
  <div class="myart1">
    <div>
      <el-input style="width: 200px" v-model="params.username" placeholder="请输入用户名"></el-input>
      <el-input style="width: 200px;margin-left: 10px" v-model="params.title" placeholder="请输入标题"></el-input>
      <el-select style="width: 200px;margin-left: 10px;" v-model="params.role" placeholder="请选择角色">
        <el-option label="企业" value="company"></el-option>
        <el-option label="开发人员" value="user"></el-option>
      </el-select>
      <el-select placeholder="请选择分类" v-model="params.postCategoryId" style="margin-left: 5px">
        <el-option
            v-for="category in categorys"
            :key="category.id"
            :label="category.name"
            :value="category.id"
        ></el-option>
      </el-select>
      <el-button style="margin-left: 5px" type="primary" @click="getPost()">查询</el-button>
      <el-button type="primary" @click="reset()">清空</el-button>
    </div>
    <template v-if="allData.length > 0">
      <PostItem v-for="a in allData" :key="a.id" v-bind="a" />
    </template>
    <el-empty
        v-else
        :image-size="250"
        description="暂未发表任何需求额"
    ></el-empty>
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
  </div>
</template>

<script>

import request from "@/utils/request";
import PostItem from "@/views/Post/PostItem.vue";
export default {
  components: {
    PostItem,
  },
  name: "AllPostView",
  data(){
    return{
      params:{
        username:'',
        title: '',
        role:'',
        PostCategoryId:'',
        pageNum: 1,
        pageSize: 5,
      },
      total: '',
      allData: [],
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
  methods: {
    getPost(){
      request.get(`/post`,{params:this.params}).then(res => {
        if(res.code === "0"){
          this.allData = res.data.list;
          this.total = res.data.total;
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
    reset(){
      this.params = {
        username:'',
        title: '',
        role:'',
        pageNum: 1,
        pageSize: 5,
      }
      this.getPost();
    },
    handleSizeChange(pageSize){
      this.params.pageSize = pageSize;
      this.getPost();
    },
    handleCurrentChange(currentPage){
      this.params.pageNum = currentPage;
      this.getPost();
    },
  },
}
</script>
<style scoped>
.myart1{
  line-height: 30px;
}
</style>
