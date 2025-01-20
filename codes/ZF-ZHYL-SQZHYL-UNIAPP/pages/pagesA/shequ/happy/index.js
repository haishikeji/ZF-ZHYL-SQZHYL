// pages/happy/index.js
import {funcList} from '../../../../utils/http'
Page({

  /**
   * 页面的初始数据
   */
  data: {
    list:[],
    id:'',
    page:1,
    hasmore:true,
    banner:''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var str = ''
    var id = ''
    var b = ''
    if(options.type==1)
    {
      str = '幸福时光'
      id = 7
      b = 'https://cwl.yt.hnpinxun.cn/banner/6.png'
    }else if(options.type==2)
    {
      str = '暖心服务'
      id = 10
      b = 'https://cwl.yt.hnpinxun.cn/banner/4.png'
    }else
    {
      str = '健康养生'
      id = 9
      b = 'https://cwl.yt.hnpinxun.cn/banner/3.png'
    }
    this.setData({
      name:str,
      id:id,
      banner:b
    })
    wx.setNavigationBarTitle({
      title: str,
    })
    this.getdata()
  },


  getdata()
  {
    var that = this
    var dic = {}
    dic.roomClass = that.data.id
    dic.pageNo = that.data.page
    dic.pageSize = 10
    wx.showLoading({
      title: '加载中...',
    })
    funcList(dic).then(res=>{
      wx.hideLoading({
        success: (res) => {},
      })
      wx.stopPullDownRefresh({
        success: (res) => {},
      })
      if(res.code==0)
      {
        var arr = res.data.records
        if(arr.length<10)
        {
          that.setData({
            hasmore:false
          })
        }
        for(var i=0;i<arr.length;i++)
        {
          var obj = arr[i]
          obj.content = obj.introduce || ''
        }

        that.setData({
          list:that.data.list.concat(arr)
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
    this.setData({
      page:1,
      hasmore:true,
      list:[]
    })
    this.getdata()
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
    if(this.data.hasmore)
    {
      this.data.page++
      this.getdata()
    }
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})