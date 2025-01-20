export const tableOption = {
  "border": true,
  "index": true,
  "indexLabel": "序号",
  "stripe": true,
  "menuAlign": "center",
  "align": "center",
  "dialogDirection": "rtl",
  "searchMenuSpan": 6,
  delBtn:false,
  addBtn:false,
  "column": [
    {
      "type": "input",
      "label": "呼叫号码",
      "prop": "phone",
      rules: [{
        required: true,
        message: "请输入呼叫号码",
        trigger: "blur"
      }]
    }]
}
