
package com.px.modulars.device.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.common.core.util.R;
import com.px.modulars.device.entity.PressureRate;
import com.px.modulars.device.service.PressureRateService;
import com.px.basic.alone.core.base.BaseController;
import com.px.common.log.annotation.SysLog;
import com.px.basic.alone.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * 血压心率
 *
 * @author 品讯科技
 * @date 2024-08
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/device/pressurerate")
@Api(value = "pressurerate", tags = "血压心率管理")
public class PressureRateController extends BaseController<PressureRate,PressureRateService> {

    private final PressureRateService pressureRateService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param pressureRate 血压心率
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page")
    @PreAuthorize("@pms.hasPermission('device_pressurerate_get')")
    public R getPressureRatePage(Page page, PressureRate pressureRate) {
        //return R.ok(pressureRateService.getPage(page,pressureRate));
        return R.ok(pressureRateService.lambdaQuery()
                .eq(PressureRate::getEnable,1)
                .orderByDesc(PressureRate::getId)
                .page(page));
    }


    /**
     * 通过id查询血压心率
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}")
    @PreAuthorize("@pms.hasPermission('device_pressurerate_get')")
    public R getById(@PathVariable("id") Integer id) {
        return super.get(id);
    }

    /**
     * 新增血压心率
     * @param pressureRate 血压心率
     * @return R
     */
    @ApiOperation(value = "新增血压心率", notes = "新增血压心率")
    @PostMapping
    @SysLog("新增血压心率")
    @PreAuthorize("@pms.hasPermission('device_pressurerate_add')")
    public R save(@Validated @RequestBody PressureRate pressureRate) {
        return super.update(pressureRate, SecurityUtils.getUser().getId());
    }

    /**
     * 修改血压心率
     * @param pressureRate 血压心率
     * @return R
     */
    @ApiOperation(value = "修改血压心率", notes = "修改血压心率")
    @PutMapping
    @SysLog("修改血压心率")
    @PreAuthorize("@pms.hasPermission('device_pressurerate_edit')")
    public R updateById(@Validated @RequestBody PressureRate pressureRate) {
        return super.update(pressureRate, SecurityUtils.getUser().getId());
    }

    /**
     * 通过id删除血压心率
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除血压心率", notes = "通过id删除血压心率")
    @DeleteMapping("/{id}")
    @SysLog("通过id删除血压心率")
    @PreAuthorize("@pms.hasPermission('device_pressurerate_del')")
    public R removeById(@PathVariable Integer id) {
        PressureRate pressureRate=super.service.queryById(id);
        if (pressureRate ==null){
            return R.failed("ID错误");
        }
        return super.del(pressureRate, SecurityUtils.getUser().getId());
    }

}
