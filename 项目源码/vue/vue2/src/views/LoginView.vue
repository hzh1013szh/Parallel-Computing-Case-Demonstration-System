
<template>
  <div>
    <div class="login_face">
		  <div class="login_box">
        <div class="img_box"></div>
			  <!-- 登录表单 -->
			  <div class="login_msg">
          <el-form :model="admin">
            <el-form-item>
              <el-input placeholder="请输入用户名" v-model="admin.username" clearable id="username"></el-input>
            </el-form-item>
            <el-form-item>
              <el-input placeholder="请输入密码" v-model="admin.password"  id="password" show-password></el-input>
            </el-form-item>
              <el-form-item>
                <el-button id="btn" type="primary" @click="login()">登录</el-button>
            </el-form-item>
            <div style="text-align: center">
              如果您没有账号<a href="javascript:void(0)" style="text-decoration: deepskyblue" @click="naVRegister()">点击注册</a>
            </div>
          </el-form>
        </div>
		  </div>
	  </div>
  </div>
</template>

<script>

import request from "@/utils/request";

export default {
  name: "LoginView",
  data() {
    return {
      admin: {
        username: '',
        password: '',
      },
    }
  },
  created() {

  },
  methods: {
    login(){
      request.post("/admin/login",this.admin).then(res => {
        if(res.code === "0"){
          this.$message({
            type: 'success',
            message: "登录成功"
          });
          // 将前端返回的数据存储到LocalStorage中
          localStorage.setItem("user",JSON.stringify(res.data));
          // console.log(res.data);
          this.$router.push("/");
        }
        else{
          this.$message({
            type: 'error',
            message: res.msg
          });
        }
      })
    },
    naVRegister(){
      this.$router.push("/register")
    }
  }

}
</script>
<style>
	.login_msg{
		width: 400px;
		height: 300px;
		margin: auto;
		margin-top: 25px;
		/* background-color: lightcyan; */
	}
	#username,#password{
		width:400px;
		height: 50px;
		margin: 15px;
		margin:15px 0;
		background: rgba(255, 255, 255, 0.65);
		border-radius: 25px;
	}
	#btn{
		width: 220px;
		margin-left: 90px;
		margin-top: 25px;
		border-radius: 40px;
	}

	* {
		margin: 0px;
		padding: 0px;
	}

	.login_face {
		display: flex;
		height: 100vh;
		align-items: center;
		justify-content: center;
		background-image: url(@/assets/img8.png);
		background-size: cover;
	}

	.login_box {
		width: 550px;
		height: 450px;
		border-radius: 20px;
		background: rgba(255, 255, 255, 0.38);
	}

	.img_box {
		width: 130px;
		height: 130px;
		background-image: url(@/assets/img1.png);
		background-size: cover;
		border-radius: 50%;
		border: white solid 6px;
		opacity: 0.95;
		margin: auto;
		margin-top: -68px;
	}
</style>