<!--
  -    Copyright (c) 2018-2025, lengleng All rights reserved.
  -
  - Redistribution and use in source and binary forms, with or without
  - modification, are permitted provided that the following conditions are met:
  -
  - Redistributions of source code must retain the above copyright notice,
  - this list of conditions and the following disclaimer.
  - Redistributions in binary form must reproduce the above copyright
  - notice, this list of conditions and the following disclaimer in the
  - documentation and/or other materials provided with the distribution.
  - Neither the name of the pig4cloud.com developer nor the names of its
  - contributors may be used to endorse or promote products derived from
  - this software without specific prior written permission.
  - Author: lengleng (wangiegie@gmail.com)
  -->
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
				<template slot="name" slot-scope="{scope,row}">
					<el-image style="width:100px;height:50px;" fit="contain" :preview-src-list="[row.name]" :src="row.name">
						<div :title="row.name" slot="error" style="line-height:58px;">
							<el-button @click="open_url(row.name)" type="text">下载</el-button>
						</div>
					</el-image>
				</template>
				<template slot="menuLeft" slot-scope="{size}">
					<el-button type="primary" @click="showfile" :size="size">文件管理</el-button>
				</template>
            </avue-crud>
        </basic-container>
		<el-dialog title="附件管理" width="80%" :visible.sync="show">
			<avue-crud ref="record"
					   :page="page1"
					   :data="tableData1"
					   :table-loading="tableLoading1"
					   :option="tableOption1"
					   @search-change="searchChange1"
					   @refresh-change="refreshChange1"
					   @current-change="currentChange1"
					   @size-change="sizeChange1"
					   @row-del="rowDel1">
			</avue-crud>
		</el-dialog>
    </div>
</template>

<script>
    import {fetchList, getObj, addObj, putObj, delObj,getFiles,delFiles} from '@/api/demoinfo'
    import {tableOption,tableOption1} from '@/const/crud/demoinfo'
    import {mapGetters} from 'vuex'

    export default {
        name: 'demoinfo',
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
				show:false,
				page1: {
					total: 0, // 总页数
					currentPage: 1, // 当前页数
					pageSize: 20 // 每页显示多少条
				},
				tableData1: [],
				tableLoading1: false,
				tableOption1: tableOption1,
				searchForm1: {},
            }
        },
        computed: {
            ...mapGetters(['permissions']),
            permissionList() {
                return {
                    addBtn: this.vaildData(this.permissions.demo_demoinfo_add, false),
                    delBtn: this.vaildData(this.permissions.demo_demoinfo_del, false),
                    editBtn: this.vaildData(this.permissions.demo_demoinfo_edit, false)
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
                this.$confirm('是否确认删除ID为' + row.id, '提示', {
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
			open_url(url){
            	window.open(url);
			},
			showfile(){
            	this.show=true;
            	this.refreshChange1();
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
			currentChange1(current) {
				this.page1.currentPage = current
				this.refreshChange1();
			},
			sizeChange1(pageSize){
				this.page1.pageSize = pageSize
				this.refreshChange1();
			},
			getList1(page, params) {
				this.tableLoading1 = true
				getFiles(Object.assign({
					current: page.currentPage,
					size: page.pageSize
				}, params, this.searchForm1 )).then(response => {
					this.tableData1 = response.data.data.records
					this.page1.total = response.data.data.total
					this.tableLoading1 = false
				}).catch(() => {
					this.tableLoading1=false
				})
			},
			rowDel1(row) {
				this.$confirm('是否确认删除文件', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(function () {
					return delFiles({ids:[row.id]})
				}).then(data => {
					this.$message.success('删除成功')
					this.getList1(this.page)
				})
			},
        }
    }
</script>
