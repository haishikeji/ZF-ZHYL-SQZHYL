
package com.px.modulars.opinion.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.common.core.util.R;
import com.px.modulars.opinion.entity.OpinionInfo;
import com.px.modulars.opinion.service.OpinionInfoService;
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
 * 征集意见
 *
 * @author 品讯科技
 * @date 2024-08
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/opinionInfo/opinioninfo")
@Api(value = "opinioninfo", tags = "征集意见管理")
public class OpinionInfoController extends BaseController<OpinionInfo,OpinionInfoService> {

    private final  OpinionInfoService opinionInfoService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param opinionInfo 征集意见
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page")
    @PreAuthorize("@pms.hasPermission('opinionInfo_opinioninfo_get')")
    public R getOpinionInfoPage(Page<Integer> page, OpinionInfo opinionInfo) {
        return super.query(opinionInfo, page);
    }


    /**
     * 通过id查询征集意见
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}")
    @PreAuthorize("@pms.hasPermission('opinionInfo_opinioninfo_get')")
    public R getById(@PathVariable("id") Integer id) {
        return super.get(id);
    }

    /**
     * 新增征集意见
     * @param opinionInfo 征集意见
     * @return R
     */
    @ApiOperation(value = "新增征集意见", notes = "新增征集意见")
    @PostMapping
    @SysLog("新增征集意见")
    @PreAuthorize("@pms.hasPermission('opinionInfo_opinioninfo_add')")
    public R save( @RequestBody OpinionInfo opinionInfo) {
        return super.update(opinionInfo, SecurityUtils.getUser().getId());
    }

    /**
     * 修改征集意见
     * @param opinionInfo 征集意见
     * @return R
     */
    @ApiOperation(value = "修改征集意见", notes = "修改征集意见")
    @PutMapping
    @SysLog("修改征集意见")
    @PreAuthorize("@pms.hasPermission('opinionInfo_opinioninfo_edit')")
    public R updateById( @RequestBody OpinionInfo opinionInfo) {
        return super.update(opinionInfo, SecurityUtils.getUser().getId());
    }

    /**
     * 通过id删除征集意见
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除征集意见", notes = "通过id删除征集意见")
    @DeleteMapping("/{id}")
    @SysLog("通过id删除征集意见")
    @PreAuthorize("@pms.hasPermission('opinionInfo_opinioninfo_del')")
    public R removeById(@PathVariable Integer id) {
        OpinionInfo opinionInfo=super.service.queryById(id);
        if (opinionInfo ==null){
            return R.failed("ID错误");
        }
        return super.del(opinionInfo, SecurityUtils.getUser().getId());
    }

}
