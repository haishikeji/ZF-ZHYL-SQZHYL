// pages/funs/index.js
import {funcCate,todayFood,dingmeal, userinfo} from '../../../../utils/http'
import util from '../../../../utils/util.js';
var QQMapWX = require('../../../../utils/qqmap-wx-jssdk1.2/qqmap-wx-jssdk.min.js');
var qqmapsdk;

const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    menus:[{name:'娱乐养老',img:'../../images/g_1.png',id:1},
    {name:'健康养老',img:'../../images/g_2.png',id:2},
    {name:'居家养老',img:'../../images/g_3.png',id:3},
    {name:'环境展示',img:'../../images/g_4.png',id:4},],
    list:[],

    isGoIndex: false,//点随便逛逛回首页
    iShidden: true,//是否隐藏
    isAuto: false,//是否自动授权

    uid:"",
    showOldMan: false,
    oldMan: [],
    oldManName: '',
    oldManId: '',
    popshow:false,

  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    // this.getdata()
    this.getOldMan()
    if(app.globalData.userInfo)
    {
      this.setData({
        uid:app.globalData.userInfo.id
      })
    }

    this.getfood()
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
     }
   })
},



  getdata()
  {
    var that = this
    funcCate().then(res=>{
      wx.hideLoading({
        success: (res) => {},
      })
      if(res.code==0)
      {
        that.setData({
          menus:res.data
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

  getfood()
  {
    var that = this
    todayFood({uid:that.data.uid,type:2}).then(res=>{

      if(res.code==0)
      {
        that.setData({
          list:res.data
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
    var index = e.currentTarget.dataset.index
    var obj = this.data.menus[index]
    wx.navigateTo({
      url: '../funs/list?id='+obj.id+'&name='+obj.name,
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

    var id = e.currentTarget.dataset.id
    var index = e.currentTarget.dataset.index
    var type = this.data.list[index].type
    this.setData({
      mealid:id,
      mealindex:index,
      currmealtype:type==1?'早':(type==2?'午':'晚')
    })
    this.getp_loc()

    

  },
  loc_yuyue () {
    let index = this.data.mealid
    let id = this.data.mealindex
    var that = this
    wx.showModal({
      title:"提示",
      content:'确定需要预定吗？',
      confirmColor:"#19CBA1",
      success(res)
      {
        if(res.confirm)
        {

          wx.requestSubscribeMessage({
            tmplIds: ['3hu_xEAuYWg58ITRJsvV55FbHH75IoJm43XX9gG-Jo4'],
            success(res) {
              console.log(res)
              if(res['3hu_xEAuYWg58ITRJsvV55FbHH75IoJm43XX9gG-Jo4']=='accept')
              {
                that.dingrequest(index,id)
              }else
              {
                that.dingrequest(index,id)
              }
            }
          })
        }
      }
    })
  },

  dingrequest()
  {
    var that = this
    wx.showLoading({
      title: '加载中...',
    })
    var dic ={}
    dic.oldManId = that.data.oldManId
    dic.type = 2
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