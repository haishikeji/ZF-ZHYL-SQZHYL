

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
 * 队员信息
 *
 * @author 品讯科技
 * @date 2024-08
 */
@Data
@TableName("team_personnel")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "队员信息")
public class TeamPersonnel extends BaseModel<TeamPersonnel> {
    private static final long serialVersionUID = 1L;

    /**
     * 队员姓名
     */
    @Size(max = 255, message = "队员姓名超出长度255的限制")
    @ApiModelProperty(value = "队员姓名")
    private String name;
    /**
     * 职位
     */
    @Size(max = 255, message = "职位超出长度255的限制")
    @ApiModelProperty(value = "职位")
    private Integer position;
    /**
     * 联系方式
     */
    @Size(max = 255, message = "联系方式超出长度255的限制")
    @ApiModelProperty(value = "联系方式")
    private String phone;
    /**
     * 简介
     */
    @ApiModelProperty(value = "简介")
    private String introduction;
    /**
     * 头像
     */
    @ApiModelProperty(value = "头像")
    private String image;
    /**
     * 用户id(崇文路系统)
     */
    @Size(max = 20, message = "用户id(崇文路系统)超出长度20的限制")
    @ApiModelProperty(value = "用户id(崇文路系统)")
    private Long allUser;
    /**
     * 所属队伍
     */
    @Size(max = 11, message = "所属队伍超出长度11的限制")
    @ApiModelProperty(value = "所属队伍")
    private Integer teamInfo;

    @TableField(exist = false)
    private Integer vid;
}
