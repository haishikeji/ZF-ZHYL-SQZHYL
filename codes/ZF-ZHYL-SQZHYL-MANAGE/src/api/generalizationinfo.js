
import request from '@/router/axios'

export function fetchList(query) {
  return request({
    url: '/generalization/generalizationinfo/page',
    method: 'get',
    params: query
  })
}

export function addObj(obj) {
  return request({
    url: '/generalization/generalizationinfo',
    method: 'post',
    data: obj
  })
}

export function getObj(id) {
  return request({
    url: '/generalization/generalizationinfo/' + id,
    method: 'get'
  })
}

export function delObj(id) {
  return request({
    url: '/generalization/generalizationinfo/' + id,
    method: 'delete'
  })
}

export function putObj(obj) {
  return request({
    url: '/generalization/generalizationinfo',
    method: 'put',
    data: obj
  })
}
