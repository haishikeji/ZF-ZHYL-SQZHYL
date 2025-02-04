// pages/fiveg/card.js
import {jkrecord} from '../../../../utils/http'
import util from '../../../../utils/util.js';
const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    detail:null,
    userdetail:null
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.getdata(options.id)
  },

  getdata(e)
  {
    var that = this
    jkrecord({id:e}).then(res=>{
      wx.hideLoading({
        success: (res) => {},
      })
      if(res.code==0)
      {
        that.setData({
          detail:res.data.wxOldPhrVo,
          userdetail:res.data.wxOldManVo
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