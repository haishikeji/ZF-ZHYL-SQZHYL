
package com.px.modulars.callLocation.controller;

import cn.hutool.core.map.TableMap;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.common.core.constant.Constants;
import com.pig4cloud.pig.common.core.util.R;
import com.px.basic.alone.security.annotation.Inner;
import com.px.modulars.build.entity.BuildingInfo;
import com.px.modulars.build.service.BuildingInfoService;
import com.px.modulars.callLocation.entity.*;
import com.px.modulars.device.entity.GsmRsp;
import com.px.modulars.callLocation.service.SosMessageInfoService;
import com.px.basic.alone.core.base.BaseController;
import com.px.common.log.annotation.SysLog;
import com.px.basic.alone.security.util.SecurityUtils;
import com.px.modulars.device.entity.DeviceInfo;
import com.px.modulars.device.service.DeviceInfoService;
import com.px.modulars.meal.entity.MealRecord;
import com.px.modulars.meal.service.MealRecordService;
import com.px.modulars.userInfo.entity.OldManInfo;
import com.px.modulars.userInfo.service.OldManInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 呼叫定位SOS信息
 *
 * @author 品讯科技
 * @date 2024-08
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/callLocation/sosmessageinfo")
@Api(value = "sosmessageinfo", tags = "呼叫定位管理")
public class SosMessageInfoController extends BaseController<SosMessageInfo, SosMessageInfoService> {

    private final SosMessageInfoService sosMessageInfoService;
    private final DeviceInfoService deviceInfoService;
    //路径信息
    private static String file = "D:\\测试读取TXT\\";
    private final OldManInfoService oldManInfoService;
    private final BuildingInfoService buildingInfoService;
    private final MealRecordService mealRecordService;
    //经度
    private static String j = null;
    //纬度
    private static String w = null;
    private Random random = new Random();
    private String[] lngAndlat = {"109.71624196171763,38.285350295721834", "109.71552347972988,38.284921384355684", "109.71643510222435,38.285044960093536", "109.71580118358133,38.284646816000425", "109.716188,38.284403", "109.715227,38.285164", "109.714944,38.285307", "109.71442856177691,38.285594671725384"};


    /**
     * 数量
     *
     * @return
     */
    @GetMapping("/getQuantityVoMap")
    @ApiOperation("数量")
    public R getQuantityVoMap() {
        Map<String, Object> map = sosMessageInfoService.getQuantityVoMap();
        return R.ok(map);
    }

    /**
     * 实时定位-老人信息
     *
     * @param page        分页对象
     * @param nameOrPhone 老人姓名或者手机号
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/getOldNameOrPhonePage")
    public R getOldNameOrPhonePage(Page page, String nameOrPhone) {
        OldManInfo oldManInfo = new OldManInfo();
        oldManInfo.setName(nameOrPhone);
        oldManInfo.setPhone(nameOrPhone);
        Page<OldManInfo> getOldNameOrPhonePage = oldManInfoService.lambdaQuery()
                .eq(OldManInfo::getEnable, Constants.ENABLE_TRUE)
                .like(StringUtils.isNotEmpty(oldManInfo.getName()), OldManInfo::getName, oldManInfo.getName())
                .or()
                .like(StringUtils.isNotEmpty(oldManInfo.getPhone()), OldManInfo::getPhone, oldManInfo.getPhone())
                .orderByDesc(OldManInfo::getCreateTime)
                .page(page);
//        for (OldManInfo a : getOldNameOrPhonePage.getRecords()) {
//            BuildingInfo buildingInfo = buildingInfoService.lambdaQuery().eq(BuildingInfo::getEnable, Constants.ENABLE_TRUE)
//                    .eq(BuildingInfo::getId, a.getBuilding()).one();
//            if (buildingInfo != null) {
//                a.setAddress(buildingInfo.getCommunity() + buildingInfo.getBuildingNumber());
//            }
//            String ll = lngAndlat[random.nextInt(lngAndlat.length)];
//            //经度
//            int index3 = ll.indexOf(",");
//            String longitude = ll.substring(0, index3);
//            a.setLongitude(longitude);
//            //纬度
//            String latitude = ll.substring(index3 + 1);
//            a.setLatitude(latitude);
//        }
        return R.ok(getOldNameOrPhonePage);
    }

    /**
     * 实时定位-SOS呼叫信息-异常记录
     *
     * @param page 分页对象
     * @return
     */
    @ApiOperation(value = "实时定位-SOS呼叫信息-异常记录", notes = "实时定位-SOS呼叫信息-异常记录")
    @GetMapping("/page")
    public R getSosMessageInfoPage(Page page, SosMessageInfo sosMessageInfo) {
        Page getSosMessageInfoPage = sosMessageInfoService.lambdaQuery()
                .eq(SosMessageInfo::getEnable, Constants.ENABLE_TRUE)
                .eq(SosMessageInfo::getIsSolve, Constants.ENABLE_FALSE)
                .like(StringUtils.isNotEmpty(sosMessageInfo.getPhone()), SosMessageInfo::getPhone, sosMessageInfo.getPhone())
                .page(page);
        return R.ok(getSosMessageInfoPage);
    }


