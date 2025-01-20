
import request from '@/router/axios'

export function fetchList(query) {
  return request({
    url: '/replaceBuyRecord/replacebuyrecord/page',
    method: 'get',
    params: query
  })
}

export function addObj(obj) {
  return request({
    url: '/replaceBuyRecord/replacebuyrecord',
    method: 'post',
    data: obj
  })
}

export function getObj(id) {
  return request({
    url: '/replaceBuyRecord/replacebuyrecord/' + id,
    method: 'get'
  })
}

export function delObj(id) {
  return request({
    url: '/replaceBuyRecord/replacebuyrecord/' + id,
    method: 'delete'
  })
}

export function putObj(obj) {
  return request({
    url: '/replaceBuyRecord/replacebuyrecord',
    method: 'put',
    data: obj
  })
}
