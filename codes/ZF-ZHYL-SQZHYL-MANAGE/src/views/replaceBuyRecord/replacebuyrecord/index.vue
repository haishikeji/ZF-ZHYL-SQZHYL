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
          <el-button @click="itemrecord(row)" v-if="row.state==2" icon="el-icon-success" :size="size" :type="type">完成
          </el-button>
        </template>

        <template slot="menuLeft">
          <el-button type="primary" size="small" @click="excel">导出</el-button>
        </template>
      </avue-crud>
    </basic-container>
  </div>
</template>

<script>
import {fetchList, getObj, addObj, putObj, delObj} from '@/api/replacebuyrecord'
import {tableOption} from '@/const/crud/replacebuyrecord'
import {mapGetters} from 'vuex'
import {baseUrl} from "../../../config/env";

export default {
  name: 'replacebuyrecord',
  data() {
    return {
      searchForm: {},
      tableData: [],
      page: {
        total: 0, // 总页数
        currentPage: 1, // 当前页数
        pageSize: 20 // 每页显示多少条
      },
      tableLoading: false,
      tableOption: tableOption,
      // stateType: 0,
    }
  },
  computed: {
    ...mapGetters(['permissions']),
    permissionList() {
      return {
        addBtn: this.vaildData(this.permissions.replaceBuyRecord_replacebuyrecord_add, false),
        delBtn: this.vaildData(this.permissions.replaceBuyRecord_replacebuyrecord_del, false),
        editBtn: this.vaildData(this.permissions.replaceBuyRecord_replacebuyrecord_edit, false)
      };
    }
  },
  methods: {
    getList(page, params) {
      if(this.$route.query && this.$route.query.name)
      {
        if(!params)
        {
          params = {}
        }
        params.oldName = this.$route.query.name
      }

      this.tableLoading = true
      fetchList(Object.assign({
        current: page.currentPage,
        size: page.pageSize
      }, params, this.searchForm)).then(response => {
        this.tableData = response.data.data.records
        this.page.total = response.data.data.total
       /* response.data.data.records.map(r => {
          this.stateType = r.state
        })*/
        this.tableLoading = false
      }).catch(() => {
        this.tableLoading = false
      })
    },

    excel(){
      window.open(baseUrl + "/replaceBuyRecord/replacebuyrecord/list/excel?type=1"+
        (this.searchForm.replaceBuy ?'&replaceBuy=' + this.searchForm.replaceBuy :'')+
        (this.searchForm.state ? '&state=' + this.searchForm.state : '')+
        (this.searchForm.oldName ?'&oldName=' + this.searchForm.oldName:'')
      );
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

    itemrecord: function (row,index) {
     /* console.log("row====", row)
      var rowid = row.id
      console.log(rowid)
      if (row.state === 2) {
        row.state = 1
      }
      putObj(row).then(data => {
        this.$message.success('标记完成')

        this.getList(this.page)
        done()
      }).catch(() => {
        loading();
      });*/
      /*if(row.state==2){
        row.state=1
      }
      if(row.cost== null){
        row.cost=0
      }
*/
      this.$refs.crud.rowEdit(row,index);

    },
  }
}
</script>
