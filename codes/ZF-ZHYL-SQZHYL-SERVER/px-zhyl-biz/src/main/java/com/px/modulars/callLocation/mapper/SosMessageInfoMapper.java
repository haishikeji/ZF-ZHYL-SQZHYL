

package com.px.modulars.callLocation.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.px.modulars.callLocation.entity.*;
import org.apache.commons.net.nntp.Article;
import org.apache.ibatis.annotations.Mapper;
import com.px.basic.alone.core.base.BaseMapperImpl;
import org.apache.ibatis.annotations.Param;
import org.springframework.context.annotation.Primary;

import java.util.List;
import java.util.Map;

/**
 * 呼叫定位SOS信息
 *
 * @author 品讯科技
 * @date 2024-08
 */
@Mapper
public interface SosMessageInfoMapper extends BaseMapperImpl<SosMessageInfo> {

    /**
     * 实时定位-SOS呼叫信息
     */
    Page<SosUserInfo> selectSosUserInfoList(Page<MealEcordTableInfo> page,@Param("nameOrPhone") String nameOrPhone);

    /**
     * 实时定位-SOS呼叫信息单查
     */
    List<SosUserInfo> selectSosUserInfoListBy(@Param("nameOrPhone") String nameOrPhone);

    /**
     * 实时定位-老人详情
     */
    List<UserDetailInfo> selectUserDetailsLocusList();

    /**
     * 实时定位-老人详情单独查询返回
     */
    List<UserDetailInfo> selectUserDetailsLocusListBy(UserDetailInfo userDetailInfo);

    /**
     * 实时定位-代买代办
     */
   /* List<ReplaceBuyRecordBDInfo> selectReplaceBuyRecordBDInfoList();*/
    Page<ReplaceBuyRecordBDInfo> selectReplaceBuyRecordBDInfoList(Page<ReplaceBuyRecordBDInfo> page);

    /**
     * 实时定位-代买代办单独查询返回
     */
    List<ReplaceBuyRecordBDInfo> selectReplaceBuyRecordBDInfoListBy(ReplaceBuyRecordBDInfo replaceBuyRecordBDInfo);

    /**
     * 实时定位-送餐信息
     */
    Page<MealEcordTableInfo> selectMealEcordTableInfoList(Page<MealEcordTableInfo> page, @Param("type") Integer type);

    /**
     * 实时定位-送餐信息单独查询返回
     */
    List<MealEcordTableInfo> selectMealEcordTableInfoListBy(MealEcordTableInfo mealEcordTableInfo);

    /**
     * 实时定位-代买代办-已完成状态修改
     */
    int updateReplaceBuyRecordState(@Param("replaceBuyRecordId") Integer replaceBuyRecordId);

    /**
     * 实时定位-送餐信息-已完成状态修改
     */
    int updateMealRecordState(@Param("mealRecordId") Integer mealRecordId);

    /**
     *  实时定位-SOS呼叫信息-已完成状态修改
     */
    int updateSosMessageState(@Param("sosMessageId") Integer sosMessageId);

    //数量
    Map<String, Object> getQuantityVoMap();
}
