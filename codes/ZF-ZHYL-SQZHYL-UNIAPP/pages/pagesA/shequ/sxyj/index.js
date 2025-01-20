// pages/sxyj/index.js
import {funcList} from '../../../../utils/http'
Page({

  /**
   * 页面的初始数据
   */
  data: {
    active:0,
    array:[],
    page:1,
    
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.getdata()
  },

  getdata()
  {
    var that = this
    var dic = {}
    dic.roomClass = 5
    dic.pageNo = that.data.page
    dic.pageSize = 10
    dic.type = parseInt(that.data.active)+1
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
          obj.content = obj.content.replace('<img','<img class="richimg"')
        }

        that.setData({
          array:that.data.array.concat(arr)
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

  onChange(e)
  {
    console.log(e)
    this.setData({
      active:e.detail.index,
      page:1,
      hasmore:true,
      array:[]
    })
    this.getdata()
  },

  gopage()
  {
    wx.navigateTo({
      url: './form',
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
    this.setData({
      page:1,
      hasmore:true,
      array:[]
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