
package com.px.modulars.donations.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.common.core.constant.Constants;
import com.pig4cloud.pig.common.core.util.R;
import com.px.basic.alone.security.annotation.Inner;
import com.px.modulars.donations.entity.DonationsInfo;
import com.px.modulars.donations.service.DonationsInfoService;
import com.px.basic.alone.core.base.BaseController;
import com.px.common.log.annotation.SysLog;
import com.px.basic.alone.security.util.SecurityUtils;
import com.px.modulars.helpold.entity.HelpOld;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;


/**
 * 捐资捐物
 *
 * @author 品讯科技
 * @date 2024-08
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/donations/donationsinfo")
@Api(value = "donationsinfo", tags = "捐资捐物管理")
public class DonationsInfoController extends BaseController<DonationsInfo, DonationsInfoService> {

    private final DonationsInfoService donationsInfoService;

    /**
     * 分页查询
     *
     * @param page          分页对象
     * @param donationsInfo 捐资捐物
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page")
    @PreAuthorize("@pms.hasPermission('donations_donationsinfo_get')")
    public R getDonationsInfoPage(Page page, DonationsInfo donationsInfo) {
        Page getDonationsInfoPage = donationsInfoService.lambdaQuery()
                .eq(DonationsInfo::getEnable, Constants.ENABLE_TRUE)
                .like(StringUtils.isNotEmpty(donationsInfo.getName()), DonationsInfo::getName, donationsInfo.getName())
                .like(StringUtils.isNotEmpty(donationsInfo.getPhone()), DonationsInfo::getPhone, donationsInfo.getPhone())
                .eq(donationsInfo.getDonationMethod() != null, DonationsInfo::getDonationMethod, donationsInfo.getDonationMethod())
                .eq(donationsInfo.getParticipationMethod() != null, DonationsInfo::getParticipationMethod, donationsInfo.getParticipationMethod())
                .orderByDesc(DonationsInfo::getCreateTime)
                .page(page);
        return R.ok(getDonationsInfoPage);
    }


    /**
     * 通过id查询捐资捐物
     *
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}")
    @PreAuthorize("@pms.hasPermission('donations_donationsinfo_get')")
    public R getById(@PathVariable("id") Integer id) {
        return super.get(id);
    }


    /**
     * 新增捐资捐物
     *
     * @param donationsInfo 捐资捐物
     * @return R
     */
    @ApiOperation(value = "新增捐资捐物", notes = "新增捐资捐物")
    @PostMapping
    @SysLog("新增捐资捐物")
    @PreAuthorize("@pms.hasPermission('donations_donationsinfo_add')")
    public R save(@Validated @RequestBody DonationsInfo donationsInfo) {
        return super.update(donationsInfo, SecurityUtils.getUser().getId());
    }

    /**
     * 修改捐资捐物
     *
     * @param donationsInfo 捐资捐物
     * @return R
     */
    @ApiOperation(value = "修改捐资捐物", notes = "修改捐资捐物")
    @PutMapping
    @SysLog("修改捐资捐物")
    @PreAuthorize("@pms.hasPermission('donations_donationsinfo_edit')")
    public R updateById(@Validated @RequestBody DonationsInfo donationsInfo) {
        return super.update(donationsInfo, SecurityUtils.getUser().getId());
    }

    /**
     * 通过id删除捐资捐物
     *
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除捐资捐物", notes = "通过id删除捐资捐物")
    @DeleteMapping("/{id}")
    @SysLog("通过id删除捐资捐物")
    @PreAuthorize("@pms.hasPermission('donations_donationsinfo_del')")
    public R removeById(@PathVariable Integer id) {
        DonationsInfo donationsInfo = super.service.queryById(id);
        if (donationsInfo == null) {
            return R.failed("ID错误");
        }
        return super.del(donationsInfo, SecurityUtils.getUser().getId());
    }

}
