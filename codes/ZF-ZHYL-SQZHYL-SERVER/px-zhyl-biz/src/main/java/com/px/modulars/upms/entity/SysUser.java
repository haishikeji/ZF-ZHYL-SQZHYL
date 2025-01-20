
package com.px.modulars.upms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.px.basic.alone.security.dto.AbsSysUser;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author 品讯科技
 * @since 2019/2/1
 */
@Data
public class SysUser extends AbsSysUser implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键ID
	 */
	@TableId(value = "user_id", type = IdType.AUTO)
	@ApiModelProperty(value = "主键id")
	private Integer userId;

	/**
	 * 用户名
	 */
	@ApiModelProperty(value = "用户名")
	private String username;

	/**
	 * 密码
	 */
	@ApiModelProperty(value = "密码")
	private String password;

	/**
	 * 随机盐
	 */
	@JsonIgnore
	@ApiModelProperty(value = "随机盐")
	private String salt;

	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间")
	private LocalDateTime createTime;

	/**
	 * 修改时间
	 */
	@ApiModelProperty(value = "修改时间")
	private LocalDateTime updateTime;

	/**
	 * 锁定标记
	 */
	@ApiModelProperty(value = "锁定标记")
	private String lockFlag;

	/**
	 * 手机号
	 */
	@ApiModelProperty(value = "手机号")
	private String phone;

	/**
	 * 头像
	 */
	@ApiModelProperty(value = "头像地址")
	private String avatar;

	/**
	 * 部门ID
	 */
	@ApiModelProperty(value = "用户所属部门id")
	private Integer deptId;

	/**
	 * 0-正常，1-删除
	 */
	@TableLogic
	private String delFlag;

	@ApiModelProperty(value = "锁定时间")
	private LocalDateTime lockTime;
	@ApiModelProperty(value = "密码过期时间")
	private LocalDateTime passExpiredTime;

}
