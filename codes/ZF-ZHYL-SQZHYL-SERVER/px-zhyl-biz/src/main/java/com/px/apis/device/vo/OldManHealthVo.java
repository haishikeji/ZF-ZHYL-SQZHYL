package com.px.apis.device.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class OldManHealthVo {

    private Integer  id;

    private String name;

    //定位
    private String location;

    //定位更新时间
    private LocalDateTime locationTime;

    //数据时间
    private LocalDateTime dataTime;

    //高压
    private Integer hightPressure;

    //低压
    private Integer lowPressure;

    //心率
    private Integer heartRate;

    //血糖
    private Integer mg;
    //血糖
    private BigDecimal mmol;

    //手表最新更新时间
    private LocalDateTime watchUpdateTime;

}
