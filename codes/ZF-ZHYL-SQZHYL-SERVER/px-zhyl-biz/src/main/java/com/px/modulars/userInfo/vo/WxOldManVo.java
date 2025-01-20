package com.px.modulars.userInfo.vo;

import lombok.Data;

@Data
public class WxOldManVo {
    /**
     * 姓名
     */
    private String name;
    /**
     * 性别（1.男2.女）
     */
    private String sex;
    /**
     * 年龄
     */
    private String age;
    /**
     * 居住地址
     */
    private String address;
    /**
     * 手机号
     */
    private String phone;

    /**
     * 户籍地址
     */
    private String permanentAddress;
    /**
     * 所属街道
     */
    private String street;
}
