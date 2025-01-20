<template>
  <el-dialog title="摄像头" :visible.sync="visible">
    <avue-crud
      ref="crud"
      :page="page"
      :data="tableData"
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
          @click="openCameraVideo(row)"
          >摄像头直播</el-button
        >
      </template>
    </avue-crud>

    <!-- <avue-crud
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
    </avue-crud> -->
    <video-popup ref="VideoPopupRef" />
  </el-dialog>
</template>

<script>
import { fetchList, getObj, addObj, putObj, delObj, getUrl } from "@/api/oldmancamera";
import { tableOption } from "@/const/crud/oldmancamera";
import { mapGetters } from "vuex";
import VideoPopup from "./video.vue";
export default {
  components: {
    VideoPopup,
  },
  data() {
    return {
      visible: false,
      oid: null,

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
    // ...mapGetters(["permissions"]),
    // permissionList() {
    //   return {
    //     // addBtn: this.vaildData(this.permissions.userInfo_oldmancamera_add, false),
    //     // delBtn: this.vaildData(this.permissions.userInfo_oldmancamera_del, false),
    //     // editBtn: this.vaildData(this.permissions.userInfo_oldmancamera_edit, false),
    //   };
    // },
  },
  methods: {
    openCameraVideo(row) {
      this.tableLoading = true;
      getUrl({ id: row.id })
        .then((r) => {
          console.log(r, "---------------");
          if (r.data.code == 0) {
            // this.player = new EZUIKit.EZUIKitPlayer({
            //   id: "video-container", // 视频容器ID
            //   accessToken: r.data.data.token,
            //   url: r.data.data.url,
            //   width: 600,
            //   height: 400,
            //   handleError: (err) => {
            //     if (err.type === "handleRunTimeInfoError" && err.data.nErrorCode === 5) {
            //       // 加密设备密码错误
            //     }
            //   },
            // });
            this.$refs.VideoPopupRef.openVideo(r.data.data);
            // setTimeout(() => {
            //   console.log(player.destroy(), "------------------------");
            // }, 2000);
          } else {
            this.$message.error(r.data.msg);
          }
          this.tableLoading = false;
        })
        .catch((err) => {
          this.$message.error(JSON.stringify(err));
          this.tableLoading = false;
        });
    },
    openPop(d) {
      this.oid = d.id;
      this.page.total = 0;
      this.page.currentPage = 1;
      this.tableLoading = false;
      this.visible = true;
      this.getList(this.page);
    },
    getList(page, params) {
      this.tableLoading = true;
      fetchList(
        Object.assign(
          {
            current: page.currentPage,
            size: page.pageSize,
            oid: this.oid,
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
      row.oid = this.oid;
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
