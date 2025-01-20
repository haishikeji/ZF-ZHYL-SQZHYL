
import request from '@/router/axios'

export function fetchList(query) {
  return request({
    url: '/buildingInfo/buildinginfo/page',
    method: 'get',
    params: query
  })
}

export function addObj(obj) {
  return request({
    url: '/buildingInfo/buildinginfo',
    method: 'post',
    data: obj
  })
}

export function getObj(id) {
  return request({
    url: '/buildingInfo/buildinginfo/' + id,
    method: 'get'
  })
}

export function delObj(id) {
  return request({
    url: '/buildingInfo/buildinginfo/' + id,
    method: 'delete'
  })
}

export function putObj(obj) {
  return request({
    url: '/buildingInfo/buildinginfo',
    method: 'put',
    data: obj
  })
}
