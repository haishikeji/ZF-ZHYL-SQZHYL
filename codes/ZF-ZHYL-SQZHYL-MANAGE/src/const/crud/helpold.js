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
  /*  {
      "type": "input",
      "label": "ID",
      "prop": "id",
      addDisplay: false,
      editDisplay: false,
      viewBtn:false,
    }, */{
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
      "type": "upload",
      "label": "标图",
      "prop": "plotting",
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
        required: true,
        message: "请上传图片",
        trigger: "blur"
      }],
      tip: "只能上传jpg/png文件，且不超过500kb"
    },
    {
      span: 22,
      slot: true,
      "type": "input",
      "label": "内容",
      "prop": "content",
      component: "avueUeditor",
      formatter: (row, value) =>{
        if(value.length >32){
          return value.slice(0,32) + '...'
        }else {
          return value
        }
      },
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
        message: "请输入内容",
        trigger: "blur"
      }]
    },
    /*{
      "type": "radio",
      "label": "展示",
      "prop": "isExhibition",
      search: true,
      slot: true,
      border: true,
      span: 22,
      /!*html:true,
      formatter:(val)=>{
        if(val.isExhibition==0){
          return '<span style="color:red">'+"否"+'</span>'
        }else {
          return '<span style="color:red">'+"是"+'</span>'
        }

      },*!/
      rules: [{
        required: true,
        message: '请选择状态',
        trigger: 'blur'
      }],
      dicData: [{
        label: '是',
        value: '1'
      }, {
        label: '否',
        value: '0'
      }]
    },*/ /*{
      "type": "input",
      "label": "备注",
      "prop": "remark",
      addDisplay: false,
      editDisplay: false,
    },*/ {
      "type": "input",
      "label": "创建时间",
      "prop": "createTime",
      addDisplay: false,
      editDisplay: false,
    }]
}
