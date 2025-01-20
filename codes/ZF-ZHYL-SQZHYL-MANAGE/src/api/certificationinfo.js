
import request from '@/router/axios'

export function fetchList(query) {
  return request({
    url: '/certificationInfo/certificationinfo/page',
    method: 'get',
    params: query
  })
}

export function addObj(obj) {
  return request({
    url: '/certificationInfo/certificationinfo',
    method: 'post',
    data: obj
  })
}

export function getObj(id) {
  return request({
    url: '/certificationInfo/certificationinfo/' + id,
    method: 'get'
  })
}

export function delObj(id) {
  return request({
    url: '/certificationInfo/certificationinfo/' + id,
    method: 'delete'
  })
}

export function putObj(obj) {
  return request({
    url: '/certificationInfo/certificationinfo',
    method: 'put',
    data: obj
  })
}

export function examinePass(id) {
  return request({
    url: '/certificationInfo/certificationinfo/examinePass?id='+id,
    method: 'Put'
  })
}

export function examineNoPass(id) {
  return request({
    url: '/certificationInfo/certificationinfo/examineNoPass?id='+id,
    method: 'Put'
  })
}

export function examineCancelPass(id) {
  return request({
    url: '/certificationInfo/certificationinfo/examineCancelPass?id='+id,
    method: 'Put'
  })
}
