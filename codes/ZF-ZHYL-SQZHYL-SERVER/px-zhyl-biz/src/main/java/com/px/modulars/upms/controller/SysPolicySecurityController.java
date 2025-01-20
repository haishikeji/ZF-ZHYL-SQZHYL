
package com.px.modulars.upms.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.common.core.util.R;
import com.px.modulars.upms.entity.SysPolicySecurity;
import com.px.modulars.upms.service.SysPolicySecurityService;
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
 * 安全配置
 *
 * @author 品讯科技
 * @date 2024-08
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/upms/syspolicysecurity")
@Api(value = "syspolicysecurity", tags = "安全配置管理")
public class SysPolicySecurityController extends BaseController<SysPolicySecurity,SysPolicySecurityService> {

    private final  SysPolicySecurityService sysPolicySecurityService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param sysPolicySecurity 安全配置
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page")
    @PreAuthorize("@pms.hasPermission('upms_syspolicysecurity_get')")
    public R getSysPolicySecurityPage(Page<Integer> page, SysPolicySecurity sysPolicySecurity) {
        return super.query(sysPolicySecurity, page);
    }


    /**
     * 通过id查询安全配置
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}")
    @PreAuthorize("@pms.hasPermission('upms_syspolicysecurity_get')")
    public R getById(@PathVariable("id") Integer id) {
        return super.get(id);
    }

    /**
     * 新增安全配置
     * @param sysPolicySecurity 安全配置
     * @return R
     */
    @ApiOperation(value = "新增安全配置", notes = "新增安全配置")
    @PostMapping
    @SysLog("新增安全配置")
    @PreAuthorize("@pms.hasPermission('upms_syspolicysecurity_add')")
    public R save(@Validated @RequestBody SysPolicySecurity sysPolicySecurity) {
        return super.update(sysPolicySecurity, SecurityUtils.getUser().getId());
    }

    /**
     * 修改安全配置
     * @param sysPolicySecurity 安全配置
     * @return R
     */
    @ApiOperation(value = "修改安全配置", notes = "修改安全配置")
    @PutMapping
    @SysLog("修改安全配置")
    @PreAuthorize("@pms.hasPermission('upms_syspolicysecurity_edit')")
    public R updateById(@Validated @RequestBody SysPolicySecurity sysPolicySecurity) {
        return super.update(sysPolicySecurity, SecurityUtils.getUser().getId());
    }

    /**
     * 通过id删除安全配置
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除安全配置", notes = "通过id删除安全配置")
    @DeleteMapping("/{id}")
    @SysLog("通过id删除安全配置")
    @PreAuthorize("@pms.hasPermission('upms_syspolicysecurity_del')")
    public R removeById(@PathVariable Integer id) {
        SysPolicySecurity sysPolicySecurity=super.service.queryById(id);
        if (sysPolicySecurity ==null){
            return R.failed("ID错误");
        }
        return super.del(sysPolicySecurity, SecurityUtils.getUser().getId());
    }

}
