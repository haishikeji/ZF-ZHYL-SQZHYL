

package com.px.modulars.function.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.px.basic.alone.core.base.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Size;

/**
 * 功能室基本信息
 *
 * @author 品讯科技
 * @date 2024-08
 */
@Data

@ApiModel(value = "功能室基本信息")
public class FunctionRoomVo extends BaseModel<FunctionRoomVo> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "标题")
    private String title;
    @ApiModelProperty(value = "标题图")
    private String image;
    @ApiModelProperty(value = "内容")
    private String content;
    @ApiModelProperty(value = "浏览量")
    private Integer views;
    @ApiModelProperty(value = "房间分类")
    private Integer roomClass;
    @ApiModelProperty(value = "捐款捐物类型（1.公益事项2.捐资捐物）")
    private Integer type;
    @ApiModelProperty(value = "房间分类名称")
    private Integer roomClassName;

}
