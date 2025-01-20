export const tableOption = {
	"align": "center",
	"index": true,
	"border": true,
	"column": [
		{
			"type": "upload",
			"label": "文件",
			"prop": "name",
			accept: ['*'],
			action: "/fastfile/upload/ali-jipfqf",
			fileSize: 3000,
			tip: '不超过3M',
			dataType: "string",
			showFileList: true,
			span: 24,
			slot:true,
			propsHttp: {
				res: "data",
				url: "kpath"
			},
			rules: [{
				required: true,
				message: "请上传文件",
				trigger: "blur"
			}],
		},
		{
			"prop": "remark",
			"span": 24,
			"type": "textarea",
			"label": "备注",
			"display": true
		}],
	"gutter": 0,
	"stripe": true,
	"menuBtn": true,
	"emptyBtn": true,
	"emptyText": "清空",
	"menuAlign": "center",
	"submitBtn": true,
	"indexLabel": "序号",
	"labelWidth": 120,
	"submitText": "提交",
	"labelSuffix": "：",
	"menuPosition": "center",
	"labelPosition": "left",
	"searchMenuSpan": 6
}

export const tableOption1 = {
	addBtn: false,
	editBtn: false,
	searchMenuSpan: 4,
	"column": [{
		"type": "input",
		"label": "文件名称",
		"prop": "name",
	}, {
		"type": "input",
		"label": "文件地址",
		"prop": "url",
	}, {
		"type": "input",
		"label": "文件大小",
		"prop": "size",
	}, {
		"type": "input",
		"label": "文件类型",
		"prop": "type",
	}, {
		"type": "input",
		"label": "上传KEY",
		"prop": "upKey",
	}, {
		"type": "select",
		"label": "上传类型",
		"prop": "upType",
		dicData: [{
			label: 'FTP',
			value: 1
		}, {
			label: 'SFTP',
			value: 2
		}, {
			label: '本地',
			value: 3
		}, {
			label: '阿里',
			value: 4
		}, {
			label: '腾讯',
			value: 5
		}, {
			label: '七牛',
			value: 6
		},]
	}, {
		"type": "input",
		"label": "上传时间",
		"prop": "createTime",
	},]
}
