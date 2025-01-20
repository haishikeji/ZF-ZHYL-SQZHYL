package com.px.modulars.equipment.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.px.basic.alone.core.base.BaseService;
import com.px.modulars.equipment.entity.EquipmentInfo;

/**
 * 设备信息
 *
 * @author 品讯科技
 * @date 2024-08
 */
public interface EquipmentInfoService extends IService<EquipmentInfo>, BaseService<EquipmentInfo> {
    Page<EquipmentInfo> getPage(Page page, EquipmentInfo equipmentInfo);
}
