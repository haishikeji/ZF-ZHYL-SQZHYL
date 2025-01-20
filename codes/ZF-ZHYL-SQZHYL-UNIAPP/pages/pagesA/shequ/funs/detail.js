// pages/funs/detail.js
import {funcDetail,replacebuydetail} from '../../../../utils/http'
import wxParse from '../../../../wxParse/wxParse'

Page({

  /**
   * 页面的初始数据
   */
  data: {
    detail:'',
    content:'',

  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.getdata(options.id,options.type)
  },

  getdata(e,t)
  {
    var that = this
    var func
    if(t==1)
    {
      func = replacebuydetail
    }else
    {
      func = funcDetail
    }
    func({id:e}).then(res=>{
      wx.hideLoading({
        success: (res) => {},
      })
      if(res.code==0)
      {
        that.setData({
          detail:res.data,
          // content:res.data.content.replace(/\<img/gi, '<img style="display:block;max-width:100%;height:auto;margin:0 auto;padding:0"')
        })
        wxParse.wxParse('content', 'html', res.data.content, this, 0);
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