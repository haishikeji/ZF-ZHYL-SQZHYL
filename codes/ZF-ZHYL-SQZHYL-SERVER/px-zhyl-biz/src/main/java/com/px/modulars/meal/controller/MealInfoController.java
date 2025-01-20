
package com.px.modulars.meal.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.common.core.constant.Constants;
import com.pig4cloud.pig.common.core.util.R;
import com.px.constants.TimeStateEnum;
import com.px.modulars.meal.entity.MealInfo;

import com.px.basic.alone.core.base.BaseController;
import com.px.common.log.annotation.SysLog;
import com.px.basic.alone.security.util.SecurityUtils;
import com.px.modulars.meal.service.MealInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;


/**
 * 配餐计划信息
 *
 * @author 品讯科技
 * @date 2024-08
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/mealInfo/mealinfo")
@Api(value = "mealinfo", tags = "配餐计划信息管理")
public class MealInfoController extends BaseController<MealInfo, MealInfoService> {

    private final MealInfoService mealInfoService;

    /**
     * 分页查询
     *
     * @param page     分页对象
     * @param mealInfo 配餐计划信息
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page")
    @PreAuthorize("@pms.hasPermission('mealInfo_mealinfo_get')")
    public R getMealInfoPage(Page page, MealInfo mealInfo) {
        Page<MealInfo> mealInfopage = mealInfoService.lambdaQuery().eq(MealInfo::getEnable, Constants.ENABLE_TRUE)
                .like(StringUtils.isNotEmpty(mealInfo.getMealName()), MealInfo::getMealName, mealInfo.getMealName())
                .eq(mealInfo.getType() != null, MealInfo::getType, mealInfo.getType())
                .like(StringUtils.isNotEmpty(mealInfo.getNumber()), MealInfo::getNumber, mealInfo.getNumber())
                .orderByDesc(MealInfo::getNumber)
                .page(page);
        mealInfopage.getRecords().forEach(meal -> {
            DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String strDate2 = dtf2.format(meal.getMealDate());
            meal.setMealDateStr(strDate2);
            Date date = new Date();
            Date sdate = Date.from(meal.getStartTime().atZone(ZoneId.systemDefault()).toInstant());
            Date edate = Date.from(meal.getEndTime().atZone(ZoneId.systemDefault()).toInstant());
            if (date.compareTo(sdate) == -1) {
                meal.setTimeState(TimeStateEnum.TIME_STATE_ENUM_ONE.getValue());
            } else if (date.compareTo(edate) == 1) {
                meal.setTimeState(TimeStateEnum.TIME_STATE_ENUM_THREE.getValue());
            } else {
                meal.setTimeState(TimeStateEnum.TIME_STATE_ENUM_TWO.getValue());
            }
        });
        return R.ok(mealInfopage);
    }


    /**
     * 通过id查询配餐计划信息
     *
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}")
    @PreAuthorize("@pms.hasPermission('mealInfo_mealinfo_get')")
    public R getById(@PathVariable("id") Integer id) {
        return super.get(id);
    }

    /**
     * 新增配餐计划信息
     *
     * @param mealInfo 配餐计划信息
     * @return R
     */
    @ApiOperation(value = "新增配餐计划信息", notes = "新增配餐计划信息")
    @PostMapping
    @SysLog("新增配餐计划信息")
    @PreAuthorize("@pms.hasPermission('mealInfo_mealinfo_add')")
    public R save(@RequestBody MealInfo mealInfo) {
        String nowStr = DateTimeFormatter.ofPattern("yyyyMMdd").format(mealInfo.getStartTime());
        mealInfo.setNumber(nowStr + mealInfo.getType());
        mealInfo.setMealDate(mealInfo.getStartTime());
        //每日每餐仅可增加一条配餐计划
        int count = mealInfoService.lambdaQuery().eq(MealInfo::getEnable,Constants.ENABLE_TRUE).eq(MealInfo::getNumber, mealInfo.getNumber()).count();
        if (count > 0) {
            return R.failed("此时间段已经有配餐计划");
        }
        return super.update(mealInfo, SecurityUtils.getUser().getId());
    }

    /**
     * 修改配餐计划信息
     *
     * @param mealInfo 配餐计划信息
     * @return R
     */
    @ApiOperation(value = "修改配餐计划信息", notes = "修改配餐计划信息")
    @PutMapping
    @SysLog("修改配餐计划信息")
    @PreAuthorize("@pms.hasPermission('mealInfo_mealinfo_edit')")
    public R updateById(@RequestBody MealInfo mealInfo) {
        String nowStr = DateTimeFormatter.ofPattern("yyyyMMdd").format(mealInfo.getStartTime());
        mealInfo.setNumber(nowStr + mealInfo.getType());
        mealInfo.setMealDate(mealInfo.getStartTime());
        mealInfo.setMealDate(mealInfo.getStartTime());
        //判断除了本条数据之外有没有相同的配餐号
        int count = mealInfoService.lambdaQuery().eq(MealInfo::getEnable,Constants.ENABLE_TRUE).eq(MealInfo::getNumber, mealInfo.getNumber()).ne(MealInfo::getId, mealInfo.getId()).count();
        if (count > 0) {
            return R.failed("此时间段已经有配餐计划");
        }
        return super.update(mealInfo, SecurityUtils.getUser().getId());
    }

    /**
     * 通过id删除配餐计划信息
     *
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除配餐计划信息", notes = "通过id删除配餐计划信息")
    @DeleteMapping("/{id}")
    @SysLog("通过id删除配餐计划信息")
    @PreAuthorize("@pms.hasPermission('mealInfo_mealinfo_del')")
    public R removeById(@PathVariable Integer id) {

        MealInfo mealInfo = super.service.queryById(id);
        if (mealInfo == null) {
            return R.failed("ID错误");
        }
        return super.del(mealInfo, SecurityUtils.getUser().getId());
    }

}
