

package com.px.modulars.replace.entity;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.px.basic.alone.core.base.BaseModel;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 代买记录表
 *
 * @author 品讯科技
 * @date 2024-08
 */
@Data
@TableName("replace_buy_record")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "代买记录表")
public class ReplaceBuyRecord extends BaseModel<ReplaceBuyRecord> {
    private static final long serialVersionUID = 1L;

    /**
     * 服务类型
     */
    @Size(max = 11, message = "服务类型超出长度11的限制")
    @ApiModelProperty(value = "服务类型")
    private Integer replaceBuy;
    /**
     * 代买说明
     */
    @ApiModelProperty(value = "代买说明")
    private String instructions;
    /**
     * 最后时限
     */
    @ApiModelProperty(value = "最后时限")
    private LocalDateTime endTime;
    /**
     * 所在楼宇
     */
    @Size(max = 11, message = "所在楼宇超出长度11的限制")
    @ApiModelProperty(value = "所在楼宇")
    private Integer buildingInfo;
    /**
     * 老人姓名
     */
    @Size(max = 255, message = "老人姓名超出长度255的限制")
    @ApiModelProperty(value = "老人姓名")
    private String oldName;
    /**
     * 联系方式
     */
    @Size(max = 255, message = "联系方式超出长度255的限制")
    @ApiModelProperty(value = "联系方式")
    private String phone;
    /**
     * 详细地址
     */
    @Size(max = 255, message = "详细地址超出长度255的限制")
    @ApiModelProperty(value = "详细地址")
    private String address;
    @ApiModelProperty(value = "经度")
    private String lng;
    @ApiModelProperty(value = "纬度")
    private String lat;
    /**
     * 代买类型（1.代叫2.自己叫）
     */
    @Size(max = 4, message = "代买类型（1.代叫2.自己叫）超出长度4的限制")
    @ApiModelProperty(value = "代买类型（1.代叫2.自己叫）")
    private Integer type;
    /**
     * 完成状态（1.完成2.未完成）
     */
    @Size(max = 4, message = "完成状态（1.完成2.未完成）超出长度4的限制")
    @ApiModelProperty(value = "完成状态（1.完成2.未完成）")
    private Integer state;
    /**
     * 老人id
     */
    @Size(max = 11, message = "老人id超出长度11的限制")
    @ApiModelProperty(value = "老人id")
    private Integer oldId;
    @ApiModelProperty(value = "费用")
    private BigDecimal cost;
    /**
     * 用户id
     */
    @Size(max = 11, message = "用户id超出长度11的限制")
    @ApiModelProperty(value = "用户id")
    private Long userId;
    @ApiModelProperty(value = "用户id")
    private Integer isEquipment;

    @TableField(exist = false)
    private ReplaceBuy replaceBuyVo;
    @TableField(exist = false)
    private String  replaceBuyName;
    @TableField(exist = false)
    private String  buildingInfoName;
    @TableField(exist = false)
    private String stateName;


/*    @TableField(exist = false)
    private String[] createTime;*/



}
