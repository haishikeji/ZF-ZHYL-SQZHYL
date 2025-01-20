// pages/home/index.js
import {funcList,sosphone,centerinfo} from '../../../../utils/http'
import deviceUtil from "../../../../utils/dist/utils/device-util"
import util from '../../../../utils/util.js';
import wxParse from '../../../../wxParse/wxParse'

const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    capsuleBarHeight: deviceUtil.getNavigationBarHeight(),
    statusbarH :0,
    topbgcolor:'transparent',
    menus:[{name:'功能室服务',img:'../../images/h_1.png',url:'../funs/index'},
          {name:'孝老团队',img:'../../images/h_2.png',url:'../xlgroup/index'},
          {name:'善行义举',img:'../../images/h_3.png',url:"../sxyj/index"},
          {name:'预约服务',img:'../../images/h_4.png',url:"../yuyue/index?type=2"},
          {name:'幸福时光',img:'../../images/h_5.png',url:"../happy/index?type=1"},
          {name:'子女关爱',img:'../../images/h_6.png',url:"../love/index?type=1"},
          {name:'活动预告',img:'../../images/h_7.png',url:"../funs/list?id=8&name=活动预告"},
          {name:'个人中心',img:'../../images/h_8.png',url:"../user/index"},],

    list:[],

    isGoIndex: false,//点随便逛逛回首页
    iShidden: true,//是否隐藏
    isAuto: false,//是否自动授权

    detailFlag:true,
    ceninfo:{},
  },


  getdata()
  {
    var that = this
    var dic = {}
    dic.roomClass = 7
    dic.pageNo = 1
    dic.pageSize = 5
    wx.showLoading({
      title: '加载中...',
    })
    funcList(dic).then(res=>{
      wx.hideLoading({
        success: (res) => {},
      })

      if(res.code==0)
      {
        var arr = res.data.records
        for(var i=0;i<arr.length;i++)
        {
          var obj = arr[i]
          obj.content = obj.introduce || ''
        }

        that.setData({
          list:arr
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

  getcenterinfo()
  {
    var that = this
    centerinfo().then(res=>{
      if(res.code==0)
      {
        that.setData({
          ceninfo:res.data
        })
        wxParse.wxParse('content', 'html', res.data.content, this, 0);
      }else
      {
      }
    });
  },


  onPageScroll (e) {
    console.log(e)
    if(e.scrollTop>50)
    {
      this.setData({
        topbgcolor:"#1AC89E"
      })
    }else
    {
      this.setData({
        topbgcolor:"#transparent"
      })
    }
  },

  gopage(e)
  {
    console.log(app.globalData.userInfo,'-----')

    if(e.mark.index==5 || e.mark.index==7 ||  e.mark.index==13)
    {
      if(!app.globalData.isLog)
      {
        this.setData({
          iShidden:false
        })
      }
      else if(!app.globalData.userInfo)
      {
        wx.showToast({
          title: '用户信息失效，请重新登录',
          icon:'none'
        })
         util.logout()
      }
      else
      {
        if (e.mark.index==7) {
          wx.switchTab({
            url: e.mark.url,
          })
        } else {
          wx.navigateTo({
            url: e.mark.url,
          })
        }
      }
    }else
    {
      wx.navigateTo({
        url: e.mark.url,
      })
    }



  },

  getphone()
  {
    var that = this
    sosphone().then(res=>{
      if(res.code==0)
      {
        app.globalData.sosphone = res.data.phone
      }else
      {

      }
    });
  },

  goback()
  {
    wx.navigateBack({
      delta: 0,
    })
  },

  showDetail: function ()
    {
      this.setData({
        detailFlag:!this.data.detailFlag
      })
    },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    console.log(this.data.capsuleBarHeight)
    const res = wx.getSystemInfoSync()
    var h = res.statusBarHeight
    this.setData({
      statusbarH:h
    })

    this.getdata()
    this.getphone()
    this.getcenterinfo()
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