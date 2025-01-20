package com.px.modulars.meal.service.impl;

import com.pig4cloud.pig.common.core.constant.Constants;
import com.px.constants.CerStateEnum;
import com.px.constants.IsEquipmentEnum;
import com.px.constants.ReplaceStateEnum;
import com.px.constants.ReplaceTypeEnum;
import com.px.modulars.meal.entity.MealInfo;
import com.px.modulars.meal.entity.MealRecord;
import com.px.modulars.meal.mapper.MealRecordMapper;
import com.px.modulars.meal.service.MealInfoService;
import com.px.modulars.meal.service.MealRecordService;
import com.px.basic.alone.core.base.BaseServiceImpl;
import com.px.modulars.upms.util.websocket.util.WebSocketServer;
import com.px.modulars.userInfo.entity.CertificationInfo;
import com.px.modulars.userInfo.entity.OldManInfo;
import com.px.modulars.userInfo.service.CertificationInfoService;
import com.px.modulars.userInfo.service.OldManInfoService;
import com.px.modulars.userInfo.vo.WxUserVo;
import com.px.msg.IMessageService;
import com.px.msg.MessageService;
import com.px.msg.vo.SendMessageParam;
import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 订餐记录
 *
 * @author 品讯科技
 * @date 2024-08
 */
@Service
public class MealRecordServiceImpl extends BaseServiceImpl<MealRecord, MealRecordMapper> implements MealRecordService {


    @Autowired
    private OldManInfoService oldManInfoService;
    @Autowired
    private MealRecordService mealRecordService;
    @Autowired
    private CertificationInfoService certificationInfoService;
    @Autowired
    private MealInfoService mealInfoService;
    @Autowired
    private MessageService messageService;

    @Override
    public Map<String, Object> mealRecord(Integer type, Integer mealId, Long uid, String address, String lng, String lat) {
        Map<String, Object> map = new HashMap<>();
        //点餐信息
        MealInfo mealInfo = mealInfoService.getById(mealId);
        if (type == 1) {
            CertificationInfo certificationInfo = certificationInfoService.lambdaQuery().eq(CertificationInfo::getUserId, uid)
                    .eq(CertificationInfo::getEnable, Constants.ENABLE_TRUE)
                    .eq(CertificationInfo::getState, CerStateEnum.CER_STATE_ENUM_TWO.getValue()).one();
            if (certificationInfo != null) {
                MealRecord mealRecord = new MealRecord();
                mealRecord.setMealInfo(mealId);
                mealRecord.setState(ReplaceStateEnum.REPLACE_TYPE_ENUM_TWO.getValue());
                mealRecord.setUserId(Long.parseLong(uid.toString()));
                mealRecord.setOldMan(certificationInfo.getOldManInfo());
                mealRecord.setMealNumber(mealInfo.getNumber());
                mealRecord.setIsEquipment(IsEquipmentEnum.IS_EQUIPMENT_ENUM_TWO.getValue());
                mealRecord.setCreateTime(LocalDateTime.now());
                mealRecord.setType(type);
                //绑定老人信息
                try {
                    OldManInfo oldManInfo = oldManInfoService.getById(certificationInfo.getOldManInfo());
                    mealRecord.setName(oldManInfo.getName());
                    mealRecord.setPhone(oldManInfo.getPhone());
                    mealRecord.setBuildingInfo(oldManInfo.getBuilding());
                  /*  if(address==null){
                        //等于空的话就是拿老人所在的楼宇信息
                        mealRecord.setAddress(oldManInfo.getAddress());
                    }else {*/
                    //添加详细地址
                    mealRecord.setAddress(address);
                    mealRecord.setLat(lng);
                    mealRecord.setLng(lat);
                    /*  }*/
                } catch (Exception e) {

                }
                mealRecordService.save(mealRecord);
                map.put("code", 1);
                map.put("msg", "成功");
                try {
                    WebSocketServer.BroadCastInfo("姓名为：" + mealRecord.getName() + " 手机号为：" + mealRecord.getPhone() + " 有新的订餐信息，请及时查看！");
                } catch (IOException e) {

                }
                return map;
            } else {
                map.put("code", 2);
                map.put("msg", "您未绑定老人");
                return map;
            }
        } else {
            MealRecord mealRecord = new MealRecord();
            mealRecord.setMealInfo(mealId);
            mealRecord.setState(ReplaceStateEnum.REPLACE_TYPE_ENUM_TWO.getValue());
            mealRecord.setUserId(Long.parseLong(uid.toString()));
            mealRecord.setMealNumber(mealInfo.getNumber());
            mealRecord.setIsEquipment(IsEquipmentEnum.IS_EQUIPMENT_ENUM_TWO.getValue());
            mealRecord.setCreateTime(LocalDateTime.now());
            mealRecord.setType(type);
            //查询用户信息
            WxUserVo wxUserVo = oldManInfoService.getUserInfo(uid);
            if (wxUserVo != null) {
                if (StringUtils.isNotEmpty(wxUserVo.getPhone())) {
                    OldManInfo oldManInfo = oldManInfoService.lambdaQuery().eq(OldManInfo::getEnable, Constants.ENABLE_TRUE)
                            .eq(OldManInfo::getPhone, wxUserVo.getPhone()).one();
                    if (oldManInfo != null) {
                        mealRecord.setOldMan(oldManInfo.getId());
                        //绑定老人信息
                        try {
                            mealRecord.setName(oldManInfo.getName());
                            mealRecord.setPhone(oldManInfo.getPhone());
                            mealRecord.setBuildingInfo(oldManInfo.getBuilding());
                            /* mealRecord.setAddress(oldManInfo.getAddress());*/
                          /*  if(address==null){
                                //等于空的话就是拿老人所在的楼宇信息
                                mealRecord.setAddress(oldManInfo.getAddress());
                            }else {*/
                            //添加详细地址
                            mealRecord.setAddress(address);
                            mealRecord.setLat(lng);
                            mealRecord.setLng(lat);
                            /*  }*/
                        } catch (Exception e) {

                        }
                        mealRecordService.save(mealRecord);
                        map.put("code", 1);
                        map.put("msg", "成功");
                        try {
                            WebSocketServer.BroadCastInfo("姓名为：" + mealRecord.getName() + " 手机号为：" + mealRecord.getPhone() + " 有新的订餐信息，请及时查看！");
                        } catch (IOException e) {

                        }
                        return map;
                    } else {
                        map.put("code", 2);
                        map.put("msg", "您的身份不是老人不能进行订餐");
                        return map;
                    }
                } else {
                    map.put("code", 2);
                    map.put("msg", "没有授权手机号");
                    return map;
                }
            } else {
                map.put("code", 2);
                map.put("msg", "没有用户信息");
                return map;
            }
        }
    }

