

package com.px.modulars.upms.vo;

import com.px.modulars.upms.entity.SysRole;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 品讯科技
 * @date 2024-08
 */
@Data
public class UserVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private Integer userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 随机盐
     */
    private String salt;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

    /**
     * 0-正常，1-删除
     */
    private String delFlag;

    /**
     * 锁定标记
     */
    private String lockFlag;

    /**
     * 简介
     */
    private String phone;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 部门ID
     */
    private Integer deptId;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 角色列表
     */
    private List<SysRole> roleList;

    @ApiModelProperty(value = "锁定时间")
    private LocalDateTime lockTime;
    @ApiModelProperty(value = "密码过期时间")
    private LocalDateTime passExpiredTime;
}
