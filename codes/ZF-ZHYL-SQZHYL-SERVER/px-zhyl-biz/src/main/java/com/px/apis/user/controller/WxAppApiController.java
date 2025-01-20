package com.px.apis.user.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.common.core.constant.Constants;

import com.pig4cloud.pig.common.core.util.R;
import com.px.basic.alone.security.annotation.Inner;
import com.px.constants.OrderStateEnum;
import com.px.constants.TimeStateEnum;
import com.px.fastfile.service.FastfileService;
import com.px.modulars.function.entity.FunctionRoom;
import com.px.modulars.function.entity.FunctionRoomClass;
import com.px.modulars.function.service.FunctionRoomClassService;
import com.px.modulars.function.service.FunctionRoomService;
import com.px.modulars.function.vo.FunctionRoomVo;
import com.px.modulars.generalization.entity.GeneralizationInfo;
import com.px.modulars.generalization.service.GeneralizationInfoService;
import com.px.modulars.helpold.entity.HelpOld;
import com.px.modulars.helpold.service.HelpOldService;
import com.px.modulars.meal.entity.MealInfo;
import com.px.modulars.meal.entity.MealRecord;
import com.px.modulars.meal.service.MealInfoService;
import com.px.modulars.meal.service.MealRecordService;
import com.px.modulars.oneTouchCall.entity.OneTouchCallInfo;
import com.px.modulars.oneTouchCall.service.OneTouchCallInfoService;
import com.px.modulars.replace.entity.ReplaceBuy;
import com.px.modulars.replace.service.ReplaceBuyService;
import com.px.modulars.team.entity.TeamInfo;
import com.px.modulars.team.entity.TeamPersonnel;
import com.px.modulars.team.service.TeamInfoService;
import com.px.modulars.team.service.TeamPersonnelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.undertow.util.DateUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/zhyl/wxapp")
@Api(value = "wxapp", tags = "小程序端首页接口管理")
public class WxAppApiController {

    @Autowired
    private FunctionRoomClassService functionRoomClassService;
    @Autowired
    private FunctionRoomService functionRoomService;
    @Autowired
    private MealInfoService mealInfoService;
    @Autowired
    private TeamInfoService teamInfoService;
    @Autowired
    private ReplaceBuyService replaceBuyService;
    @Autowired
    private TeamPersonnelService teamPersonnelService;
    @Autowired
    private HelpOldService helpOldService;
    @Autowired
    private FastfileService fastfileService;
    @Autowired
    private MealRecordService mealRecordService;
    @Autowired
    private OneTouchCallInfoService oneTouchCallInfoService;
    @Autowired
    private GeneralizationInfoService generalizationInfoService;

    /**
     * 功能室分类列表
     *
     * @return
     */
    @GetMapping("/functionRoomClass/list")
    @ApiOperation("功能室分类列表")
    @Inner(value = false)
    public R list() {
        List<FunctionRoomClass> functionRoomClassList = functionRoomClassService.lambdaQuery().eq(FunctionRoomClass::getEnable, Constants.ENABLE_TRUE).list();
        return R.ok(functionRoomClassList);
    }

    /**
     * 功能室分类详情
     *
     * @return
     */
    @GetMapping("/functionRoomClass/getOne")
    @ApiOperation("功能室分类详情")
    @Inner(value = false)
    public R getfunctionRoomClassOne(@RequestParam("id") Integer id) {
        FunctionRoomClass functionRoomClass = functionRoomClassService.getById(id);
        return R.ok(functionRoomClass);
    }


    /**
     * 智慧助老/5G微诊室
     *
     * @return
     */
    @GetMapping("/helpOld/getOne")
    @ApiOperation("智慧助老/5G微诊室")
    @Inner(value = false)
    public R helpGetOne(@RequestParam("id") Integer id) {
        HelpOld helpOld = helpOldService.getById(id);
        return R.ok(helpOld);
    }

    /**
     * 中心概括
     *
     * @return
     */
    @GetMapping("/generalization/getOne")
    @ApiOperation("中心概括")
    @Inner(value = false)
    public R generalizationGetOne() {
        GeneralizationInfo generalizationInfo = generalizationInfoService.getById(1);
        return R.ok(generalizationInfo);
    }