    /**
     * 通过id查询呼叫定位
     *
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}")
    @PreAuthorize("@pms.hasPermission('callLocation_sosmessageinfo_get')")
    public R getById(@PathVariable("id") Integer id) {
        return super.get(id);
    }

    /**
     * 新增呼叫定位
     *
     * @param sosMessageInfo 呼叫定位
     * @return R
     */
    @ApiOperation(value = "新增呼叫定位", notes = "新增呼叫定位")
    @PostMapping
    @SysLog("新增呼叫定位")
    @PreAuthorize("@pms.hasPermission('callLocation_sosmessageinfo_add')")
    public R save(@Validated @RequestBody SosMessageInfo sosMessageInfo) {
        return super.update(sosMessageInfo, SecurityUtils.getUser().getId());
    }

    /**
     * 修改呼叫定位
     *
     * @param sosMessageInfo 呼叫定位
     * @return R
     */
    @ApiOperation(value = "修改呼叫定位", notes = "修改呼叫定位")
    @PutMapping
    @SysLog("修改呼叫定位")
    @PreAuthorize("@pms.hasPermission('callLocation_sosmessageinfo_edit')")
    public R updateById(@Validated @RequestBody SosMessageInfo sosMessageInfo) {
        return super.update(sosMessageInfo, SecurityUtils.getUser().getId());
    }

    /**
     * 通过id删除呼叫定位
     *
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除呼叫定位", notes = "通过id删除呼叫定位")
    @DeleteMapping("/{id}")
    @SysLog("通过id删除呼叫定位")
    @PreAuthorize("@pms.hasPermission('callLocation_sosmessageinfo_del')")
    public R removeById(@PathVariable Integer id) {
        SosMessageInfo sosMessageInfo = super.service.queryById(id);
        if (sosMessageInfo == null) {
            return R.failed("ID错误");
        }
        return super.del(sosMessageInfo, SecurityUtils.getUser().getId());
    }

    /**
     * 实时定位-SOS呼叫信息
     * 只查询未解决的SOS信息-此信息为设备终端进行添加
     * 经度纬度为添加SOS时候的位置
     *
     * @return
     */
    @ApiOperation(value = "实时定位-SOS呼叫信息", notes = "实时定位-SOS呼叫信息")
    @GetMapping("/selectSosUserInfoList")
    public R getSelectSosUserInfoList(Page page, String nameOrPhone) {
     /*   Page getSosMessageInfoPage = sosMessageInfoService.lambdaQuery()
                .eq(SosMessageInfo::getEnable, Constants.ENABLE_TRUE)
                .eq(SosMessageInfo::getIsSolve, Constants.ENABLE_FALSE)
                .eq(StringUtils.isNotEmpty(nameOrPhone), SosMessageInfo::getPhone, nameOrPhone)
                .orderByAsc(SosMessageInfo::getCreateTime)
                .page(page);*/
        Page<SosUserInfo> selectSosUserInfoList = sosMessageInfoService.selectSosUserInfoList(page, nameOrPhone);
        return R.ok(selectSosUserInfoList);
    }

