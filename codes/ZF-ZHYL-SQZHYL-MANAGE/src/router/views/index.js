import Layout from '@/page/index/'
export default [{
  path: '/wel',
  component: Layout,
  redirect: '/wel/index',
  children: [{
    path: 'index',
    name: '首页',
    component: () =>
      import( /* webpackChunkName: "views" */ '@/views/wel/index')
  },
  
  ]
}, {
  path: '/info',
  component: Layout,
  redirect: '/info/index',
  children: [{
    path: 'index',
    name: '个人信息',
    component: () =>
      import( /* webpackChunkName: "page" */ '@/views/admin/user/info'),
  }]
},
// {
//   path: '/missionTwo',
//   name: '任务中心',
//   component: () =>
//     import( /* webpackChunkName: "page" */ '@/views/mission/indexTwo'),
// }
// {
//   path: '/address',
//   name: '地图坐标',
//   component: () =>
//     import( /* webpackChunkName: "page" */ '@/views/address/index'),
// }
  // {
  //   path: '/code',
  //   component: Layout,
  //   redirect: '/code/version',
  //   children: [{
  //     path: 'version',
  //     name: '代码版本管理',
  //     component: () =>
  //       import( /* webpackChunkName: "page" */ '@/views/code/version'),
  //   }]
  // }, {
  //   path: '/code/task',
  //   component: Layout,
  //   redirect: '/code/task/example',
  //   children: [{
  //     path: 'example',
  //     name: '执行记录',
  //     component: () =>
  //       import( /* webpackChunkName: "page" */ '@/views/code/task/example'),
  //   }]
  // }
  // , {
  //   path: '/wxconfig',
  //   component: Layout,
  //   redirect: '/wxconfig/index',
  //   children: [{
  //       path: 'index',
  //       name: '微信配置',
  //       component: () =>
  //           import ( /* webpackChunkName: "page" */ '@/views/wxconfig/index'),
  //   }]
  // }
]
