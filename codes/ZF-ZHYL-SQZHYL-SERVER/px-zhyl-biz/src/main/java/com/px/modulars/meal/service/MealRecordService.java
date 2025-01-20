package com.px.modulars.meal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.px.basic.alone.core.base.BaseService;
import com.px.modulars.meal.entity.MealRecord;

import java.util.Map;

/**
 * 订餐记录
 *
 * @author 品讯科技
 * @date 2024-08
 */
public interface MealRecordService extends IService<MealRecord>, BaseService<MealRecord> {

    public Map<String,Object> mealRecord(Integer type,Integer mealId,Long uid,String address, String lng, String lat);


    /**
     * 设备订餐
     */
    public Map<String, Object> equipmentMeal(Integer oldId);

    /**
     * 订餐模板消息通知
     */
    public void mealMsg(Integer mealIdRecord);
}
