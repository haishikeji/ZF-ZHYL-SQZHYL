import request from "./../utils/request.js";
/**
 * 
 * 用户相关接口
 * 
*/

/**
 * 小程序用户登录
 * @param data object 小程序用户登陆信息
 */
export function login(data) {
  return request.get("login/wx", data, { noAuth : true });
}

/**
 * 获取用户信息
 * 
*/
export function getUserInfo(data){
  return request.get('allUser/read/AllUserList',{openid:data});
}


/**
 * 获取手机号
 * 
*/
export function getUserphone(data){
  return request.get('login/phone',data,{ noAuth : true }); 
}