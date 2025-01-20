// components/slist/index.js
Component({
  /**
   * 组件的属性列表
   */
  properties: {
    array:Object,
    date:Boolean,
    type:Number,//1代买代办分类
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
    godetail(e)
    {
      wx.navigateTo({
        url: '/pages/pagesA/shequ/funs/detail?id='+e.currentTarget.dataset.id+'&type='+this.data.type,
      })
    }
  }
})
