

package com.px.modulars.meal.mapper;

import com.px.basic.alone.core.base.BaseMapperImpl;
import com.px.modulars.meal.entity.MealInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 配餐计划信息
 *
 * @author 品讯科技
 * @date 2024-08
 */
@Mapper
public interface MealInfoMapper extends BaseMapperImpl<MealInfo> {

     List<MealInfo> getMealInfoList();

}
