
import request from '@/router/axios'

export function fetchList(query) {
  return request({
    url: '/oneTouchCall/onetouchcallinfo/page',
    method: 'get',
    params: query
  })
}

export function addObj(obj) {
  return request({
    url: '/oneTouchCall/onetouchcallinfo',
    method: 'post',
    data: obj
  })
}

export function getObj(id) {
  return request({
    url: '/oneTouchCall/onetouchcallinfo/' + id,
    method: 'get'
  })
}

export function delObj(id) {
  return request({
    url: '/oneTouchCall/onetouchcallinfo/' + id,
    method: 'delete'
  })
}

export function putObj(obj) {
  return request({
    url: '/oneTouchCall/onetouchcallinfo',
    method: 'put',
    data: obj
  })
}
