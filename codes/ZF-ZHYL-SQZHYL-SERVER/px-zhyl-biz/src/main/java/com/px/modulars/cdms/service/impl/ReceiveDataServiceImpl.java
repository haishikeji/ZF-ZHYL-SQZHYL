package com.px.modulars.cdms.service.impl;


import com.px.modulars.blood.entity.BloodSugar;
import com.px.modulars.blood.service.BloodSugarService;
import com.px.modulars.cdms.common.bean.SrcDataValue;
import com.px.modulars.cdms.service.ReceiveDataService;
import com.px.modulars.equipment.entity.EquipmentInfo;
import com.px.modulars.equipment.service.EquipmentInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.ZoneId;


/***************************************************************************
 * <PRE>
 *
 *  Project Name    : cdms-api
 *
 *  Description     : 血糖设备数据采集service
 *
 *  Author          : huke.zhang
 *
 * </PRE>
 ***************************************************************************/
@Service
public class ReceiveDataServiceImpl implements ReceiveDataService {

    private static Logger logger = LoggerFactory.getLogger(ReceiveDataServiceImpl.class);


    @Autowired
    private EquipmentInfoService equipmentInfoService;


    @Autowired
    private BloodSugarService bloodSugarService;


    /**
     * 处理上报的数据
     *
     * @param srcDatavalue
     * @return
     */
    @Override
    public boolean handleReceivedData(SrcDataValue srcDatavalue) {
        String patientId = null;

        //设备序列号
        String devSN = srcDatavalue.getSerialNumber();
        try {
			//查找绑定的老人信息
            EquipmentInfo info = equipmentInfoService.lambdaQuery().eq(EquipmentInfo::getBloodCard, devSN).eq(EquipmentInfo::getEnable,1).one();
            if (info != null && info.getBindOldMan() != null) {
				//插入血糖检测记录
				BloodSugar saveInfo = new BloodSugar();
				//老人id
				saveInfo.setOldManId(info.getBindOldMan());
				//时间
				saveInfo.setCreateTime(LocalDateTime.now());
				saveInfo.setDataTime(LocalDateTime.ofInstant(srcDatavalue.getCheckTime().toInstant(), ZoneId.systemDefault()));
				//血糖值原数据
				saveInfo.setMg(Integer.parseInt(srcDatavalue.getData3()));
                //换算值
				saveInfo.setMmol(new BigDecimal(saveInfo.getMg()).divide(new BigDecimal(18), 1,RoundingMode.HALF_UP));
				bloodSugarService.save(saveInfo);

            }
        } catch (Exception e) {
            logger.error("GPRS血糖设备血糖上传异常", e);

        }
        return true;
    }

}
	
	


