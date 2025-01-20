
package com.px.modulars.generalization.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.common.core.util.R;
import com.px.modulars.generalization.entity.GeneralizationInfo;
import com.px.modulars.generalization.service.GeneralizationInfoService;
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
 * 中心概括
 *
 * @author 品讯科技
 * @date 2024-08
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/generalization/generalizationinfo")
@Api(value = "generalizationinfo", tags = "中心概括管理")
public class GeneralizationInfoController extends BaseController<GeneralizationInfo,GeneralizationInfoService> {

    private final  GeneralizationInfoService generalizationInfoService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param generalizationInfo 中心概括
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page")
    @PreAuthorize("@pms.hasPermission('generalization_generalizationinfo_get')")
    public R getGeneralizationInfoPage(Page<Integer> page, GeneralizationInfo generalizationInfo) {
        return super.query(generalizationInfo, page);
    }


    /**
     * 通过id查询中心概括
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}")
    @PreAuthorize("@pms.hasPermission('generalization_generalizationinfo_get')")
    public R getById(@PathVariable("id") Integer id) {
        return super.get(id);
    }

    /**
     * 新增中心概括
     * @param generalizationInfo 中心概括
     * @return R
     */
   /* @ApiOperation(value = "新增中心概括", notes = "新增中心概括")
    @PostMapping
    @SysLog("新增中心概括")
    @PreAuthorize("@pms.hasPermission('generalization_generalizationinfo_add')")
    public R save(@Validated @RequestBody GeneralizationInfo generalizationInfo) {
        return super.update(generalizationInfo, SecurityUtils.getUser().getId());
    }*/

    /**
     * 修改中心概括
     * @param generalizationInfo 中心概括
     * @return R
     */
    @ApiOperation(value = "修改中心概括", notes = "修改中心概括")
    @PutMapping
    @SysLog("修改中心概括")
    @PreAuthorize("@pms.hasPermission('generalization_generalizationinfo_edit')")
    public R updateById(@Validated @RequestBody GeneralizationInfo generalizationInfo) {
        return super.update(generalizationInfo, SecurityUtils.getUser().getId());
    }

    /**
     * 通过id删除中心概括
     * @param id id
     * @return R
     */
  /*  @ApiOperation(value = "通过id删除中心概括", notes = "通过id删除中心概括")
    @DeleteMapping("/{id}")
    @SysLog("通过id删除中心概括")
    @PreAuthorize("@pms.hasPermission('generalization_generalizationinfo_del')")
    public R removeById(@PathVariable Integer id) {
        GeneralizationInfo generalizationInfo=super.service.queryById(id);
        if (generalizationInfo ==null){
            return R.failed("ID错误");
        }
        return super.del(generalizationInfo, SecurityUtils.getUser().getId());
    }*/

}
