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
  abelWidth: 160,
  searchLabelWidth: 100,
  "column": [
    {
      span: 22,
      "type": "input",
      "label": "姓名",
      "prop": "name",
      search: true,
      rules: [{
        required: true,
        message: "请输入姓名",
        trigger: "blur"
      }]
    }, {
      span: 22,
      "type": "select",
      "label": "性别",
      "prop": "sex",
      search: true,
      dicData: [{
        label: '男',
        value: 1
      }, {
        label: '女',
        value: 2
      }],
      rules: [{
        required: true,
        message: "请选择性别",
        trigger: "blur"
      }],
    }, {
      "type": "upload",
      "label": "头像",
      "prop": "image",
      span: 22,
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
        required: false,
        message: "请上传图片",
        trigger: "blur"
      }],
      tip: "只能上传jpg/png文件，且不超过500kb"
    }, {
      span: 22,
      "type": "number",
      "label": "年龄",
      "prop": "age",
      rules: [{
        required: false,
        message: "请输入年龄",
        trigger: "blur"
      }],
    }, {
      span: 22,
      "type": "input",
      "label": "手机号",
      "prop": "phone",
      width: 150,
      search: true,
      rules: [{
        required: false,
        message: "请输入手机号",
        trigger: "blur"
      }],
    }, {
      span: 22,
      "type": "input",
      "label": "民族",
      "prop": "national",
      rules: [{
        required: false,
        message: "请输入民族",
        trigger: "blur"
      }],
    }, {
      span: 22,
      "type": "input",
      "label": "身体状况",
      "prop": "physical",
    }, {
      span: 22,
      "type": "select",
      "label": "所在楼宇",
      "prop": "building",
      width: 200,
      search: true,
      dicUrl: "/buildingInfo/buildinginfo/list",
      props: {
        label: "name",
        value: "id"
      },
      rules: [{
        required: false,
        message: "请选择楼宇",
        trigger: "blur"
      }],
    }, {
      span: 22,
      "type": "textarea",
      "label": "详细地址信息",
      "prop": "address",
      width: 200,
      rules: [{
        required: false,
        message: "请输入详细信息",
        trigger: "blur"
      }],
    }, {
      span: 22,
      "type": "input",
      "label": "身份证信息",
      "prop": "idCard",
      search: true,
      width: 200,
      rules: [{
        required: false,
        message: "请输入身份证信息",
        trigger: "blur"
      }],

    }, {
      span: 22,
      "type": "textarea",
      "label": "户籍地址",
      "prop": "permanentAddress",
      search: true,
      width: 200,
      rules: [{
        required: false,
        message: "户籍地址",
        trigger: "blur"
      }],

    },  {
      span: 22,
      "type": "input",
      "label": "所属街道",
      "prop": "street",
      search: true,
      width: 200,
      rules: [{
        required: false,
        message: "所属街道",
        trigger: "blur"
      }],

    },  {
      span: 22,
      "type": "input",
      "label": "文化程度",
      "prop": "education"
    }, {
      span: 22,
      "type": "select",
      "label": "婚姻状况",
      "prop": "maritalStatus",
      search: true,
      dicData: [{
        label: '已婚',
        value: 1
      }, {
        label: '未婚',
        value: 2
      }],
      rules: [{
        required: false,
        message: "请选择",
        trigger: "blur"
      }],
    }, {
      span: 22,
      "type": "input",
      "label": "家庭困难度",
      "prop": "familyHardship",
      width: 100,
    }, {
      span: 22,
      "type": "select",
      "label": "是否党员",
      "prop": "partyStatus",
      search: true,
      dicData: [{
        label: '是',
        value: 1
      }, {
        label: '否',
        value: 2
      }],
      rules: [{
        required: false,
        message: "请选择",
        trigger: "blur"
      }],
    }, {
      span: 22,
      "type": "select",
      "label": "保险状况",
      "prop": "insuranceStatus",
      search: true,
      dicData: [{
        label: '有保险',
        value: 1
      }, {
        label: '无保险',
        value: 2
      }],
      rules: [{
        required: false,
        message: "请选择",
        trigger: "blur"
      }],
    }, {
      span: 22,
      "type": "select",
      "label": "优抚情况",
      "prop": "materialsStatus",
      search: true,
      dicData: [{
        label: '有',
        value: 1
      }, {
        label: '无',
        value: 2
      }],
      rules: [{
        required: false,
        message: "请选择",
        trigger: "blur"
      }],
    }, {
      span: 22,
      "type": "select",
      "label": "社会救助",
      "prop": "socialAssistance",
      search: true,
      dicData: [{
        label: '有',
        value: 1
      }, {
        label: '无',
        value: 2
      }],
      rules: [{
        required: false,
        message: "请选择",
        trigger: "blur"
      }],
    }]
}