    /**
     * 实时定位-SOS呼叫信息-条件查询
     *
     * @param nameOrPhone 老人姓名或者手机号
     * @return
     */
    @ApiOperation(value = "实时定位-SOS呼叫信息", notes = "实时定位-SOS呼叫信息")
    @GetMapping("/selectSosUserInfoListBy")
    public R selectSosUserInfoListBy(String nameOrPhone) {
        SosMessageInfo sosMessageInfo = sosMessageInfoService.lambdaQuery()
                .eq(SosMessageInfo::getEnable, Constants.ENABLE_TRUE)
                .eq(SosMessageInfo::getIsSolve, Constants.ENABLE_FALSE)
                .eq(SosMessageInfo::getPhone, nameOrPhone)
                .one();
        return R.ok(sosMessageInfo);
       /* List<SosUserInfo> selectSosUserInfoListBy = null;
        if (nameOrPhone == null || nameOrPhone == "") {
            return R.ok(selectSosUserInfoListBy);
        } else {
            selectSosUserInfoListBy = sosMessageInfoService.selectSosUserInfoListBy(nameOrPhone);
            return R.ok(selectSosUserInfoListBy);
        }*/
    }

    /**
     * 实时定位-SOS呼叫信息-已解决状态修改
     *
     * @param sosMessageId SOS记录id
     */
    @ApiOperation("实时定位-送餐信息-已完成状态修改")
    @PutMapping("/updateSosMessageState")
    public R updateSosMessageState(Integer sosMessageId) {
        if (sosMessageId == null) {
            return R.failed("sosMessageId不能为空");
        }
        int a = sosMessageInfoService.updateSosMessageState(sosMessageId);
        if (a == 1) {
            return R.ok("成功");
        } else {
            return R.failed("失败");
        }

    }

    /**
     * 实时定位-轨迹查询
     *
     * @param id        老人ID
     * @param sumBtDate 日期不能为空
     * @param startTime 轨迹时间段查询 起始（开始日期不能大于截止日期未判断）
     * @param endTime   轨迹时间段查询 截止
     * @return
     */
    @ApiOperation(value = "实时定位-轨迹查询", notes = "实时定位-轨迹查询")
    @GetMapping("/selectUserLocusList")
    public R selectUserLocusList(Integer id, String sumBtDate, String startTime, String endTime) throws ParseException {
        if (id == null) {
            return R.failed("老人ID不能为空");
        }
        if (sumBtDate == null || sumBtDate.length() == 0) {
            return R.failed("日期不能为空");

        } else {
            if (!isValidDate(sumBtDate)) {
                return R.failed("日期格式不正确");
            }
        }
        List<GsmRsp> list2 = null;
        //拿到老人ID去查询老人绑定的设备编号
        DeviceInfo deviceInfo = new DeviceInfo();
        deviceInfo.setBindingId(id);
        List<DeviceInfo> selectDeviceInfoById = deviceInfoService.selectDeviceInfoById(deviceInfo);
        if (selectDeviceInfoById.size() <= 0) {
            return R.failed("未查询到老人信息或者老人未绑定设备");
        } else {
            //设备编号
            String deviceNumber = selectDeviceInfoById.get(0).getDeviceNumber();
            //读取txt文件
            String filePath = file + sumBtDate + "\\" + deviceNumber + ".txt";
            list2 = new ArrayList<>();
            List<GsmRsp> list = readTxtFile(filePath);
            if (list.size() <= 0) {
                return R.failed("当前日期没有轨迹信息");
            }
            if (startTime == null && endTime == null && startTime != "" && endTime != "") {
                return R.ok(list);
            } else {
                if (isValidDates(startTime) == false) {
                    return R.failed("开始时间格式不正确");
                }
                SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                if (endTime == null) {
                    endTime = sumBtDate + " 23:59:59";
                } else {
                    if (isValidDates(endTime) == false) {
                        return R.failed("截止时间格式不正确");
                    }
                }
                Date startTime1 = ft.parse(startTime);
                Date endTime1 = ft.parse(endTime);
                for (GsmRsp a : list) {
                    boolean effectiveDate = isEffectiveDate(ft.parse(a.getDate()), startTime1, endTime1);
                    if (effectiveDate) {
                        list2.add(a);
                    }
                }
                return R.ok(list2);
            }
        }
    }


