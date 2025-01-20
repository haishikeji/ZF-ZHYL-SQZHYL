package com.px.modulars.upms.dto;

import com.px.modulars.upms.entity.SysUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author 品讯科技
 * @date 2024-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserDTO extends SysUser {

	/**
	 * 角色ID
	 */
	private List<Integer> role;

	private Integer deptId;

	/**
	 * 新密码
	 */
	private String newpassword1;
	/**
	 * 编辑标识，0为修改手机号，1为修改密码
	 */
	private Integer editIdea;
}
