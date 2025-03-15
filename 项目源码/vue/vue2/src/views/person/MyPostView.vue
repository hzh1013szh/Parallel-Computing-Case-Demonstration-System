
<template>
  <div>
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
          :page-sizes="[3, 6, 9, 12]"
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
  name: "MyPostView" ,
  components: {PostItem},
  data(){
    return{
      params:{
        pageNum: 1,
        pageSize: 3,
      },
      total: 0,
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
      allData: [],
      admin: {

      },
    }
  },
  created() {
    this.load();
  },
  mounted() {
    this.load();
  },
  methods: {
    load(){
      request.get(`/admin/search/${this.$route.params.id}`).then(res => {
        if(res.code === "0"){
          this.admin = res.data;
          this.getMyPost();
        }
        else{
          this.$message({
            type: 'error',
            message: res.msg
          });
        }
      })
    },
    getMyPost(){
      request.get(`/post/user/${this.admin.id}`,{params:this.params}).then(res => {
        if(res.code === "0"){
          console.log(res.data);
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
    handleSizeChange(pageSize){
      this.params.pageSize = pageSize;
      this.getMyPost();
    },
    handleCurrentChange(currentPage){
      console.log(currentPage);
      this.params.pageNum = currentPage;
      this.getMyPost();
    },
  }
}
</script>
<style scoped>

</style>