
import request from '@/router/axios'

export function fetchList(query) {
  return request({
    url: '/helpold/helpold/page',
    method: 'get',
    params: query
  })
}

export function addObj(obj) {
  return request({
    url: '/helpold/helpold',
    method: 'post',
    data: obj
  })
}

export function getObj(id) {
  return request({
    url: '/helpold/helpold/' + id,
    method: 'get'
  })
}

export function delObj(id) {
  return request({
    url: '/helpold/helpold/' + id,
    method: 'delete'
  })
}

export function putObj(obj) {
  return request({
    url: '/helpold/helpold',
    method: 'put',
    data: obj
  })
}