    /**
     * 功能室列表
     *
     * @param pageNo    当前页数
     * @param pageSize  每页显示多少行数据
     * @param roomClass 分类id
     * @param
     * @param type      类型（捐款捐物类型（1.公益事项2.捐资捐物））
     * @return
     */
    @GetMapping("/functionRoom/list")
    @ApiOperation("功能室列表")
    @Inner(value = false)
    public R functionRoomlist(@RequestParam("pageNo") Integer pageNo, @RequestParam("pageSize") Integer pageSize,
                              @RequestParam(required = false) Integer roomClass, @RequestParam(required = false) String name,
                              @RequestParam(required = false) Integer type) {
        Page<FunctionRoom> functionRoomPage = functionRoomService.lambdaQuery()
                .eq(FunctionRoom::getEnable, Constants.ENABLE_TRUE).like(StringUtils.isNotEmpty(name), FunctionRoom::getTitle, name)
                .eq(roomClass != null, FunctionRoom::getRoomClass, roomClass).eq(type != null, FunctionRoom::getType, type)
                .orderByDesc(FunctionRoom::getCreateTime)
                .page(new Page(pageNo, pageSize));
        return R.ok(functionRoomPage);
    }

    /**
     * 功能室详情
     *
     * @param id
     * @return
     */
    @GetMapping("/functionRoom/getOne")
    @ApiOperation("功能室详情")
    @Inner(value = false)
    public R getOne(@RequestParam("id") Integer id) {

        FunctionRoom functionRoom = functionRoomService.getById(id);
        //更新浏览量
        functionRoom.setViews(functionRoom.getViews() + 1);
        functionRoomService.updateById(functionRoom);
        FunctionRoomVo functionRoomVo = new FunctionRoomVo();
        //copy Vo类返回给前端
        BeanUtil.copyProperties(functionRoom, functionRoomVo, true);
        return R.ok(functionRoomVo);
    }

    /**
     * 今日配餐计划
     *
     * @return
     */
    @GetMapping("/mealInfo/list")
    @ApiOperation("今日配餐计划")
    @Inner(value = false)
    public R getMealList(@RequestParam(required = false) Long uid, @RequestParam(required = false) Integer type) {

       /* List<MealInfo> mealInfoList = mealInfoService.lambdaQuery().eq(MealInfo::getEnable, Constants.ENABLE_TRUE)
                .ge(MealInfo::getMealDate, DateUtil.beginOfDay(new Date()))
                .le(MealInfo::getMealDate, DateUtil.endOfDay(new Date()))
                .orderByAsc(MealInfo::getType).list();*/

        List<MealInfo> mealInfoList = mealInfoService.getMealInfoList();
        mealInfoList.forEach(mealInfo -> {
            Date date = new Date();
            Date sdate = Date.from(mealInfo.getStartTime().atZone(ZoneId.systemDefault()).toInstant());
            Date edate = Date.from(mealInfo.getEndTime().atZone(ZoneId.systemDefault()).toInstant());
            if (date.compareTo(sdate) == -1) {
                mealInfo.setTimeState(TimeStateEnum.TIME_STATE_ENUM_ONE.getValue());
                mealInfo.setSort(3);
            } else if (date.compareTo(edate) == 1) {
                mealInfo.setTimeState(TimeStateEnum.TIME_STATE_ENUM_THREE.getValue());
                mealInfo.setSort(2);
            } else {
                mealInfo.setTimeState(TimeStateEnum.TIME_STATE_ENUM_TWO.getValue());
                mealInfo.setSort(1);
            }
            //判断用户是否预定
//            if (uid != null) {
//                int count = mealRecordService.lambdaQuery().eq(MealRecord::getUserId, uid)
//                        .eq(MealRecord::getOldMan,oldManId)
//                        .eq(MealRecord::getType, type)
//                        .eq(MealRecord::getMealInfo, mealInfo.getId()).count();
//                if (count > 0) {
//                    mealInfo.setOrderState(OrderStateEnum.ORDER_STATE_ENUM_ONE.getValue());
//                } else {
//                    mealInfo.setOrderState(OrderStateEnum.ORDER_STATE_ENUM_TWO.getValue());
//                }
//            } else {
//                mealInfo.setOrderState(OrderStateEnum.ORDER_STATE_ENUM_TWO.getValue());
//            }
        });


        return R.ok(mealInfoList);
    }

