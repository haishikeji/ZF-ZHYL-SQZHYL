
package com.px.modulars.demo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.px.basic.alone.core.base.BaseModel;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 测试
 *
 * @author 品讯科技
 * @date 2024-08
 */
@Data
@TableName("demo_info")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "测试")
public class DemoInfo extends BaseModel<DemoInfo> {
private static final long serialVersionUID = 1L;

    /**
     * ID
     */
        /**
     * 
     */
            @ApiModelProperty(value="")
        private String name;
        /**
     * 创建时间
     */
            @ApiModelProperty(value="创建时间")
        private LocalDateTime createTime;
        /**
     * 创建人
     */
            @ApiModelProperty(value="创建人")
        private Integer createBy;
        /**
     * 修改时间
     */
            @ApiModelProperty(value="修改时间")
        private LocalDateTime updateTime;
        /**
     * 修改人
     */
            @ApiModelProperty(value="修改人")
        private Integer updateBy;
        /**
     * 逻辑删除标记
     */
        /**
     * 排序号
     */
        /**
     * 备注
     */
        }
