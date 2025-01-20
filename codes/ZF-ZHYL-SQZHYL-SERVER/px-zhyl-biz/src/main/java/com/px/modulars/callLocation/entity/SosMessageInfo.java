

package com.px.modulars.callLocation.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.px.basic.alone.core.base.BaseModel;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 呼叫定位SOS信息
 *
 * @author 品讯科技
 * @date 2024-08
 */
@Data
@TableName("sos_message_info")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "呼叫定位")
public class SosMessageInfo extends BaseModel<SosMessageInfo> {
    private static final long serialVersionUID = 1L;

    /**
     * 设备编号
     */
    @Size(max = 255, message = "设备编号超出长度255的限制")
    @ApiModelProperty(value = "设备编号")
    private String deviceNumber;
    /**
     * 设备卡号
     */
    @Size(max = 255, message = "设备卡号超出长度255的限制")
    @ApiModelProperty(value = "设备卡号")
    private String deviceCard;
    /**
     * 是否解决
     * 0，未解决
     * 1，已解决
     */
    @Size(max = 255, message = "是否解决 0，未解决 1，已解决超出长度255的限制")
    @ApiModelProperty(value = "是否解决 0，未解决 1，已解决")
    private String isSolve;
    /**
     * 经度
     */
    @Size(max = 255, message = "经度超出长度255的限制")
    @ApiModelProperty(value = "经度")
    private String longitude;
    /**
     * 纬度
     */
    @Size(max = 255, message = "纬度超出长度255的限制")
    @ApiModelProperty(value = "纬度")
    private String latitude;

    /**
     * 联系方式
     */
    @ApiModelProperty(value = "联系方式")
    private String phone;
    /**
     * 老人ID
     */
    @ApiModelProperty(value = "老人ID")
    private Integer  oldId ;
}
