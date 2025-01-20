
package com.px.modulars.auth.handler;

import cn.hutool.core.thread.ThreadUtil;
import com.pig4cloud.pig.common.core.exception.IllegalParameterException;
import com.pig4cloud.pig.common.core.exception.LoginException;
import com.px.basic.alone.security.handler.AbstractAuthenticationSuccessEventHandler;
import com.px.basic.alone.security.service.PigUser;
import com.px.modulars.upms.entity.SysPolicySecurity;
import com.px.modulars.upms.entity.SysUser;
import com.px.modulars.upms.service.SysPolicySecurityService;
import com.px.modulars.upms.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

/**
 * @author 品讯科技
 * @date 2024-08
 */
@Slf4j
@Component
public class PxAuthenticationSuccessEventHandler extends AbstractAuthenticationSuccessEventHandler {
    @Autowired
    private SysPolicySecurityService policySecurityService;
    @Autowired
    private SysUserService userService;

    /**
     * 处理登录成功方法
     * <p>
     * 获取到登录的authentication 对象
     *
     * @param authentication 登录对象
     */
    @Override
    public void handle(Authentication authentication) {
        log.info("用户：{} 登录成功", authentication.getPrincipal());
        ThreadUtil.execute(() -> {
            Object obj = authentication.getPrincipal();
            if (obj == null) {
                return;
            }
            PigUser pigUser = (PigUser) obj;
            SysUser user = this.userService.getUserByUsername(pigUser.getUsername());
            SysPolicySecurity policy = this.policySecurityService.readDef();
            boolean loginFlag = this.policySecurityService.isAllowLogin(policy, user, true);
            boolean codeFlag = this.policySecurityService.isCode(policy, user, true);

            log.info("登录验证状态：{}，验证码状态：{}", loginFlag, codeFlag);
        });
    }

}
