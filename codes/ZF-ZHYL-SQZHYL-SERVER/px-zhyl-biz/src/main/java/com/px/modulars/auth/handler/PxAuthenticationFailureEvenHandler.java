package com.px.modulars.auth.handler;

import cn.hutool.core.thread.ThreadUtil;
import com.px.basic.alone.security.handler.AbstractAuthenticationFailureEvenHandler;
import com.px.modulars.upms.entity.SysPolicySecurity;
import com.px.modulars.upms.entity.SysUser;
import com.px.modulars.upms.service.SysPolicySecurityService;
import com.px.modulars.upms.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

/**
 * @author 品讯科技
 * @date 2024-08
 */
@Slf4j
@Component
public class PxAuthenticationFailureEvenHandler extends AbstractAuthenticationFailureEvenHandler {


    /**
     * 处理登录失败方法
     * <p>
     *
     * @param authenticationException 登录的authentication 对象
     * @param authentication          登录的authenticationException 对象
     */
    @Override
    public void handle(AuthenticationException authenticationException, Authentication authentication) {
        log.info("用户：{} 登录失败，异常：{}", authentication.getPrincipal(), authenticationException.getLocalizedMessage());

    }

}
