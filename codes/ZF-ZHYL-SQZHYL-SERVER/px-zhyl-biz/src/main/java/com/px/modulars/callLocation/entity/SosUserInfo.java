

package com.px.modulars.callLocation.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.px.basic.alone.core.base.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Size;

/**
 * 呼叫定位SOS信息
 *
 * @author 品讯科技
 * @date 2024-08
 */
@Data
public class SosUserInfo extends BaseModel<SosUserInfo> {
    private static final long serialVersionUID = 1L;

    /**
     * 设备编号
     */
    @ApiModelProperty(value = "设备编号")
    private String deviceNumber;
    /**
     * 设备卡号
     */
    @ApiModelProperty(value = "设备卡号")
    private String deviceCard;
    /**
     * 是否解决
     * 0，未解决
     * 1，已解决
     */
    @ApiModelProperty(value = "是否解决 0，未解决 1，已解决")
    private String isSolve;
    /**
     * 经度
     */
    @ApiModelProperty(value = "经度")
    private String longitude;
    /**
     * 纬度
     */
    @ApiModelProperty(value = "纬度")
    private String latitude;

    /**
     * 老人姓名
     */
    @ApiModelProperty(value = "老人姓名")
    private String name;

    /**
     * 老人手机号
     */
    @ApiModelProperty(value = "老人手机号")
    private String phone;

    /**
     * 老人健康状态
     */
    @ApiModelProperty(value = "老人健康状态")
    private String physical;

    /**
     * 老人所在楼宇
     */
    @ApiModelProperty(value = "老人所在楼宇")
    private String building;

    /**
     * 老人年龄
     */
    @ApiModelProperty(value = "老人年龄")
    private String age;


    private String oldId;


    private String buildingId;

    private String community;


    private String building_number;
    private String image;

}
