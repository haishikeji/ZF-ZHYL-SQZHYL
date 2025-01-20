

package com.px.modulars.callLocation.entity;

import lombok.Data;

/**
 * 实时定位-送餐信息
 */
@Data
public class MealEcordTableInfo {

    /**
     * 订餐记录ID
     */
    private Integer mealRecordId;

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
     * 地址
     */
    private String buildingName;

    /**
     * 记录地址
     */
    private String  mealEcordTableAddress;

    /**
     * 老人姓名
     */
    private String oldName;

    /**
     * 老人手机号
     */
    private String phone;



    /**
     * 楼宇地址
     */
    private String address;


    /**
     * 菜名
     */
    private String mealName;

    /**
     * 配餐类型
     */
    private String  type;

    /**
     * 创建时间
     */
    private String  createTime;
    private String  age;
    private String  image;

}
