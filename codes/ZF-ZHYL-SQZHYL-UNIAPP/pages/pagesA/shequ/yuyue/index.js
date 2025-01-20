// pages/yuyue/index.js
import {yestodayFood,todayFood,dingmeal,userinfo} from '../../../../utils/http'
import util from '../../../../utils/util.js';
var QQMapWX = require('../../../../utils/qqmap-wx-jssdk1.2/qqmap-wx-jssdk.min.js');
var qqmapsdk;

const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    array:[],
    array1:[],
    type:'',//1代买2自己
    uid:'',

    isGoIndex: false,//点随便逛逛回首页
    iShidden: true,//是否隐藏
    isAuto: false,//是否自动授权


    latitude: '',
    longitude: '',
    defaultKeyword: '住宅',
    popshow:false,
    // currentRegion:'',

    authLoc:false,

    mealid:'',
    mealindex:0,
    currmealtype:'',
    sendtype:"",

    showOldMan: false,
    oldMan: [],
    oldManName: '',
    oldManId: ''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    console.log(app)
    this.getOldMan()
    if(app.globalData.userInfo)
    {
      this.setData({
        uid:app.globalData.userInfo.id
      })
    }
    this.setData({
      type:options.type
    })
    this.getfood()
    this.getyesfood()


    var t = this
    wx.getSetting({
      withSubscriptions: true,
      success(res)
      {
        var statu = res.authSetting;
        if(statu['scope.userLocation'])
        {
          t.setData({
            authLoc:true
          })
        }else
        {
          // t.getp_loc()
        }
      }
    })
  },


    /**
   * 授权回调
  */
 onLoadFun: function (e) {
  console.log(e)
  this.getUserInfo(e.detail.openid);
},
getUserInfo:function(e)
{
  var that = this
  wx.showLoading({
    title: '加载中...',
  })
   wx.request({
     url: app.globalData.url +'allUser/read/AllUserList',
     method: 'put',
     header: {
       "Content-Type": "application/x-www-form-urlencoded",
       "Authorization": app.globalData.token
     },
     data:{
       openid:e
     },
     success(res)
     {
        wx.hideLoading()
        app.globalData.userInfo = res.data.data
        that.setData({
          uid:res.data.data.id
        })
     }
   })
},


  getfood()
  {
    var that = this
    todayFood({uid:that.data.uid,type:that.data.type}).then(res=>{

      if(res.code==0)
      {
        that.setData({
          array:res.data
        })
      }else
      {
        wx.showToast({
          title: res.msg,
          icon:'none'
        })
      }
    });
  },

  getyesfood()
  {
    var that = this
    yestodayFood().then(res=>{

      if(res.code==0)
      {
        that.setData({
          array1:res.data
        })
      }else
      {
        wx.showToast({
          title: res.msg,
          icon:'none'
        })
      }
    });
  },


  gopage(e)
  {
    wx.navigateTo({
      url: e.mark.url,
    })
  },

  yyclick(e)
  {
    if(!app.globalData.isLog)
    {
      this.setData({
        iShidden:false
      })
      return
    }else if(!app.globalData.userInfo)
    {
      wx.showToast({
        title: '用户信息失效，请重新登录',
        icon:'none'
      })
       util.logout()
       return
    }
    // else if(!this.data.authLoc)
    // {
      // this.getp_loc()
    //   return
    // }
    // this.setData({
    //   popshow:true
    // })
    // return

    // var that = this

    var id = e.currentTarget.dataset.id
    var index = e.currentTarget.dataset.index
    var type = this.data.array[index].type
    this.setData({
      mealid:id,
      mealindex:index,
      currmealtype:type==1?'早':(type==2?'午':'晚')
    })
    this.getp_loc()

    // wx.showModal({
    //   title:"提示",
    //   content:'确定需要预定吗？',
    //   confirmColor:"#19CBA1",
    //   success(res)
    //   {
    //     if(res.confirm)
    //     {

    //       wx.requestSubscribeMessage({
    //         tmplIds: ['3hu_xEAuYWg58ITRJsvV55FbHH75IoJm43XX9gG-Jo4'],
    //         success(res) {
    //           console.log(res)
    //           if(res['3hu_xEAuYWg58ITRJsvV55FbHH75IoJm43XX9gG-Jo4']=='accept')
    //           {
    //             that.dingrequest(index,id)
    //           }else
    //           {
    //             that.dingrequest(index,id)
    //           }

    //         }
    //       })

    //     }
    //   }
    // })

  },
  loc_yuyue()
  {
    var that = this
    wx.requestSubscribeMessage({
      tmplIds: ['3hu_xEAuYWg58ITRJsvV55FbHH75IoJm43XX9gG-Jo4'],
      success(res) {
        console.log(res)
        if(res['3hu_xEAuYWg58ITRJsvV55FbHH75IoJm43XX9gG-Jo4']=='accept')
        {
          that.dingrequest()
        }else
        {
          that.dingrequest()
        }

      }
    })
  },

  closepop()
  {
    this.setData({
      popshow:false
    })
  },
  sendloc(e)
  {
    this.setData({
      sendtype:e.currentTarget.dataset.type,
      popshow:false
    })
    this.loc_yuyue()
  },
  dingrequest()
  {
    var that = this
    wx.showLoading({
      title: '加载中...',
    })
    var dic ={}
    dic.oldManId = that.data.oldManId
    dic.type = that.data.type
    dic.mealId = that.data.mealid
    if(that.data.sendtype==1)
    {
      dic.address = that.data.currentRegion
      dic.lng = that.data.longitude
      dic.lat = that.data.latitude
    }
    dingmeal(dic).then(res=>{

      wx.hideLoading({
        success: (res) => {},
      })
      if(res.code==0)
      {
        wx.showToast({
          title: '预定成功',
          icon:'none'
        })
        this.getfood()
        this.getyesfood()
        var arr = that.data.array
        var obj = arr[that.data.mealindex]
        obj.orderState = 1
        that.setData({
          array:arr
        })

      }else
      {
        wx.showToast({
          title: res.msg,
          icon:'none'
        })
      }
    });
  },



  getp_loc()
  {
    let self =this;
    self.mapCtx = wx.createMapContext('myMap')
    // 实例化API核心类
    qqmapsdk = new QQMapWX({
      key: 'IASBZ-KAH36-7TCSZ-EXRTJ-IZC77-VWFD7'
    });
    wx.showLoading({
      title: '加载中'
    });
    //定位
    wx.getLocation({
      type: 'wgs84',
      success(res) {
        console.log(res,'------------------------------')
        const latitude = res.latitude
        const longitude = res.longitude
        const speed = res.speed
        const accuracy = res.accuracy
        //逆地址解析
        qqmapsdk.reverseGeocoder({
          location: {
            latitude: latitude,
            longitude: longitude,

          },
          success: function (res) {
            wx.hideLoading({});
            console.log(res,1111)
            self.setData({
              latitude: latitude,
              longitude: longitude,
              currentRegion: res.result.address_component.province+res.result.address_component.city+res.result.formatted_addresses.recommend,
              popshow:true
            })

          },
        });
      },
      fail(err) {
        console.log(err,2222)
        wx.hideLoading({});

        wx.showToast({
          title: '定位失败',
          icon: 'none',
          duration: 1500
        })

      }
    })
  },

    //监听拖动地图，拖动结束根据中心点更新页面
    mapChange: function (e) {
      let self = this;
      if (e.type == 'end' && (e.causedBy == 'scale' || e.causedBy == 'drag')){
        self.mapCtx.getCenterLocation({
          success: function (res) {
            console.log(res)
            self.setData({
              // suggestion:[],
              latitude: res.latitude,
              longitude: res.longitude,
            })

              //你地址解析
        qqmapsdk.reverseGeocoder({
          location: {
            latitude: res.latitude,
            longitude: res.longitude,

          },
          success: function (res) {
            wx.hideLoading({});
            console.log(res,1111)
            self.setData({
              latitude: res.latitude,
              longitude: res.longitude,
              currentRegion: res.result.address_component.province+res.result.address_component.city+res.result.formatted_addresses.recommend,
            })

          },
        });


          }
        })
      }

    },
    //重新定位
    reload: function () {
      this.getp_loc();
    },

    // 根据关键词搜索附近位置
  nearby_search: function () {
    var self = this;
    wx.hideLoading();
    wx.showLoading({
      title: '加载中'
    });
    // 调用接口
    qqmapsdk.getSuggestion({
      keyword: self.data.keyword,  //搜索关键词
      //boundary: 'nearby(' + self.data.latitude + ', ' + self.data.longitude + ', 1000, 16)',
      location: self.data.latitude + ',' + self.data.longitude,
      page_size: 20,
      page_index: 1,
      success: function (res) { //搜索成功后的回调
        //console.log(res.data)
        var sug = [];
        for (var i = 0; i < res.data.length; i++) {
          sug.push({ // 获取返回结果，放到sug数组中
            title: res.data[i].title,
            id: res.data[i].id,
            addr: res.data[i].address,
            // province: res.data[i].ad_info.province,
            // city: res.data[i].ad_info.city,
            // district: res.data[i].ad_info.district,
            latitude: res.data[i].location.lat,
            longitude: res.data[i].location.lng
          });
        }
        self.setData({
          selectedId: 0,
          centerData: sug[0],
          nearList: sug,
          suggestion: sug
        })
        self.addMarker(sug[0]);
        console.log(sug,'*****');
      },
      fail: function (res) {
        //console.log(res);
      },
      complete: function (res) {
        //console.log(res);
      }
    });
  },

    //地图标记点
    addMarker: function (data) {
      //console.log(data)
      //console.log(data.title)
      var mks = [];
      mks.push({ // 获取返回结果，放到mks数组中
        title: data.title,
        id: data.id,
        addr: data.addr,
        province: data.province,
        city: data.city,
        district: data.district,
        latitude: data.latitude,
        longitude: data.longitude,
        iconPath: "/pages/pagesA/images/my_marker.png", //图标路径
        width: 25,
        height: 25
      })
      this.setData({ //设置markers属性，将搜索结果显示在地图中
        markers: mks,
        // currentRegion: {
        //   province: data.province,
        //   city: data.city,
        //   district: data.district,
        // }
      })
      wx.hideLoading({});
    },
    // 获取老人详细列表
    getOldMan () {
      wx.showLoading({
        title: '加载中...',
        mask:true
      })
      var that = this
      userinfo({type:2}).then(res=>{
        wx.hideLoading({
          success: (res) => {},
        })
        if(res.code==0) {
          let arr = res.data.oldManInfo.filter(item => {
            if (item.cerState == 2) {
              return item
            }
          })
          that.setData({
            oldMan: arr || '',
            latitude: arr[0].latitude || '',
            longitude: arr[0].longitude || '',
            oldManName: arr[0].name || '',
            oldManId: arr[0].id || '',
          })
          // wx.setStorageSync('oldinfo', res.data)
        } else {
          wx.showToast({
            title: res.msg,
            icon:'none'
          })
        }
      });
      console.log(this.data, 999999999)
    },
    showOldMan () {
      this.setData({
        showOldMan:true
      })
    },
    oldManConfirm (e) {
      this.setData({
        oldManId: e.detail.value.id,
        oldManName: e.detail.value.name,
        latitude: e.detail.value.latitude || '',
        longitude: e.detail.value.longitude || '',
        showOldMan:false
      });
    },
    oldManCancel () {
      this.setData({
        showOldMan:false
      })
    },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})