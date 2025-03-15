<template xmlns="http://www.w3.org/1999/html">
  <div class="fan_box">
    <div class="fan" v-for="(item, index) in allData">
      <div class="fan_left">
        <img class="fan_img" :src="'http://localhost:8080/api/files/' + item.avatar" />
      </div>
      <div class="fan_info">
        <div class="fan_info_top">
              <span
                  style="color: #666; max-width: 180px"
                  @click="personal(item.id)"
              >{{ item.username }}</span
              >
        </div>
        <div class="fan_info_bottom">
          <span @click="personal(item.id)">{{ item.specialty }}</span>
        </div>
      </div>
      <div class="fan_botton">
        <el-button
            @click="follow(item)"
            type="primary"
            size="small"
            round
            icon="el-icon-check"
            v-if="Number($route.params.id) === user.id"
            v-text="Myfollow.indexOf(item.id) > -1 ? '已关注' : '关注'"
        ></el-button>
      </div>
    </div>
    <div>
      <el-empty
          v-if="allData.length == 0"
          :image-size="250"
          description="这里什么都没有哟"
      ></el-empty>
    </div>
    <div style="margin-top: 15px">
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="params.pageNum"
          :page-sizes="[3, 6, 9, 12]"
          :page-size="params.pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
      >
      </el-pagination>
    </div>>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "MyFollowView",
  data(){
    return{
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
      params:{
        idlist:[],
        pageNum: 1,
        pageSize: 3,
      },
      total: 0,
      allData: [],
      isfollow: false,
      Myfollow:[],
      FollowForm:{
        userid: "",
        followid: "",
      },
      _follow:{

      },
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
    load() {
      request.get(`/admin/search/${this.$route.params.id}`).then(res => {
        if (res.code === "0") {
          this.admin = res.data;
          this.getMyfollow();
        } else {
          this.$message({
            type: 'error',
            message: res.msg
          });
        }
      })
    },
    getMyfollow() {
      request.get(`/follow/myfollow/${this.admin.id}`).then(res => {
        if (res.code === "0") {
          this.Myfollow = res.data;
          this.params.idlist = res.data;
          this.getMyfollowInfo();
        } else {
          this.$message({
            type: 'error',
            message: res.msg
          });
        }
      })
    },
    getMyfollowInfo(){
      request.post("/admin/search/idlist",this.params).then(res =>{
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
    personal(id) {
      this.$router.push({path:`/user/info/${id}`});
    },
    follow(followdata) {
      this.FollowForm.userid = this.admin.id;
      this.FollowForm.followid = followdata.id;
      this.isfollow = this.Myfollow.includes(followdata.id);
      console.log(followdata.myfan);
      const handleResponse = (res) => {
        if (res.code === "0") {
          this.$message({
            type: 'success',
            message: this.isfollow ? '取消关注成功' : '关注成功'
          });
          if (this.isfollow) {
            this.admin.myfollow -= 1;
            this.$set(followdata, 'myfan', followdata.myfan - 1);
          } else {
            this.admin.myfollow += 1;
            this.$set(followdata, 'myfan', followdata.myfan + 1);
          }
          this._follow = followdata;
          this.updateUser();
          this.load();
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
    },
    updateUser(){
      request.post("/admin",this.admin).then(res => {
        if(res.code === "0") {
        }
        else{
          this.$message({
            type: 'error',
            message: res.msg
          });
        }
      })
      request.post("/admin",this._follow).then(res => {
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
    handleSizeChange(pageSize){
      this.params.pageSize = pageSize;
      this.getMyfans();
    },
    handleCurrentChange(currentPage){
      this.params.pageNum = currentPage;
      this.getMyfans();
    },
  }
}
</script>
<style scoped>
.fan_box :hover {
  border-width: 1px;
  border-color: deepskyblue;
}
.fan {
  padding: 15px 40px 15px 30px;
  height: 50px;
  display: flex;
  align-items: center;
  border: 1px solid #ebebeb;
}
.fan :hover {
  border-width: 1px;
  border-color: deepskyblue;
}
.fan_left {
  width: 60px;
  height: 60px;
}
.fan_img {
  object-fit: cover;
  margin-top: 5px;
  width: 50px;
  height: 50px;
  border-radius: 50%;
  border: 1px solid #ebebeb;
  vertical-align: top;
}
.fan_info {
  display: inline-block;
  margin-left: 20px;
  -webkit-box-flex: 1;
  -ms-flex-positive: 1;
  flex-grow: 1;
  overflow: hidden;
}
.fan_info_top {
  display: inline-block;
  font-size: 10;
  line-height: 14px;
  vertical-align: top;
  cursor: pointer;
}
.fan_info_top :hover {
  color: deepskyblue;
}
.fan_info_bottom {
  line-height: 14px;
  color: #999;
  margin-top: 5px;
  cursor: pointer;
}
.fan_info_bottom :hover {
  color: deepskyblue;
}
</style>