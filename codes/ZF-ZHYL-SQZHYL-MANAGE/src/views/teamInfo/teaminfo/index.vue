<template>
  <div class="execution">
    <basic-container>
      <avue-crud ref="crud"
                 :page="page"
                 :data="tableData"
                 :permission="permissionList"
                 :table-loading="tableLoading"
                 :option="tableOption"
                 @on-load="getList"
                 @search-change="searchChange"
                 @refresh-change="refreshChange"
                 @size-change="sizeChange"
                 @current-change="currentChange"
                 @row-update="handleUpdate"
                 @row-save="handleSave"
                 @row-del="rowDel">

        <template slot-scope="{type,size,row}" slot="menu">
          <el-button @click="itemrecord(row)" icon="el-icon-view" :size="size" :type="type">团队人员</el-button>
        </template>
      </avue-crud>
      <el-dialog title="团队人员" width="80%" :visible.sync="dialogPermissionVisible">
        <avue-crud ref="crud"
                   :page="page1"
                   v-model="form1"
                   :data="tableData1"
                   :permission="permissionList"
                   :table-loading="tableLoading1"
                   :option="tableOption1"
                   @on-load="getList1"
                   @search-change="searchChange1"
                   @refresh-change="refreshChange1"
                   @size-change="sizeChange1"
                   @current-change="currentChange1"
                   @row-update="handleUpdate1"
                   @row-save="handleSave1"
                   @row-del="rowDel1">
          <!-- 等接口 -->
          <template slot-scope="scope" slot="didForm">
            <el-select

              v-model="form1.id"
              filterable
              remote
              reserve-keyword
              placeholder="请输入关键词"
              :remote-method="remoteMethod"
            >
              <el-option
                v-for="item in options"
                :key="item.id"
                :label="item.name"
                :value="item.did">
              </el-option>
            </el-select>
<!--             <el-button style="margin-top:5px" @click="remoteMethod" type="primary" :size="size">
              搜索
            </el-button>-->
          </template>
        </avue-crud>
      </el-dialog>

    </basic-container>
  </div>
</template>

<script>
import {fetchList, getObj, addObj, putObj, delObj} from '@/api/teaminfo'
import {fetchList1, getObj1, addObj1, putObj1, delObj1} from '@/api/teampersonnel'
import {tableOption,tableOption1} from '@/const/crud/teaminfo'
import {mapGetters} from 'vuex'
import AvueUeditor from 'avue-plugin-ueditor'

export default {
  name: 'teaminfo',
  data() {
    return {
      searchForm: {},
      tableData: [],
      searchForm1: {},
      tableData1: [],
      page: {
        total: 0, // 总页数
        currentPage: 1, // 当前页数
        pageSize: 20 // 每页显示多少条
      },
      page1: {
        total: 0, // 总页数
        currentPage: 1, // 当前页数
        pageSize: 20 // 每页显示多少条
      },
      tableLoading: false,
      tableOption: tableOption,
      tableLoading1: false,
      tableOption1: tableOption1,
      rowid: 0,
      form1:{},
      dialogPermissionVisible:false
    }
  },
  computed: {
    ...mapGetters(['permissions']),
    permissionList() {
      return {
        addBtn: this.vaildData(this.permissions.teamInfo_teaminfo_add, false),
        delBtn: this.vaildData(this.permissions.teamInfo_teaminfo_del, false),
        editBtn: this.vaildData(this.permissions.teamInfo_teaminfo_edit, false)
      };
    }
  },
  methods: {
    getList(page, params) {
      this.tableLoading = true
      fetchList(Object.assign({
        current: page.currentPage,
        size: page.pageSize
      }, params, this.searchForm)).then(response => {
        this.tableData = response.data.data.records
        this.page.total = response.data.data.total
        this.tableLoading = false
      }).catch(() => {
        this.tableLoading = false
      })
    },
    rowDel: function (row, index) {
      this.$confirm('是否确认删除', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(function () {
        return delObj(row.id)
      }).then(data => {
        this.$message.success('删除成功')
        this.getList(this.page)
      })
    },
    handleUpdate: function (row, index, done, loading) {
      putObj(row).then(data => {
        this.$message.success('修改成功')
        done()
        this.getList(this.page)
      }).catch(() => {
        loading();
      });
    },
    handleSave: function (row, done, loading) {
      addObj(row).then(data => {
        this.$message.success('添加成功')
        done()
        this.getList(this.page)
      }).catch(() => {
        loading();
      });
    },
    sizeChange(pageSize) {
      this.page.pageSize = pageSize
    },
    currentChange(current) {
      this.page.currentPage = current
    },
    searchChange(form, done) {
      this.searchForm = form
      this.page.currentPage = 1
      this.getList(this.page, form)
      done()
    },
    refreshChange() {
      this.getList(this.page)
    },

    //团队人员

    getList1(page, params) {
      this.tableLoading1 = true
      var params = params ? params : {}
      params.teamInfo = this.rowid
      fetchList1(Object.assign({
        current: page.currentPage,
        size: page.pageSize
      }, params, this.searchForm1)).then(response => {
        /*response.data.data.records.map(r=>{
          r.did=1
        })*/
        this.tableData1 = response.data.data.records
        this.page1.total = response.data.data.total
        this.tableLoading1 = false
      }).catch(() => {
        this.tableLoading1 = false
      })
    },

    rowDel1: function (row, index) {
      this.$confirm('是否确认删除此人员：', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(function () {
        return delObj1(row.id)
      }).then(data => {
        this.$message.success('删除成功')
        this.getList1(this.page1)
      })
    },
    handleUpdate1: function (row, index, done, loading) {
      console.log(row, 1111111111111)
      putObj1(row).then(data => {
        this.$message.success('修改成功')
        done()
        this.options = []
        this.getList1(this.page1)
      }).catch(() => {
        loading();
      });
    },
    handleSave1: function (row, done, loading) {

      var row = row
      row.teamInfo = this.rowid
      addObj1(row).then(data => {
        this.$message.success('添加成功')
        this.options = []
        done()
        this.getList1(this.page1)
      }).catch(() => {
        loading();
      });
    },
    sizeChange1(pageSize) {
      this.page1.pageSize = pageSize
    },
    currentChange1(current) {
      this.page1.currentPage = current
    },
    searchChange1(form, done) {
      this.searchForm1 = form
      this.page1.currentPage = 1
      this.getList1(this.page1, form)
      done()
    },
    refreshChange1() {
      this.getList1(this.page1)
    },

    itemrecord: function (row) {
      this.rowid = row.id
      //var did = this.findObject(this.tableOption1.column, 'did')
      // did.dicUrl = `/szvote/vote/object?voteId=` + row.id;
      var dic = {teamInfo: row.id}
      this.getList1(this.page1, dic)
      this.dialogPermissionVisible = true
    },
  }
}
</script>
