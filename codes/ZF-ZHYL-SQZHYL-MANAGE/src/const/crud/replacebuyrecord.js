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
  editBtn: false,
  viewBtn: true,
  "column": [
    {
      span: 22,
      "type": "input",
      "label": "服务类型",
      "prop": "replaceBuyName",
      "readonly": true,
    },
    {
      span: 22,
      "type": "select",
      "label": "服务类型",
      "prop": "replaceBuy",
      "readonly": true,
      editDisplay:false,
      search: true,
      hide: true,
      viewDisplay: false,
      dicUrl: "/zhyl/wxapp/replaceBuy/list",
      props: {
        label: "name",
        value: "id"
      },
    },
    {
      span: 22,
      "type": "input",
      "label": "代买说明",
      "prop": "instructions",
      "readonly": true,
    }, {
      span: 22,
      "type": "input",
      "label": "最后时限",
      "prop": "endTime",
      "readonly": true,

    }, {
      span: 22,
      "type": "input",
      "label": "所在楼宇",
      "prop": "buildingInfoName",
      "readonly": true,

    }, {
      span: 22,
      "type": "input",
      "label": "老人姓名",
      "prop": "oldName",
      "readonly": true,
      search: true
    }, {
      span: 22,
      "type": "input",
      "label": "联系方式",
      "prop": "phone",
      "readonly": true,
    }, {
      span: 22,
      "type": "select",
      "label": "完成状态",
      "prop": "state",
      search: true,
      viewDisplay: false,
      editDisplay: false,
      dicData: [{
        label: '完成',
        value: 1
      },
        {
          label: '未完成',
          value: 2
        },
      ]

    }, {
      span: 22,
      "type": "input",
      "label": "完成状态",
      "prop": "stateName",
      "readonly": true,
      hide: true,
    },{
      span: 22,
      "type": "input",
      "label": "创建时间",
      "prop": "createTime",
      editDisplay: false
    },{
      span: 22,
      "type": "input",
      "label": "费用",
      "prop": "cost",
       hide:true,
     /* rules: [{
        required: true,
        message: "请输入费用",
        trigger: "blur"
      }]*/

    }]
}
