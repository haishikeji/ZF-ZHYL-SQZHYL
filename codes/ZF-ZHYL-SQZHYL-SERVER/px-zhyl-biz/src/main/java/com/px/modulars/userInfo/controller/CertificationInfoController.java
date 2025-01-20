
package com.px.modulars.userInfo.controller;

import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.http.HttpRequest;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.common.core.constant.Constants;
import com.pig4cloud.pig.common.core.util.R;
import com.px.modulars.userInfo.entity.CertificationInfo;
import com.px.modulars.userInfo.service.CertificationInfoService;
import com.px.basic.alone.core.base.BaseController;
import com.px.common.log.annotation.SysLog;
import com.px.basic.alone.security.util.SecurityUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * 认证信息表
 *
 * @author 品讯科技
 * @date 2024-08
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/certificationInfo/certificationinfo")
@Api(value = "certificationinfo", tags = "认证信息表管理")
public class CertificationInfoController extends BaseController<CertificationInfo, CertificationInfoService> {

    private final CertificationInfoService certificationInfoService;


    /**
     * 分页查询
     *
     * @param page              分页对象
     * @param certificationInfo 认证信息表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page")
    @PreAuthorize("@pms.hasPermission('certificationInfo_certificationinfo_get')")
    public R getCertificationInfoPage(Page page, CertificationInfo certificationInfo) {
        Page<CertificationInfo> certificationInfoPage = certificationInfoService.lambdaQuery().eq(CertificationInfo::getEnable, Constants.ENABLE_TRUE)
                .like(StringUtils.isNotEmpty(certificationInfo.getOldName()), CertificationInfo::getOldName, certificationInfo.getOldName())
                .like(StringUtils.isNotEmpty(certificationInfo.getOldCard()), CertificationInfo::getOldCard, certificationInfo.getOldCard())
                .like(StringUtils.isNotEmpty(certificationInfo.getOldPhone()), CertificationInfo::getOldPhone, certificationInfo.getOldPhone())
                .orderByDesc(CertificationInfo::getCreateTime).page(page);
        return R.ok(certificationInfoPage);
    }


    /**
     * 通过id查询认证信息表
     *
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}")
    @PreAuthorize("@pms.hasPermission('certificationInfo_certificationinfo_get')")
    public R getById(@PathVariable("id") Integer id) {
        return super.get(id);
    }

    /**
     * 新增认证信息表
     *
     * @param certificationInfo 认证信息表
     * @return R
     */
    @ApiOperation(value = "新增认证信息表", notes = "新增认证信息表")
    @PostMapping
    @SysLog("新增认证信息表")
    @PreAuthorize("@pms.hasPermission('certificationInfo_certificationinfo_add')")
    public R save(@RequestBody CertificationInfo certificationInfo) {
        return super.update(certificationInfo, SecurityUtils.getUser().getId());
    }

    /**
     * 修改认证信息表
     *
     * @param certificationInfo 认证信息表
     * @return R
     */
    @ApiOperation(value = "修改认证信息表", notes = "修改认证信息表")
    @PutMapping
    @SysLog("修改认证信息表")
    @PreAuthorize("@pms.hasPermission('certificationInfo_certificationinfo_edit')")
    public R updateById(@RequestBody CertificationInfo certificationInfo) {
        if (certificationInfo.getState() == 4) {
            certificationInfo.setEnable(2);
        }
        super.update(certificationInfo, SecurityUtils.getUser().getId());
        ThreadUtil.execute(() -> {
            certificationInfoService.Certification(certificationInfo.getId());
        });
        return R.ok();
    }

    /**
     * 通过id删除认证信息表
     *
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除认证信息表", notes = "通过id删除认证信息表")
    @DeleteMapping("/{id}")
    @SysLog("通过id删除认证信息表")
    @PreAuthorize("@pms.hasPermission('certificationInfo_certificationinfo_del')")
    public R removeById(@PathVariable Integer id) {
        CertificationInfo certificationInfo = super.service.queryById(id);
        if (certificationInfo == null) {
            return R.failed("ID错误");
        }
        return super.del(certificationInfo, SecurityUtils.getUser().getId());
    }

    /**
     * 审核通过
     *
     * @return R
     */
    @PutMapping("/examinePass")
    @ApiOperation("审核通过")
    @SysLog("修改认证信息表")
    public R examinePass(@RequestParam("id") Integer id) {
        if (id == null) {
            return R.failed("id不能为空");
        }
        int a = certificationInfoService.updateCertificationState(id, null, 2);
        if (a > 0) {
            ThreadUtil.execute(() -> {
                certificationInfoService.Certification(id);
            });
            return R.ok();
        } else {
            return R.failed("失败");
        }

    }

    /**
     * 审核不通过
     *
     * @return R
     */
    @PutMapping("/examineNoPass")
    @ApiOperation("审核不通过")
    @SysLog("修改认证信息表")
    public R examineNoPass(@RequestParam("id") Integer id) {
        if (id == null) {
            return R.failed("id不能为空");
        }
        int a = certificationInfoService.updateCertificationState(id, null, 3);
        if (a > 0) {
            ThreadUtil.execute(() -> {
                certificationInfoService.Certification(id);
            });
            return R.ok();
        } else {
            return R.failed("失败");
        }
    }

    /**
     * 取消绑定
     *
     * @return R
     */
    @PutMapping("/examineCancelPass")
    @ApiOperation("取消绑定")
    @SysLog("修改认证信息表")
    public R examineCancelPass(@RequestParam("id") Integer id) {
        if (id == null) {
            return R.failed("id不能为空");
        }
        int a = certificationInfoService.updateCertificationState(id, 2, 4);
        if (a > 0) {
            return R.ok();
        } else {
            return R.failed("失败");
        }
    }

}
