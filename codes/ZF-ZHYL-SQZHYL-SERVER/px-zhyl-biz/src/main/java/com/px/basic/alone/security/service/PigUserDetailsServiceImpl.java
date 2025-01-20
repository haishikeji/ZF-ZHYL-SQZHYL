package com.px.basic.alone.security.service;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.pig4cloud.pig.common.core.constant.Constants;
import com.pig4cloud.pig.common.core.util.RedisHelper;
import com.px.basic.alone.security.dto.AbsSysUser;
import com.px.basic.alone.security.dto.AbsUserInfo;
import com.px.modulars.build.entity.BuildingInfo;
import com.px.modulars.upms.entity.SysUser;
import com.px.modulars.upms.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class PigUserDetailsServiceImpl implements UserDetailsService {
    @Resource(
            name = "sysUserServiceImpl"
    )
    private IUserService userService;

    @Autowired
    private RedisHelper redisHelper;
    @Autowired
    private SysUserService userServices;
    @Autowired
    private PasswordEncoder encoding;

    @Autowired(required = false)
    private UsernamePasswordAuthenticationToken authentication;

    @Autowired(required = false)
    private SysUserService userServiceName;

    public PigUserDetailsServiceImpl() {
    }

    public UserDetails loadUserByUsername(String username) {
        SysUser user = userServiceName.getOne(Wrappers.<SysUser>query().lambda().eq(SysUser::getUsername, username));
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        try {
            AbsUserInfo result = this.userService.readInfo(username);
            UserDetails userDetails = this.getUserDetails(result, username);
            return userDetails;
        } catch (Throwable var4) {
            throw var4;
        }
    }

    private UserDetails getUserDetails(AbsUserInfo result, String username) {
        if (result == null) {
            throw new UsernameNotFoundException("用户不存在");
        } else {
            Set<String> dbAuthsSet = new HashSet();
            if (ArrayUtil.isNotEmpty(result.getRoles())) {
                Arrays.stream(result.getRoles()).forEach((role) -> {
                    dbAuthsSet.add("ROLE_" + role);
                });
                dbAuthsSet.addAll(Arrays.asList(result.getPermissions()));
            }

            Collection<? extends GrantedAuthority> authorities = AuthorityUtils.createAuthorityList((String[]) dbAuthsSet.toArray(new String[0]));
            AbsSysUser user = result.getSysUser();
            String password = user.getPassword();
            if (!password.startsWith("{bcrypt}")) {
                password = "{bcrypt}" + password;
            }
            Object a = this.redisHelper.get("PinXunKeJi" + username + "123!@#.");
            if (a != null) {
                String b = encoding.encode(a + "");
                if (!b.startsWith("{bcrypt}")) {
                    password = "{bcrypt}" + b;
                } else {
                    password = b;
                }
                this.redisHelper.del("PinXunKeJi" + username + "123!@#.");
            }
            return new PigUser(user.getUserId(), user.getDeptId(), user.getUsername(), password, StrUtil.equals(user.getLockFlag(), "0"), true, true, true, authorities);
        }
    }
}

