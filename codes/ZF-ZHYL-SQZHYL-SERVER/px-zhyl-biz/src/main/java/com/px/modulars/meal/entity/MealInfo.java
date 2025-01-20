

package com.px.modulars.meal.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.px.basic.alone.core.base.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * 配餐计划信息
 *
 * @author 品讯科技
 * @date 2024-08
 */
@Data
@TableName("meal_info")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "配餐计划信息")
public class MealInfo extends BaseModel<MealInfo> {
    private static final long serialVersionUID = 1L;

    /**
     * 配餐编号（YYYYMMDDL）L是类型编号
     */
    @ApiModelProperty(value = "配餐编号（YYYYMMDDL）L是类型编号")
    private String number;
    /**
     * 配餐日期 YYYY-MM-DD
     */
    @ApiModelProperty(value = "配餐日期 YYYY-MM-DD")
    private LocalDateTime mealDate;
    /**
     * 类型（1.早餐2.午餐3.晚餐）
     */
    @Size(max = 4, message = "类型（1.早餐2.午餐3.晚餐）超出长度4的限制")
    @ApiModelProperty(value = "类型（1.早餐2.午餐3.晚餐）")
    private Integer type;
    /**
     * 菜名
     */
    @Size(max = 255, message = "菜名超出长度255的限制")
    @ApiModelProperty(value = "菜名")
    private String mealName;
    /**
     * 图片
     */
    @Size(max = 255, message = "图片超出长度255的限制")
    @ApiModelProperty(value = "图片")
    private String image;
    /**
     * 配方
     */
    @ApiModelProperty(value = "配方")
    private String introduction;
    /**
     * 特点（多个）
     */
    @Size(max = 255, message = "特点（多个）超出长度255的限制")
    @ApiModelProperty(value = "特点（多个）")
    private String characteristics;

    @ApiModelProperty(value = "开始时间")
    private LocalDateTime startTime;

    @ApiModelProperty(value = "结束时间")
    private LocalDateTime endTime;

    @TableField(exist = false)
    private String mealDateStr;
    @TableField(exist = false)
    private Integer timeState;

    /**
     * 预定订单状态
     */
    @TableField(exist = false)
    private Integer orderState;
}

