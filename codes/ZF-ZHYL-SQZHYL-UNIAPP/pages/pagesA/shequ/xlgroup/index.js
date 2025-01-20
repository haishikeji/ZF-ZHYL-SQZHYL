// pages/xlgroup/index.js
import {teamList} from '../../../../utils/http'
Page({

  /**
   * 页面的初始数据
   */
  data: {
    array:[],
    page:1,
    hasmore:true,

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
    dic.pageNo = that.data.page
    dic.pageSize = 10
    wx.showLoading({
      title: '加载中...',
    })
    teamList(dic).then(res=>{
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
          
          obj.content = obj.introduce.replace('<img','<img class="richimg"')
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


  godetail(e)
  {
    var id = e.currentTarget.dataset.id
    wx.navigateTo({
      url: './detail?id='+id,
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
      array:[],
      page:1,
      hasmore:true
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