

package com.px.modulars.upms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.px.modulars.upms.entity.SysDept;
import com.px.modulars.upms.entity.SysDeptRelation;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 品讯科技
 * @since 2019/2/1
 */
public interface SysDeptRelationService extends IService<SysDeptRelation> {

	/**
	 * 新建部门关系
	 * @param sysDept 部门
	 */
	void saveDeptRelation(SysDept sysDept);

	/**
	 * 通过ID删除部门关系
	 * @param id
	 */
	void removeDeptRelationById(Integer id);

	/**
	 * 更新部门关系
	 * @param relation
	 */
	void updateDeptRelation(SysDeptRelation relation);

}
