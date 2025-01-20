// pages/pagesA/shequ/health/index.js
import { getOldManHealthPage } from '../../../../utils/http'
import util from '../../../../utils/util'
var QQMapWX = require('../../../../utils/qqmap-wx-jssdk1.2/qqmap-wx-jssdk.min.js');
var qqmapsdk;
const app = getApp()
Page({

    /**
     * 页面的初始数据
     */
    data: {
      iShidden: true,
      showMap: false,
      latitude: 0,
      longitude: 0,
      page: 1,
      pageSize: 10,

      markers: [
        {
          id: 1,
          latitude: 0,
          longitude: 0,
          title: '老人',
          iconPath: '../../images/lr-1.png',
          width: '30',
          height: '30'
        },
        {
          id: 2,
          latitude: 0,
          longitude: 0,
          title: '我',
          iconPath: '../../images/lr-2.png',
          width: '30',
          height: '30'
        }
      ],
      detail: ''
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad(options) {

    },

    /**
     * 生命周期函数--监听页面初次渲染完成
     */
    onReady() {

    },

    /**
     * 生命周期函数--监听页面显示
     */
    onShow() {
      if(!app.globalData.isLog) {
        this.setData({
          iShidden:false
        })
      } else if(!app.globalData.userInfo){
        wx.showToast({
          title: '用户信息失效，请重新登录',
          icon:'none'
        })
        util.logout()
      } else {
        this.getHealthPage()
      }
    },

    getHealthPage () {
      wx.showLoading({
        title: '加载中...',
        mask: true
      })
      let date = util.formatTime(new Date())+':00'
      const data = {
        page: this.data.page,
        pageSize: this.data.pageSize
      }
      getOldManHealthPage(data).then(res => {
        let  detail = res.data.records
        detail.forEach((item, index) => {
          if (item.watchUpdateTime) {
            let timeNum =  this.changeTimes(date, '/') - this.changeTimes(item.watchUpdateTime, '-')
            timeNum = timeNum/60
            if (timeNum <= 15) {
              item.status = false
            } else {
              item.status = true
            }
          } else {
            item.status = true
          }
          if (item.location) {
            this.getMapInfo(item.location, index)
          }
        })
        this.setData({
          detail
        })
        wx.hideLoading()
      })
    },

    goMap (e) {
      this.setData({
        showMap: true
      })
      const info = e.mark.location.split(',')
      let markers = this.data.markers
      markers[0].latitude = info[0],
      markers[0].longitude = info[1],
      wx.getLocation({
          type: 'wgs84',
          success (res) {
            markers[1].latitude = res.latitude,
            markers[1].longitude = res.longitude
          }
       })
      this.setData({
        markers,
        latitude: info[0],
        longitude: info[1],
      })
    },
    closeMap (e) {
      this.setData({
        showMap: false
      })
    },

    changeTimes (time, symbol) {
      var temp = time.split(' ')
      var arr = temp[0].split(symbol)
      var brr = temp[1].split(':')
      if(brr.length == 3){
          var timestamp = new Date(Date.UTC(arr[0], arr[1] - 1, arr[2], +brr[0] - 8, brr[1], brr[2]))
      }else if(brr.length == 2){
          var timestamp = new Date(Date.UTC(arr[0], arr[1] - 1, arr[2], +brr[0] - 8, brr[1]))
      }
      var timestamp = timestamp.getTime() / 1000;
      return timestamp;
    },

    getMapInfo (e, index) {
      const info = e.split(',')
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
      qqmapsdk.reverseGeocoder({
        location: {
          latitude: info[0],
          longitude: info[1],
        },
        success: function (res) {
          let detail = self.data.detail
          detail[index].address = res.result.address_component.province+res.result.address_component.city+res.result.formatted_addresses.recommend || ''
          self.setData({
            detail
          })
        },
      });
      
    },

    /**
     * 生命周期函数--监听页面隐藏
     */
    onHide() {

    },

    /**
     * 生命周期函数--监听页面卸载
     */
    onUnload() {

    },

    /**
     * 页面相关事件处理函数--监听用户下拉动作
     */
    onPullDownRefresh() {

    },

    /**
     * 页面上拉触底事件的处理函数
     */
    onReachBottom() {

    },

    /**
     * 用户点击右上角分享
     */
    onShareAppMessage() {

    }
})