

package com.px.modulars.upms.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.px.basic.alone.core.base.BaseModel;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 系统APP版本日志
 *
 * @author 品讯科技
 * @date 2024-08
 */
@Data
@TableName("sys_app_version")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "系统APP版本日志")
public class SysAppVersion extends BaseModel<SysAppVersion> {
    private static final long serialVersionUID = 1L;

    /**
     * 版本号
     */
    @Size(max = 255, message = "版本号超出长度255的限制")
    @ApiModelProperty(value = "版本号")
    private String version;
    /**
     * 更新内容
     */
    @Size(max = 255, message = "更新内容超出长度255的限制")
    @ApiModelProperty(value = "更新内容")
    private String content;
    /**
     * 所属appid
     */
    @ApiModelProperty(value = "所属appid")
    private Integer aid;
}
