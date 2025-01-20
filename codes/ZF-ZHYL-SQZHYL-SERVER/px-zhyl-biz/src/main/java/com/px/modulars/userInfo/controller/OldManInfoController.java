
package com.px.modulars.userInfo.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.common.core.constant.Constants;
import com.pig4cloud.pig.common.core.util.R;
import com.px.modulars.userInfo.entity.CertificationInfo;
import com.px.modulars.userInfo.entity.OldManInfo;
import com.px.modulars.userInfo.service.CertificationInfoService;
import com.px.modulars.userInfo.service.OldManInfoService;
import com.px.basic.alone.core.base.BaseController;
import com.px.common.log.annotation.SysLog;
import com.px.basic.alone.security.util.SecurityUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 老人基本信息
 *
 * @author 品讯科技
 * @date 2024-08
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/oldManInfo/oldmaninfo")
@Api(value = "oldmaninfo", tags = "老人基本信息管理")
public class OldManInfoController extends BaseController<OldManInfo, OldManInfoService> {

    private final OldManInfoService oldManInfoService;

    private final CertificationInfoService certificationInfoService;

    /**
     * 分页查询
     *
     * @param page       分页对象
     * @param oldManInfo 老人基本信息
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page")
    @PreAuthorize("@pms.hasPermission('oldManInfo_oldmaninfo_get')")
    public R getOldManInfoPage(Page page, OldManInfo oldManInfo) {

        Page<OldManInfo> oldManInfoPage = oldManInfoService.lambdaQuery().eq(OldManInfo::getEnable, Constants.ENABLE_TRUE)
                .like(StringUtils.isNotEmpty(oldManInfo.getName()), OldManInfo::getName, oldManInfo.getName())
                .eq(oldManInfo.getSex() != null, OldManInfo::getSex, oldManInfo.getSex())
                .like(StringUtils.isNotEmpty(oldManInfo.getPhone()), OldManInfo::getPhone, oldManInfo.getPhone())
                .eq(oldManInfo.getBuilding() != null, OldManInfo::getBuilding, oldManInfo.getBuilding())
                .like(StringUtils.isNotEmpty(oldManInfo.getIdCard()), OldManInfo::getIdCard, oldManInfo.getIdCard())
                .eq(oldManInfo.getMaritalStatus() != null, OldManInfo::getMaritalStatus, oldManInfo.getMaritalStatus())
                .eq(oldManInfo.getPartyStatus() != null, OldManInfo::getPartyStatus, oldManInfo.getPartyStatus())
                .eq(oldManInfo.getInsuranceStatus() != null, OldManInfo::getInsuranceStatus, oldManInfo.getInsuranceStatus())
                .eq(oldManInfo.getMaterialsStatus() != null, OldManInfo::getMaterialsStatus, oldManInfo.getMaterialsStatus())
                .page(page);

        return R.ok(oldManInfoPage);
    }


    /**
     * 通过id查询老人基本信息
     *
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}")
    @PreAuthorize("@pms.hasPermission('oldManInfo_oldmaninfo_get')")
    public R getById(@PathVariable("id") Integer id) {
        return super.get(id);
    }

    /**
     * 新增老人基本信息
     *
     * @param oldManInfo 老人基本信息
     * @return R
     */
    @ApiOperation(value = "新增老人基本信息", notes = "新增老人基本信息")
    @PostMapping
    @SysLog("新增老人基本信息")
    @PreAuthorize("@pms.hasPermission('oldManInfo_oldmaninfo_add')")
    public R save(@RequestBody OldManInfo oldManInfo) {
        //查询新增老人信息手机号是否重复
        int count = oldManInfoService.lambdaQuery().eq(OldManInfo::getPhone, oldManInfo.getPhone()).count();
        if (count > 0) {
            return R.failed("此手机号已被添加");
        } else {
            return super.update(oldManInfo, SecurityUtils.getUser().getId());
        }
    }

    /**
     * 修改老人基本信息
     *
     * @param oldManInfo 老人基本信息
     * @return R
     */
    @ApiOperation(value = "修改老人基本信息", notes = "修改老人基本信息")
    @PutMapping
    @SysLog("修改老人基本信息")
    @PreAuthorize("@pms.hasPermission('oldManInfo_oldmaninfo_edit')")
    public R updateById(@RequestBody OldManInfo oldManInfo) {
        //查询此用户下修改的是否包含手机号
        int count = oldManInfoService.lambdaQuery().eq(OldManInfo::getPhone, oldManInfo.getPhone()).eq(OldManInfo::getId, oldManInfo.getId()).count();
        if (count == 1) { //不包含手机号
            return super.update(oldManInfo, SecurityUtils.getUser().getId());
        } else { // 包含手机号
            // 查询修改的手机号是否被使用
            int count1 = oldManInfoService.lambdaQuery().eq(OldManInfo::getPhone, oldManInfo.getPhone()).count();
            if (count1 > 0) {
                return R.failed("您修改的手机号已经被添加");
            } else {
                return super.update(oldManInfo, SecurityUtils.getUser().getId());
            }
        }

    }

    /**
     * 通过id删除老人基本信息
     *
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除老人基本信息", notes = "通过id删除老人基本信息")
    @DeleteMapping("/{id}")
    @SysLog("通过id删除老人基本信息")
    @PreAuthorize("@pms.hasPermission('oldManInfo_oldmaninfo_del')")
    public R removeById(@PathVariable Integer id) {
        OldManInfo oldManInfo = super.service.queryById(id);
        if (oldManInfo == null) {
            return R.failed("ID错误");
        }
        List<CertificationInfo> r = certificationInfoService.lambdaQuery().eq(CertificationInfo::getOldManInfo,id).eq(CertificationInfo::getEnable,1).list();
        if (r!=null){
            r.forEach(val->{
                certificationInfoService.removeById(val.getId());
            });
        }
        return super.del(oldManInfo, SecurityUtils.getUser().getId());
    }

    /**
     * 设备绑定查询老人姓名
     *
     * @return R
     */
    @RequestMapping("/list")
    @ApiOperation(value = "设备绑定查询老人姓名", notes = "设备绑定查询老人姓名")
    public R list() {
        return R.ok(this.service.list());
    }
}
