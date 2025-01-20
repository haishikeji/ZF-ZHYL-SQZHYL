
package com.px.modulars.upms.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.common.core.util.R;
import com.px.modulars.upms.entity.SysProhibiteWord;
import com.px.modulars.upms.service.SysProhibiteWordService;
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
 * 关键词过滤
 *
 * @author 品讯科技
 * @date 2024-08
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/upms/sysprohibiteword")
@Api(value = "sysprohibiteword", tags = "关键词过滤管理")
public class SysProhibiteWordController extends BaseController<SysProhibiteWord,SysProhibiteWordService> {

    private final  SysProhibiteWordService sysProhibiteWordService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param sysProhibiteWord 关键词过滤
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page")
    @PreAuthorize("@pms.hasPermission('upms_sysprohibiteword_get')")
    public R getSysProhibiteWordPage(Page<Integer> page, SysProhibiteWord sysProhibiteWord) {
        return super.query(sysProhibiteWord, page);
    }


    /**
     * 通过id查询关键词过滤
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}")
    @PreAuthorize("@pms.hasPermission('upms_sysprohibiteword_get')")
    public R getById(@PathVariable("id") Integer id) {
        return super.get(id);
    }

    /**
     * 新增关键词过滤
     * @param sysProhibiteWord 关键词过滤
     * @return R
     */
    @ApiOperation(value = "新增关键词过滤", notes = "新增关键词过滤")
    @PostMapping
    @SysLog("新增关键词过滤")
    @PreAuthorize("@pms.hasPermission('upms_sysprohibiteword_add')")
    public R save(@Validated @RequestBody SysProhibiteWord sysProhibiteWord) {
        return super.update(sysProhibiteWord, SecurityUtils.getUser().getId());
    }

    /**
     * 修改关键词过滤
     * @param sysProhibiteWord 关键词过滤
     * @return R
     */
    @ApiOperation(value = "修改关键词过滤", notes = "修改关键词过滤")
    @PutMapping
    @SysLog("修改关键词过滤")
    @PreAuthorize("@pms.hasPermission('upms_sysprohibiteword_edit')")
    public R updateById(@Validated @RequestBody SysProhibiteWord sysProhibiteWord) {
        return super.update(sysProhibiteWord, SecurityUtils.getUser().getId());
    }

    /**
     * 通过id删除关键词过滤
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除关键词过滤", notes = "通过id删除关键词过滤")
    @DeleteMapping("/{id}")
    @SysLog("通过id删除关键词过滤")
    @PreAuthorize("@pms.hasPermission('upms_sysprohibiteword_del')")
    public R removeById(@PathVariable Integer id) {
        SysProhibiteWord sysProhibiteWord=super.service.queryById(id);
        if (sysProhibiteWord ==null){
            return R.failed("ID错误");
        }
        return super.del(sysProhibiteWord, SecurityUtils.getUser().getId());
    }

}
