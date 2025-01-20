
import request from '@/router/axios'

export function fetchList(query) {
  return request({
    url: '/device/deviceinfo/page',
    method: 'get',
    params: query
  })
}

export function addObj(obj) {
  return request({
    url: '/device/deviceinfo',
    method: 'post',
    data: obj
  })
}

export function getObj(id) {
  return request({
    url: '/device/deviceinfo/' + id,
    method: 'get'
  })
}

export function delObj(id) {
  return request({
    url: '/device/deviceinfo/' + id,
    method: 'delete'
  })
}

export function contactBinding(id) {
  return request({
    url: '/device/deviceinfo/contactBinding/?deviceId='+id,
    method: 'post'
  })
}

export function putObj(obj) {
  return request({
    url: '/device/deviceinfo',
    method: 'put',
    data: obj
  })


}
