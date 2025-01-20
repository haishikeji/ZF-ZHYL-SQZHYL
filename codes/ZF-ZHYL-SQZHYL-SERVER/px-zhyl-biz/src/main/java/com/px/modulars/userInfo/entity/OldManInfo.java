

package com.px.modulars.userInfo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.px.basic.alone.core.base.BaseModel;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

/**
 * 老人基本信息
 *
 * @author 品讯科技
 * @date 2024-08
 */
@Data
@TableName("old_man_info")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "老人基本信息")
public class OldManInfo extends BaseModel<OldManInfo> {
    private static final long serialVersionUID = 1L;

    /**
     * 姓名
     */
    @Size(max = 255, message = "姓名超出长度255的限制")
    @ApiModelProperty(value = "姓名")
    private String name;
    /**
     * 性别（1.男2.女）
     */
    @Size(max = 4, message = "性别（1.男2.女）超出长度4的限制")
    @ApiModelProperty(value = "性别（1.男2.女）")
    private Integer sex;
    /**
     * 年龄
     */
    @Size(max = 4, message = "年龄超出长度4的限制")
    @ApiModelProperty(value = "年龄")
    private Integer age;
    /**
     * 手机号
     */
    @Size(max = 255, message = "手机号超出长度255的限制")
    @ApiModelProperty(value = "手机号")
    private String phone;
    /**
     * 民族
     */
    @Size(max = 255, message = "民族超出长度255的限制")
    @ApiModelProperty(value = "民族")
    private String national;
    /**
     * 身体状况
     */
    @Size(max = 255, message = "身体状况超出长度255的限制")
    @ApiModelProperty(value = "身体状况")
    private String physical;
    /**
     * 所在楼宇
     */
    @Size(max = 11, message = "所在楼宇超出长度11的限制")
    @ApiModelProperty(value = "所在楼宇")
    private Integer building;
    /**
     * 详细信息
     */
    @Size(max = 500, message = "详细信息超出长度500的限制")
    @ApiModelProperty(value = "详细信息")
    private String address;
    /**
     * 身份证信息
     */
    @Size(max = 255, message = "身份证信息超出长度255的限制")
    @ApiModelProperty(value = "身份证信息")
    private String idCard;
    /**
     * 文化程度
     */
    @Size(max = 255, message = "文化程度超出长度255的限制")
    @ApiModelProperty(value = "文化程度")
    private String education;
    /**
     * 婚姻状况（1.已婚2.未婚）
     */
    @Size(max = 4, message = "婚姻状况（1.已婚2.未婚）超出长度4的限制")
    @ApiModelProperty(value = "婚姻状况（1.已婚2.未婚）")
    private Integer maritalStatus;
    /**
     * 家庭困难程度
     */
    @Size(max = 255, message = "家庭困难程度超出长度255的限制")
    @ApiModelProperty(value = "家庭困难程度")
    private String familyHardship;
    /**
     * 党员情况（1.是2.否）
     */
    @Size(max = 4, message = "党员情况（1.是2.否）超出长度4的限制")
    @ApiModelProperty(value = "党员情况（1.是2.否）")
    private Integer partyStatus;
    /**
     * 保险状况(1.有保险2.无保险)
     */
    @Size(max = 4, message = "保险状况(1.有保险2.无保险)超出长度4的限制")
    @ApiModelProperty(value = "保险状况(1.有保险2.无保险)")
    private Integer insuranceStatus;
    /**
     * 优抚情况(1.有2.无)
     */
    @Size(max = 4, message = "优抚情况(1.有2.无)超出长度4的限制")
    @ApiModelProperty(value = "优抚情况(1.有2.无)")
    private Integer materialsStatus;
    /**
     * 社会救助(1.有2.无)
     */
    @Size(max = 4, message = "社会救助(1.有2.无)超出长度4的限制")
    @ApiModelProperty(value = "社会救助(1.有2.无)")
    private Integer socialAssistance;
    /**
     * 崇文路老人用户id
     */
    @Size(max = 20, message = "崇文路老人用户id超出长度20的限制")
    @ApiModelProperty(value = "崇文路老人用户id")
    private Long allUser;


    @ApiModelProperty(value = "最后一次定位地点")
    private String location;

    @ApiModelProperty(value = "最后一次定位时间")
    private LocalDateTime locationTime;

    /**
     * 户籍地址
     */
    @ApiModelProperty(value = "户籍地址")
    private String permanentAddress;
    /**
     * 所属街道
     */
    @ApiModelProperty(value = "所属街道")
    private String street;

    /**
     * 经度
     */
    @TableField(exist = false)
    private String longitude;
    /**
     * 纬度
     */
    @TableField(exist = false)
    private String latitude;
    /**
     *头像
     */
    @ApiModelProperty(value = "头像")
    private String  image;

    /**
     *  认证状态
     */
    @TableField(exist = false)
    private Integer cerState;

    /**
     *  认证状态原因
     */
    @TableField(exist = false)
    private String reason;

}
