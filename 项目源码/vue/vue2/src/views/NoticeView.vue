<template>
  <div>
    <div style="margin-bottom: 15px">
      <el-input v-model="params.name" style="width: 200px" placeholder="请输入公告标题"></el-input>
      <el-button type="primary" style="margin-left: 10px" @click="add()" v-if="user.role === 'admin'">新增</el-button>
      <el-button type="warning" style="margin-left: 10px" @click="getBySearch()">查询</el-button>
      <el-button type="warning" style="margin-left: 10px" @click="reset()">清空</el-button>
    </div>
    <div class="admin">
      <el-table
          :data="tableData"
          style="width: 100%">
        <el-table-column
            prop="name"
            label="公告标题">
        </el-table-column>
        <el-table-column
            prop="content"
            label="公告内容">
        </el-table-column>
        <el-table-column
            prop="time"
            label="发布时间">
        </el-table-column>
        <el-table-column label="操作" v-if="user.role === 'admin'">
          <!--这里的scope.row表示我们编辑按钮所在行-->
          <template slot-scope="scope">
            <el-button type="primary" @click="edit(scope.row)">编辑</el-button>
            <el-popconfirm title="这是一段内容确定删除吗？" @confirm="del(scope.row.id)">
              <el-button slot="reference" type="danger" style="margin-left: 5px">删除</el-button>
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
    <div>
      <el-dialog title="请输入信息" :visible.sync="dialogFormVisible" width="30%">
        <el-form :model="form">
          <el-form-item label="公告标题" label-width="15%">
            <el-input v-model="form.name" autocomplete="off" style="width: 80%"></el-input>
          </el-form-item>
        </el-form>
        <el-form :model="form">
          <el-form-item label="公告内容" label-width="15%">
            <el-input type="textarea" v-model="form.content" autocomplete="off" style="width: 80%"></el-input>
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
  data() {
    return {
      // 查询参数
      params:{
        name: '',
        pageNum: 1,
        pageSize: 5,
      },
      tableData: [],
      total : 0,
      dialogFormVisible : false,
      form:{
        name: '',
        content: '',
        time: '',
      },
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
    }
  },
  // 生命周期 - 页面创建时会做的事情（访问当前this实例）
  created() {
    this.getBySearch();
  },
  // 定义页面触发一些事件调用的方法
  methods: {
    getBySearch() {
      request.get("/notice/search",{params: this.params}).then(res => {
        if(res.code === "0"){
          this.tableData = res.data.list;
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
    add(){
      this.form = {};
      this.dialogFormVisible = true;
    },
    submit(){
      // 管理员用户新增用户，初始化密码为1234
      request.post("/notice",this.form).then(res => {
        if(res.code === "0") {
          this.$message({
            type: 'success',
            message: '操作成功'
          });
          this.dialogFormVisible = false;
          this.getBySearch();
        }
        else{
          this.$message({
            type: 'error',
            message: res.msg
          });
        }
      })
    },
    edit(currow){
      this.dialogFormVisible = true;
      this.form = currow;
    },
    del(id){
      request.delete(`/notice/${id}`).then(res => {
        if (res.code === "0") {
          this.$message({
            type: 'success',
            message: '操作成功'
          });
          this.getBySearch();
        } else {
          this.$message({
            type: 'error',
            message: res.msg
          });
        }
      })
    },
    cancel(){
      this.dialogFormVisible = false;
      this.getBySearch();
    },
    reset(){
      this.params = {
        name : '',
        pageNum: 1,
        pageSize: 5,
      }
      this.getBySearch();
    },
    handleSizeChange(pageSize){
      this.params.pageSize = pageSize;
      this.getBySearch();
    },
    handleCurrentChange(currentPage){
      this.params.pageNum = currentPage;
      this.getBySearch();
    },
  }
}
</script>