    /**
     * 读取txt文件内容
     */
    public static List<GsmRsp> readTxtFile(String filePath) {
        List<GsmRsp> list = new ArrayList<GsmRsp>();
        try {
            String encoding = "UTF-8";
            File file = new File(filePath);
            if (file.isFile() && file.exists()) { //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file), encoding); //考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    GsmRsp gsmRsp = new GsmRsp();
                    int index = lineTxt.indexOf("|");//获取第一个"|"的位置3
                    String date = lineTxt.substring(0, index);//不含"|",这叫含头不含尾
                    gsmRsp.setDate(date);
                    //|后面的为经纬度
                    int index2 = lineTxt.indexOf("|");//获取第一个"|"的位置3
                    String str1 = lineTxt.substring(index2);//含"|"
                    //经度
                    int index3 = str1.indexOf(",");//获取第一个"|"的位置3
                    String longitude = str1.substring(1, index3);//不含"|",这叫含头不含尾
                    gsmRsp.setLongitude(longitude);
                    //纬度
                    int index4 = lineTxt.indexOf(",");//获取第一个"|"的位置3
                    String latitude = lineTxt.substring(index4 + 1);//含"|"
                    gsmRsp.setLatitude(latitude);

                    list.add(gsmRsp);
                }
                read.close();

            } else {
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 判断当前时间是否在[startTime, endTime]区间，注意时间格式要一致
     *
     * @param nowTime   当前时间
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return
     * @author 品讯科技
     */
    public static boolean isEffectiveDate(Date nowTime, Date startTime, Date endTime) {
        if (nowTime.getTime() == startTime.getTime()
                || nowTime.getTime() == endTime.getTime()) {
            return true;
        }

        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(startTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        if (date.after(begin) && date.before(end)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断时间格式是否正确
     *
     * @param str
     * @return
     */
    public static boolean isValidDate(String str) {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); //这里的时间格式根据自己需求更改（注意：格式区分大小写、格式区分大小写、格式区分大小写）
        try {
            Date date = (Date) formatter.parse(str);
            return str.equals(formatter.format(date));
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 判断时间格式是否正确
     *
     * @param str
     * @returns
     */
    public static boolean isValidDates(String str) {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //这里的时间格式根据自己需求更改（注意：格式区分大小写、格式区分大小写、格式区分大小写）
        try {
            Date date = (Date) formatter.parse(str);
            return str.equals(formatter.format(date));
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 实时定位-老人详情
     *
     * @return
     */
    @ApiOperation(value = "实时定位-老人详情", notes = "实时定位-老人详情")
    @GetMapping("/selectUserDetailsLocusList")
    public R selectUserDetailsLocusList() {
        //查询老人所有信息
        List<UserDetailInfo> selectUserDetailsLocusList = sosMessageInfoService.selectUserDetailsLocusList();
        for (UserDetailInfo a : selectUserDetailsLocusList) {
            //设备编号
            a.getDeviceNumber();
            //拿到设备编号之后去请求终端获取定位信息
            int i = (int) (Math.random() * 90) + 10;
            j = "109.7149" + i;
            w = "38.2853" + i;
            a.setLongitude(j);
            a.setLatitude(w);
        }
        //
        return R.ok(selectUserDetailsLocusList);
    }

    /**
     * 实时定位-老人详情单独查询返回
     *
     * @param nameOrPhone 老人姓名或者手机号
     * @return
     */
    @ApiOperation(value = "实时定位-老人详情单独查询返回", notes = "实时定位-老人详情单独查询返回")
    @GetMapping("/selectUserDetailsLocusListBy")
    public R selectUserDetailsLocusListBy(String nameOrPhone) {
        List<UserDetailInfo> selectUserDetailsLocusListBy = null;
        if (nameOrPhone == null || nameOrPhone == "") {
            return R.ok(selectUserDetailsLocusListBy);
        } else {
            UserDetailInfo userDetailInfo = new UserDetailInfo();
            userDetailInfo.setName(nameOrPhone);
            userDetailInfo.setPhone(nameOrPhone);
            //查询老人所有信息
            selectUserDetailsLocusListBy = sosMessageInfoService.selectUserDetailsLocusListBy(userDetailInfo);
            for (UserDetailInfo a : selectUserDetailsLocusListBy) {
                //设备编号
                a.getDeviceNumber();
                //拿到设备编号之后去请求终端获取定位信息
                int i = (int) (Math.random() * 90) + 10;
                j = "109.7149" + i;
                w = "38.2853" + i;
                a.setLongitude(j);
                a.setLatitude(w);
            }
            return R.ok(selectUserDetailsLocusListBy);
        }
    }

    /**
     * 实时定位-代买代办
     *
     * @return
     */
    @ApiOperation("实时定位-代买代办")
    @GetMapping("/selectReplaceBuyRecordBDInfoList")
    public R selectReplaceBuyRecordBDInfoList(@RequestParam("pageNo") Integer pageNo, @RequestParam("pageSize") Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        //查询老人所有信息
        Page<ReplaceBuyRecordBDInfo> selectReplaceBuyRecordBDInfoList = sosMessageInfoService.selectReplaceBuyRecordBDInfoList(new Page<ReplaceBuyRecordBDInfo>(pageNo, pageSize));
        map.put("total", (int) selectReplaceBuyRecordBDInfoList.getTotal());
        map.put("list", selectReplaceBuyRecordBDInfoList);
        return R.ok(map);
    }

    /**
     * 实时定位-代买代办单独查询返回
     *
     * @param nameOrPhone 老人姓名或者手机号
     * @return
     */
    @ApiOperation("实时定位-代买代办单独查询返回")
    @GetMapping("/selectReplaceBuyRecordBDInfoListBy")
    public R selectReplaceBuyRecordBDInfoListBy(String nameOrPhone) {
        List<ReplaceBuyRecordBDInfo> selectReplaceBuyRecordBDInfoListBy = null;
        if (nameOrPhone == null || nameOrPhone == "") {
            return R.ok(selectReplaceBuyRecordBDInfoListBy);
        } else {
            ReplaceBuyRecordBDInfo replaceBuyRecordBDInfo = new ReplaceBuyRecordBDInfo();
            replaceBuyRecordBDInfo.setOldName(nameOrPhone);
            replaceBuyRecordBDInfo.setPhone(nameOrPhone);
            //查询老人所有信息
            selectReplaceBuyRecordBDInfoListBy = sosMessageInfoService.selectReplaceBuyRecordBDInfoListBy(replaceBuyRecordBDInfo);
            return R.ok(selectReplaceBuyRecordBDInfoListBy);
        }
    }

    /**
     * 实时定位-代买代办-已完成状态修改
     *
     * @param replaceBuyRecordId 代买代办ID
     */
    @ApiOperation("实时定位-代买代办-已完成状态修改")
    @PutMapping("/updateReplaceBuyRecordState")
    public R updateReplaceBuyRecordState(Integer replaceBuyRecordId) {
        if (replaceBuyRecordId == null) {
            return R.failed("replaceBuyRecordId不能为空");
        }
        int a = sosMessageInfoService.updateReplaceBuyRecordState(replaceBuyRecordId);
        if (a == 1) {
            return R.ok("成功");
        } else {
            return R.failed("失败");
        }

    }


    /**
     * 实时定位-送餐信息-当天的
     *
     * @param pageNo
     * @param pageSize
     * @return
     */
    @ApiOperation("实时定位-送餐信息")
    @GetMapping("/selectMealEcordTableInfoList")
    @Inner(value = false)
    public Map<String, Object> selectMealEcordTableInfoList(@RequestParam("pageNo") Integer pageNo, @RequestParam("pageSize") Integer pageSize, Integer type) {
        Map<String, Object> map = new HashMap<>();
        //类型
        Page<MealEcordTableInfo> page1 = sosMessageInfoService.selectMealEcordTableInfoList(new Page<MealEcordTableInfo>(pageNo, pageSize), type); //自定义方法，多表
        int total = (int) page1.getTotal();
        //条数
        map.put("total", total);
        //查询老人所有信息
        for (MealEcordTableInfo a : page1.getRecords()) {
            OldManInfo oldManInfo = oldManInfoService.lambdaQuery().eq(OldManInfo::getEnable, Constants.ENABLE_TRUE).eq(OldManInfo::getId, mealRecordService.getById(a.getMealRecordId()).getOldMan()).one();
            BuildingInfo buildingInfo = buildingInfoService.getById(oldManInfoService.getById(mealRecordService.getById(a.getMealRecordId()).getOldMan()).getBuilding());
            if (a.getAddress() == null || "".equals(a.getAddress())) {
                a.setAddress(buildingInfo.getCommunity() + "-" + buildingInfo.getBuildingNumber() + "-" + oldManInfo.getAddress());
                a.setLatitude(buildingInfo.getLat());
                a.setLongitude(buildingInfo.getLng());
            }
            /*  a.setBuildingName(a.getCommunity() + "-" + a.getBuildingNumber() + a.getAddress() + a.getMealEcordTableAddress());*/
            String b = a.getType().equals("1") ? "早餐" : a.getType().equals("2") ? "午餐" : a.getType().equals("3") ? "晚餐" : a.getType();
            a.setType(b);
        }
        map.put("list", page1.getRecords());
        //
        return map;
    }

    /**
     * 实时定位-送餐信息单独查询返回
     *
     * @param nameOrPhone 老人姓名或者手机号
     * @return
     */
    @ApiOperation("实时定位-送餐信息单独查询返回")
    @GetMapping("/selectMealEcordTableInfoListBy")
    public R selectMealEcordTableInfoListBy(String nameOrPhone) {
        //类型
        String type = null;
        List<MealEcordTableInfo> selectMealEcordTableInfoListBy = null;
        if (nameOrPhone == null || nameOrPhone == "") {
            return R.ok(selectMealEcordTableInfoListBy);
        } else {
            MealEcordTableInfo mealEcordTableInfo = new MealEcordTableInfo();
            mealEcordTableInfo.setOldName(nameOrPhone);
            mealEcordTableInfo.setPhone(nameOrPhone);
            //查询老人所有信息
            selectMealEcordTableInfoListBy = sosMessageInfoService.selectMealEcordTableInfoListBy(mealEcordTableInfo);
            for (MealEcordTableInfo a : selectMealEcordTableInfoListBy) {
                OldManInfo oldManInfo = oldManInfoService.lambdaQuery().eq(OldManInfo::getEnable, Constants.ENABLE_TRUE).eq(OldManInfo::getId, mealRecordService.getById(a.getMealRecordId()).getOldMan()).one();
                BuildingInfo buildingInfo = buildingInfoService.getById(oldManInfoService.getById(mealRecordService.getById(a.getMealRecordId()).getOldMan()).getBuilding());
                if (a.getAddress() == null || "".equals(a.getAddress())) {
                    a.setAddress(buildingInfo.getCommunity() + "-" + buildingInfo.getBuildingNumber() + "-" + oldManInfo.getAddress());
                    a.setLatitude(buildingInfo.getLat());
                    a.setLongitude(buildingInfo.getLng());
                }
                /* a.setBuildingName(a.getCommunity() + "-" + a.getBuildingNumber() + a.getAddress() + a.getMealEcordTableAddress());*/
                type = a.getType().equals("1") ? "早餐" : a.getType().equals("2") ? "午餐" : a.getType().equals("3") ? "晚餐" : a.getType();
                a.setType(type);
            }
            return R.ok(selectMealEcordTableInfoListBy);
        }
    }

    /**
     * 实时定位-送餐信息-已完成状态修改
     *
     * @param mealRecordId 订餐记录id
     */
    @ApiOperation("实时定位-送餐信息-已完成状态修改")
    @PutMapping("/updateMealRecordState")
    public R updateMealRecordState(Integer mealRecordId) {
        if (mealRecordId == null) {
            return R.failed("mealRecordId不能为空");
        }
        int a = sosMessageInfoService.updateMealRecordState(mealRecordId);
        if (a == 1) {
            return R.ok("成功");
        } else {
            return R.failed("失败");
        }

    }


}
