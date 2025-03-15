<template>
  <div>
    <div>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-card shadow="hover">
            <div class="usercard">
              <img class="userinfo-avatar" :src="'http://localhost:8080/api/files/' + user.avatar"></img>
                <div class="userinfo">
                  <div class="userinfo-name">{{user.username}}</div>
                  <div class="userinfo-role">{{user.role}}</div>
                </div>
            </div>
          </el-card>
        </el-col>

        <el-col :span="12">
          <el-card shadow="hover">
            <div>
              <div style="margin-bottom: 15px">系统公告</div>
              <el-collapse v-model="activeName" accordion>
                <el-collapse-item v-for="item in tableData" :title="item.name" :name="item.id">
                  <div>{{item.content}}</div>
                </el-collapse-item>
              </el-collapse>
            </div>
          </el-card>
        </el-col>
      </el-row>
    </div>
    <div style="display: flex">
      <div style="flex: 1;margin-top: 50px">
        <div id="role_pie" style="width: 100%;height: 400px"></div>
      </div>
      <div style="flex: 1;margin-top: 50px">
        <div id="post_pie" style="width: 100%;height: 400px"></div>
      </div>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";
import * as echarts from 'echarts';
export default {
  name: 'HomeView',
  data(){
    return{
      activeName: '1',
      tableData: [],
      imgUrl: require('@/assets/img3.png'),
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
    }
  },
  mounted(){
    this.getNewNotice();
    this.initdata();
  },
  methods: {
    getNewNotice(){
      request.get("/notice").then(res => {
        if(res.code === "0"){
          this.tableData = res.data;
          this.activeName = res.data[0].id;
        }
        else{
          this.$message({
            type: 'error',
            message: res.msg
          });
        }
      })
    },
    initdata(){
      request.get("/admin/echarts/role_pie").then(res => {
        if(res.code === "0"){
          this.initRolePie(res.data);
        }
        else{
          this.$message({
            type: 'error',
            message: res.msg
          });
        }
      })
      request.get("post/echarts/pie").then(res => {
        if(res.code === "0"){
          this.initPostPie(res.data);
        }
        else{
          this.$message({
            type: 'error',
            message: res.msg
          });
        }
      })
    },
    initRolePie(data){
      let chartDom = document.getElementById('role_pie');
      let myChart = echarts.init(chartDom);
      let option;

      option = {
        title: {
          text: '角色分类',
          subtext: '网站角色分类信息',
          left: 'center'
        },
        tooltip: {
          trigger: 'item'
        },
        legend: {
          orient: 'vertical',
          left: 'left'
        },
        series: [
          {
            name: '角色分类',
            type: 'pie',
            radius: '50%',
            data: data,
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }
        ]
      };

      option && myChart.setOption(option);
    },
    initPostPie(data){
      let chartDom = document.getElementById('post_pie');
      let myChart = echarts.init(chartDom);
      let option;

      option = {
        title: {
          top: '0',
          text: '需求分类',
          subtext: '网站需求分类信息',
          left: 'center'
        },
        tooltip: {
          trigger: 'item'
        },
        legend: {
          top: '10%',
          left: 'left'
        },
        series: [
          {
            name: '需求分类',
            type: 'pie',
            radius: ['40%', '70%'],
            avoidLabelOverlap: false,
            itemStyle: {
              borderRadius: 10,
              borderColor: '#fff',
              borderWidth: 2
            },
            label: {
              show: false,
              position: 'center'
            },
            emphasis: {
              label: {
                show: true,
                fontSize: 40,
                fontWeight: 'bold'
              }
            },
            labelLine: {
              show: false
            },
            data: data,
          }
        ]
      };

      option && myChart.setOption(option);

    },
  }

}
</script>

<style>
.userinfo-avatar{
  object-fit: fill;
  width: 150px;
  height: 150px;
  border: 1px solid #ddd;
  border-radius: 50%;
  vertical-align: top;
  background-color: #c8cac8;
}
.usercard{
  height: 180px;
  display: flex;
  border-bottom: 2px solid #DCDFE6;
  border-radius: 2px;
}
.userinfo {
  width: auto;
  padding: 6% 0 0 6%;
}

.userinfo-name{
    font-weight: 900;
    font-size: 25px;
}
.userinfo-role{
  margin-top: 15px;
  color: #909399;
}
</style>