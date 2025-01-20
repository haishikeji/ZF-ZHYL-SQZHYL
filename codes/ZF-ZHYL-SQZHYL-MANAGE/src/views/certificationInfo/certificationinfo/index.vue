
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
                <el-button @click="examinePass(row)" v-show="row.state==1" icon="el-icon-user-solid" :size="size" :type="type">审核通过</el-button>
                <el-button  @click="examineNoPass(row)" v-show="row.state==1" icon="el-icon-user" :size="size" :type="type">审核不通过</el-button>
                <el-button   @click="examineCancelPass(row)" v-show="row.state==2" icon="el-icon-circle-close" :size="size" :type="type">取消绑定</el-button>
              </template>
            </avue-crud>
        </basic-container>
    </div>
</template>

<script>
    import {fetchList, getObj, addObj, putObj, delObj} from '@/api/certificationinfo'
    import {tableOption} from '@/const/crud/certificationinfo'
    import {mapGetters} from 'vuex'
    import {examinePass,examineNoPass,examineCancelPass} from "../../../api/certificationinfo";

    export default {
        name: 'certificationinfo',
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
                tableOption: tableOption
            }
        },
        computed: {
            ...mapGetters(['permissions']),
            permissionList() {
                return {
                    addBtn: this.vaildData(this.permissions.certificationInfo_certificationinfo_add, false),
                    delBtn: this.vaildData(this.permissions.certificationInfo_certificationinfo_del, false),
                    editBtn: this.vaildData(this.permissions.certificationInfo_certificationinfo_edit, false)
                };
            }
        },
        methods: {
            getList(page, params) {
                this.tableLoading = true
                fetchList(Object.assign({
                    current: page.currentPage,
                    size: page.pageSize
                }, params, this.searchForm )).then(response => {
                    this.tableData = response.data.data.records
                    this.page.total = response.data.data.total
                    this.tableLoading = false
                }).catch(() => {
                    this.tableLoading=false
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
            handleUpdate: function (row, index, done,loading) {
                putObj(row).then(data => {
                    this.$message.success('修改成功')
                    done()
                    this.getList(this.page)
                }).catch(() => {
                    loading();
                });
            },
            handleSave: function (row, done,loading) {
                addObj(row).then(data => {
                    this.$message.success('添加成功')
                    done()
                    this.getList(this.page)
                }).catch(() => {
                    loading();
                });
            },
            sizeChange(pageSize){
                this.page.pageSize = pageSize
            },
            currentChange(current){
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
          examinePass (row) {
            this.rowid = row.id
            this.$confirm('是否确认审核通过', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
            }).then(function () {
              return examinePass(row.id)
            }).then(data => {
              this.$message.success("审核通过")
              this.getList(this.page)
            })
          },
          examineNoPass (row) {
            this.rowid = row.id
            this.$confirm('是否确认审核不通过', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
            }).then(function () {
              return examineNoPass(row.id)
            }).then(data => {
              this.$message.success("成功")
              this.getList(this.page)
            })
          },
          examineCancelPass (row) {
            this.rowid = row.id
            this.$confirm('是否确认取消绑定', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
            }).then(function () {
              return examineCancelPass(row.id)
            }).then(data => {
              this.$message.success("取消绑定成功")
              this.getList(this.page)
            })
          }
        }
    }
</script>
