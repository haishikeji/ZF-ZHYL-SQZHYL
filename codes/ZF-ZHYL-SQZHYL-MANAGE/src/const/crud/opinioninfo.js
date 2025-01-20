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
  addBtn: false,
  viewBtn: true,
  editBtn: false,
  delBtn: false,
  "column": [
    {
      sapn: 22,
      "type": "textarea",
      "label": "意见建议",
      "prop": "opinion",

    }, {
      span: 22,
      "type": "upload",
      "label": "附件(图片)",
      dataType: 'string',
      "prop": "attachment"
    },
    {
      span: 22,
      "type": "upload",
      "label": "附件（视频）",
      hide:true,
      dataType: 'string',
      "prop": "video"
    },
    {
      sapn: 22,
      "type": "input",
      "label": "创建时间",
      "prop": "createTime"
    },]
}
