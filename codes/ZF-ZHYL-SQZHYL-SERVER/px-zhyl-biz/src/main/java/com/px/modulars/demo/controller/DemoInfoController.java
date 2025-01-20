
package com.px.modulars.demo.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.common.core.util.R;
import com.px.modulars.demo.entity.DemoInfo;
import com.px.modulars.demo.service.DemoInfoService;
import com.px.basic.alone.core.base.BaseController;
import com.px.basic.alone.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 测试
 *
 * @author 品讯科技
 * @date 2024-08
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/demo/demoinfo")
@Api(value = "demoinfo", tags = "测试管理")
public class DemoInfoController extends BaseController<DemoInfo,DemoInfoService> {

    private final  DemoInfoService demoInfoService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param demoInfo 测试
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page")
    @PreAuthorize("@pms.hasPermission('demo_demoinfo_get')")
    public R getDemoInfoPage(Page<Integer> page, DemoInfo demoInfo) {
        return super.query(demoInfo, page);
    }


    /**
     * 通过id查询测试
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}")
    @PreAuthorize("@pms.hasPermission('demo_demoinfo_get')")
    public R getById(@PathVariable("id") Integer id) {

        return R.ok(this.demoInfoService.getById(id));
    }

    /**
     * 新增测试
     * @param demoInfo 测试
     * @return R
     */
    @ApiOperation(value = "新增测试", notes = "新增测试")
    @PostMapping
//    @PreAuthorize("@pms.hasPermission('demo_demoinfo_add')")
    public R save(@RequestBody DemoInfo demoInfo) {
        return super.update(demoInfo, SecurityUtils.getUser().getId());
    }

    /**
     * 修改测试
     * @param demoInfo 测试
     * @return R
     */
    @ApiOperation(value = "修改测试", notes = "修改测试")
    @PutMapping
    @PreAuthorize("@pms.hasPermission('demo_demoinfo_edit')")
    public R updateById(@RequestBody DemoInfo demoInfo) {
        return super.update(demoInfo, SecurityUtils.getUser().getId());
    }

    /**
     * 通过id删除测试
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除测试", notes = "通过id删除测试")
    @DeleteMapping("/{id}")
    @PreAuthorize("@pms.hasPermission('demo_demoinfo_del')")
    public R removeById(@PathVariable Integer id) {
        DemoInfo demoInfo=super.service.queryById(id);
        if (demoInfo ==null){
            return R.failed("ID错误");
        }
        return super.del(demoInfo, SecurityUtils.getUser().getId());
    }

}
