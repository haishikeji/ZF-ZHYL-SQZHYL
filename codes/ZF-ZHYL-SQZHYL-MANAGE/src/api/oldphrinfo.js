
import request from '@/router/axios'

export function fetchList(query) {
  return request({
    url: '/userInfo/oldphrinfo/page',
    method: 'get',
    params: query
  })
}

export function addObj(obj) {
  return request({
    url: '/userInfo/oldphrinfo',
    method: 'post',
    data: obj
  })
}

export function getObj(id) {
  return request({
    url: '/userInfo/oldphrinfo/' + id,
    method: 'get'
  })
}

export function delObj(id) {
  return request({
    url: '/userInfo/oldphrinfo/' + id,
    method: 'delete'
  })
}

export function putObj(obj) {
  return request({
    url: '/userInfo/oldphrinfo',
    method: 'put',
    data: obj
  })


}

