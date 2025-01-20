// pages/xlgroup/detail.js
import {teamDetail, teamList} from '../../../../utils/http'
const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    detail:{},
    
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    
    if(options.id)
    {
      this.getdata(options.id)
    }else
    {
       wx.showToast({
         title: '未找到队伍信息',
         icon:'none'
       })
       
       setTimeout(() => {
         app.appback()
       }, 2000);
    }
   
  },


  
  getdata(e)
  {
    var that = this
    teamDetail({id:e}).then(res=>{
      wx.hideLoading({
        success: (res) => {},
      })
      if(res.code==0)
      {
        
        that.setData({
          detail:res.data
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

  goconcat(e)
  {
    var phone = e.currentTarget.dataset.phone
    wx.makePhoneCall({
      phoneNumber: phone,
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