package com.px.modulars.upms.service.impl;

import cn.hutool.captcha.LineCaptcha;
import cn.hutool.captcha.generator.CodeGenerator;
import cn.hutool.captcha.generator.RandomGenerator;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.RandomUtil;
import com.github.benmanes.caffeine.cache.Cache;
import com.pig4cloud.pig.common.core.util.RedisHelper;
import com.pig4cloud.pig.common.core.util.WebUtils;
import com.px.basic.alone.core.base.BaseServiceImpl;

import com.px.modulars.upms.entity.SysPolicySecurity;
import com.px.modulars.upms.entity.SysUser;
import com.px.modulars.upms.mapper.SysPolicySecurityMapper;
import com.px.modulars.upms.service.SysPolicySecurityService;
import com.px.modulars.upms.vo.VerificationCodeVO;
import net.oschina.j2cache.CacheChannel;
import net.oschina.j2cache.CacheObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 安全配置
 *
 * @author 品讯科技
 * @date 2024-08
 */
@Service
public class SysPolicySecurityServiceImpl extends BaseServiceImpl<SysPolicySecurity, SysPolicySecurityMapper> implements SysPolicySecurityService {
    @Autowired
    private CacheChannel cacheChannel;
    @Autowired
    private RedisHelper redisHelper;

    private final String REGION_POLICY_REGION = "SYS_POLICY_SECURITY";
    /**
     * 用户的密码错误次数
     */
    private final String REGION_USER_PASS_ERROR = "REGION_USER_PASS_ERROR";
    /**
     * 需要填写验证码的用户
     */
    private final String REGION_VERIFICATION_USER = "REGION_VERIFICATION_USER";
    /**
     * 用户的验证码
     */
    private final String REGION_VERIFICATION_CODE = "REGION_VERIFICATION_CODE";
    /**
     * 用户账户锁定错误次数
     */
    private final String REGION_USER_LOCKING_ERROR = "REGION_USER_LOCKING_ERROR";

    @Override
    public SysPolicySecurity readDef() {
        CacheObject cacheObject = this.cacheChannel.get(REGION_POLICY_REGION, SysPolicySecurityService.defId.toString());
        if (cacheObject.getValue() != null) {
            Object obj = cacheObject.getValue();
            SysPolicySecurity policySecurity = (SysPolicySecurity) obj;
            return policySecurity;
        } else {
            SysPolicySecurity policySecurity = super.queryById(SysPolicySecurityService.defId);
            //如果等于空了，初始化一个值
            if (policySecurity == null) {
                policySecurity = new SysPolicySecurity();
                policySecurity.setFirstModify(1);
                policySecurity.setTimingModify(1);
                policySecurity.setVerificationCodePolicy(1);
                super.update(policySecurity);
            }
            //存入缓存，缓存时间1分钟
            this.cacheChannel.set(REGION_POLICY_REGION, SysPolicySecurityService.defId.toString(), policySecurity);
            return policySecurity;
        }
    }

    @Override
    public boolean isCode(SysPolicySecurity policy, SysUser user, boolean loginFlag) {
        if (user == null) {
            return true;
        }
        if (policy.getVerificationCodePolicy() == 1) {
            return false;
        } else if (policy.getVerificationCodePolicy() == 2) {
            //写入标志，需要填写验证码
            this.redisHelper.set(REGION_VERIFICATION_USER + "_" + user.getUsername(), "1", policy.getVerificationCodeTimeout());
            return true;
        } else if (policy.getVerificationCodePolicy() == 3) {
            //如果用户登录成功了，删除之前记录次数，返回成功
            if (loginFlag) {
                this.redisHelper.del(REGION_USER_PASS_ERROR + "_" + user.getUsername());
                return false;
            }
            int num = this.getIntCacheInfo(REGION_USER_PASS_ERROR, user.getUsername());
            num++;
            //如果密码输入错误次数超过，需要填写验证码
            if (num > policy.getPassErrorNumCode()) {
                this.redisHelper.set(REGION_VERIFICATION_USER + "_" + user.getUsername(), "1", policy.getVerificationCodeTimeout());
                return true;
            }
        }
        return false;
    }

