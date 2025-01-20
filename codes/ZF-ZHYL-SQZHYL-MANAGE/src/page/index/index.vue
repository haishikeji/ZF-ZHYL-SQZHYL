<template>
  <div class="avue-contail" :class="{ 'avue--collapse': isCollapse }">
    <div class="avue-header">
      <!-- 顶部导航栏 -->
      <top />
    </div>

    <div class="avue-layout">
      <div class="avue-left">
        <!-- 左侧导航栏 -->
        <sidebar />
      </div>
      <div class="avue-main">
        <!-- 顶部标签卡 -->
        <tags />
        <!-- 主体视图层 -->
        <el-scrollbar style="height: 100%">
          <keep-alive>
            <router-view class="avue-view" v-if="$route.meta.$keepAlive" />
          </keep-alive>
          <router-view class="avue-view" v-if="!$route.meta.$keepAlive" />
        </el-scrollbar>
      </div>
    </div>
    <div class="avue-shade" @click="showCollapse"></div>
  </div>
</template>
a:14:{s:6:"seturl";s:0:"";s:12:"generatehtml";s:1:"1";s:12:"generatelish";s:1:"0";s:10:"meta_title";s:0:"";s:13:"meta_keywords";s:0:"";s:16:"meta_description";s:0:"";s:13:"list_template";s:17:"list_dangjian.php";s:13:"show_template";s:19:"show_newsdetial.php";s:19:"list_customtemplate";s:0:"";s:6:"ishtml";s:1:"0";s:9:"repagenum";s:0:"";s:14:"content_ishtml";s:1:"0";s:15:"category_ruleid";s:1:"1";s:11:"show_ruleid";s:1:"4";}
<script>
import { mapGetters } from 'vuex'
import tags from './tags'
import top from './top/'
import sidebar from './sidebar/'
import admin from '@/util/admin';
import { validatenull } from '@/util/validate';
import { calcDate } from '@/util/date.js';
import { getStore } from '@/util/store.js';
var pageDjs;
export default {
  components: {
    top,
    tags,
    sidebar
  },
  name: 'index',
  data() {
    return {
      //刷新token锁
      refreshLock: false,
      //刷新token的时间
      refreshTime: '',
    }
  },
  created() {
    
  },
  destroyed() {
    clearInterval(this.refreshTime)
    clearInterval(pageDjs)
  },
  mounted() {
    this.init()
  },
  computed: mapGetters(['userInfo', 'isLock', 'isCollapse', 'website', 'expires_in']),
  props: [],
  methods: {
    showCollapse() {
      this.$store.commit("SET_COLLAPSE")
    },
    // 屏幕检测
    init() {
      this.$store.commit('SET_SCREEN', admin.getScreen())
      window.onresize = () => {
        setTimeout(() => {
          this.$store.commit('SET_SCREEN', admin.getScreen())
        }, 0);
      }
    },
  }
}
</script>
