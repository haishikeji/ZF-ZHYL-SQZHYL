

package com.px.modulars.team.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.px.basic.alone.core.base.BaseModel;

import javax.validation.constraints.*;

/**
 * 团队信息
 *
 * @author 品讯科技
 * @date 2024-08
 */
@Data
@TableName("team_info")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "团队信息")
public class TeamInfo extends BaseModel<TeamInfo> {
    private static final long serialVersionUID = 1L;

    /**
     * 队伍名字
     */
    @Size(max = 255, message = "队伍名字超出长度255的限制")
    @ApiModelProperty(value = "队伍名字")
    private String name;
    /**
     * 队伍标题图
     */
    @Size(max = 500, message = "队伍标题图超出长度500的限制")
    @ApiModelProperty(value = "队伍标题图")
    private String image;
    /**
     * 队伍介绍
     */
    @ApiModelProperty(value = "队伍介绍")
    private String introduce;
    @ApiModelProperty(value = "图标")
    private String icon;

    /**
     * 队伍人员数
     */
    @TableField(exist = false)
    private Integer PeopleCount;

}
