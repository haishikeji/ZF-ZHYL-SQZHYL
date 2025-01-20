// pages/sugg/submit.js
import {suggestAdd} from '../../../../utils/http'
const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    suggstr:'',
    // filelist:['https://yctk-1256675456.cos.ap-nanjing.myqcloud.com/1000000006083130.png','https://yctk-1256675456.cos.ap-nanjing.myqcloud.com/1000000006083130.png','https://yctk-1256675456.cos.ap-nanjing.myqcloud.com/1000000006083130.png','https://yctk-1256675456.cos.ap-nanjing.myqcloud.com/1000000006083130.png'],
    show:false,
    // videourl:'https://yctk-1256675456.cos.ap-nanjing.myqcloud.com/1000000801895871.mp4',
    filelist:[],
    videourl:"",
    videoContext:'',
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {

  },

  showpop()
  {
    this.setData({
      show:true
    })
  },
  closepop()
  {
    this.setData({
      show:false
    })
  },

  chooseimg:function()
  {
    this.setData({
      show:false
    })
    if(this.data.filelist.length==5)
    {
      wx.showToast({
        title: '最多上传5张图片',
        icon:'none'
      })
      return
    }
    var that = this
    wx.chooseImage({
      count: 5-that.data.filelist.length,
      sizeType: ['compressed'],
      sourceType: ['album', 'camera'],
      success: function (res) {
        wx.showLoading({
          title: '上传中',
        })

        console.log(res,121)
        for(var i=0;i<res.tempFilePaths.length;i++)
        {
        const filePath = res.tempFilePaths[i]
        
        wx.uploadFile({
          url: app.globalData.url +'zhyl/wxapp/fastfile/upload/yctk-1256675456',
          filePath:filePath,
          name:'file',
          success: res => {
            console.log('[上传文件] 成功：', res)
            var data = JSON.parse(res.data)
            var arr = that.data.filelist
            arr.unshift(data.data.kpath)
            that.setData({
              filelist:arr
            })
            console.log(that.data.filelist,212)
          },
          fail: e => {
            console.error('[上传文件] 失败：', e)
            wx.showToast({
              icon: 'none',
              title: '上传失败',
            })
          },
          complete: () => {
            wx.hideLoading()
          }
        })
      }
      },
      fail: e => {
        console.error(e)
      }
    })

  },
  choosevio:function()
  {
    this.setData({
      show:false
    })
    var that = this
    wx.chooseVideo({
      sourceType: ['album','camera'],
      maxDuration: 60,
      camera: 'back',
      success(res) {
        console.log(res.tempFilePath)
        const filePath = res.tempFilePath
        wx.uploadFile({
          url: app.globalData.url +'zhyl/wxapp/fastfile/upload/yctk-1256675456',
          filePath:filePath,
          name:"file",

          success: res => {
            console.log('[上传文件] 成功：', res)
            var data = JSON.parse(res.data)
            that.setData({
              videourl:data.data.kpath,
            })
          },
          fail: e => {
            console.error('[上传文件] 失败：', e)
            wx.showToast({
              icon: 'none',
              title: '上传失败',
            })
          },
          complete: () => {
            wx.hideLoading()
          }
        })


      }
    })
  },

  delimg:function(e)
  {
    var index = e.currentTarget.dataset.index
    var arr = this.data.filelist
    arr.splice(index,1)
    this.setData({
      filelist:arr
    })
  },
  delvideo:function()
  {
    this.setData({
      videourl:''
    })
  },

  showbig:function(e)
  {
    wx.previewImage({
      urls: this.data.filelist,
      current:e.currentTarget.dataset.url
    })
  },


  submit()
  {
    if(this.data.suggstr.length==0)
    {
      wx.showToast({
        title: '请输入您的意见',
        icon:'none'
      })
      return
    }

     wx.showLoading({
      title: '提交中...',
      mask:true
    })
    var dic = {}
    dic.opinion = this.data.suggstr
    dic.attachment = this.data.filelist.length>0?this.data.filelist.join(','):''
    dic.video = this.data.videourl
    var that = this
    suggestAdd(dic).then(res=>{
      wx.hideLoading({
        success: (res) => {},
      })
      if(res.code==0)
      {
        wx.showToast({
          title: '提交成功',
          icon:"none"
        })
        that.setData({
          suggstr:'',
          filelist:[],
          videourl:''
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

  getneed(e)
  {
    this.setData({
      suggstr:e.detail.value
    })
  },
  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
    var videoContext = wx.createVideoContext('Video')
    this.setData({
      videoContext:videoContext
    })
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