
import request from '@/router/axios'

export function fetchList(query) {
  return request({
    url: '/teamInfo/teaminfo/page',
    method: 'get',
    params: query
  })
}

export function addObj(obj) {
  return request({
    url: '/teamInfo/teaminfo',
    method: 'post',
    data: obj
  })
}

export function getObj(id) {
  return request({
    url: '/teamInfo/teaminfo/' + id,
    method: 'get'
  })
}

export function delObj(id) {
  return request({
    url: '/teamInfo/teaminfo/' + id,
    method: 'delete'
  })
}

export function putObj(obj) {
  return request({
    url: '/teamInfo/teaminfo',
    method: 'put',
    data: obj
  })
}



