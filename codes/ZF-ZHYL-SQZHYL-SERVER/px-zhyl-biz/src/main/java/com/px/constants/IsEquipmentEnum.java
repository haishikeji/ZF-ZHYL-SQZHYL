package com.px.constants;

import com.pig4cloud.pig.common.core.constant.enums.BaseEnum;

/**
 *
 *
 * @author 品讯科技
 */
public enum IsEquipmentEnum implements BaseEnum {

    IS_EQUIPMENT_ENUM_ONE(1, "是"),
    IS_EQUIPMENT_ENUM_TWO(2, "否");


    private Integer value;
    private String desc;

    private IsEquipmentEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public Integer getValue() {
        return this.value;
    }

    @Override
    public String getDesc() {
        return this.desc;
    }
}
