
package com.px.modulars.team.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.common.core.constant.Constants;
import com.pig4cloud.pig.common.core.util.R;
import com.px.modulars.team.entity.TeamInfo;
import com.px.modulars.team.service.TeamInfoService;
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
 * 团队信息
 *
 * @author 品讯科技
 * @date 2024-08
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/teamInfo/teaminfo")
@Api(value = "teaminfo", tags = "团队信息管理")
public class TeamInfoController extends BaseController<TeamInfo, TeamInfoService> {

    private final TeamInfoService teamInfoService;

    /**
     * 分页查询
     *
     * @param page     分页对象
     * @param teamInfo 团队信息
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page")
    @PreAuthorize("@pms.hasPermission('teamInfo_teaminfo_get')")
    public R getTeamInfoPage(Page page, TeamInfo teamInfo) {
        Page<TeamInfo> teamInfoPage = teamInfoService.lambdaQuery().eq(TeamInfo::getEnable, Constants.ENABLE_TRUE)
                .like(StringUtils.isNotEmpty(teamInfo.getName()), TeamInfo::getName, teamInfo.getName()).orderByDesc(TeamInfo::getCreateTime)
                .page(page);
        return R.ok(teamInfoPage);
    }


    /**
     * 通过id查询团队信息
     *
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}")
    @PreAuthorize("@pms.hasPermission('teamInfo_teaminfo_get')")
    public R getById(@PathVariable("id") Integer id) {
        return super.get(id);
    }

    /**
     * 新增团队信息
     *
     * @param teamInfo 团队信息
     * @return R
     */
    @ApiOperation(value = "新增团队信息", notes = "新增团队信息")
    @PostMapping
    @SysLog("新增团队信息")
    @PreAuthorize("@pms.hasPermission('teamInfo_teaminfo_add')")
    public R save( @RequestBody TeamInfo teamInfo) {
        return super.update(teamInfo, SecurityUtils.getUser().getId());
    }

    /**
     * 修改团队信息
     *
     * @param teamInfo 团队信息
     * @return R
     */
    @ApiOperation(value = "修改团队信息", notes = "修改团队信息")
    @PutMapping
    @SysLog("修改团队信息")
    @PreAuthorize("@pms.hasPermission('teamInfo_teaminfo_edit')")
    public R updateById( @RequestBody TeamInfo teamInfo) {
        return super.update(teamInfo, SecurityUtils.getUser().getId());
    }

    /**
     * 通过id删除团队信息
     *
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除团队信息", notes = "通过id删除团队信息")
    @DeleteMapping("/{id}")
    @SysLog("通过id删除团队信息")
    @PreAuthorize("@pms.hasPermission('teamInfo_teaminfo_del')")
    public R removeById(@PathVariable Integer id) {
        TeamInfo teamInfo = super.service.queryById(id);
        if (teamInfo == null) {
            return R.failed("ID错误");
        }
        return super.del(teamInfo, SecurityUtils.getUser().getId());
    }

}
