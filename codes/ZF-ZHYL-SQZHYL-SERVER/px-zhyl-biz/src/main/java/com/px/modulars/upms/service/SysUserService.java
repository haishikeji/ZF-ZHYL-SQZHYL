

package com.px.modulars.upms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.px.basic.alone.security.service.IUserService;
import com.px.modulars.upms.dto.UserDTO;
import com.px.modulars.upms.dto.UserInfo;
import com.px.modulars.upms.entity.SysUser;
import com.px.modulars.upms.vo.UserVO;
import com.pig4cloud.pig.common.core.util.R;

import java.util.List;

/**
 * @author 品讯科技
 * @date 2024-08
 */
public interface SysUserService extends IService<SysUser>, IUserService {

    /**
     * 查询用户信息
     *
     * @param sysUser 用户
     * @return userInfo
     */
    UserInfo getUserInfo(SysUser sysUser);

    /**
     * 根据用户名查询用户信息
     *
     * @param username
     * @return
     */
    public SysUser getUserByUsername(String username);

    /**
     * 分页查询用户信息（含有角色信息）
     *
     * @param page    分页对象
     * @param userDTO 参数列表
     * @return
     */
    IPage getUserWithRolePage(Page page, UserDTO userDTO);

    /**
     * 删除用户
     *
     * @param sysUser 用户
     * @return boolean
     */
    Boolean removeUserById(SysUser sysUser);

    /**
     * 更新当前用户基本信息
     *
     * @param userDto 用户信息
     * @return Boolean
     */
    R updateUserInfo(UserDTO userDto);

    /**
     * 更新指定用户信息
     *
     * @param userDto 用户信息
     * @return
     */
    Boolean updateUser(UserDTO userDto);

    /**
     * 通过ID查询用户信息
     *
     * @param id 用户ID
     * @return 用户信息
     */
    UserVO getUserVoById(Integer id);

    /**
     * 查询上级部门的用户信息
     *
     * @param username 用户名
     * @return R
     */
    List<SysUser> listAncestorUsersByUsername(String username);

    /**
     * 保存用户信息
     *
     * @param userDto DTO 对象
     * @return success/fail
     */
    Boolean saveUser(UserDTO userDto);

}