    /**
     * 昨日配餐计划
     *
     * @return
     */
    @GetMapping("/yesterday/mealInfo/list")
    @ApiOperation("昨日配餐计划")
    @Inner(value = false)
    public R getyearMealList() {
        List<MealInfo> mealInfoList = mealInfoService.lambdaQuery().eq(MealInfo::getEnable, Constants.ENABLE_TRUE)
                .ge(MealInfo::getMealDate, DateUtil.beginOfDay(DateUtil.yesterday()))
                .le(MealInfo::getMealDate, DateUtil.endOfDay(DateUtil.yesterday()))
                .orderByAsc(MealInfo::getType).list();
        mealInfoList.forEach(mealInfo -> {
            mealInfo.setTimeState(TimeStateEnum.TIME_STATE_ENUM_THREE.getValue());
            mealInfo.setOrderState(OrderStateEnum.ORDER_STATE_ENUM_TWO.getValue());
        });
        return R.ok(mealInfoList);
    }

    /**
     * 配餐计划详情
     *
     * @param id
     * @return
     */
    @GetMapping("/mealInfo/getOne")
    @ApiOperation("配餐计划详情")
    @Inner(value = false)
    public R getMealOne(@RequestParam("id") Integer id) {
        MealInfo mealInfo = mealInfoService.getById(id);
        return R.ok(mealInfo);
    }

    /**
     * 队伍列表
     *
     * @return
     */
    @GetMapping("/team/list")
    @ApiOperation("队伍列表")
    @Inner(value = false)
    public R getTeamList(@RequestParam("pageNo") Integer pageNo, @RequestParam("pageSize") Integer pageSize) {
        Page page = new Page(pageNo, pageSize);
        Page<TeamInfo> teamInfoPage = teamInfoService.lambdaQuery().eq(TeamInfo::getEnable, Constants.ENABLE_TRUE)
                .orderByAsc(TeamInfo::getCreateTime).page(page);
        teamInfoPage.getRecords().forEach(teamInfo -> {
            int count = teamPersonnelService.lambdaQuery().eq(TeamPersonnel::getEnable, Constants.ENABLE_TRUE)
                    .eq(TeamPersonnel::getTeamInfo, teamInfo.getId()).count();
            teamInfo.setPeopleCount(count);
        });

        return R.ok(teamInfoPage);
    }

    /**
     * 队伍详情
     *
     * @param id
     * @return
     */
    @GetMapping("/team/getOne")
    @ApiOperation("队伍详情")
    @Inner(value = false)
    public R getTeamOne(@RequestParam("id") Integer id) {
        Map<String, Object> map = new HashMap<>();
        TeamInfo teamInfo = teamInfoService.getById(id);
        List<TeamPersonnel> teamPersonnelList = teamPersonnelService.lambdaQuery().eq(TeamPersonnel::getEnable, Constants.ENABLE_TRUE)
                .eq(TeamPersonnel::getTeamInfo, teamInfo.getId()).orderByAsc(TeamPersonnel::getPosition).list();
        map.put("teamInfo", teamInfo);
        map.put("teamPersonnelList", teamPersonnelList);
        return R.ok(map);
    }

    /**
     * 待办分类
     */
    @GetMapping("/replaceBuy/list")
    @ApiOperation("待办分类")
    @Inner(value = false)
    public R getReplaceBuyList() {
        List<ReplaceBuy> replaceBuyList = replaceBuyService.lambdaQuery().eq(ReplaceBuy::getEnable, Constants.ENABLE_TRUE).orderByAsc(ReplaceBuy::getCreateTime).list();
        return R.ok(replaceBuyList);
    }

    /**
     * 待办分类详情
     */
    @GetMapping("/replaceBuy/getOne")
    @ApiOperation("待办分类详情")
    @Inner(value = false)
    public R getReplaceBuyOne(@RequestParam("id") Integer id) {
        ReplaceBuy replaceBuy = replaceBuyService.getById(id);
        return R.ok(replaceBuy);
    }

    @ApiOperation(value = "上传文件，根据key去找对应的配置文件")
    @PostMapping(value = "/fastfile/upload/{key}")
    @Inner(value = false)
    public Object imageUp(@RequestParam MultipartFile file, HttpServletRequest request, HttpServletResponse response, @PathVariable("key") String key) {
        Map<String, Object> upResult = this.fastfileService.uploadFile(key, file);
        return R.ok(upResult);
    }

    /**
     * SOS 求助电话
     */
    @GetMapping("/sos/phone")
    @ApiOperation("SOS 求助电话")
    @Inner(value = false)
    public R getSosPhone() {
        OneTouchCallInfo oneTouchCallInfo = oneTouchCallInfoService.lambdaQuery().eq(OneTouchCallInfo::getEnable, Constants.ENABLE_TRUE).list().get(0);
        return R.ok(oneTouchCallInfo);
    }
}
