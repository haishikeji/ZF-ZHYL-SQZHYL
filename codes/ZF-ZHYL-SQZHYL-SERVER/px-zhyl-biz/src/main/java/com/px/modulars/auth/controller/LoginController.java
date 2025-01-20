package com.px.modulars.auth.controller;

import com.pig4cloud.pig.common.core.util.R;
import com.px.basic.alone.core.base.AbstractController;
import com.px.basic.alone.security.annotation.Inner;
import com.px.modulars.upms.entity.SysPolicySecurity;
import com.px.modulars.upms.service.SysPolicySecurityService;
import com.px.modulars.upms.vo.VerificationCodeVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户登录服务
 *
 * @author 品讯科技
 * @date 2024-08
 * @Description
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/l")
@Api(value = "login", tags = "登录服务")
public class LoginController extends AbstractController {
    @Autowired
    private SysPolicySecurityService policySecurityService;
    @Autowired
    private HttpServletRequest request;

    /**
     * 获取验证码
     *
     * @return
     */
    @ApiOperation(value = "图形验证码", notes = "图形验证码")
    @GetMapping("/imgCode")
    @Inner(false)
    public R createVerficationCode() {
        SysPolicySecurity policy = this.policySecurityService.readDef();
        VerificationCodeVO code = this.policySecurityService.createVerificationCode(policy, request);
        return R.ok(code);
    }

}
