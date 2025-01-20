<template>
    <div class="content" v-loading="loading">
        <div class="execution" id='mapcon'>

        </div>
        <div class="searchview">
            <input type="text" placeholder="请输入姓名或手机号码" v-model="keyword" @keyup.enter="onenter">
            <img src="/img/l_1.png" alt="" @click="search()">
        </div>
        <!-- <div class="menubar">
            <div class="item">
                <img src="/img/sos.png" alt="">
                <span>2</span>
            </div>
            <div class="item hasline">
                <img src="/img/dc.png" alt="">
                <span>2</span>
            </div>
            <div class="item">
                <img src="/img/db.png" alt="">
                <span>2</span>
            </div>
        </div> -->

        <el-drawer
        title="我是标题"
        :visible.sync="drawer"
        :direction="direction"
        :with-header="false"
        :before-close="handleClose"
        size="300px">

            <div class="popview" v-loading="loading">
                <div class="poptitle">
                    <!-- <i class="el-icon-arrow-left" color="#fff"></i> -->
                    <div>搜索 {{keyword}}</div>
                </div>
                <div class="popresult">共{{total}}人</div>
                <div class="poplist" v-if='data.length>0'>
                    <div class="popitem" v-for="(item,i) in data" @click="godetail(item)">
                        <el-image
                        style="width: 98px; height: 113px"
                        :src="item.image"
                        fit="fill">
                            <div slot="error" class="image-slot">
                                <i class="el-icon-s-custom"></i>
                            </div>
                        </el-image>
                        <div class="item_r">
                            <div>姓<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>名：{{item.name}} <i @click.stop='locationClock(item.id)' v-if="item.location" class="el-icon-location" style="color: red;font-size: 18px;"></i></div>
                            <div>联系电话：{{item.phone}}</div>
                            <div>年<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>龄：{{item.age}}</div>
                            <div>身体状况：{{item.physical}}</div>
                        </div>
                    </div>
                </div>
                <div v-if='data.length==0' style="margin-top: 100px;"><el-empty :image-size="200"></el-empty></div>
                <div class="morebtn" v-if='hasmore' @click="loadmore">加载更多</div>
                <div class="nomore" v-if='!hasmore&&data.length>0'>没有更多了</div>
            </div>
        </el-drawer>


        <el-drawer
        title="我是标题"
        :visible.sync="drawer1"
        :direction="direction"
        :with-header="false"
        :before-close="handleClose1"
        size="300px">
            <div class="popview">
                <div class="poptitle" @click="closepop()">
                    <i class="el-icon-arrow-left" color="#fff"></i>
                    <div>个人信息</div>
                </div>
                <div class="popbody">
                    <div class="popinfo">
                        <el-image
                        style="width: 280px; height: 310px"
                        :src="curritem.image"
                        fit="fill">
                            <div slot="error" class="image-slot">
                                <i class="el-icon-s-custom"></i>
                            </div>
                        </el-image>
                        <div>姓<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>名：{{curritem.name}}</div>
                        <div>联系电话：{{curritem.phone}}</div>
                        <div>年<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span>龄：{{curritem.age}}</div>
                        <div>身体状况：{{curritem.physical}}</div>
                    </div>
                    <!-- <div class="popmenubtn jkbtn">
                        <img src="/img/jkda.png" alt="">
                        <div>健康档案</div>
                    </div> -->
                    <div class="popmenubtn dcbtn" @click="menuclick(2)">
                        <img src="/img/dcjl.png" alt="">
                        <div>订餐记录</div>
                    </div>
                    <div class="popmenubtn dbbtn" @click="menuclick(3)">
                        <img src="/img/dmdbjl.png" alt="">
                        <div>代买代办记录</div>
                    </div>

                </div>
            </div>
        </el-drawer>

    </div>
</template>

