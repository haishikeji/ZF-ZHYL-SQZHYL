export const tableOption = {
  "border": true,
  "index": true,
  "indexLabel": "序号",
  "stripe": true,
  "menuAlign": "center",
  "align": "center",
  "dialogDirection": "rtl",
  "searchMenuSpan": 6,
  delBtn: false,
  addBtn: false,
  column: [
    {
      "type": "input",
      "label": "标题",
      "prop": "title",
      span: 22,
      rules: [{
        required: true,
        message: "请输入标题",
        trigger: "blur"
      }],
    }, 
    {
      "type": "input",
      "label": "内容",
      "prop": "content",
      "component": "AvueUeditor",
      params: {
        options: {
          //普通图片上传
          action: "/functionRoom/functionroom/fastfile/upload/yctk-1256675456",
          customConfig: {},//wangEditor编辑的配置
          fileSize: 500000,
          props: {
            res: "data",
            url: 'kpath'
          }
        },
      },
      hide: true,
      span: 22,
      rules: [{
        required: true,
        message: "请输内容",
        trigger: "blur"
      }],
    }
  ]
}
