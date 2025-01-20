package com.px.apis.user.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.common.core.constant.Constants;
import com.pig4cloud.pig.common.core.util.R;
import com.px.basic.alone.core.base.BaseApiController;
import com.px.basic.alone.security.annotation.Inner;
import com.px.constants.*;
import com.px.modulars.build.entity.BuildingInfo;
import com.px.modulars.build.service.BuildingInfoService;
import com.px.modulars.callLocation.entity.SosMessageInfo;
import com.px.modulars.device.service.DeviceInfoService;
import com.px.modulars.donations.entity.DonationsInfo;
import com.px.modulars.donations.service.DonationsInfoService;
import com.px.modulars.function.service.FunctionRoomClassService;
import com.px.modulars.meal.entity.MealInfo;
import com.px.modulars.meal.entity.MealRecord;
import com.px.modulars.meal.service.MealInfoService;
import com.px.modulars.meal.service.MealRecordService;
import com.px.modulars.miniusers.entity.MiniUsers;
import com.px.modulars.miniusers.service.MiniUsersService;
import com.px.modulars.opinion.entity.OpinionInfo;
import com.px.modulars.opinion.service.OpinionInfoService;
import com.px.modulars.replace.entity.ReplaceBuy;
import com.px.modulars.replace.entity.ReplaceBuyRecord;
import com.px.modulars.replace.service.ReplaceBuyRecordService;
import com.px.modulars.replace.service.ReplaceBuyService;
import com.px.modulars.replace.vo.ReplaceVo;
import com.px.modulars.upms.util.websocket.util.WebSocketServer;
import com.px.modulars.userInfo.entity.CertificationInfo;
import com.px.modulars.userInfo.entity.OldManInfo;
import com.px.modulars.userInfo.service.CertificationInfoService;
import com.px.modulars.userInfo.service.OldManInfoService;
import com.px.modulars.userInfo.service.OldPhrInfoService;
import com.px.modulars.userInfo.vo.CertificationVo;
import com.px.modulars.userInfo.vo.WxOldManVo;
import com.px.modulars.userInfo.vo.WxOldPhrVo;
import com.px.modulars.userInfo.vo.WxUserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/zhyl")
@Api(value = "user", tags = "小程序端需要授权接口管理")
public class WxApiController extends BaseApiController {

    @Autowired
    private FunctionRoomClassService functionRoomClassService;
    @Autowired
    private OpinionInfoService opinionInfoService;
    @Autowired
    private OldManInfoService oldManInfoService;
    @Autowired
    private CertificationInfoService certificationInfoService;
    @Autowired
    private ReplaceBuyRecordService replaceBuyRecordService;
    @Autowired
    private MealRecordService mealRecordService;
    @Autowired
    private MealInfoService mealInfoService;
    @Autowired
    private ReplaceBuyService replaceBuyService;
    //捐资捐物
    @Autowired
    private DonationsInfoService donationsInfoService;
    //SOS
    @Autowired
    private DeviceInfoService deviceInfoService;
    //楼宇
    @Autowired
    private BuildingInfoService buildingInfoService;
    //健康档案
    @Autowired
    private OldPhrInfoService oldPhrInfoService;

    @Autowired
    private MiniUsersService miniUsersService;

