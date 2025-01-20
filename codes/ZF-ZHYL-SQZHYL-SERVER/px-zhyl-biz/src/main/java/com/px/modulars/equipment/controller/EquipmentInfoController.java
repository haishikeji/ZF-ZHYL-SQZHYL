
package com.px.modulars.equipment.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.common.core.util.R;
import com.px.modulars.equipment.entity.EquipmentInfo;
import com.px.modulars.equipment.service.EquipmentInfoService;
import com.px.basic.alone.core.base.BaseController;
import com.px.common.log.annotation.SysLog;
import com.px.basic.alone.security.util.SecurityUtils;
import com.px.modulars.userInfo.entity.OldManInfo;
import com.px.modulars.userInfo.service.OldManInfoService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * 设备信息
 *
 * @author 品讯科技
 * @date 2024-08
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/equipmentinfo")
@Api(value = "equipmentinfo", tags = "设备信息管理")
public class EquipmentInfoController extends BaseController<EquipmentInfo,EquipmentInfoService> {

    private final EquipmentInfoService equipmentInfoService;


    private final OldManInfoService oldManInfoService;
    /**
     * 分页查询
     * @param page 分页对象
     * @param equipmentInfo 设备信息
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page")
    @PreAuthorize("@pms.hasPermission('equipment_equipmentinfo_get')")
    public R getEquipmentInfoPage(Page page, EquipmentInfo equipmentInfo) {
        //return R.ok(equipmentInfoService.getPage(page,equipmentInfo));
        Page<EquipmentInfo> result = equipmentInfoService.lambdaQuery()
                .eq(EquipmentInfo::getEnable,1)
                .orderByDesc(EquipmentInfo::getId)
                .page(page);

        result.getRecords().forEach(val->{
            //查询老人信息
            OldManInfo info = oldManInfoService.getById(val.getBindOldMan());
            if (info!=null){
                val.setBindOldName(info.getName());
            }
        });
        return R.ok(result);
    }

    /**
     * 通过id查询设备信息
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}")
    @PreAuthorize("@pms.hasPermission('equipment_equipmentinfo_get')")
    public R getById(@PathVariable("id") Integer id) {
        return super.get(id);
    }

    /**
     * 修改设备信息
     * @param equipmentInfo 设备信息
     * @return R
     */
    @ApiOperation(value = "修改设备信息", notes = "修改设备信息")
    @PutMapping
    @SysLog("修改设备信息")
    @PreAuthorize("@pms.hasPermission('equipment_equipmentinfo_edit')")
    public R updateById(@Validated @RequestBody EquipmentInfo equipmentInfo) {
        return super.update(equipmentInfo, SecurityUtils.getUser().getId());
    }

    /**
     * 通过id删除设备信息
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除设备信息", notes = "通过id删除设备信息")
    @DeleteMapping("/{id}")
    @SysLog("通过id删除设备信息")
    @PreAuthorize("@pms.hasPermission('equipment_equipmentinfo_del')")
    public R removeById(@PathVariable Integer id) {
        EquipmentInfo equipmentInfo=super.service.queryById(id);
        if (equipmentInfo ==null){
            return R.failed("ID错误");
        }
        return super.del(equipmentInfo, SecurityUtils.getUser().getId());
    }

}
