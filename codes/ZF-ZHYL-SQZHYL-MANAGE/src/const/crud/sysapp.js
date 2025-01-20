export const tableOption = {
	"border": true,
	"index": true,
	"indexLabel": "序号",
	"stripe": true,
	"menuAlign": "center",
	"align": "center",
	"dialogDirection": "rtl",
	"searchMenuSpan": 6,
	"column": [{
		"type": "input",
		"label": "APP名称",
		"prop": "name",
		span:24,
		rules: [{
			required: true,
			message: "请输入APP名称",
			trigger: "blur"
		}]
	}, {
		"type": "input",
		"label": "APPKEY",
		"prop": "appkey",
		span:24,
		rules: [{
			required: true,
			message: "请输入APPKEY",
			trigger: "blur"
		}]
	}, {
		"type": "select",
		"label": "类型",
		"prop": "type",
		span:24,
		dicData: [{
			label: 'WEB',
			value: 1
		}, {
			label: '小程序',
			value: 2
		}, {
			label: '安卓',
			value: 3
		}, {
			label: 'IOS',
			value: 4
		}],
		rules: [{
			required: true,
			message: "请选择所属类型",
			trigger: "change"
		}]
	}, {
		"type": "input",
		"label": "当前版本号",
		"prop": "version",
		addDisplay: false,
		editDisplay: false
	}, {
		"type": "textarea",
		"label": "当前更新内容",
		"prop": "content",
		addDisplay: false,
		editDisplay: false
	}, {
		"type": "input",
		"label": "添加时间",
		"prop": "createTime",
		addDisplay: false,
		editDisplay: false
	}, {
		"type": "input",
		"label": "修改时间",
		"prop": "updateTime",
		addDisplay: false,
		editDisplay: false
	}]
}
