package com.px.modulars.device.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.px.basic.alone.core.base.BaseService;
import com.px.modulars.build.entity.BuildingInfo;
import com.px.modulars.callLocation.entity.MealEcordTableInfo;
import com.px.modulars.callLocation.entity.SosMessageInfo;
import com.px.modulars.device.entity.DeviceInfo;
import com.px.modulars.device.vo.AccountedEntity;
import com.px.modulars.device.vo.CurveEntity;
import com.px.modulars.userInfo.entity.OldManInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * 设备信息
 *
 * @author 品讯科技
 * @date 2024-08
 */
public interface DeviceInfoService extends IService<DeviceInfo>, BaseService<DeviceInfo> {

   /* List<OldManInfo> selectOldManList(OldManInfo oldManInfo); */

    Page<OldManInfo> selectOldManList(Page<OldManInfo> page,OldManInfo oldManInfo);

    List<DeviceInfo> selectDeviceInfoById(DeviceInfo deviceInfo);

    List<BuildingInfo> selectBuildingList(BuildingInfo buildingInfo);

    //修改设备状态
    int updateDeviceStatus(DeviceInfo deviceInfo);

    //拿到所有设备信息
    List<DeviceInfo> selectDeviceInfo();

    //根据设备编号查询老人ID
    DeviceInfo selectDeviceInfoByOldId(DeviceInfo deviceInfo);

    //查询设备编号唯一
    DeviceInfo selectDeviceInfoByDeviceNumber(DeviceInfo deviceInfo);

    //查询ID
    DeviceInfo deviceInfoById(Integer id);

    //拿到没有绑定设备的老人信息进行绑定
    List<OldManInfo> selectOldManInfo();

    //添加SOS消息
    int saveSosMessageInfo(SosMessageInfo sosMessageInfo);

    //解绑
    int contactBinding(Integer deviceId);

    //首页数据总览
    Map<String, Object> getOverviewMap();

    //曲线-订餐信息
    List<CurveEntity> getTogetherfor();

    //曲线-订餐信息
    List<CurveEntity> getAgents();

    //饼状图-楼宇分布
    List<AccountedEntity> getDistribution(Integer timeType);

    //饼状图-年龄占比
    List<AccountedEntity> getAgeInfo(Integer timeType);

    //柱状图-年龄占比
    List<AccountedEntity> getAgeInfoz(Integer timeType);
}
