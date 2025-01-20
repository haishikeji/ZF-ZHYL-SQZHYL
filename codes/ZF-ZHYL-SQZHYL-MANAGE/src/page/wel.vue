<template>
  <div class="contain" v-loading="tableLoading1">
    <div class="w-title">
      <div class="w-line"></div>
      <div class="w-name">数据总览</div>
    </div>
    <el-row :gutter="20">
    <el-col :span="8">
      <div class="grid-content">
        <div class="grid-left">
          <div class="grid-t">1200</div>
          <div class="grid-b">
            <img src="/img/lrrs.png" alt="">
            <div>老人人数</div>
          </div>
        </div>
        <img src="/img/h1.png" alt="" style="height:65px">
      </div>
    </el-col>
    <!-- <el-col :span="6">
      <div class="grid-content">
        <div class="grid-left">
          <div class="grid-t">2200</div>
          <div class="grid-b">
            <img src="/img/sbsl.png" alt="">
            <div>设备数量</div>
          </div>
        </div>
        <img src="/img/h2.png" alt="" style="height:65px">
      </div>
    </el-col> -->
    <el-col :span="8">
      <div class="grid-content">
        <div class="grid-left">
          <div class="grid-t">1600</div>
          <div class="grid-b">
            <img src="/img/dcsl.png" alt="">
            <div>订餐数量</div>
          </div>
        </div>
        <img src="/img/h3.png" alt="" style="height:65px">
      </div>
    </el-col>
    <el-col :span="8">
      <div class="grid-content">
        <div class="grid-left">
          <div class="grid-t">310</div>
          <div class="grid-b">
            <img src="/img/dmdb.png" alt="">
            <div>代买代办数量</div>
          </div>
        </div>
        <img src="/img/h4.png" alt="" style="height:65px">
      </div>
    </el-col>

  </el-row>

  <div class="w-title">
      <div class="w-line"></div>
      <div class="w-name">老人数据分析</div>
    </div>
  <el-row :gutter="20">
     <el-col :span="10">
        <div class="grid-content">
          <div class="qLine" ref="qLine" id="qLine"></div>
        </div>
      </el-col>
      <el-col :span="7">
        <div class="grid-content">
          
        </div>
      </el-col>
      <el-col :span="7">
        <div class="grid-content">
          
        </div>
      </el-col>
  </el-row>

   <el-row :gutter="20">
     <el-col :span="12">
         <div class="w-title">
              <div class="w-line"></div>
              <div class="w-name">订餐数量</div>
            </div>
        <div class="grid-content">

        </div>
      </el-col>
      <el-col :span="12">
          <div class="w-title">
              <div class="w-line"></div>
              <div class="w-name">代买代办数量</div>
            </div>
        <div class="grid-content">
          
        </div>
      </el-col>
  </el-row>
  </div>
</template>

<script>
  import {mapGetters} from 'vuex';

  export default {
    name: 'wel',
    data() {
      return {
        activeNames: ['1', '2', '3', '4'],
        DATA: [],
        text: '',
        actor: '',
        count: 0,
        isText: false,
        detail:{},
        tableLoading1:false,

        searchForm: {},

        tableData: [],

        page: {
            total: 0, // 总页数
            currentPage: 1, // 当前页数
            pageSize: 20 // 每页显示多少条
        },

        tableLoading: false,

         myChart: [],

      }
    },
    computed: {
      ...mapGetters(['website']),

      zhuoption:function()
      {
        return{
           
            xAxis: {
              type: 'category',
              data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
            },
            yAxis: {
              type: 'value'
            },
            series: [
              {
                data: [120, 200, 150, 80, 70, 110, 130],
                type: 'bar'
              }
            ]
          
        }
      },
    },

     mounted() {

         this.setChart(this.$refs.qLine, 'qLine');   
         this.goscorereocrd()
      },
      methods: {

      goscorereocrd:function(row)
      {

        this.myChart.qLine.setOption(this.zhuoption);

      },

       //初始化图表后丢进myChart
      setChart: function (refName, myChartname) {
        this.myChart[myChartname] = echarts.init(refName);
      },
    }
  }
</script>

<style scoped="scoped" lang="scss">
  .wel-contailer {
    position: relative;
  }
  .contain
  {
    padding-top: 0;
  }
  .banner-text {
    position: relative;
    padding: 0 20px;
    font-size: 20px;
    text-align: center;
    color: #333;
  }

  .banner-img {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    opacity: 0.8;
    display: none;
  }

  .actor {
    height: 250px;
    overflow: hidden;
    font-size: 18px;
    color: #333;
  }

  .actor:after {
    content: '';
    width: 3px;
    height: 25px;
    vertical-align: -5px;
    margin-left: 5px;
    background-color: #333;
    display: inline-block;
    animation: blink 0.4s infinite alternate;
  }

  .typeing:after {
    animation: none;
  }
  .w-title
  {
    padding: 10px 0 20px 0;
    display: flex;
    align-items: center;
    font-size: 18px;
  }
  .w-line
  {
    background: #1AAE70;
    width: 4px;
    height: 20px;
    border-radius: 8px;
    margin-right: 10px;
  }
  .w-name
  {
    height: 20px;
    line-height: 20px;
  }

  .grid-content
  {
    height: 131px;
    background-color: #fff;
    border-radius: 10px;
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 0 60px;
    box-sizing: border-box;
  }
  .grid-left
  {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    margin-right: 40px;
  }
  .grid-t
  {
    font-size: 50px;
    font-weight: bold;
  }
  .grid-b
  {
    font-size: 13px;
    display: flex;
    align-items: center;
  }
  .grid-b img
  {
    width: 24px;
    height: 24px;
    margin-right: 10px;
  }

  @keyframes blink {
    to {
      opacity: 0;
    }
  }
</style>
