<template>
  <div class="login-container" v-loading="loading">
    <div class="login-weaper  animated bounceInDown">

      <div class="login-border">
        <div class="login-main">
          <userLogin v-if="activeName==='user'" :userlogin='user_name' @getAction="callAction"></userLogin>
        </div>
      </div>
      <div class="login-left">
        <!-- <img class="img"
             src="/img/logo.png"
             alt="">
        <p class="title">{{website.infoTitle}}</p>
        <p>©2020 v1.0.0</p> -->
      </div>
    </div>
  </div>
</template>
<script>
  import '@/styles/login.scss'
  import userLogin from "./userlogin";
  import {mapGetters} from "vuex";
  import {getStore, setStore} from "@/util/store";
  import {dateFormat} from "@/util/date";
  import {validatenull} from "@/util/validate";

  export default {
    name: "login",
    components: {
      userLogin
    },
    data() {
      return {
        activeName: "user",
        user_name:{},
        loading:false
      };
    },
    watch: {},
    created() {
    },
    mounted() {
      if(this.$route.query && this.$route.query.name)
      {
        this.loading = true
        var dic = {}
        dic.name = this.$route.query.name
        dic.password = this.$route.query.password
        this.user_name = dic
      }
    },
    computed: {
      ...mapGetters(["website"])

    },
    props: [],
    methods: {
      callAction()
      {
        this.loading = false
      }
    }
  };
</script>