    /**
     * 获取老人的健康档案卡信息
     *
     * @param id
     * @return WxOldPhrVo  vo
     */
    @GetMapping("/oldphr/getOne")
    @ApiOperation("获取老人的健康档案卡信息")
    @Inner(value = false)
    public R getOldPhrGetOne(@Validated String id) {
        Map<String, Object> map = new HashMap<>();
        if (id == null || id.isEmpty()) {
            return R.failed("ID错误");
        }
        //老人信息
        OldManInfo oldManInfo = null;
        WxOldPhrVo wxOldPhrVo = null;
        if (id.length() < 10) {
            oldManInfo = oldManInfoService.lambdaQuery().eq(OldManInfo::getId, Integer.parseInt(id)).eq(OldManInfo::getEnable, Constants.ENABLE_TRUE).one();
        }
        WxOldManVo wxOldManVo = new WxOldManVo();
        if (oldManInfo != null) {
            wxOldManVo.setName(oldManInfo.getName());
            wxOldManVo.setSex(oldManInfo.getSex() == 1 ? "男" : "女");
            wxOldManVo.setPhone(oldManInfo.getPhone());
            BuildingInfo buildingInfo = buildingInfoService.lambdaQuery().eq(BuildingInfo::getId, oldManInfo.getBuilding()).eq(BuildingInfo::getEnable, Constants.ENABLE_TRUE).one();
            if (buildingInfo!=null){
                wxOldManVo.setAddress(buildingInfo.getCommunity() == null ? "无" : buildingInfo.getCommunity() + "-" + buildingInfo.getBuildingNumber() + "-" + oldManInfo.getAddress());
            }

            wxOldManVo.setPermanentAddress(oldManInfo.getPermanentAddress());
            wxOldManVo.setStreet(oldManInfo.getStreet());
            wxOldManVo.setAge(oldManInfo.getAge() + "");
            wxOldPhrVo = oldPhrInfoService.oldPhrInfoGetOne(Integer.parseInt(id));
        } else {
            WxUserVo wxUserVo = oldManInfoService.getUserInfo(Long.valueOf(getCurrentId()));
            if (UserTypeStateEnum.USER_TYPE_STATE_ENUM_ONE.getValue().equals(wxUserVo.getUserType())) {
                //老人信息
                oldManInfo = oldManInfoService.lambdaQuery().eq(OldManInfo::getId, wxUserVo.getId()).eq(OldManInfo::getEnable, Constants.ENABLE_TRUE).one();
                if (oldManInfo != null) {
                    wxOldManVo.setName(oldManInfo.getName());
                    wxOldManVo.setSex(oldManInfo.getSex() == 1 ? "男" : "女");
                    wxOldManVo.setPhone(oldManInfo.getPhone());
                    BuildingInfo buildingInfo = buildingInfoService.lambdaQuery().eq(BuildingInfo::getId, oldManInfo.getBuilding()).eq(BuildingInfo::getEnable, Constants.ENABLE_TRUE).one();
                    if (buildingInfo!=null){
                        wxOldManVo.setAddress(buildingInfo.getCommunity() == null ? "无" : buildingInfo.getCommunity() + "-" + buildingInfo.getBuildingNumber() + "-" + oldManInfo.getAddress());
                    }
                    wxOldManVo.setPermanentAddress(oldManInfo.getPermanentAddress());
                    wxOldManVo.setStreet(oldManInfo.getStreet());
                    wxOldManVo.setAge(oldManInfo.getAge() + "");
                    long a = wxUserVo.getId();
                    int b = (int) a;
                    wxOldPhrVo = oldPhrInfoService.oldPhrInfoGetOne(b);
                }
            } else {
                map.put("wxOldPhrVo", null);
                map.put("wxOldManVo", null);
                return R.ok(map);
            }
        }
        map.put("wxOldPhrVo", wxOldPhrVo);
        map.put("wxOldManVo", wxOldManVo);
        return R.ok(map);
    }