<script>
import {peopleList} from '@/api/location'
import TMap from 'TMap'
    export default {
        name: 'location',
        data() {
            return {
                originalLocation: {
                  latitude: '',
                  longitude: ''
                },
                data:[],
                total:0,
                page:1,

                markerLayer:null,
                map:null,
                infoWin:null,
                infoWin: null,

                drawer: false,
                direction: 'rtl',

                keyword:'',
                loading:false,


                drawer1:false,
                curritem:'',

                hasmore:true,
            }
        },
        computed: {

        },

        methods: {

            initMap() {
                //定义地图中心点坐标
                var center = new TMap.LatLng(this.originalLocation.latitude, this.originalLocation.longitude)
                //定义map变量，调用 TMap.Map() 构造函数创建地图
                var map = new TMap.Map(document.getElementById('mapcon'), {
                    center: center,//设置地图中心点坐标
                    zoom: 17.5,   //设置地图缩放级别
                    pitch: 43.5,  //设置俯仰角
                    rotation: 45    //设置地图旋转角度
                })
                this.map = map

                //创建并初始化MultiMarker
                this.markerLayer = new TMap.MultiMarker({
                    map: map,  //指定地图容器
                    //样式定义
                    styles: {
                        //创建一个styleId为"myStyle"的样式（styles的子属性名即为styleId）
                        "myStyle": new TMap.MarkerStyle({
                            "width": 25,  // 点标记样式宽度（像素）
                            "height": 35, // 点标记样式高度（像素）
                            "src": '/img/dw.png',  //图片路径
                            //焦点在图片中的像素位置，一般大头针类似形式的图片以针尖位置做为焦点，圆形点以圆心位置为焦点
                            "anchor": { x: 16, y: 32 }
                        })
                    },
                });
            },

            //创建marker事件
            createMarker() {



                var data = this.data
                var arr = []
                for(let i=0;i<data.length;i++)
                {
                    var dic = {}
                    dic.id = data[i].id
                    dic.styleId = 'myStyle'
                    dic.position =  new TMap.LatLng(data[i].latitude, data[i].longitude)
                    dic.address = data[i].address
                    arr.push(dic)
                }
                this.markerLayer.add(arr)

                //添加提示窗
                this.infoWin = new TMap.InfoWindow({
                    map: this.map,
                    enableCustom: true,
                    position: new TMap.LatLng(this.data[0].latitude, this.data[0].longitude),
                    offset: { x: 0, y: -42 },
                    content:"<div class='wincss'><div>"+data[0].address+"</div><img class='sj' src='/img/sj.png'><div>"
                });

                this.infoWin.close()

                var that = this
                this.markerLayer.on("click", function (evt) {
                //设置infoWindow
                that.infoWin.open(); //打开信息窗
                that.infoWin.setPosition(evt.geometry.position);//设置信息窗位置
                that.infoWin.setContent("<div class='wincss'><div>"+evt.geometry.address+"</div><img class='sj' src='/img/sj.png'><div>");//设置信息窗内容

                // that.drawer = true
                console.log('^^^^^^^^')
             })

            },
            locationClock (id) {
                this.infoWin.close()
                const data = this.markerLayer.geometries.find(item => { return item.id === id })
                console.log(data)
                this.infoWin = new TMap.InfoWindow({
                    map: this.map,
                    enableCustom: true,
                    position: new TMap.LatLng(data.position.lat, data.position.lng),
                    offset: { x: 0, y: -42 },
                    content:"<div class='wincss'><div></div><img class='sj' src='/img/sj.png'><div>"
                });
                this.infoWin.open(); //打开信息窗
                this.infoWin.setPosition(data.position);//设置信息窗位置
                this.infoWin.setContent("<div class='wincss'><div>"+data.address+"</div><img class='sj' src='/img/sj.png'><div>");//设置信息窗内容
                this.drawer = false
                this.drawer1 = false
            },
            handleClose()
            {
                this.drawer = false
            },
            handleClose1()
            {
                this.drawer1 = false
            },

            search()
            {
                // window.open('http://192.168.5.108:9300/oauth/token?username=admin&password=admina.5d&randomStr=38771640223918744&code=&grant_type=password&scope=server')
                this.drawer = true
                this.page = 1
                this.data = []
                this.hasmore = true
                this.getList()
            },
            onenter()
            {
                this.drawer = true
                console.log(this.keyword)
                this.data = []
                this.page = 1
                this.hasmore = true
                this.getList()
            },
            closepop()
            {
                this.drawer1 = false
            },
            getList() {
                this.loading = true
                var params = {}
                params.nameOrPhone = this.keyword

                peopleList(Object.assign({
                     current: this.page,
                     size: 20
                }, params)).then(response => {
                    console.log(response)
                    var arr = response.data.data.records
                    if(arr.length<20)
                    {
                        this.hasmore = false
                    }
                    this.data = this.data.concat(arr)
                    this.originalLocation = {
                      latitude: '',
                      longitude: ''
                    }
                    this.data.forEach(item => {
                      if (item.location) {
                        this.originalLocation = {
                          latitude: this.originalLocation.latitude ||  Number(item.location.split(',')[0]) || 38.287622,
                          longitude: this.originalLocation.longitude || Number(item.location.split(',')[1]) || 109.719231
                        }
                        item.latitude = item.location.split(',')[0]
                        item.longitude = item.location.split(',')[1]
                      }
                    })
                    this.total = response.data.data.total
                    this.loading = false
                    this.initMap()
                    this.createMarker()
                }).catch(() => {
                    this.loading = false
                })
            },

            godetail(e)
            {
                this.drawer1 = true
                this.curritem = e
            },
            menuclick(e)
            {
                if(e==2)
                {
                    this.$router.push('/mealRecord/mealrecord/index?name='+this.curritem.name);
                }else if(e==3)
                {
                    this.$router.push('/replaceBuyRecord/replacebuyrecord/index?name='+this.curritem.name);
                }

            },
            loadmore()
            {
                this.page++
                this.getList()
            },

        },
        mounted () {

            this.getList()
        },
        destroyed()
        {
            this.map.destroy()
        },
    }
