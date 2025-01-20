

package com.px.modulars.userInfo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.px.basic.alone.core.base.BaseModel;

import javax.validation.constraints.*;

/**
 * 认证信息表
 *
 * @author 品讯科技
 * @date 2024-08
 */
@Data
@TableName("certification_info")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "认证信息表")
public class CertificationInfo extends BaseModel<CertificationInfo> {
    private static final long serialVersionUID = 1L;

    /**
     * 姓名
     */
    @Size(max = 255, message = "姓名超出长度255的限制")
    @ApiModelProperty(value = "姓名")
    private String name;
    /**
     * 身份证号
     */
    @Size(max = 255, message = "身份证号超出长度255的限制")
    @ApiModelProperty(value = "身份证号")
    private String idCard;
    /**
     * 手机号
     */
    @Size(max = 255, message = "手机号超出长度255的限制")
    @ApiModelProperty(value = "手机号")
    private String phone;
    /**
     * 与老人关系
     */
    @Size(max = 255, message = "与老人关系超出长度255的限制")
    @ApiModelProperty(value = "与老人关系")
    private String relationship;
    /**
     * 老人姓名
     */
    @Size(max = 255, message = "老人姓名超出长度255的限制")
    @ApiModelProperty(value = "老人姓名")
    private String oldName;
    /**
     * 老人手机号
     */
    @Size(max = 255, message = "老人手机号超出长度255的限制")
    @ApiModelProperty(value = "老人手机号")
    private String oldPhone;
    /**
     * 老人信息id
     */
    @Size(max = 255, message = "老人信息id超出长度255的限制")
    @ApiModelProperty(value = "老人信息id")
    private Integer oldManInfo;
    /**
     * 认证用户id
     */
    @Size(max = 20, message = "认证用户id超出长度20的限制")
    @ApiModelProperty(value = "认证用户id")
    private Long userId;
    /**
     * 认证状态（1.待认证2.认证通过3.认证不通过4.取消绑定）
     */
    @Size(max = 4, message = "认证状态（1.待认证2.认证通过3.认证不通过4.取消绑定）超出长度4的限制")
    @ApiModelProperty(value = "认证状态（1.待认证2.认证通过3.认证不通过4.取消绑定）")
    private Integer state;
    /**
     * 认证状态原因
     */
    @Size(max = 255, message = "认证状态原因超出长度255的限制")
    @ApiModelProperty(value = "认证状态原因")
    private String reason;

    /**
     * 老人身份证号
     */
    @ApiModelProperty(value = "老人身份证号")
    private String oldCard;
}
