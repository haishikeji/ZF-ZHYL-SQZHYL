
import request from '@/router/axios'

export function louList(query) {
  return request({
    url: '/device/deviceinfo/getBuildingInList',
    method: 'get',
    params: query
  })
}

export function louPeopleList(query) {
    return request({
      url: '/device/deviceinfo/geBuildingUserPage',
      method: 'get',
      params: query
    })
  }

  export function louCurrPeopleList(query) {
    return request({
      url: '/device/deviceinfo/geBuildingCurrentUser',
      method: 'get',
      params: query
    })
  }


  export function peopleList(query) {
    return request({
      url: '/callLocation/sosmessageinfo/getOldNameOrPhonePage',
      method: 'get',
      params: query
    })
  }

  export function sosList(query) {
    return request({
      url: '/callLocation/sosmessageinfo/selectSosUserInfoList',
      method: 'get',
      params: query
    })
  }

  export function mealList(query) {
    return request({
      url: '/callLocation/sosmessageinfo/selectMealEcordTableInfoList',
      method: 'get',
      params: query
    })
  }

  export function replaceList(query) {
    return request({
      url: '/callLocation/sosmessageinfo/selectReplaceBuyRecordBDInfoList',
      method: 'get',
      params: query
    })
  }

  export function sercount(query) {
    return request({
      url: '/callLocation/sosmessageinfo/getQuantityVoMap',
      method: 'get',
      params: query
    })
  }


  export function updatesos(query) {
    return request({
      url: '/callLocation/sosmessageinfo/updateSosMessageState',
      method: 'put',
      params: query
    })
  }
  export function updatemeal(query) {
    return request({
      url: '/callLocation/sosmessageinfo/updateMealRecordState',
      method: 'put',
      params: query
    })
  }
  export function updatereplace(query) {
    return request({
      url: '/callLocation/sosmessageinfo/updateReplaceBuyRecordState',
      method: 'put',
      params: query
    })
  }