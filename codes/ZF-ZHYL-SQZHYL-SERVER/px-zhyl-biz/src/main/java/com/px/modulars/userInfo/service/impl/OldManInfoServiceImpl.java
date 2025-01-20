package com.px.modulars.userInfo.service.impl;

import cn.hutool.http.HttpRequest;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.pig4cloud.pig.common.core.constant.Constants;
import com.px.constants.CerStateEnum;
import com.px.constants.UserTypeStateEnum;
import com.px.modulars.miniusers.entity.MiniUsers;
import com.px.modulars.miniusers.service.MiniUsersService;
import com.px.modulars.userInfo.entity.CertificationInfo;
import com.px.modulars.userInfo.entity.OldManInfo;
import com.px.modulars.userInfo.mapper.OldManInfoMapper;
import com.px.modulars.userInfo.service.CertificationInfoService;
import com.px.modulars.userInfo.service.OldManInfoService;
import com.px.basic.alone.core.base.BaseServiceImpl;
import com.px.modulars.userInfo.vo.WxUserVo;
import com.px.msg.IMessageService;
import com.px.msg.MessageService;
import com.px.msg.vo.SendMessageParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 老人基本信息
 *
 * @author 品讯科技
 * @date 2024-08
 */
@Service
public class OldManInfoServiceImpl extends BaseServiceImpl<OldManInfo, OldManInfoMapper> implements OldManInfoService {

    @Value("${selectuser.url}")
    public String url;
    @Autowired
    private MessageService messageService;
    @Autowired
    private OldManInfoService oldManInfoService;
    @Autowired
    private CertificationInfoService certificationInfoService;


    @Autowired
    private MiniUsersService miniUsersService;

    @Override
    public WxUserVo getUserInfo(Long uid) {
        WxUserVo wxUserVo = new WxUserVo();
//        String result = HttpRequest.get(url + "/allUser/user/getOne?uid=" + uid)
//                .header("Content-Type", "application/json")
//                .execute().body();
//        JSONObject jsonObject = JSONUtil.parseObj(result);
//        JSONObject jsonObject1 = JSONUtil.parseObj(jsonObject.get("data"));
//        if (!jsonObject1.get("avatar").equals("")) {
//            wxUserVo.setAvatar(jsonObject1.get("avatar").toString());
//        } else {
//            wxUserVo.setAvatar("");
//        }
//        if (!jsonObject1.get("id").equals("")) {
//            wxUserVo.setId(Long.parseLong(jsonObject1.get("id").toString()));
//        }
//        if (!jsonObject1.get("nickname").equals("")) {
//            wxUserVo.setNickname(jsonObject1.get("nickname").toString());
//        } else {
//            wxUserVo.setNickname("");
//        }
//        if (!jsonObject1.get("phone").equals("")) {
//            wxUserVo.setPhone(jsonObject1.get("phone").toString());
//        } else {
//            wxUserVo.setPhone("");
//        }
//        if (!jsonObject1.get("openid").equals("")) {
//            wxUserVo.setOpenid(jsonObject1.get("openid").toString());
//        } else {
//            wxUserVo.setOpenid("");
//        }
        MiniUsers users =  miniUsersService.getById(uid);
        if (users!=null){
            wxUserVo.setAvatar(users.getAvatar());
            wxUserVo.setId(Long.parseLong(users.getId().toString()));
            wxUserVo.setNickname(users.getNickName());
            wxUserVo.setPhone(users.getPhone());
            wxUserVo.setOpenid(users.getOpenId());
        }

        List<OldManInfo> oldManInfoList = new ArrayList<>();
        //查询身份
        OldManInfo oldManInfo = oldManInfoService.lambdaQuery().eq(OldManInfo::getEnable, Constants.ENABLE_TRUE).eq(OldManInfo::getPhone, wxUserVo.getPhone()).one();
         if(oldManInfo != null){
             oldManInfoList.add(oldManInfo);
             //改变身份
             wxUserVo.setUserType(UserTypeStateEnum.USER_TYPE_STATE_ENUM_ONE.getValue());
             //把老人id返回前端
             wxUserVo.setOldManId(oldManInfo.getId());
//             wxUserVo.setId(Long.parseLong(oldManInfo.getId().toString()));
             wxUserVo.setOldManInfo(oldManInfoList);
         }else {
             //判断是否为家人
             int count =  certificationInfoService.lambdaQuery().eq(CertificationInfo::getEnable,Constants.ENABLE_TRUE)
                     .eq(CertificationInfo::getState,CerStateEnum.CER_STATE_ENUM_TWO.getValue()).eq(CertificationInfo::getUserId,wxUserVo.getId()).count();
             if(count>0){
                 wxUserVo.setUserType(UserTypeStateEnum.USER_TYPE_STATE_ENUM_TWO.getValue());
             }else {
                 wxUserVo.setUserType(UserTypeStateEnum.USER_TYPE_STATE_ENUM_FOUR.getValue());
             }
         }
        return wxUserVo;
    }

    @Override
    public void sosMsg(Integer uid) {
        OldManInfo oldManInfo = oldManInfoService.getById(uid);
        List<CertificationInfo> certificationInfoList = certificationInfoService.lambdaQuery().eq(CertificationInfo::getEnable, Constants.ENABLE_TRUE)
                .eq(CertificationInfo::getState, CerStateEnum.CER_STATE_ENUM_TWO.getValue())
                .eq(CertificationInfo::getOldManInfo, uid).list();

        certificationInfoList.forEach(certificationInfo -> {
            WxUserVo wxUserVo = oldManInfoService.getUserInfo(Long.parseLong(certificationInfo.getUserId().toString()));
            SendMessageParam msgParam1 = new SendMessageParam();
            msgParam1.setMethod(IMessageService.METHOD_WX);
            String str = oldManInfo.getName() + "在" + new Date() + "使用穿戴设备进行报警，请尽快与他联系或与社区取得联系确认情况。如已确认请忽略";
            msgParam1.setKey("orderFinish");
            msgParam1.putData("thing2", str);
            msgParam1.putData("thing4", "请及时处理");
            msgParam1.putOtherData("id", "");
//            msgParam1.addTo("oqlqo5IPuLXlMxUjQ1d8e3wKfhtM");
            msgParam1.addTo(wxUserVo.getOpenid());
            this.messageService.send(msgParam1);
        });

    }
}
