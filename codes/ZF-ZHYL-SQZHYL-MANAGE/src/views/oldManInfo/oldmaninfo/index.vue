<template>
  <div class="execution">
    <basic-container>
      <avue-crud
        ref="crud"
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
        @row-del="rowDel"
      >
        <template slot-scope="{ row, index, type, size }" slot="menu">
          <el-button
            icon="el-icon-video-camera"
            :type="type"
            :size="size"
            @click="openCamera(row)"
            >摄像头</el-button
          >
        </template>
      </avue-crud>
    </basic-container>

    <!-- 摄像头弹窗组件 -->
    <camera-popup ref="CameraPopupRef" />
  </div>
</template>

<script>
import { fetchList, getObj, addObj, putObj, delObj } from "@/api/oldmaninfo";
import { tableOption } from "@/const/crud/oldmaninfo";
import { mapGetters } from "vuex";
import CameraPopup from "../camera/index.vue";
export default {
  name: "oldmaninfo",
  components: {
    CameraPopup,
  },
  data() {
    return {
      searchForm: {},
      tableData: [],
      page: {
        total: 0, // 总页数
        currentPage: 1, // 当前页数
        pageSize: 20, // 每页显示多少条
      },
      tableLoading: false,
      tableOption: tableOption,
    };
  },
  computed: {
    ...mapGetters(["permissions"]),
    permissionList() {
      return {
        addBtn: this.vaildData(this.permissions.oldManInfo_oldmaninfo_add, false),
        delBtn: this.vaildData(this.permissions.oldManInfo_oldmaninfo_del, false),
        editBtn: this.vaildData(this.permissions.oldManInfo_oldmaninfo_edit, false),
      };
    },
  },
  methods: {
    openCamera(row) {
      row && row.id && this.$refs.CameraPopupRef.openPop(row);
    },
    getList(page, params) {
      this.tableLoading = true;
      fetchList(
        Object.assign(
          {
            current: page.currentPage,
            size: page.pageSize,
          },
          params,
          this.searchForm
        )
      )
        .then((response) => {
          this.tableData = response.data.data.records;
          this.page.total = response.data.data.total;
          this.tableLoading = false;
        })
        .catch(() => {
          this.tableLoading = false;
        });
    },
    rowDel: function (row, index) {
      this.$confirm("是否确认删除", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return delObj(row.id);
        })
        .then((data) => {
          this.$message.success("删除成功");
          this.getList(this.page);
        });
    },
    handleUpdate: function (row, index, done, loading) {
      putObj(row)
        .then((data) => {
          this.$message.success("修改成功");
          done();
          this.getList(this.page);
        })
        .catch(() => {
          loading();
        });
    },
    handleSave: function (row, done, loading) {
      addObj(row)
        .then((data) => {
          this.$message.success("添加成功");
          done();
          this.getList(this.page);
        })
        .catch(() => {
          loading();
        });
    },
    sizeChange(pageSize) {
      this.page.pageSize = pageSize;
    },
    currentChange(current) {
      this.page.currentPage = current;
    },
    searchChange(form, done) {
      this.searchForm = form;
      this.page.currentPage = 1;
      this.getList(this.page, form);
      done();
    },
    refreshChange() {
      this.getList(this.page);
    },
  },
};
</script>
