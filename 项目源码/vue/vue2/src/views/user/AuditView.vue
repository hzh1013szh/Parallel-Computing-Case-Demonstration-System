<template>
  <div>
    <div style="margin-bottom: 15px">
      <el-input v-model="params.name" style="width: 200px" placeholder="请输入姓名"></el-input>
      <el-input v-model="params.username" style="width: 200px;margin-left: 5px" placeholder="请输入用户名"></el-input>
      <el-button type="warning" style="margin-left: 10px" @click="getBySearchUnAccept()">查询</el-button>
      <el-button type="warning" style="margin-left: 10px" @click="reset()">清空</el-button>
    </div>
    <div class="admin">
      <el-table
          :data="tableData"
          style="width: 100%">
        <el-table-column
            prop="username"
            label="用户名">
        </el-table-column>
        <el-table-column
            prop="name"
            label="姓名">
        </el-table-column>
        <el-table-column
            prop="phone"
            label="电话">
        </el-table-column>
        <el-table-column
            prop="email"
            label="邮箱">
        </el-table-column>
        <el-table-column
            prop="role"
            label="角色">
        </el-table-column>
        <el-table-column
            prop="specialty"
            label="特长">
        </el-table-column>
        <el-table-column
            prop="status"
            label="状态">
        </el-table-column>
        <el-table-column label="操作" v-if="user.role === 'admin'">
          <!--这里的scope.row表示我们编辑按钮所在行-->
          <template slot-scope="scope">
            <el-popconfirm title="确定通过吗？" @confirm="approve(scope.row)">
              <el-button slot="reference" type="primary" style="margin-left: 5px">通过</el-button>
            </el-popconfirm>
            <el-popconfirm title="确定不通过吗？" @confirm="del(scope.row)">
              <el-button slot="reference" type="danger" style="margin-left: 5px">不通过</el-button>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
    </div>
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

export default {
  data() {
    return {
      // 查询参数
      params:{
        name: '',
        username: '',
        pageNum: 1,
        pageSize: 5,
      },
      tableData: [],
      total : 0,
      form:{
        username: '',
        name: '',
        phone: '',
        email: '',
        role: '',
        specialty:'',
        status: '',
      },
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
    }
  },
  // 生命周期 - 页面创建时会做的事情（访问当前this实例）
  created() {
    this.getBySearchUnAccept();
  },
  // 定义页面触发一些事件调用的方法
  methods: {
    getBySearchUnAccept() {
      request.get("/admin/search/UnAccept",{params: this.params}).then(res => {
        if(res.code === "0"){
          this.tableData = res.data.list;
          this.total = res.data.total;
          console.log(res.data);
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
      request.post("/admin",this.form).then(res => {
        if(res.code === "0") {
          this.$message({
            type: 'success',
            message: '操作成功'
          });
          this.dialogFormVisible = false;
          this.getBySearchUnAccept();
        }
        else{
          this.$message({
            type: 'error',
            message: res.msg
          });
        }
      })
    },
    approve(currow){
      this.dialogFormVisible = true;
      this.form = currow;
      this.form.status = "1";
      this.submit();
    },
    del(currow){
      this.form = currow;
      this.form.status = "-1";
      this.submit();
    },
    reset(){
      this.params = {
        name : '',
        username : '',
        pageNum: 1,
        pageSize: 5,
      }
      this.getBySearchUnAccept();
    },
    handleSizeChange(pageSize){
      this.params.pageSize = pageSize;
      this.getBySearchUnAccept();
    },
    handleCurrentChange(currentPage){
      this.params.pageNum = currentPage;
      this.getBySearchUnAccept();
    },
  }
}
</script>
