

package com.px.modulars.upms.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.px.basic.alone.core.base.BaseModel;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 关键词过滤
 *
 * @author 品讯科技
 * @date 2024-08
 */
@Data
@TableName("sys_prohibite_word")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "关键词过滤")
public class SysProhibiteWord extends BaseModel<SysProhibiteWord> {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    /**
     * 类型
     */
    @Size(max = 200, message = "类型超出长度200的限制")
    @ApiModelProperty(value = "类型")
    private String skey;
    /**
     * 过滤词
     */
    @Size(max = 200, message = "过滤词超出长度200的限制")
    @ApiModelProperty(value = "过滤词")
    private String word;
}
