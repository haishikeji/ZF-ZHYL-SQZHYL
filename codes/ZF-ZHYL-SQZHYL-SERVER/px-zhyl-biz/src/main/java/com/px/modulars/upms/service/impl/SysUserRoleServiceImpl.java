

package com.px.modulars.upms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.px.modulars.upms.entity.SysUserRole;
import com.px.modulars.upms.mapper.SysUserRoleMapper;
import com.px.modulars.upms.service.SysUserRoleService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户角色表 服务实现类
 * </p>
 *
 * @author 品讯科技
 * @since 2019/2/1
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {

	/**
	 * 根据用户Id删除该用户的角色关系
	 * @param userId 用户ID
	 * @return boolean
	 * @author 品讯科技
	 * @date 2024-08
	 */
	@Override
	public Boolean removeRoleByUserId(Integer userId) {
		return baseMapper.deleteByUserId(userId);
	}

}
