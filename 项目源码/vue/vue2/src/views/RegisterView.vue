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
              <el-input placeholder="请输入密码" v-model="admin.password" id="password" show-password></el-input>
            </el-form-item>
            <el-form-item>
              <el-input placeholder="请输入电话" v-model="admin.phone" id="phone" ></el-input>
            </el-form-item>
            <el-form-item>
              <el-input placeholder="请输人邮箱" v-model="admin.email" id="email"></el-input>
            </el-form-item>
            <el-form-item>
              <el-select v-model="admin.role" placeholder="请选择角色" id="role">
                <el-option label="企业" value="company"></el-option>
                <el-option label="开发人员" value="user"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item>
              <el-button id="btn" type="primary" @click="register()">注册</el-button>
            </el-form-item>
            <div style="text-align: center">
              如果您有账号<a href="javascript:void(0)" style="text-decoration: deepskyblue" @click="naVLogin()">点击登录</a>
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
  name: "RegisterView",
  data() {
    return {
      admin: {
        username: '',
        password: '',
        email: '',
        phone: ''
      },
    }
  },
  methods: {
    register() {
      request.post("/admin/register", this.admin).then(res => {
        if (res.code === "0") {
          this.$message({
            type: 'success',
            message: "注册成功"
          });
          this.$router.push("/login");
        } else {
          this.$message({
            type: 'error',
            message: res.msg
          });
        }
      }).catch(error => {
        console.error("注册失败:", error);
        this.$message({
          type: 'error',
          message: "注册失败，请稍后再试"
        });
      });
    },
    naVLogin(){
      this.$router.push("/login")
    }
  }
}
</script>

<style>
.login_msg {
  width: 400px;
  height: 300px;
  margin: auto;
  margin-top: 25px;
  /* background-color: lightcyan; */
}

#username, #password, #phone,#email,#role {
  width: 400px;
  height: 30px;
  margin: 5px;
  margin: 5px 0;
  background: rgba(255, 255, 255, 0.65);
  border-radius: 25px;
}

#btn {
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
  height: 550px;
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