    /**
     * SOS信息添加
     *
     * @param sosMessageInfo 位置信息，电话号
     * @return R
     */
    @PostMapping("/sosMessage/add")
    @ApiOperation("SOS信息添加")
    @Inner(value = false)
    public R sosMessageSave(@RequestBody SosMessageInfo sosMessageInfo) {
       /* WxUserVo wxUserVo = oldManInfoService.getUserInfo(super.getCurrentId());
        wxUserVo.getNickname();*/

        //添加SOS信息
        sosMessageInfo.setEnable(1);
        sosMessageInfo.setIsSolve("2");
        sosMessageInfo.setCreateTime(LocalDateTime.now());
        //查询老人的信息
        OldManInfo oldManInfo = oldManInfoService.lambdaQuery().eq(OldManInfo::getPhone, sosMessageInfo.getPhone()).eq(OldManInfo::getEnable, Constants.ENABLE_TRUE).one();
        if (oldManInfo != null) {
            sosMessageInfo.setOldId(oldManInfo.getId());
        }
        int a = deviceInfoService.saveSosMessageInfo(sosMessageInfo);
        if (a > 0) {
            try {
                if (oldManInfo != null) {
                    WebSocketServer.BroadCastInfo("姓名为：" + oldManInfo.getName() + " 手机号为：" + oldManInfo.getPhone() + " 发出了SOS消息，请及时查看！");
                } else {
                    WebSocketServer.BroadCastInfo("手机号为：" + sosMessageInfo.getPhone() + " 发出了SOS消息，请及时查看！");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return R.ok();
    }

    /**
     * 新增捐资捐物
     *
     * @param donationsInfo 捐资捐物
     * @return R
     */
    @PostMapping("/donations/add")
    @ApiOperation("物资捐资新增")
    @Inner(value = false)
    public R save(@RequestBody DonationsInfo donationsInfo) {
        donationsInfo.setCreateTime(LocalDateTime.now());
        donationsInfo.setAllUserId(Long.valueOf(super.getCurrentId()));
        return R.ok(donationsInfoService.save(donationsInfo));
    }

    /**
     * 物资捐资列表
     */
/*    @GetMapping("/donations/list")
    @ApiOperation("物资捐资列表")
    @Inner(value = false)
    public R getDonationsList(@RequestParam("pageNo") Integer pageNo, @RequestParam("pageSize") Integer pageSize) {
        Page page = new Page(pageNo, pageSize);
        Page<DonationsInfo> donationsInfoPage = donationsInfoService.lambdaQuery()
                .eq(DonationsInfo::getEnable, Constants.ENABLE_TRUE)
                .eq(DonationsInfo::getAllUserId, super.getCurrentId())
                .orderByDesc(DonationsInfo::getCreateTime)
                .page(page);
        return R.ok(donationsInfoPage);
    }*/

    /**
     * 物资捐资列表详情
     */
/*    @GetMapping("/donations/getOne")
    @ApiOperation("物资捐资列表详情")
    @Inner(value = false)
    public R getDonationsGetOne(Integer id) {
        if (id == null) {
            return R.failed("ID错误");
        }
        DonationsInfo donationsInfo=donationsInfoService.lambdaQuery()
                .eq(DonationsInfo::getEnable, Constants.ENABLE_TRUE)
                .eq(DonationsInfo::getAllUserId, super.getCurrentId())
                .eq(DonationsInfo::getId, id).one();
        return R.ok(donationsInfo);
    }*/


    /**
     * 意见反馈新增
     *
     * @return
     */
    @PostMapping("/opinion/add")
    @ApiOperation("意见反馈新增")
    @Inner(value = false)
    public R add(@RequestBody OpinionInfo opinionInfo) {
        opinionInfo.setCreateTime(LocalDateTime.now());

        opinionInfo.setAllUserId(Long.valueOf(super.getCurrentId()));
        return R.ok(opinionInfoService.save(opinionInfo));
    }

    /**
     * 意见反馈列表
     */
    @GetMapping("/opinion/list")
    @ApiOperation("意见反馈列表")
    @Inner(value = false)
    public R getList(@RequestParam("pageNo") Integer pageNo, @RequestParam("pageSize") Integer pageSize) {
        Page page = new Page(pageNo, pageSize);
        Page<OpinionInfo> opinionInfoPage = opinionInfoService.lambdaQuery().eq(OpinionInfo::getEnable, Constants.ENABLE_TRUE)
                .eq(OpinionInfo::getAllUserId, super.getCurrentId())
                .orderByDesc(OpinionInfo::getCreateTime)
                .page(page);
        return R.ok(opinionInfoPage);
    }

    /**
     * 绑定老人
     */
    @PostMapping("/binding/old")
    @ApiOperation("绑定老人")
    @Inner(value = false)
    public R bingdingOld(@RequestBody CertificationVo certificationVo) {
        Integer userId= super.getCurrentId();
        if (userId == null){
            return R.failed().setCode(401);
        }


        //查询是否已经绑定老人
//        int count = certificationInfoService.lambdaQuery().eq(CertificationInfo::getEnable, Constants.ENABLE_TRUE)
//                .eq(CertificationInfo::getUserId, super.getCurrentId())
//                .eq(CertificationInfo::getState, CerStateEnum.CER_STATE_ENUM_TWO).count();
//        if (count > 0) {
//            return R.failed("您已经绑定过老人，不能再添加");
//        }

        OldManInfo oldManInfo = oldManInfoService.lambdaQuery().eq(OldManInfo::getEnable, Constants.ENABLE_TRUE)
                .eq(OldManInfo::getPhone, certificationVo.getOldPhone())
                .eq(OldManInfo::getIdCard, certificationVo.getOldCard()).one();
        if (oldManInfo == null) {
            return R.failed().setMsg("未找到老人信息");
        }
//        //查询老人是否已经被绑定
//        int count1 = certificationInfoService.lambdaQuery().eq(CertificationInfo::getEnable, Constants.ENABLE_TRUE).eq(CertificationInfo::getState, CerStateEnum.CER_STATE_ENUM_TWO.getValue())
//                .eq(CertificationInfo::getOldManInfo, oldManInfo.getId()).count();
//        if (count1 > 0) {
//            return R.failed().setMsg("此老人已经被绑定");
//        }

        int count = certificationInfoService.lambdaQuery().eq(CertificationInfo::getEnable, Constants.ENABLE_TRUE)
                .eq(CertificationInfo::getUserId,userId)
                .eq(CertificationInfo::getState, CerStateEnum.CER_STATE_ENUM_TWO)
                .eq(CertificationInfo::getOldManInfo,oldManInfo.getId())
                .count();
        if (count > 0) {
            return R.failed("您已经绑定该老人，不能再添加");
        }

        CertificationInfo certificationInfo = new CertificationInfo();
        BeanUtil.copyProperties(certificationVo, certificationInfo, true);
        certificationInfo.setCreateTime(LocalDateTime.now());
        certificationInfo.setOldManInfo(oldManInfo.getId());
        certificationInfo.setUserId(Long.valueOf(userId));
        certificationInfo.setState(CerStateEnum.CER_STATE_ENUM_ONE.getValue());
        certificationInfo.setOldName(oldManInfo.getName());
        certificationInfoService.save(certificationInfo);
        return R.ok();
    }


    /**
     * 代买代办
     */
    @PostMapping("/replace/buy")
    @ApiOperation("代买代办")
    @Inner(value = false)
    public R replaceBuy(@RequestBody ReplaceVo replaceVo) {
        Integer userId = super.getCurrentId();
        if (userId == null){
            return R.failed().setCode(401);
        }
//        replaceVo.setUserId(Long.valueOf(super.getCurrentId()));
//        Map<String, Object> map = replaceBuyRecordService.ReplaceBuyRecord(replaceVo);
//        if (map.get("code").equals(1)) {
//            return R.ok(map.get("msg"));
//        } else {
//            return R.failed().setMsg(map.get("msg").toString());
//        }
        OldManInfo oldManInfo = oldManInfoService.getById(replaceVo.getOldManId());
        if (oldManInfo!=null){
            ReplaceBuyRecord replaceBuyRecord = new ReplaceBuyRecord();
            BeanUtil.copyProperties(replaceVo, replaceBuyRecord, true);
            replaceBuyRecord.setCreateTime(LocalDateTime.now());
            replaceBuyRecord.setState(ReplaceStateEnum.REPLACE_TYPE_ENUM_TWO.getValue());
            replaceBuyRecord.setOldId(oldManInfo.getId());
            replaceBuyRecord.setIsEquipment(IsEquipmentEnum.IS_EQUIPMENT_ENUM_TWO.getValue());
            replaceBuyRecord.setOldName(oldManInfo.getName());
            replaceBuyRecord.setPhone(oldManInfo.getPhone());
            replaceBuyRecord.setBuildingInfo(oldManInfo.getBuilding());
            replaceBuyRecord.setUserId(userId.longValue());
            if (replaceVo.getAddress() == null) {
                //等于空的话就是拿老人所在的楼宇信息
                replaceBuyRecord.setAddress(oldManInfo.getAddress());
            } else {
                //添加详细地址
                replaceBuyRecord.setAddress(replaceVo.getAddress());
                replaceBuyRecord.setLat(replaceVo.getLat());
                replaceBuyRecord.setLng(replaceVo.getLng());
            }
            replaceBuyRecordService.save(replaceBuyRecord);
            try {
                WebSocketServer.BroadCastInfo("姓名为：" + replaceBuyRecord.getOldName() + " 手机号为：" + replaceBuyRecord.getPhone() + " 有新的代买代办消息，请及时查看！");
            } catch (Exception e) {

            }
            return R.ok();
        }else {
            return R.failed("未找到老人信息");
        }

    }

    /**
     * 个人信息
     * type(1.个人信息2.老人信息)
     */
    @GetMapping("/user/getOne")
    @ApiOperation("个人信息")
    @Inner(value = false)
    public R getUser(@RequestParam("type") Integer type) {

        Integer user = super.getCurrentId();
        if (user == null) {
            return R.failed().setCode(401).setMsg("请登录");
        }
        MiniUsers users = miniUsersService.getById(user);
        if (user == null) {
            return R.failed().setCode(401).setMsg("请登录");
        }
        if (type == 1) {
            WxUserVo wxUserVo = oldManInfoService.getUserInfo(Long.valueOf(super.getCurrentId()));
            return R.ok(wxUserVo);
        } else {
            List<CertificationInfo> certificationInfo = certificationInfoService.lambdaQuery().eq(CertificationInfo::getEnable, Constants.ENABLE_TRUE)
                    .eq(CertificationInfo::getUserId, super.getCurrentId()).list();
            if (certificationInfo != null) {

                WxUserVo wxUserVo = new WxUserVo();

                wxUserVo.setAvatar(users.getAvatar());
                wxUserVo.setId(Long.parseLong(users.getId().toString()));
                wxUserVo.setNickname(users.getNickName());
                wxUserVo.setPhone(users.getPhone());
                wxUserVo.setOpenid(users.getOpenId());

                List<OldManInfo> re = new ArrayList<>();
                certificationInfo.forEach(val -> {
                    OldManInfo oldManInfo = oldManInfoService.getById(val.getOldManInfo());
                    if (oldManInfo != null) {
                        oldManInfo.setCerState(val.getState());
                        oldManInfo.setReason(val.getReason());
                        re.add(oldManInfo);
                    }
                });
                wxUserVo.setOldManInfo(re);
//                wxUserVo.setPhone(oldManInfo.getPhone());
//                wxUserVo.setNickname(oldManInfo.getName());
//                wxUserVo.setIdCard(oldManInfo.getIdCard());
//                wxUserVo.setCerState(certificationInfo.getState());
//                wxUserVo.setId(Long.parseLong(certificationInfo.getOldManInfo().toString()));
//                wxUserVo.setReason(certificationInfo.getReason());
//                wxUserVo.setBuilding(oldManInfo.getBuilding());
//                BuildingInfo buildingInfo = buildingInfoService.getById(oldManInfo.getBuilding());
//                if (buildingInfo != null) {
//                    wxUserVo.setAddress(buildingInfo.getAddress());
//                    wxUserVo.setLat(buildingInfo.getLat());
//                    wxUserVo.setLng(buildingInfo.getLng());
//                }
                return R.ok(wxUserVo);
            } else {
                return R.ok();
            }
        }
    }

    /**
     * 订餐
     * type(1.代买2.自己买)
     *
     * @param address 地址
     * @param lng     经度
     * @param lat     纬度
     */
    @GetMapping("/meal/reserve")
    @ApiOperation("订餐")
    public R mealReserve(@RequestParam("type") Integer type, @RequestParam("mealId") Integer mealId, String address, String lng, String lat,@RequestParam("oldManId")Integer oldManId) {

        Integer userId= super.getCurrentId();
        if (userId==null){
            return R.failed().setCode(401);
        }
//        Map<String, Object> map = mealRecordService.mealRecord(type, mealId, Long.valueOf(super.getCurrentId()), address, lng, lat);
//        if (map.get("code").equals(1)) {
//            return R.ok(map.get("msg"));
//        } else {
////            return R.failed(map.get("msg"));
//            return R.failed().setMsg(map.get("msg").toString());
//        }

        OldManInfo oldManInfo = oldManInfoService.getById(oldManId);
        if (oldManInfo!=null){


            int count = mealRecordService.lambdaQuery()
                    .eq(MealRecord::getOldMan,oldManId)
                    .eq(MealRecord::getType, type)
                    .eq(MealRecord::getMealInfo, mealId).count();
            if (count > 0) {
              return R.failed("已预订");
            }


            MealInfo mealInfo = mealInfoService.getById(mealId);
            if (mealInfo!=null){
                MealRecord mealRecord = new MealRecord();
                mealRecord.setMealInfo(mealId);
                mealRecord.setState(ReplaceStateEnum.REPLACE_TYPE_ENUM_TWO.getValue());
                mealRecord.setUserId(Long.parseLong(userId.toString()));
                mealRecord.setOldMan(oldManId);
                mealRecord.setMealNumber(mealInfo.getNumber());
                mealRecord.setIsEquipment(IsEquipmentEnum.IS_EQUIPMENT_ENUM_TWO.getValue());
                mealRecord.setCreateTime(LocalDateTime.now());
                mealRecord.setType(type);
                mealRecord.setName(oldManInfo.getName());
                mealRecord.setPhone(oldManInfo.getPhone());
                mealRecord.setBuildingInfo(oldManInfo.getBuilding());
                mealRecord.setAddress(address);
                mealRecord.setLat(lng);
                mealRecord.setLng(lat);
                mealRecordService.save(mealRecord);
                try {
                    WebSocketServer.BroadCastInfo("姓名为：" + mealRecord.getName() + " 手机号为：" + mealRecord.getPhone() + " 有新的订餐信息，请及时查看！");
                } catch (IOException e) {

                }
                return R.ok();
            }else {
                return R.failed("失败");
            }
        }else {
            return R.failed("未找到老人信息");
        }

    }


    /**
     * 订餐记录
     */
    @GetMapping("/meal/record")
    @ApiOperation("订餐记录")
    public R mealRecord(@RequestParam("oldId") Integer oldId, @RequestParam("pageNo") Integer pageNo
            , @RequestParam("pageSize") Integer pageSize) {
        Page<MealRecord> mealRecordPage = mealRecordService.lambdaQuery().eq(MealRecord::getOldMan, oldId)
                .eq(MealRecord::getUserId, super.getCurrentId())
                .orderByDesc(MealRecord::getCreateTime)
                .page(new Page(pageNo, pageSize));
        mealRecordPage.getRecords().forEach(mealRecord -> {
            MealInfo mealInfo = mealInfoService.getById(mealRecord.getMealInfo());
            mealRecord.setMealInfoVo(mealInfo);
        });
        return R.ok(mealRecordPage);

    }

    /**
     * 代买记录
     */
    @GetMapping("/buy/record")
    @ApiOperation("代买记录")
    public R buyRecord(@RequestParam("oldId") Integer oldId, @RequestParam("pageNo") Integer pageNo
            , @RequestParam("pageSize") Integer pageSize) {

        Page<ReplaceBuyRecord> replaceBuyRecordPage = replaceBuyRecordService.lambdaQuery()
                .eq(ReplaceBuyRecord::getOldId, oldId)
                .eq(ReplaceBuyRecord::getUserId, super.getCurrentId())
                .orderByDesc(ReplaceBuyRecord::getCreateTime).page(new Page(pageNo, pageSize));
        replaceBuyRecordPage.getRecords().forEach(replaceBuyRecord -> {
            ReplaceBuy replaceBuy = replaceBuyService.getById(replaceBuyRecord.getReplaceBuy());
            replaceBuyRecord.setReplaceBuyVo(replaceBuy);
        });
        return R.ok(replaceBuyRecordPage);
    }
}
