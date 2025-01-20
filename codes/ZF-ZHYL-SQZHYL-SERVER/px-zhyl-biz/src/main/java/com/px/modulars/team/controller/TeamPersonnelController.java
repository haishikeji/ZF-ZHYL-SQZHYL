
package com.px.modulars.team.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.common.core.constant.Constants;
import com.pig4cloud.pig.common.core.util.R;
import com.px.modulars.team.entity.TeamPersonnel;
import com.px.modulars.team.service.TeamPersonnelService;
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
 * 队员信息
 *
 * @author 品讯科技
 * @date 2024-08
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/teamPersonnel/teampersonnel")
@Api(value = "teampersonnel", tags = "队员信息管理")
public class TeamPersonnelController extends BaseController<TeamPersonnel,TeamPersonnelService> {

    private final  TeamPersonnelService teamPersonnelService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param teamPersonnel 队员信息
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page")
    //@PreAuthorize("@pms.hasPermission('teamPersonnel_teampersonnel_get')")
    public R getTeamPersonnelPage(Page page, TeamPersonnel teamPersonnel) {

     Page<TeamPersonnel> teamPersonnelPage = teamPersonnelService.lambdaQuery().eq(TeamPersonnel::getEnable, Constants.ENABLE_TRUE)
                .eq(teamPersonnel.getTeamInfo() != null,TeamPersonnel::getTeamInfo,teamPersonnel.getTeamInfo())
                .page(page);
        return R.ok(teamPersonnelPage);
    }


    /**
     * 通过id查询队员信息
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}")
   // @PreAuthorize("@pms.hasPermission('teamPersonnel_teampersonnel_get')")
    public R getById(@PathVariable("id") Integer id) {
        return super.get(id);
    }

    /**
     * 新增队员信息
     * @param teamPersonnel 队员信息
     * @return R
     */
    @ApiOperation(value = "新增队员信息", notes = "新增队员信息")
    @PostMapping
    //@SysLog("新增队员信息")
   // @PreAuthorize("@pms.hasPermission('teamPersonnel_teampersonnel_add')")
    public R save( @RequestBody TeamPersonnel teamPersonnel) {
        return super.update(teamPersonnel, SecurityUtils.getUser().getId());
    }

    /**
     * 修改队员信息
     * @param teamPersonnel 队员信息
     * @return R
     */
    @ApiOperation(value = "修改队员信息", notes = "修改队员信息")
    @PutMapping
   // @SysLog("修改队员信息")
   //@PreAuthorize("@pms.hasPermission('teamPersonnel_teampersonnel_edit')")
    public R updateById( @RequestBody TeamPersonnel teamPersonnel) {
        return super.update(teamPersonnel, SecurityUtils.getUser().getId());
    }

    /**
     * 通过id删除队员信息
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除队员信息", notes = "通过id删除队员信息")
    @DeleteMapping("/{id}")
    @SysLog("通过id删除队员信息")
   // @PreAuthorize("@pms.hasPermission('teamPersonnel_teampersonnel_del')")
    public R removeById(@PathVariable Integer id) {
        TeamPersonnel teamPersonnel=super.service.queryById(id);
        if (teamPersonnel ==null){
            return R.failed("ID错误");
        }
        return super.del(teamPersonnel, SecurityUtils.getUser().getId());
    }

}
