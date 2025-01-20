export const tableOption = {
  "border": true,
  "index": true,
  "indexLabel": "序号",
  "stripe": true,
  "menuAlign": "center",
  "align": "center",
  "dialogDirection": "rtl",
  "searchMenuSpan": 6,
  labelWidth: 160,
  "column": [{

    span: 22,
    "type": "input",
    "label": "服务内容名称",
    "prop": "name",
    rules: [{
      required: true,
      message: "请输入名称",
      trigger: "blur"
    }]
  }, {
    span: 22,
    "type": "upload",
    "label": "标题图",
    "prop": "image",
    width: 100,
    listType: 'picture-img',
    accept: ["image/png", "image/jpeg"],
    action: "/functionRoom/functionroom/fastfile/upload/yctk-1256675456",
    multiple: true,
    fileSize: 500000,
    dataType: "string",
    propsHttp: {
      res: "data",
      url: "kpath"
    },
    showFileList: true,
    /* "hide": true,*/
    rules: [{
      required: true,
      message: "请上传活动图片",
      trigger: "blur"
    }],
    tip: "只能上传jpg/png文件，且不超过500kb"
  }, {
    span: 22,
    "type": "upload",
    "label": "图标",
    "prop": "icon",
    width: 100,
    listType: 'picture-img',
    accept: ["image/png", "image/jpeg"],
    action: "/functionRoom/functionroom/fastfile/upload/yctk-1256675456",
    multiple: true,
    fileSize: 500000,
    dataType: "string",
    propsHttp: {
      res: "data",
      url: "kpath"
    },
    showFileList: true,
    /* "hide": true,*/
    rules: [{
      required: true,
      message: "请上传活动图片",
      trigger: "blur"
    }],
    tip: "只能上传jpg/png文件，且不超过500kb"
  }, {
    span: 22,
    "type": "textarea",
    "label": "内容",
    "prop": "content",
   // "component": "AvueUeditor",
   /* params: {
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
    },*/
    rules: [{
      required: true,
      message: "请输入内容",
      trigger: "blur"
    }]
  },
    {
      span: 22,
      "type": "input",
      "label": "创建时间",
      "prop": "createTime",
      addDisplay: false,
      editDisplay: false
    },]
}
