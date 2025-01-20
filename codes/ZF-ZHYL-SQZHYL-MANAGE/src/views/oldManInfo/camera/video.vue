<template>
  <el-dialog
    title="摄像头直播"
    width="1050px"
    append-to-body
    :visible.sync="visible"
    :before-close="handleClose"
    destroy-on-close
  >
    <div class="flex-c">
      <div id="video-container"></div>
    </div>
  </el-dialog>
</template>

<script>
import { getUrl } from "@/api/oldmancamera";
export default {
  data() {
    return {
      visible: false,
      player: null,
    };
  },
  computed: {},
  methods: {
    openVideo(d) {
      if (this.player) {
        this.player.stop();
        this.player = null;
      }
      this.visible = true;
      this.$nextTick(() => {
        this.player = new EZUIKit.EZUIKitPlayer({
          id: "video-container", // 视频容器ID
          accessToken: d.token,
          url: d.url,
          width: 1000,
          height: 600,
          // template: "standard",  // 按钮
          handleError: (err) => {
            console.log(err, "----------------err");
            if (err.type === "handleRunTimeInfoError" && err.data.nErrorCode === 5) {
              // 加密设备密码错误
            }

            this.$message.error(err.msg);
          },
        });
      });
    },
    handleClose(done) {
      if (this.player) {
        this.player.stop();
        this.player.destroy && this.player.destroy();
        this.player = null;
      }
      done();
    },
  },
};
</script>