</script>

<style>
.content
{
    width: 100%;
    height: 100%;
    position: relative;
}
    .execution
    {
        width: 100%;
        height: 100%;
    }
    .searchview
    {
        width: 350px;
        height: 44px;
        position: absolute;
        left: 20px;
        top: 10px;
        background-color: #fff;
        border: 0.5px solid #DEDEDE;
        box-sizing: border-box;
        box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
        border-radius: 5px;
        display: flex;
        align-items: center;
        padding: 0 10px;
        justify-content: space-between;
        z-index: 1000;
    }
    .searchview input
    {
        width: 300px;
        font-size: 16px;
        border: none;
    }
    .searchview img
    {
        width: 24px;
        height: 24px;
    }
    .menubar
    {
        position: absolute;
        left: 20px;
        bottom: 50px;
        height: 45px;
        box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
        border-radius: 5px;
        display: flex;
        align-items: center;
        width: 348px;
        background-color: #fff;
        z-index: 10000;
        padding: 10px 0;
    }
    .menubar .item
    {
        width: 116px;
        display: flex;
        align-items: center;
        justify-content: center;
        font-size: 16px;
    }
    .menubar .item img
    {
        width: 36px;
        height: 36px;
        margin-right: 10px;
    }
    .hasline
    {
        border-left: 1px solid #DEDEDE;
        border-right: 1px solid #DEDEDE;
    }
    .wincss
    {
        border: 1px solid red;
        padding: 10px;
        box-sizing: border-box;
        background-color: #fff;
        border-radius: 10px;
        position: relative;
    }
    .wincss .sj
    {
        position: absolute;
        bottom: -5px;
        left: 49%;
        width: 10px;
        height: 5px;
    }

    .poptitle
    {
        width: 100%;
        height: 44px;
        background-color: #1AAE70;
        color: #fff;
        display: flex;
        align-items: center;
        padding: 0 10px;
        box-sizing: border-box;
        cursor: pointer;
    }
    .poplist
    {
        padding: 15px;
        box-sizing: border-box;
    }
    .popitem
    {
        display: flex;
        align-items: center;
        /* margin-bottom: 15px; */
        cursor: pointer;
        border-bottom: 1px solid #DEDEDE;
        padding: 10px 0;
        box-sizing: border-box;
    }

    .popitem .item_r
    {
        margin-left: 10px;
        margin-top: 5px;
        font-size: 15px;
    }
    .popitem .item_r div
    {
        margin-bottom: 9px;
    }
    .popitem .item_r span
    {
        opacity: 0;
    }


    .popbody
    {
        padding: 30px;
        box-sizing: border-box;
    }
    .popinfo
    {
        background-color: #f3f1f1;
        padding: 30px;
        box-sizing: border-box;
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;
        padding-top: 20px;
    }
     .popinfo div
     {
         width: 100%;
         margin-top: 15px;
     }
     .popinfo div span
     {
         opacity: 0;
     }
     .popmenubtn
     {
         width: 100%;
         height: 40px;
         display: flex;
         align-items: center;
         justify-content: center;
         margin: auto;
         margin-top: 30px;
         border-radius: 20px;
         cursor: pointer;
     }
      .popmenubtn img
     {
         width: 24px;
         height: 24px;
         margin-right: 10px;
     }
     .jkbtn
     {
         border: 1px solid #FF9D51;
         color: #FF9D51;
     }

     .dcbtn
     {
         border: 1px solid #568EFE;
         color: #568EFE;
     }

     .dbbtn
     {
         border: 1px solid #FF6868;
         color: #FF6868;
     }

    .popresult
     {
         padding: 10px 0 0 20px ;
         font-size: 14px;
         color: #999;
     }
     .morebtn
     {
         margin: 20px auto;
         width: 50%;
         height: 40px;
         line-height: 40px;
         text-align: center;
         border: 1px solid #999;
         color: #999;
         border-radius: 20px;
         cursor: pointer;
     }
     .nomore
     {
          margin: 20px auto;
          color: #999;
          text-align: center;
     }
     .image-slot
     {
         width: 100%;
         height: 100%;
         display: flex;
         align-items: center;
         justify-content: center;
         background-color: #f6f6f6;
         font-size: 42px;
         color:#999;
     }

</style>
