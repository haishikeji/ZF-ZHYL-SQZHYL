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
  "column": [
    {
      span: 22,
      "type": "input",
      "label": "队伍名字",
      "prop": "name",
      with: 100,
      search: true,
      rules: [{
        required: true,
        message: "请输入队伍名字",
        trigger: "blur"
      }],
    }, {
      span: 22,
      "type": "upload",
      "label": "队伍标题图",
      "prop": "image",
      showColumn: true,
      mgWidth: 200,
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
        message: "请上传队伍标题图",
        trigger: "blur"
      }],
      tip: "只能上传jpg/png文件，且不超过500kb"
    },
    {
      span: 22,
      "type": "upload",
      "label": "banner图",
      "prop": "icon",

      showColumn: true,
      mgWidth: 200,

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
        message: "请上传队伍标题图",
        trigger: "blur"
      }],
      tip: "只能上传jpg/png文件，且不超过500kb"
    },
    {
      span: 22,
      "type": "input",
      "label": "队伍介绍",
      "prop": "introduce",
      "component": "AvueUeditor",
      hide: true,
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
      rules: [{
        required: true,
        message: "请输入介绍内容",
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
export const tableOption1 = {
  "border": true,
  "index": true,
  "indexLabel": "序号",
  "stripe": true,
  "menuAlign": "center",
  "align": "center",
  "dialogDirection": "rtl",
  "searchMenuSpan": 6,
  "column": [
    {
      span: 22,
      "type": "input",
      "label": "队员姓名",
      "prop": "name",
      rules: [{
        required: true,
        message: "请输入队员姓名",
        trigger: "blur"
      }],
    },

    {
      span: 22,
      "type": "upload",
      "label": "头像",
      "prop": "image",
      showColumn: true,
      /* mgWidth: 100,*/
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
        message: "请上传头像",
        trigger: "blur"
      }],
      tip: "只能上传jpg/png文件，且不超过500kb"
    },

    {
      span: 22,
      "type": "select",
      "label": "职位",
      "prop": "position",
      dicData: [{
        label: '队长',
        value: 1
      }, {
        label: '队员',
        value: 2
      }],
      rules: [{
        required: true,
        message: "请输入职位",
        trigger: "blur"
      }],
    }, {
      span: 22,
      "type": "input",
      "label": "联系方式",
      "prop": "phone",
      rules: [{
        required: true,
        message: "请输入联系方式",
        trigger: "blur"
      }],
    }, {
      span: 22,
      "type": "textarea",
      "label": "简介",
      "prop": "introduction",
      rules: [{
        required: true,
        message: "请输入简介",
        trigger: "blur"
      }],
    },]
}

