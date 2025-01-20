
package com.px.modulars.helpold.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.common.core.constant.Constants;
import com.pig4cloud.pig.common.core.util.R;
import com.px.modulars.helpold.entity.HelpOld;
import com.px.modulars.helpold.service.HelpOldService;
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


/**
 * 智慧助老
 *
 * @author 品讯科技
 * @date 2024-08
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/helpold/helpold")
@Api(value = "helpold", tags = "智慧助老管理")
public class HelpOldController extends BaseController<HelpOld,HelpOldService> {

    private final  HelpOldService helpOldService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param helpOld 智慧助老
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page")
    @PreAuthorize("@pms.hasPermission('helpold_helpold_get')")
    public R getHelpOldPage(Page page, HelpOld helpOld) {
        Page helpOldPage =  helpOldService.lambdaQuery()
                .eq(HelpOld::getEnable, Constants.ENABLE_TRUE)
                .like(StringUtils.isNotEmpty(helpOld.getTitle()),HelpOld::getTitle,helpOld.getTitle())
                .orderByDesc(HelpOld::getCreateTime)
                .page(page);
        return R.ok(helpOldPage);
    }


    /**
     * 通过id查询智慧助老
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}")
    @PreAuthorize("@pms.hasPermission('helpold_helpold_get')")
    public R getById(@PathVariable("id") Integer id) {
        return super.get(id);
    }

    /**
     * 新增智慧助老
     * @param helpOld 智慧助老
     * @return R
     */
    @ApiOperation(value = "新增智慧助老", notes = "新增智慧助老")
    @PostMapping
    @SysLog("新增智慧助老")
    @PreAuthorize("@pms.hasPermission('helpold_helpold_add')")
    public R save( @RequestBody HelpOld helpOld) {
      /*  if(helpOldService.selectHelpoldList().size()>0){
            return R.failed("只能展示一条，请修改展示条件！");
        }*/
        return super.update(helpOld, SecurityUtils.getUser().getId());
    }

    /**
     * 修改智慧助老
     * @param helpOld 智慧助老
     * @return R
     */
    @ApiOperation(value = "修改智慧助老", notes = "修改智慧助老")
    @PutMapping
    @SysLog("修改智慧助老")
    @PreAuthorize("@pms.hasPermission('helpold_helpold_edit')")
    public R updateById( @RequestBody HelpOld helpOld) {
       /* if(helpOldService.selectHelpoldList().size()>0&&helpOldService.selectHelpoldList().get(0).getId()!=helpOld.getId()){
            return R.failed("只能展示一条，请修改展示条件！");
        }*/
        return super.update(helpOld, SecurityUtils.getUser().getId());
    }

    /**
     * 通过id删除智慧助老
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除智慧助老", notes = "通过id删除智慧助老")
    @DeleteMapping("/{id}")
    @SysLog("通过id删除智慧助老")
    @PreAuthorize("@pms.hasPermission('helpold_helpold_del')")
    public R removeById(@PathVariable Integer id) {
        HelpOld helpOld=super.service.queryById(id);
        if (helpOld ==null){
            return R.failed("ID错误");
        }
        return super.del(helpOld, SecurityUtils.getUser().getId());
    }

}