    private int getIntCacheInfo(String region, String key) {
        Object cacheObject = this.redisHelper.get(region + "_" + key);
        int num = 0;
        if (cacheObject != null) {
            num = Integer.parseInt(cacheObject.toString());
        }
        return num;
    }

    @Override
    public VerificationCodeVO createVerificationCode(SysPolicySecurity policy, HttpServletRequest request) {
        boolean reFlag = false;
        if (policy.getVerificationCodePolicy() == 1) {
            //如果不需要，则返回空
            return null;
        } else if (policy.getVerificationCodePolicy() == 2) {
            reFlag = true;
        } else if (policy.getVerificationCodePolicy() == 3) {
            reFlag = true;
        }
        if (reFlag) {
            //如果需要，则返回信息
            //TODO 验证码暂时固定宽高，后期有需要了扩展，干扰个数也固定
            CodeGenerator codeGenerator = new RandomGenerator(policy.getVerificationCodeDice(), policy.getVerificationCodeLen());
            LineCaptcha lineCaptcha = new LineCaptcha(100, 50);
            lineCaptcha.setGenerator(codeGenerator);
            lineCaptcha.createCode();
            String imgBase64 = lineCaptcha.getImageBase64Data();
            VerificationCodeVO codeVo = new VerificationCodeVO();
            codeVo.setCode(imgBase64);

            String clientId = UUID.randomUUID().toString();
            codeVo.setToken(clientId);
            String code = lineCaptcha.getCode();
            this.redisHelper.set(REGION_VERIFICATION_CODE + "_" + codeVo.getToken(), code, policy.getVerificationCodeTimeout());
            return codeVo;
        } else {
            return null;
        }
    }

    @Override
    public boolean checkCode(SysPolicySecurity policy, String username, String token, String code) {
        boolean reFlag = false;
        //1、判断是否需要验证码
        if (policy.getVerificationCodePolicy() == 1) {
            //如果不需要验证码，直接通过
            return true;
        } else if (policy.getVerificationCodePolicy() == 2) {
            reFlag = true;
        } else if (policy.getVerificationCodePolicy() == 3) {
            //判断用户是否需要填写验证码
            Object flagObj = this.redisHelper.get(REGION_VERIFICATION_USER + "_" + username);
            if (flagObj != null) {
                reFlag = true;
            }
        }
        //2、判断验证码是否输入正确
        if (reFlag) {
            Object codeObj = this.redisHelper.get(REGION_VERIFICATION_CODE + "_" + token);
            if (codeObj == null) {
                return false;
            } else {
                return codeObj.equals(code);
            }
        }
        return true;
    }

    /**
     * @param policy 规则
     * @param user
     * @return
     */
    @Override
    public boolean isAllowLogin(SysPolicySecurity policy, SysUser user, boolean loginFlag) {
        //如果不限制，则跳过
        if (policy.getLockingErrorNum() == 0) {
            return true;
        }
        if (user == null) {
            return true;
        }
        //判断锁定时间
        if (user.getLockTime() != null) {
            if (user.getLockTime().isAfter(LocalDateTime.now())) {
                return true;
            }
        }

        int num = this.getIntCacheInfo(REGION_USER_LOCKING_ERROR, user.getUsername());
        //如果登录失败，则加一
        if (!loginFlag) {
            num++;
            this.redisHelper.set(REGION_USER_LOCKING_ERROR + "_" + user.getUsername(), num, policy.getLockingErrorNumTime());
        } else {

            this.redisHelper.del(REGION_USER_LOCKING_ERROR + "_" + user.getUsername());
            return true;
        }
        boolean flag = true;
        //如果超过锁定限制，不允许用户访问，更新用户的锁定标签
        if (num > policy.getLockingErrorNum()) {
            user.setLockTime(LocalDateTime.now().plusMinutes(policy.getLockingErrorNumTime()));
            this.redisHelper.del(REGION_USER_LOCKING_ERROR + "_" + user.getUsername());
            flag = false;
        }
        return flag;
    }

}
