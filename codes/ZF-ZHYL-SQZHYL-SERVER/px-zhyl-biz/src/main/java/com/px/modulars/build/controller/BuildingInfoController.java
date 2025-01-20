
package com.px.modulars.build.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.common.core.constant.Constants;
import com.pig4cloud.pig.common.core.util.R;
import com.px.modulars.build.entity.BuildingInfo;
import com.px.modulars.build.service.BuildingInfoService;
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
 * 楼宇信息
 *
 * @author 品讯科技
 * @date 2024-08
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/buildingInfo/buildinginfo")
@Api(value = "buildinginfo", tags = "楼宇信息管理")
public class BuildingInfoController extends BaseController<BuildingInfo, BuildingInfoService> {

    private final BuildingInfoService buildingInfoService;

    /**
     * 分页查询
     *
     * @param page         分页对象
     * @param buildingInfo 楼宇信息
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page")
    @PreAuthorize("@pms.hasPermission('buildingInfo_buildinginfo_get')")
    public R getBuildingInfoPage(Page page, BuildingInfo buildingInfo) {

        Page<BuildingInfo> buildingInfoPage = buildingInfoService.lambdaQuery()
                .eq(BuildingInfo::getEnable, Constants.ENABLE_TRUE)
                .like(StringUtils.isNotEmpty(buildingInfo.getCommunity()), BuildingInfo::getCommunity, buildingInfo.getCommunity())
                .like(StringUtils.isNotEmpty(buildingInfo.getBuildingNumber()), BuildingInfo::getBuildingNumber, buildingInfo.getBuildingNumber())
                .orderByDesc(BuildingInfo::getCreateTime)
                .page(page);
        return R.ok(buildingInfoPage);
    }


    /**
     * 通过id查询楼宇信息
     *
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}")
    @PreAuthorize("@pms.hasPermission('buildingInfo_buildinginfo_get')")
    public R getById(@PathVariable("id") Integer id) {
        return super.get(id);
    }

    /**
     * 新增楼宇信息
     *
     * @param buildingInfo 楼宇信息
     * @return R
     */
    @ApiOperation(value = "新增楼宇信息", notes = "新增楼宇信息")
    @PostMapping
    @SysLog("新增楼宇信息")
    @PreAuthorize("@pms.hasPermission('buildingInfo_buildinginfo_add')")
    public R save( @RequestBody BuildingInfo buildingInfo) {
        return super.update(buildingInfo, SecurityUtils.getUser().getId());
    }

    /**
     * 修改楼宇信息
     *
     * @param buildingInfo 楼宇信息
     * @return R
     */
    @ApiOperation(value = "修改楼宇信息", notes = "修改楼宇信息")
    @PutMapping
    @SysLog("修改楼宇信息")
    @PreAuthorize("@pms.hasPermission('buildingInfo_buildinginfo_edit')")
    public R updateById( @RequestBody BuildingInfo buildingInfo) {
        return super.update(buildingInfo, SecurityUtils.getUser().getId());
    }

    /**
     * 通过id删除楼宇信息
     *
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除楼宇信息", notes = "通过id删除楼宇信息")
    @DeleteMapping("/{id}")
    @SysLog("通过id删除楼宇信息")
    @PreAuthorize("@pms.hasPermission('buildingInfo_buildinginfo_del')")
    public R removeById(@PathVariable Integer id) {
        BuildingInfo buildingInfo = super.service.queryById(id);
        if (buildingInfo == null) {
            return R.failed("ID错误");
        }
        return super.del(buildingInfo, SecurityUtils.getUser().getId());
    }

    @ApiOperation(value = "列表", notes = "列表")
    @GetMapping("/list")
    public R getList() {
        List<BuildingInfo> buildingInfoList = buildingInfoService.lambdaQuery()
                .eq(BuildingInfo::getEnable, Constants.ENABLE_TRUE)
                .orderByAsc(BuildingInfo::getCommunity).orderByAsc(BuildingInfo::getBuildingNumber).list();
        buildingInfoList.forEach(buildingInfo -> {
            buildingInfo.setName(buildingInfo.getCommunity()+"-"+buildingInfo.getBuildingNumber());
        });
        return R.ok(buildingInfoList);
    }
}
