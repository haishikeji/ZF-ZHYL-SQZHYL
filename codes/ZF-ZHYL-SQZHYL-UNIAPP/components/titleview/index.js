// components/titleview/index.js
Component({
  /**
   * 组件的属性列表
   */
  properties: {
    name: String,
    right:Boolean,
    subname:String,
    url:String
  },

  /**
   * 组件的初始数据
   */
  data: {

  },

  /**
   * 组件的方法列表
   */
  methods: {
    gopgae(e)
    {
      if(e.mark.url)
      {
        wx.navigateTo({
          url: e.mark.url,
        })
      }
    }
  }
})
