<template>
 <div>
   <div>
     <el-alert
         title="该用户已被封禁"
         type="error"
         effect="dark"
         v-if="admin.status === '-1'">
     </el-alert>
   </div>
   <div>
     <el-alert
         title="该用户还未通过管理员审核"
         type="warning"
         effect="dark"
         v-if="admin.status === '0'">
     </el-alert>
   </div>
    <el-card class="box-car">
      <div class="PersonTop">
          <div class="PersonTop_img">
            <el-image style="width: 100%; height: 100%;object-fit: cover" :src="'http://localhost:8080/api/files/' + admin.avatar" ></el-image>
          </div>
          <div class="PersonTop_text">
            <div class="user_text">
              <div class="user_name">
                <span> {{ admin.username}} </span>
              </div>
                <div class="user_qianming">
                <span> 个人特长：{{ admin.specialty }}</span>
              </div>
              <div class="user_anniu">
                <el-button
                  class="el-icon-edit"
                  v-show="Number(this.$route.params.id) === this.user.id"
                  type="primary"
                  size="medium"
                  plain
                  @click="edit(admin)"
                  >编辑</el-button>
                  <el-button
                    v-show="Number(this.$route.params.id) !== this.user.id"
                    @click="follow()"
                    type="primary"
                    size="medium"
                    icon="el-icon-check"
                    v-text="
                      Myfans.indexOf(this.user.id) > -1
                        ? '已关注'
                        : '关注'
                    "></el-button>
              </div>
            </div>
          </div>
          <div class="user_num">
            <div style="cursor: pointer" @click="Tomyfan()">
              <div class="num_number">{{ admin.myfan }}</div>
              <span class="num_text">粉丝</span>
            </div>
            <div style="cursor: pointer" @click="Tomyfollow()">
              <div class="num_number">{{ admin.myfollow }}</div>
              <span class="num_text">关注</span>
            </div>
          </div>
      </div>
    </el-card>
     <div class="person_body">
        <div class="person_body_left">
          <el-card class="box-card" :body-style="{ padding: '0px' }">
            <div slot="header" class="clearfix">
              <span class="person_body_list" style="border-bottom: none"
                >个人中心</span
              >
            </div>
            <el-menu
              router
              active-text-color="#00c3ff"
              class="el-menu-vertical-demo">
              <el-menu-item
                index="info"
                :route="{ name: 'info', params: $route.params.id }"
              >
                <i class="el-icon-user"></i>
                <span slot="title">个人简介</span>
              </el-menu-item>
              <el-menu-item
                index="post"
                :route="{ name: 'post', params: $route.params.id }"
              >
                <i class="el-icon-edit-outline"></i>
                <span slot="title">发帖</span>
              </el-menu-item>
              <el-menu-item
                index="favorite"
                :route="{ name: 'favorite', params: $route.params.id }"
              >
                <i class="el-icon-document"></i>
                <span slot="title">收藏</span>
              </el-menu-item>
              <el-menu-item
                index="myFan"
                :route="{ name: 'myFan', params: $route.params.id }"
              >
                <i class="el-icon-tableware"></i>
                <span slot="title">粉丝</span>
              </el-menu-item>
              <el-menu-item
                index="myFollow"
                :route="{ name: 'myFollow', params: $route.params.id }"
              >
                <i class="el-icon-circle-plus-outline"></i>
                <span slot="title">关注</span>
              </el-menu-item>
            </el-menu>
          </el-card>
        </div>
       <div class="person_body_right">
        <router-view></router-view>
      </div>
     </div>
     <div>
      <el-dialog title="请输入信息" :visible.sync="dialogFormVisible" width="30%">
        <el-form :model="form">
          <el-form-item label="头像" label-width="15%">
            <el-upload action="http://localhost:8080/api/files/upload" :on-success="HandlerSuccess">
              <el-button size="small" type="primary">点击上传</el-button>
            </el-upload>
          </el-form-item>
        </el-form>
        <el-form :model="form">
          <el-form-item label="用户名" label-width="15%">
            <el-input v-model="form.username" autocomplete="off" style="width: 80%"></el-input>
          </el-form-item>
        </el-form>
        <el-form :model="form">
          <el-form-item label="密码" label-width="15%">
            <el-input v-model="form.password" autocomplete="off" style="width: 80%"></el-input>
          </el-form-item>
        </el-form>
        <el-form :model="form">
          <el-form-item label="姓名" label-width="15%">
            <el-input v-model="form.name" autocomplete="off" style="width: 80%"></el-input>
          </el-form-item>
        </el-form>
        <el-form :model="form">
          <el-form-item label="电话" label-width="15%">
            <el-input v-model="form.phone" autocomplete="off" style="width: 80%"></el-input>
          </el-form-item>
        </el-form>
        <el-form :model="form">
          <el-form-item label="邮箱" label-width="15%">
            <el-input v-model="form.email" autocomplete="off" style="width: 80%"></el-input>
          </el-form-item>
          <el-form-item label="特长" label-width="15%">
            <el-input v-model="form.specialty" placeholder="用关键字描述" autocomplete="off" style="width: 80%" ></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="cancel()">取 消</el-button>
          <el-button type="primary" @click="submit()">确 定</el-button>
        </div>
      </el-dialog>
    </div>
 </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: 'UserView',
  data(){
    return{
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
      userdata:{
        id:'',
        avatar: '',
        myfan:'',
        myfollow:'',
      },
      imgUrl: require('@/assets/img3.png'),
      Myfans: [],
      admin:{
        id:'',
        avatar: '',
        myfan:'',
        myfollow:'',
      },
      dialogFormVisible : false,
      isfollow: false,
      form:{
        username: '',
        name: '',
        phone: '',
        email: '',
        specialty: '',
        avatar: String,
      },
      Followform:{
        userid:'',
        followid:'',
      }
    }
  },
  created() {
    this.load();
  },
  watch: {
    '$route.params.id': function(newId, oldId) {
      if (newId !== oldId) {
        this.load(); // 调用数据加载函数
      }
    }
  },
  methods: {
    load(){
      request.get(`/admin/search/${this.$route.params.id}`).then(res => {
        if(res.code === "0"){
          this.admin = res.data;
          this.getMyfans();
          this.getUserData();
        }else{
          this.$message({
            type: 'error',
            message: res.msg
          });
        }
      })
    },
    getMyfans(){
      request.get(`/follow/myfan/${this.admin.id}`).then(res => {
        if(res.code === "0"){
          this.Myfans = res.data;
        }else{
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
    follow() {
      this.Followform.userid = this.user.id;
      this.Followform.followid = this.admin.id;
      this.isfollow = this.Myfans.includes(this.user.id); // 使用 includes 方法更简洁

      console.log(this.Followform);

      const handleResponse = (res) => {
        if (res.code === "0") {
          this.$message({
            type: 'success',
            message: this.isfollow ? '取消关注成功' : '关注成功'
          });
          if (this.isfollow) {
            this.userdata.myfollow -= 1;
            this.admin.myfan -= 1;
          } else {
            this.userdata.myfollow += 1;
            this.admin.myfan += 1;
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
        request.post("/follow", this.Followform)
            .then(handleResponse)
            .catch(handleError);
      } else {
        request.delete("/follow", { data: this.Followform })
            .then(handleResponse)
            .catch(handleError);
      }
      this.load();
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
    },
    cancel(){
      this.dialogFormVisible = false;
    },
    edit(currow){
      this.dialogFormVisible = true;
      this.form = currow;
    },
    submit(){
      // 管理员用户新增用户，初始化密码为1234
      request.post("/admin",this.form).then(res => {
        if(res.code === "0") {
          this.$message({
            type: 'success',
            message: '操作成功'
          });
          this.dialogFormVisible = false;
          this.load();
        }
        else{
          this.$message({
            type: 'error',
            message: res.msg
          });
        }
      })
    },
    Tomyfan(){

    },
    Tomyfollow(){

    },
    HandlerSuccess(res){
      this.form.avatar = res.data;
    }
  }

}
</script>

<style>
.me-video-player {
  background-color: transparent;
  width: 100%;
  height: 100%;
  object-fit: fill;
  display: block;
  position: fixed;
  left: 0;
  z-index: 0;
  top: 0;
}
.PersonTop {
  width: 1000px;
  height: 140px;
  background-color: white;
  position: absolute;
  left: 50%;
  transform: translateX(-50%);
  display: flex;
  border-radius: 5px;
}
.box-car{
  width: 1000px;
  height: 160px;
  background-color: white;
  margin-top: 30px;
  position: absolute;
  left: 50%;
  transform: translateX(-50%);
  display: flex;
  border-radius: 5px
}
.PersonTop_img {
  object-fit: cover;
  width: 240px;
  height: 120px;
  background-color: #8c939d;
  margin-right: 24px;
  margin-left: 20px;
  overflow: hidden;
  border-radius: 20px;
}

.PersonTop_img img {
  width: 100%;
  height: 100%;
  border-radius: 20px;
}

.PersonTop_text {
  height: 120px;
  width: 880px;
  display: flex;
}

.user_text {
  width: 60%;
  height: 100%;
  line-height: 30px;
}

.user_name {
  font-weight: bold;
}
.user-v {
  margin-bottom: -5px;
}
.user-v-img {
  width: 15px;
  height: 15px;
}
.user-v-font {
  font-size: 15px;
  color: #00c3ff;
}
.user_qianming {
  font-size: 14px;
  color: #999;
}

.user_num {
  width: 40%;
  height: 100%;
  display: flex;
  align-items: center;
}

.user_num > div {
  text-align: center;
  border-right: 1px dotted #999;
  box-sizing: border-box;
  width: 80px;
  height: 40px;
  line-height: 20px;
}

.num_text {
  color: #999;
}

.num_number {
  font-size: 20px;
  color: #333;
}
.el-menu-item>span {
  font-size: 16px;
  color: #999;
}

/*下面部分样式*/
.person_body {
  width: 1000px;
  margin-top: 210px;
  display: flex;
  position: absolute;
  left: 50%;
  transform: translateX(-50%);
  border-radius: 5px;
}

.person_body_left {
  width: 27%;
  height: 600px;
  border-radius: 5px;
  margin-right: 3%;
  text-align: center;
}

.person_body_right {
  width: 70%;
  /* height: 500px; */
  border-radius: 5px;
  background-color: white;
}

.box-card {
  height: 500px;
}
</style>