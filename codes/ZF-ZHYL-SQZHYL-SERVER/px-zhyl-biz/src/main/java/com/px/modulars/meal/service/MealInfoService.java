package com.px.modulars.meal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.px.basic.alone.core.base.BaseService;
import com.px.modulars.meal.entity.MealInfo;

import java.util.List;

/**
 * 配餐计划信息
 *
 * @author 品讯科技
 * @date 2024-08
 */
public interface MealInfoService extends IService<MealInfo>, BaseService<MealInfo> {


   public List<MealInfo> getMealInfoList();
}
