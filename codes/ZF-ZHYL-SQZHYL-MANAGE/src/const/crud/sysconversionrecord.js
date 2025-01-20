export const tableOption = {
  align: "center",
  index: true,
  border: true,

  column: [
    { prop: "name", type: "input", label: "文件名称", display: true },
    { prop: "path", type: "input", label: "文件地址", display: true },
    { prop: "totalLen", type: "input", label: "数据量", display: true },
    { prop: "errorLen", type: "input", label: "异常数量", display: true },
    { prop: "remark", type: "input", label: "备注", display: true }
  ],
  gutter: 0,
  stripe: true,
  menuBtn: true,
  emptyBtn: true,
  emptyText: "清空",
  menuAlign: "center",
  submitBtn: false,
  indexLabel: "序号",
  labelWidth: 120,
  submitText: "提交",
  labelSuffix: "：",
  menuPosition: "center",
  labelPosition: "left",
  searchMenuSpan: 6,
  dialogDirection: "rtl"
};
