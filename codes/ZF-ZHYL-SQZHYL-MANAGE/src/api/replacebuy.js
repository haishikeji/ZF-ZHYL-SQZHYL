
import request from '@/router/axios'

export function fetchList(query) {
  return request({
    url: '/replaceBuy/replacebuy/page',
    method: 'get',
    params: query
  })
}

export function addObj(obj) {
  return request({
    url: '/replaceBuy/replacebuy',
    method: 'post',
    data: obj
  })
}

export function getObj(id) {
  return request({
    url: '/replaceBuy/replacebuy/' + id,
    method: 'get'
  })
}

export function delObj(id) {
  return request({
    url: '/replaceBuy/replacebuy/' + id,
    method: 'delete'
  })
}

export function putObj(obj) {
  return request({
    url: '/replaceBuy/replacebuy',
    method: 'put',
    data: obj
  })
}
