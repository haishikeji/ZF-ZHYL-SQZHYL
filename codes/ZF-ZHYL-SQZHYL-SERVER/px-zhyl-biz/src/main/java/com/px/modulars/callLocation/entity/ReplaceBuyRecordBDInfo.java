

package com.px.modulars.callLocation.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 实时定位-老人详情
 */
@Data
public class ReplaceBuyRecordBDInfo {

    /**
     * 代买记录ID
     */
    private Integer replaceBuyRecordId;
    /**
     * 楼宇ID
     */
    private Integer buildingInfoId;
    /**
     * 设备ID
     */
    private Integer deviceInfoId;

    /**
     * 设备编号
     */
    private String deviceNumber;
    /**
     * 经度
     */
    private String longitude;
    /**
     * 纬度
     */
    private String latitude;
    /**
     * 小区
     */
    private String community;
    /**
     * 楼号
     */
    private String buildingNumber;

    /**
     * 楼宇地址
     */
    private String address;
    /**
     * 记录地址
     */
    private String  replaceBuyRecordAddress;

    /**
     * 老人姓名
     */
    private String oldName;

    /**
     * 老人手机号
     */
    private String phone;

    /**
     * 提交日期
     */
    private String createTime;


    /**
     * 说明
     */
    private String remark;


    /**
     * 最迟服务时间
     */
    private String endTime;

    /**
     * 服务类型
     */
    private String  replaceBuy;
    private String  image;
}
