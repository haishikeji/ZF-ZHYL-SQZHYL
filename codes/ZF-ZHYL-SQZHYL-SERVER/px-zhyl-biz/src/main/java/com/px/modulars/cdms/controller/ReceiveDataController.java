package com.px.modulars.cdms.controller;


import com.px.basic.alone.security.annotation.Inner;
import com.px.modulars.cdms.common.bean.SrcDataValue;
import com.px.modulars.cdms.common.consts.ApiConsts;
import com.px.modulars.cdms.common.utils.HdwDataCheckUtils;
import com.px.modulars.cdms.common.utils.StringUtil;
import com.px.modulars.cdms.service.ReceiveDataService;
import com.px.modulars.cdms.service.ReceivePressureDataService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;


@Controller
@RequestMapping({"/receive/api"})
public class ReceiveDataController {

    private static Logger logger = LoggerFactory
            .getLogger(ReceiveDataController.class);

    @Autowired
    private ReceiveDataService receiveDataService;
    @Autowired
    private ReceivePressureDataService receivePressureDataService;

    /**
     * 服务器IP地址
     */
    @Value("${server.ip}")
    private String currServerIP;

    /**
     * 接收设备数据上报请求
     *
     * @param request
     * @param response
     * @param data
     * @throws IOException
     */
    @Inner(value = false)
    @RequestMapping({"data"})
    public void receiveData(HttpServletRequest request,
                            HttpServletResponse response, @RequestParam("data") String data)
            throws IOException {


        System.out.println("进来了" + data);
        logger.debug("##receive data start...");

        // 校验数据合法性
        if (checkReceivedData(data)) {

            SrcDataValue srcDatavalue = new SrcDataValue(data);

            // 只接收血糖、血压数据!!!!!!!!!!!!!!!!
            if (!(ApiConsts.DataTypeEnum.BLPRESSURE.getCode().equals(srcDatavalue.getDeviceType()) || ApiConsts.DataTypeEnum.BLGLUCOSE.getCode().equals(
                    srcDatavalue.getDeviceType()))) {
                return;
            }
            boolean isSuccess = false;
            // 处理上报的数据
            if (ApiConsts.DataTypeEnum.BLGLUCOSE.getCode().equals(
                    srcDatavalue.getDeviceType())) {
                isSuccess = this.receiveDataService
                        .handleReceivedData(srcDatavalue);
            } else if (ApiConsts.DataTypeEnum.BLPRESSURE.getCode().equals(srcDatavalue.getDeviceType())) {
                isSuccess = this.receivePressureDataService
                        .handleReceivedData(srcDatavalue);
            }


            logger.debug("## handle the received data {} result of {}", data,
                    isSuccess);

            // 给血糖设备回复报文
            if (true) {
                StringBuilder rslt = new StringBuilder("+IP");
                rslt.append(getServerIp(request));
                rslt.append(getServerDate());
                rslt.append("OK");

                OutputStream outWriter = response.getOutputStream();

                byte[] towrite = rslt.toString().getBytes();

                response.setContentLength(towrite.length);
                outWriter.write(towrite);
                outWriter.flush();
                outWriter.close();

                logger.debug("##return data({})", rslt.toString());
            }
        }

        logger.debug("##receive data end...");
    }

    /**
     * 校验上报数据的合法性
     *
     * @param data
     * @return
     */
    private boolean checkReceivedData(String data) {

        if ((data != null) && ((data.length() == 85) || (data.length() == 87))) {

            if ((data.length() == 85)
                    && (HdwDataCheckUtils.isSumCheckCode(data))) {
                return true;
            }

            if ((data.length() == 87)
                    && (HdwDataCheckUtils.isSumCheckCode(data))
                    && (HdwDataCheckUtils.isCrc8Code(data))) {
                return true;
            }
        }

        return false;
    }

    /**
     * 获取请求端ip地址
     *
     * @param request
     * @return
     */
    private String getServerIp(HttpServletRequest request) {

        String[] ips = StringUtil.toString(currServerIP).split("\\.");

        if ((null == ips) || (0 >= ips.length)) {
            return "";
        }

        String ports = StringUtils.leftPad(
                Integer.toHexString(request.getLocalPort()), 4, "0");

        StringBuilder ipsb = new StringBuilder();

        int sum = 0;

        for (int i = 0; i < ips.length; i++) {
            ipsb.append(StringUtils.leftPad(
                    Integer.toHexString(Integer.valueOf(ips[i]).intValue()), 2,
                    "0"));
            sum ^= Integer.valueOf(ips[i]).intValue();
        }

        sum ^= Integer.valueOf(ports.substring(2, 4), 16).intValue();
        ipsb.append(ports.substring(2, 4));
        sum ^= Integer.valueOf(ports.substring(0, 2), 16).intValue();
        ipsb.append(ports.substring(0, 2));
        ipsb.append(Integer.toHexString(sum));

        return ipsb.toString().toUpperCase();
    }

    /**
     * 获取服务器时间
     *
     * @return
     */
    private String getServerDate() {

        StringBuilder datasb = new StringBuilder();

        int sum = 0;

        Calendar cal = Calendar.getInstance();
        Integer year = Integer.valueOf(cal.get(1) % 100);
        datasb.append(StringUtils.leftPad(Integer.toHexString(year.intValue()),
                2, "0"));
        sum ^= year.intValue();

        Integer month = Integer.valueOf(cal.get(2) + 1);
        datasb.append(StringUtils.leftPad(
                Integer.toHexString(month.intValue()), 2, "0"));
        sum ^= month.intValue();

        Integer day = Integer.valueOf(cal.get(5));
        datasb.append(StringUtils.leftPad(Integer.toHexString(day.intValue()),
                2, "0"));
        sum ^= day.intValue();

        Integer hour = Integer.valueOf(cal.get(11));
        datasb.append(StringUtils.leftPad(Integer.toHexString(hour.intValue()),
                2, "0"));
        sum ^= hour.intValue();

        Integer minute = Integer.valueOf(cal.get(12));
        datasb.append(StringUtils.leftPad(
                Integer.toHexString(minute.intValue()), 2, "0"));
        sum ^= minute.intValue();

        datasb.append(StringUtils.leftPad(Integer.toHexString(sum), 2, "0"));

        return datasb.toString().toUpperCase();
    }
}
