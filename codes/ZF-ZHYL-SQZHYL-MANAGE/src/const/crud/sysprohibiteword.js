export const tableOption = {
  align: "center",
  index: true,
  border: true,
  column: [
    {
      prop: "skey",
      span: 24,
      type: "input",
      label: "类型",
      rules: [{ message: "类型必须填写", required: true }],
      required: true,
      maxlength: 20
    },
    {
      prop: "word",
      span: 24,
      type: "input",
      label: "过滤词",
      rules: [{ message: "过滤词必须填写", required: true }],
      required: true,
      maxlength: 20
    },
    { prop: "remark", span: 24, type: "textarea", label: "备注", display: true }
  ],
  gutter: 0,
  stripe: true,
  menuBtn: true,
  emptyBtn: true,
  emptyText: "清空",
  menuAlign: "center",
  submitBtn: true,
  indexLabel: "序号",
  labelWidth: 120,
  submitText: "提交",
  labelSuffix: "：",
  menuPosition: "center",
  labelPosition: "left",
  searchMenuSpan: 6,
  dialogDirection: "rtl"
};
