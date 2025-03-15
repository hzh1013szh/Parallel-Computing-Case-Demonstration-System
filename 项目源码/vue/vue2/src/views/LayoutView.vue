
<template>
  <div>
      <el-container>
        <el-header style="background-color: #45494b">
          <img src="@/assets/logo.png" alt="" style="width: 40px; position: relative; top: 10px">
          <span style="font-size: 20px; color: whitesmoke ;margin-left: 15px">并行计算案例展示平台</span>
          <el-dropdown style="float: right;height: 60px;line-height: 60px" trigger="click">
            <div class="circle">
              <img class="me-view-picture_avatar" :src="'http://localhost:8080/api/files/' + user.avatar"></img>
            </div>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item @click.native="logout()">退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </el-header>
      </el-container>
      <el-container>
        <!--min-height是为了让他拉伸到底部        -->
        <!--route.path表示我们现在选择的激活方式时路由模式 router表示激活路由模式-->
        <el-aside style="overflow: hidden;min-height: 100vh; background-color: #545c64; width: 250px">
          <el-menu
              :default-active="$route.path"
              router
              class="el-menu-vertical-demo"
              background-color="#545c64"
              text-color="#fff"
              active-text-color="#ffd04b">
            <el-menu-item index="1" :route="{name: 'home'}">
              <i class="el-icon-s-home"></i>
              <span slot="title">首页</span>
            </el-menu-item>
            <el-menu-item index="2" :route="{name: 'user',params:{id: user.id}}">
              <i class="el-icon-user"></i>
              <span slot="title">个人中心</span>
            </el-menu-item>
            <el-submenu index="3">
              <template slot="title">
                <i class="el-icon-location"></i>
                <span>用户管理</span>
              </template>
              <el-menu-item-group>
                <el-menu-item index="/admin" :route="{name: 'admin'}">用户信息</el-menu-item>
              </el-menu-item-group>
              <el-menu-item index="/audit" :route="{name: 'audit'}" v-if="user.role === 'admin'">审核用户</el-menu-item>
            </el-submenu>
            <el-submenu index="4" v-if="this.user.role === 'admin'">
              <template slot="title">
                <i class="el-icon-location"></i>
                <span>信息管理</span>
              </template>
              <el-menu-item-group>
                <el-menu-item index="/notice">系统公告</el-menu-item>
              </el-menu-item-group>
              <el-menu-item-group>
                <el-menu-item index="/post/category">需求分类</el-menu-item>
              </el-menu-item-group>
            </el-submenu>
            <el-submenu index="5">
              <template slot="title">
                <i class="el-icon-location"></i>
                <span>需求管理</span>
              </template>
              <el-menu-item-group>
                <el-menu-item index="/addpost" :route="{name: 'addpost'}">发帖</el-menu-item>
              </el-menu-item-group>
              <el-menu-item-group>
                <el-menu-item index="/allpost" :route="{name: 'allpost'}">需求信息</el-menu-item>
              </el-menu-item-group>
            </el-submenu>
          </el-menu>
        </el-aside>
        <el-main>

          <router-view/>
        </el-main>
      </el-container>
  </div>
</template>

<script>
export default {
  name: "Layout",
  data(){
    return{
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
    }
  },
  methods:{
    logout(){
      localStorage.removeItem("user");
      this.$router.push("/login");
    }
  }
}
</script>
<!--去除菜单栏右边多出来的一部分-->
<style>
.el-menu {
  border-right: none !important;
}
.el-dropdown-link {
  cursor: pointer;
  color: #f4f5f6;
}
.el-icon-arrow-down {
  font-size: 12px;
}
.circle{
  margin-top: 5px;
  height: 50px;
}
.me-view-picture_avatar{
  object-fit: cover;
  width: 50px;
  height: 50px;
  border: 1px solid #ddd;
  border-radius: 50%;
  vertical-align: top;
  background-color: #ced5d0;
}
</style>