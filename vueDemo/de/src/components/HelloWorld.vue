<template>
  <div>
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item>
        <el-input
          v-model="search"
          class="search_name"
          style="width:250px"
          placeholder="输入姓名">
        </el-input>
      </el-form-item>
      <el-form-item>
        <el-button
          type="text"
          @click="onSearch()"
          class="el-icon-search">查询
        </el-button>
      </el-form-item>
      <el-form-item>
        <el-button
          type="text"
          @click="refreshData"
          class="el-icon-refresh">刷新
        </el-button>
      </el-form-item>
      <el-form-item>
        <el-button
          type="text"
          @click="dialogVisible = true"
          class="el-icon-circle-plus-outline">添加
        </el-button>
      </el-form-item>
    </el-form>

    <el-table
        :data="tableData"
        highlight-current-row
        border
        style="width: 100%">

      <el-table-column
        label="姓名">
        <template slot-scope="scope">
          <el-popover trigger="hover" placement="right">
            <p>姓名: {{ scope.row.name }}</p>
            <p>学号: {{ scope.row.id }}</p>
            <p>手机号码: {{ scope.row.mobile }}</p>
            <div slot="reference" class="name-wrapper">
              <el-button type="text">{{ scope.row.name }}</el-button>
            </div>
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column
        label="学号">
        <template slot-scope="scope">
          <span>{{ scope.row.id }}</span>
        </template>
        </el-table-column>
      <el-table-column
        label="手机号码">
        <template slot-scope="scope">
          <span>{{ scope.row.mobile }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="操作"
        fixed="right"
        width="200">
        <template slot-scope="scope">
          <el-button round
            size="mini"
            type="info"
            icon="el-icon-edit"
            @click="handleEdit(scope.$index, scope.row)">编辑
          </el-button>
          <el-button round
            size="mini"
            type="danger"
            icon="el-icon-delete"
            @click="handleDelete(scope.$index, scope.row)">删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="80px" class="demo-ruleForm" size="medium">
      <el-dialog
        title="添加"
        :append-to-body='true'
        :visible.sync="dialogVisible"
        width="66%"
        :before-close="handleClose">
        <el-input type="hidden" v-model="ruleForm.number"/>
          <el-form-item label="学号" prop="id">
            <el-input v-model="ruleForm.id"></el-input>
          </el-form-item>
          <el-form-item label="姓名" prop="name">
            <el-input v-model="ruleForm.name"></el-input>
          </el-form-item>
          <el-form-item label="手机号码" prop="mobile">
            <el-input v-model="ruleForm.mobile"></el-input>
          </el-form-item>

          <span slot="footer" class="dialog-footer">
            <el-button @click="cancel()" size="medium">取 消</el-button>
            <el-button @click="addUser()" type="primary" size="medium">确 定</el-button>
          </span>
      </el-dialog>
    </el-form>

    <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="80px" class="demo-ruleForm" size="medium">
      <el-dialog
        title="编辑"
        :append-to-body='true'
        :visible.sync="dialogUpdate"
        width="66%"
        :before-close="handleClose">
        <el-input type="hidden" v-model="ruleForm.number"/>
          <el-form-item label="学号" prop="id">
            <el-input v-model="ruleForm.id"></el-input>
          </el-form-item>
          <el-form-item label="姓名" prop="name">
            <el-input v-model="ruleForm.name"></el-input>
          </el-form-item>
          <el-form-item label="手机号码" prop="mobile">
            <el-input v-model="ruleForm.mobile"></el-input>
          </el-form-item>

          <span slot="footer" class="dialog-footer">
            <el-button @click="cancel()" size="medium">取 消</el-button>
            <el-button @click="updateUser()" type="primary" size="medium">确 定</el-button>
          </span>
      </el-dialog>
    </el-form>

    <br>
      <div class="pages">
        <el-pagination
          background
          :disabled = "disablePage"
          :current-page.sync="currentPage"
          small
          layout="prev, pager, next"
          :page-size="pageSize"
          :total="total"
          @current-change="handleCurrentChange">
        </el-pagination>
      </div>
  </div>
</template>

<script>
    export default {
        data() {
            return {
                ruleForm: {
                    number:'',
                    name: '',
                    id: '',
                    mobile: ''
                },
                rules: {
                    id: [
                       { required: true, message: '请输入学号', trigger: 'blur' },
                       { min: 5, message: '请输入正确学号', trigger: 'blur' }
                    ],
                    name: [
                        { required: true, message: '请输入姓名', trigger: 'blur' },
                        { min: 2,  message: '请输入正确姓名', trigger: 'blur' }
                    ],
                    mobile: [
                        { required: true, message: '请输入手机号码', trigger: 'blur' },
                        { min: 5, message: '请输入正确手机号码', trigger: 'blur' }
                    ],
                },
                tableData: [],
                search: '',
                dialogVisible: false,
                dialogUpdate: false,
                pageSize: 4,
                currentPage: 1,
                total: 0,
                disablePage: false
            }
        },
        methods: {
            handleEdit(index, row) {
                this.dialogUpdate = true;
                this.ruleForm = Object.assign({}, row); //这句是关键！！！
            },

            handleDelete(index, row) {
              console.log(index, row);
                this.$confirm('删除操作, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    let postData = this.qs.stringify({
                        number: row.number,
                    });
                    this.axios({
                        method: 'post',
                        url:'/del',
                        data:postData
                    }).then(response =>
                    {
                        this.getPages();
                        this.currentPage = 1;
                        this.axios.post('/page').then(response =>
                        {
                            this.tableData = response.data;
                        }).catch(error =>
                        {
                            console.log(error);
                        });
                        this.$message({
                            type: 'success',
                            message: '删除成功!'
                        });
                        console.log(response);
                    }).catch(error =>
                    {
                        console.log(error);
                    });

                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });
            },

            handleClose(done) {
                this.$confirm('确认关闭？')
                    .then(_ => {
                        done();
                    })
                    .catch(_ => {});
            },

            handleCurrentChange() {
                console.log(`当前页: ${this.currentPage}`);
                let postData = this.qs.stringify({
                    page: this.currentPage
                });
                this.axios({
                    method: 'post',
                    url:'/page',
                    data:postData
                }).then(response =>
                {
                    this.tableData = response.data;
                }).catch(error =>
                {
                    console.log(error);
                });
            },

            cancel() {
                this.dialogUpdate = false;
                this.dialogVisible = false;
                this.personData();
            },

            personData(){
                this.ruleForm = {
                    name: '',
                    id: '',
                    mobile: ''
                }
            },

            addUser() {
                let postData = this.qs.stringify({
                    number:this.ruleForm.number,
                    id: this.ruleForm.id,
                    name: this.ruleForm.name,
                    mobile: this.ruleForm.mobile
                });
                this.axios({
                    method: 'post',
                    url:'/add',
                    data:postData
                }).then(response =>
                {
                    this.axios.post('/page').then(response =>
                    {
                        this.tableData = response.data;
                        this.currentPage = 1;
                        this.$message({
                            type: 'success',
                            message: '已添加!'
                        });
                    }).catch(error =>
                    {
                        console.log(error);
                    });
                    this.getPages();
                    this.dialogVisible = false
                    console.log(response);
                }).catch(error =>
                {
                    console.log(error);
                });
            },

            updateUser() {
                let postData = this.qs.stringify({
                    number:this.ruleForm.number,
                    id: this.ruleForm.id,
                    name: this.ruleForm.name,
                    mobile: this.ruleForm.mobile
                });
                this.axios({
                    method: 'post',
                    url:'/edit',
                    data:postData
                }).then(response =>
                {
                    this.handleCurrentChange();
                    this.cancel();
                    this.$message({
                        type: 'success',
                        message: '更新成功!'
                    });
                    console.log(response);
                }).catch(error =>
                {
                    this.$message({
                        type: 'success',
                        message: '更新失败!'
                    });
                    console.log(error);
                });
            },

            onSearch() {
                let postData = this.qs.stringify({
                    name: this.search
                });
                this.axios({
                    method: 'post',
                    url: '/query',
                    data: postData
                }).then(response =>
                {
                    this.tableData = response.data;
                    this.disablePage = true;
                }).catch(error =>
                {
                    console.log(error);
                });
            },

            getPages() {
                this.axios.post('/rows').then(response =>
                {
                    this.total = response.data;
                }).catch(error =>
                {
                    console.log(error);
                });
            },

            refreshData() {
                location.reload();
            }
        },

        created() {
            this.axios.post('/page').then(response =>
            {
                this.tableData = response.data;
            }).catch(error =>
            {
                console.log(error);
            });

            this.axios.post('/rows').then(response =>
            {
                this.total = response.data;
            }).catch(error =>
            {
                console.log(error);
            });
        },
    }
</script>
<style scoped>
  .search_name{
    width: 200px;
  }
  .pages{
    margin: 0px;
    padding: 0px;
    text-align: right;
  }
</style>
