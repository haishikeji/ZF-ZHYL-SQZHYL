export const tableOption = {
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
      "type": "input",
      "label": "设备编号",
      "prop": "deviceNumber",
      rules: [{
        required: true,
        message: "请输入设备编号",
        trigger: "blur"
      }],
    }, {
      "type": "input",
      "label": "设备卡号",
      "prop": "deviceCard",
      rules: [{
        required: true,
        message: "请输入设备卡号",
        trigger: "blur"
      }],
    }, {
      "type": "select",
      "label": "设备状态",
      "prop": "deviceStatus",
      addDisplay: false,
      editDisplay: false,
      search: true,
      dicData: [{
        label: '正常运行',
        value: '0'
      }, {
        label: '网络异常',
        value: '1'
      },{
        label: '电量异常',
        value: '2'
      }]
    }, {
      "type": "select",
      "label": "绑定状态",
      "prop": "bindingStatus",
      slot:true,
      search: true,
      addDisplay: false,
      editDisplay: false,
      dicData: [{
        label: '绑定',
        value: '0'
      }, {
        label: '未绑定',
        value: '1'
      }]
    },
    {
      "type": "select",
      "label": "绑定姓名",
      "prop": "bindingId",
      dicUrl: '/oldManInfo/oldmaninfo/list',
     /* addDisplay: false,
      disabled:true,*/
      editDisplay: false,
      rules: [{
        required: true,
        message: "请选择绑定老人",
        trigger: "blur"
      }],
      props: {
        label: "name",
        value: "id"
      },
    }, {
      "type": "input",
      "label": "最后一次链接时间",
      "prop": "lastUpdateTime",
      addDisplay: false,
      editDisplay: false,
    }
  ]
}
