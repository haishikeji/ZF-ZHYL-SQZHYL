

package com.px.modulars.device.entity;

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
 * 血压心率
 *
 * @author 品讯科技
 * @date 2024-08
 */
@Data
@TableName("pressure_rate")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "血压心率")
public class PressureRate extends BaseModel<PressureRate> {
    private static final long serialVersionUID = 1L;

    /**
     * 设备id
     */
    @ApiModelProperty(value = "设备id")
    private String device;
    /**
     * 高压
     */
    @ApiModelProperty(value = "高压")
    private Integer high;
    /**
     * 低压
     */
    @ApiModelProperty(value = "低压")
    private Integer low;
    /**
     * 心率
     */
    @ApiModelProperty(value = "心率")
    private Integer rate;
    /**
     * 设备类型：1-手表，2-血压仪
     */
    @ApiModelProperty(value = "设备类型：1-手表，2-血压仪")
    private Integer type;
}
