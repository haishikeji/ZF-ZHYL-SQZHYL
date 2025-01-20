package com.px.modulars.upms.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.common.core.util.R;
import com.px.modulars.upms.entity.SysLog;
import com.px.modulars.upms.service.SysLogService;
import com.px.basic.alone.core.base.BaseController;
import com.px.basic.alone.security.util.SecurityUtils;
import com.px.modulars.upms.service.impl.SysLogServiceImpl;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 日志表
 *
 * @author 品讯科技
 * @date 2024-08
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/log")
@Api(value = "syslog", tags = "日志表管理")
public class SysLogController extends BaseController<SysLog, SysLogServiceImpl> {

    private final  SysLogService sysLogService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param sysLog 日志表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page")
    @PreAuthorize("@pms.hasPermission('sys_log_get')")
    public R getSysLogPage(Page<Integer> page, SysLog sysLog) {
        return super.query(sysLog, page);
    }


    /**
     * 通过id查询日志表
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}")
    @PreAuthorize("@pms.hasPermission('sys_log_get')")
    public R getById(@PathVariable("id") Integer id) {
        return super.get(id);
    }


    /**
     * 通过id删除日志表
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除日志表", notes = "通过id删除日志表")
    @DeleteMapping("/{id}")
    @PreAuthorize("@pms.hasPermission('sys_log_del')")
    public R removeById(@PathVariable Integer id) {
        SysLog sysLog=super.service.queryById(id);
        if (sysLog ==null){
            return R.failed("ID错误");
        }
        return super.del(sysLog, SecurityUtils.getUser().getId());
    }

}
