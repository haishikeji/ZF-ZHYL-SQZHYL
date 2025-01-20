
import request from '@/router/axios'

export function fetchList(query) {
  return request({
    url: '/upms/sysapp/page',
    method: 'get',
    params: query
  })
}

export function addObj(obj) {
  return request({
    url: '/upms/sysapp',
    method: 'post',
    data: obj
  })
}

export function getObj(id) {
  return request({
    url: '/upms/sysapp/' + id,
    method: 'get'
  })
}

export function delObj(id) {
  return request({
    url: '/upms/sysapp/' + id,
    method: 'delete'
  })
}

export function putObj(obj) {
  return request({
    url: '/upms/sysapp',
    method: 'put',
    data: obj
  })
}
