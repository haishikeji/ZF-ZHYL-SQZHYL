// pages/pagesA/shequ/apparatus/index.js
import { queryEquipmentInfo, boundEquipmentInfo, unBoundEquipmentInfo } from '../../../../utils/http'
import Dialog from '@vant/weapp/dialog/dialog';
const app = getApp()

Page({

    /**
     * 页面的初始数据
     */
    data: {
      show: false,
      value: '',
      cardNo: '',
      bloodCard: '',
      iscardNo: false,
      isbloodCard: false,
      type: '',
      id: ''
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad(options) {
      this.setData({
        id: options.id
      })
      this.queryApp()
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
    },

    queryApp () {
      const data = {
        id: this.data.id
      }
      queryEquipmentInfo(data).then(res => {
        this.setData({
          cardNo: res.data.cardNo || '',
          bloodCard: res.data.bloodCard || '',
          iscardNo: !!res.data.cardNo,
          isbloodCard: !!res.data.bloodCard,
        })
      })
    },

    showDialog (e) {
      this.setData({
        show: true,
        type: e.mark.type
      })
    },

    close () {
      this.setData({
        show: false,
        value: ''
      })
    },

    scan (e) {
      const that = this
      wx.scanCode({
        success (res) {
          console.log(res.result)
          if (that.data.type == 'bloodCard') {
            that.setData({
              value: res.result
            })
          } else {
            if (/^\d{15}$/.test(res.result)) {
              that.setData({
                value: res.result
              })
            } else {
              wx.showToast({
                title: '未识别到合法设备码',
                icon:'none'
              })
            }
          }
        },
        fail (res) {
          wx.showToast({
            title: '未识别到合法设备码',
            icon:'none'
          })
        }
      })
    },

    deleteApp () {
      Dialog.confirm({
        title: '标题',
        message: '弹窗内容',
      }).then(() => {
        // on confirm
        console.log('confim')
      }).catch(() => {
        // on cancel
        console.log('error')
      });
    },

    boundApp () {
      const data = {
        id: this.data.id
      }
      const type = this.data.type
      data[type] = this.data.value
      console.log(data)
      boundEquipmentInfo(data).then(res => {
        this.queryApp()
      })
    },
    unBoundApp (e) {
      const that = this
      let data = {
        id: this.data.id,
        cardNo: '',
        bloodCard: ''
      }
      const type = e.mark.type
      data[type] = this.data[type]
      wx.showModal({
        title: '解除绑定',
        content: '是否要解除绑定!',
        success (res) {
          if (res.confirm) {
            unBoundEquipmentInfo(data).then(res => {
              that.queryApp()
            })
          }
        }
      })
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