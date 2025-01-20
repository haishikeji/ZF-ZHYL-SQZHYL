package com.px.modulars.device.vo;

import lombok.Data;

/**
 * 饼状图
 */
@Data
public class AccountedEntity {

    //标题
    private String nameTitle;
    //总数量
    private String conut;
    //所占数量
    private String getcount;
    //占比
    private String accounted;
}
