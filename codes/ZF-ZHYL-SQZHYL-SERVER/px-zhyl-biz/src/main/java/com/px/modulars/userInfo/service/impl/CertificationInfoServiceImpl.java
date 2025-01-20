package com.px.modulars.userInfo.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.px.apis.device.vo.OldManHealthVo;
import com.px.constants.CerStateEnum;
import com.px.modulars.userInfo.entity.CertificationInfo;
import com.px.modulars.userInfo.mapper.CertificationInfoMapper;
import com.px.modulars.userInfo.service.CertificationInfoService;
import com.px.basic.alone.core.base.BaseServiceImpl;
import com.px.modulars.userInfo.service.OldManInfoService;
import com.px.modulars.userInfo.vo.WxUserVo;
import com.px.msg.IMessageService;
import com.px.msg.MessageService;
import com.px.msg.vo.SendMessageParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.cert.CertificateFactory;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * 认证信息表
 *
 * @author 品讯科技
 * @date 2024-08
 */
@Service
public class CertificationInfoServiceImpl extends BaseServiceImpl<CertificationInfo, CertificationInfoMapper> implements CertificationInfoService {

    @Autowired
    private CertificationInfoService certificationInfoService;
    @Autowired
    private CertificationInfoMapper certificationInfoMapper;

    @Autowired
    private OldManInfoService oldManInfoService;
    @Autowired
    private MessageService messageService;

    @Override
    public void Certification(Integer id) {

        CertificationInfo certificationInfo = certificationInfoService.getById(id);
        String state = "";
        if (certificationInfo.getState().equals(CerStateEnum.CER_STATE_ENUM_TWO.getValue())) {
            state = "通过";
        } else if (certificationInfo.getState().equals(CerStateEnum.CER_STATE_ENUM_THREE.getValue())) {
            state = "不通过";
        } else {
            state = "";
        }
        WxUserVo wxUserVo = oldManInfoService.getUserInfo(Long.parseLong(certificationInfo.getUserId().toString()));
        String str = "您提交的绑定" + certificationInfo.getOldName() + "信息的申请已经审核。";
        SendMessageParam msgParam1 = new SendMessageParam();
        msgParam1.setMethod(IMessageService.METHOD_WX);
        msgParam1.setKey("certification");
        msgParam1.putData("thing1", "绑定老人");
        msgParam1.putData("thing2", str,20);
        msgParam1.putData("phrase3", state);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        String dateString = formatter.format(new Date());
        msgParam1.putData("time4", dateString);
        msgParam1.putOtherData("type", "1");
//        msgParam1.addTo("oqlqo5IPuLXlMxUjQ1d8e3wKfhtM");
        msgParam1.addTo(wxUserVo.getOpenid());
        this.messageService.send(msgParam1);
    }

    @Override
    public int updateCertificationState(Integer id, Integer enable,Integer state) {
        return certificationInfoMapper.updateCertificationState(id,enable,state);
    }

    @Override
    public Page<OldManHealthVo> getOldManHealthPage(Page page, Map map) {
        return baseMapper.getOldManHealthPage(page, map);
    }
}
