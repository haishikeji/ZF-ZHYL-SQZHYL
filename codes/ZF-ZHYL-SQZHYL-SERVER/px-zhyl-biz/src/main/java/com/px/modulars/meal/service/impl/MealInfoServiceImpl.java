package com.px.modulars.meal.service.impl;


import com.px.basic.alone.core.base.BaseServiceImpl;
import com.px.modulars.meal.entity.MealInfo;
import com.px.modulars.meal.mapper.MealInfoMapper;
import com.px.modulars.meal.service.MealInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 配餐计划信息
 *
 * @author 品讯科技
 * @date 2024-08
 */
@Service
public class MealInfoServiceImpl extends BaseServiceImpl<MealInfo, MealInfoMapper> implements MealInfoService {

    @Autowired
    private MealInfoMapper mealInfoMapper;
    @Override
    public List<MealInfo> getMealInfoList() {
        return mealInfoMapper.getMealInfoList();
    }
}
