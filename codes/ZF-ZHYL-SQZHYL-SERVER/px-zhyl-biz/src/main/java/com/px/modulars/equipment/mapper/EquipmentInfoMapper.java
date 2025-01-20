

package com.px.modulars.equipment.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.px.modulars.equipment.entity.EquipmentInfo;
import org.apache.ibatis.annotations.Mapper;
import com.px.basic.alone.core.base.BaseMapperImpl;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 设备信息
 *
 * @author 品讯科技
 * @date 2024-08
 */
@Mapper
public interface EquipmentInfoMapper extends BaseMapperImpl<EquipmentInfo> {
    @Select("select a.* from equipment_info a " +
            "${ew.customSqlSegment}")
    Page<EquipmentInfo> getPage(Page page, @Param(Constants.WRAPPER) QueryWrapper queryWrapper);
}
