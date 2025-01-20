package com.px.apis.user.controller;

import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.core.util.RedisHelper;
import com.px.apis.user.vo.LoginResult;
import com.px.apis.user.vo.MiniUsersParam;
import com.px.apis.user.vo.MiniUsersVo;
import com.px.basic.alone.security.dto.AbsAppUser;
import com.px.modulars.miniusers.entity.MiniUsers;
import com.px.modulars.miniusers.service.MiniUsersService;
import com.px.modulars.upms.entity.SysDictItem;
import com.px.modulars.upms.service.SysDictItemService;
import com.px.wx.mini.core.WxMiniUserBaseController;
import com.px.wx.vo.param.AbsWxLoginUserInfoParam;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * @author 品讯科技
 * @date 2024-08
 * @Description
 */
@RestController
@RequestMapping("/api/wx/user")
@RequiredArgsConstructor
@Api(value = "user", tags = "小程序端登录接口管理")
public class WxUserLoginController extends WxMiniUserBaseController {

    @Value("${api.token-key}")
    private String apiTokenKey;

    @Autowired
    private MiniUsersService miniUsersService;

    @Autowired
    private SysDictItemService dictItemService;

    @Autowired
    private RedisHelper redisHelper;

    @Override
    protected AbsAppUser autoUserLogin(String s) {
        return getUser(s);
    }

    @Override
    protected AbsAppUser userLogin(AbsWxLoginUserInfoParam absWxLoginUserInfoParam) {
        return getUser(absWxLoginUserInfoParam.getOpenid());
    }

    @Override
    protected String getAppid() {
        return dictItemService.lambdaQuery()
                .eq(SysDictItem::getLabel, "appid")
                .eq(SysDictItem::getType, "wx_config")
                .one().getValue();
    }

    public MiniUsersVo getUser(String openid) {
        if (openid != null) {
            MiniUsers u = miniUsersService.lambdaQuery().eq(MiniUsers::getOpenId, openid).one();
            if (u == null) {
                u = new MiniUsers();
                u.setOpenId(openid);
                u.setCreateTime(LocalDateTime.now());
                miniUsersService.save(u);
            }
            MiniUsersVo result = new MiniUsersVo();
            BeanUtil.copyProperties(u, result, true);
            return result;
        }
        return null;
    }

    @PostMapping("/login")
    public R login(@RequestBody MiniUsersParam param) {
        LoginResult result = new LoginResult();

        AbsAppUser user = super.loginHandler(param);
        result.setAbsAppUser(user);
        this.redisHelper.set(this.apiTokenKey + "_" + user.getToken(), user.getId(), 60 * 60 * 24 * 365);
        MiniUsers miniUser = miniUsersService.getById(user.getId());
        if (miniUser != null) {
            miniUser.setNickName(param.getNickName());
            miniUser.setAvatar(param.getAvatar());
            miniUser.setSex(param.getSex());
            miniUser.setPhone(param.getPhone());
            miniUsersService.updateById(miniUser);
            result.setMiniUser(miniUser);
        }
        return super.setSuccessModelMap(result);
    }

    @PostMapping("/autoLogin")
    public R autoLogin(@RequestBody AbsWxLoginUserInfoParam param) {
        AbsAppUser user = super.autoLoginHandler(param);
        return super.setSuccessModelMap(user);
    }

    @PostMapping("/getphone")
    public R getphone(@RequestBody MiniUsersParam param) {
        WxMaJscode2SessionResult session = super.getSession(getAppid(), param.getCode());
        WxMaPhoneNumberInfo phoneNoInfo = super.getWxUserService(getAppid()).getPhoneNoInfo(session.getSessionKey(), param.getEncryptedData(), param.getIv());
        if (phoneNoInfo != null && StrUtil.isNotEmpty(phoneNoInfo.getPhoneNumber())) {
            Integer uid = super.getCurrentId();
            miniUsersService.lambdaUpdate()
                    .eq(MiniUsers::getId, uid)
                    .set(MiniUsers::getPhone, phoneNoInfo.getPhoneNumber())
                    .set(MiniUsers::getUpdateTime, LocalDateTime.now())
                    .update();
            return super.setSuccessModelMap(phoneNoInfo.getPhoneNumber());
        }
        return super.setFailedModelMap("获取手机号失败");
    }

}
