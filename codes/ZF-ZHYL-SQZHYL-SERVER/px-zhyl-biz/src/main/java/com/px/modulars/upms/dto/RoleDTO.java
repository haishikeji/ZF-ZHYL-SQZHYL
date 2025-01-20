
package com.px.modulars.upms.dto;

import com.px.modulars.upms.entity.SysRole;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 品讯科技
 * @date 2024-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RoleDTO extends SysRole {

	/**
	 * 角色部门Id
	 */
	private Integer roleDeptId;

	/**
	 * 部门名称
	 */
	private String deptName;

}
