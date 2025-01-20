

package com.px.modulars.replace.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.px.basic.alone.core.base.BaseModel;

import javax.validation.constraints.*;

/**
 * 代买代办服务类别
 *
 * @author 品讯科技
 * @date 2024-08
 */
@Data
@TableName("replace_buy")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "代买代办服务类别")
public class ReplaceBuy extends BaseModel<ReplaceBuy> {
    private static final long serialVersionUID = 1L;

    /**
     * 服务内容名称
     */
    @Size(max = 255, message = "服务内容名称超出长度255的限制")
    @ApiModelProperty(value = "服务内容名称")
    private String name;
    /**
     * 标题图
     */
    @Size(max = 500, message = "标题图超出长度500的限制")
    @ApiModelProperty(value = "标题图")
    private String image;
    /**
     * 内容
     */
    @ApiModelProperty(value = "内容")
    private String content;
    /**
     * 图标
     */
    @ApiModelProperty(value = "图标")
    private String icon;
}

