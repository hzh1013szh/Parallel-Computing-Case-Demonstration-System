
<template>
<div>
    <el-card>
       <el-descriptions class="margin-top" title="个人简介" :column="2" border v-model="admin">
        <template slot="extra">
          <el-button type="primary" size="small">操作</el-button>
        </template>
         <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-picture"></i>
            头像
          </template>
           <el-image :src="`http://localhost:8080/api/files/${admin.avatar}`" style="margin-left: 10px;margin-top: 5px;width: 120px;height: 100px"></el-image>
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-user"></i>
            用户名
          </template>
          {{ admin.username }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-mobile-phone"></i>
            手机号
          </template>
          {{admin.phone}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-user"></i>
            姓名
          </template>
          {{ admin.name }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-connection"></i>
            邮箱
          </template>
          {{admin.email}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-s-custom"></i>
            角色
          </template>
          {{ admin.role }}
        </el-descriptions-item>
         <el-descriptions-item v-if="Number(this.$route.params.id) === user.id">
          <template slot="label">
            <i class="el-icon-s-custom"></i>
            密码
          </template>
          {{ admin.password }}
        </el-descriptions-item>
         <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-s-custom"></i>
            特长
          </template>
          {{ admin.specialty }}
        </el-descriptions-item>
      </el-descriptions>
    </el-card>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "InfoView",
  data(){
    return {
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
      imgUrl: require('@/assets/img3.png'),
      admin:{
        username: '',
        password: ''
      }
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
          console.log(`http://localhost:8080/api/files/${this.admin.avatar}`);
        }else{
          this.$message({
            type: 'error',
            message: res.msg
          });
        }
      })
    }
  }
}
</script>

<style scoped>

</style>