    @Override
    public Map<String, Object> equipmentMeal(Integer uid) {
        Map<String, Object> map = new HashMap<>();
        Date date = new Date();
        List<MealInfo> mealInfoList = mealInfoService.lambdaQuery().eq(MealInfo::getEnable, Constants.ENABLE_TRUE)
                .lt(MealInfo::getStartTime, date).gt(MealInfo::getEndTime, date).orderByAsc(MealInfo::getType).list();
        if (mealInfoList.size() <= 0) {
            map.put("code", 2);
            map.put("msg", "未找到订餐信息");
            return map;
        }
        MealInfo mealInfo = mealInfoList.get(0);
        MealRecord mealRecord = new MealRecord();
        mealRecord.setMealInfo(mealInfo.getId());
        mealRecord.setState(ReplaceStateEnum.REPLACE_TYPE_ENUM_TWO.getValue());
        mealRecord.setUserId(Long.parseLong(uid.toString()));
        mealRecord.setMealNumber(mealInfo.getNumber());
        //查询用户信息
        OldManInfo oldManInfo = oldManInfoService.getById(uid);
        if (oldManInfo != null) {
            mealRecord.setOldMan(oldManInfo.getId());
            //绑定老人信息
            try {
                mealRecord.setName(oldManInfo.getName());
                mealRecord.setPhone(oldManInfo.getPhone());
                mealRecord.setBuildingInfo(oldManInfo.getBuilding());
            } catch (Exception e) {
            }
            mealRecordService.save(mealRecord);
            map.put("code", 1);
            map.put("msg", "成功");
            try {
                WebSocketServer.BroadCastInfo("姓名为：" + mealRecord.getName() + " 手机号为：" + mealRecord.getPhone() + " 有新的订餐信息，请及时查看！");
            } catch (IOException e) {

            }
            return map;
        } else {
            map.put("code", 2);
            map.put("msg", "未找到老人信息");
            return map;
        }
    }

    @Override
    public void mealMsg(Integer mealIdRecord) {
        //订餐记录
        MealRecord mealRecord = mealRecordService.getById(mealIdRecord);
        //判断订餐记录是否自己订餐
        WxUserVo wxUserVo = new WxUserVo();
        //查询订餐类型
        MealInfo mealInfo = mealInfoService.getById(mealRecord.getMealInfo());
        String mealType = "";
        if (mealInfo.getType() == 1) {
            mealType = "早餐";
        } else if (mealInfo.getType() == 2) {
            mealType = "午餐";
        } else {
            mealType = "晚餐";
        }

        if (mealRecord.getIsEquipment() == 2) {
            wxUserVo = oldManInfoService.getUserInfo(Long.parseLong(mealRecord.getUserId().toString()));
            //短信通知
           /* SendMessageParam msgParam = new SendMessageParam();
            msgParam.setMethod(IMessageService.METHOD_SMS);
            msgParam.setKey("personalAcStart");
            msgParam.putData("aname", "", 20);
            msgParam.addTo("17737469506");
            this.messageService.send(msgParam);*/
            //微信模板通知
            DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH时mm分ss秒");
            String strDate2 = dtf2.format(mealRecord.getCreateTime());
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
            String dateString = formatter.format(new Date());
            String str = mealRecord.getName() + "的" + mealType + "需求" + "已送达";
            SendMessageParam msgParam1 = new SendMessageParam();
            msgParam1.setMethod(IMessageService.METHOD_WX);
            msgParam1.setKey("orderFinish");
            msgParam1.putData("thing1", str, 20);
            msgParam1.putData("thing3", "请注意及时查收");
            //  msgParam1.putOtherData("oldid", mealRecord.getOldMan().toString());
//            msgParam1.addTo("oqlqo5IPuLXlMxUjQ1d8e3wKfhtM");
            msgParam1.addTo(wxUserVo.getOpenid());
            this.messageService.send(msgParam1);
        }


    }

}
