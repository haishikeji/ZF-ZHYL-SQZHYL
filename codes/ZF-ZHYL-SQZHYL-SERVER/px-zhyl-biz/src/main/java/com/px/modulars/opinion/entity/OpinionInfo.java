

package com.px.modulars.opinion.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.px.basic.alone.core.base.BaseModel;

import javax.validation.constraints.*;

/**
 * 征集意见
 *
 * @author 品讯科技
 * @date 2024-08
 */
@Data
@TableName("opinion_info")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "征集意见")
public class OpinionInfo extends BaseModel<OpinionInfo> {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @Size(max = 11, message = "超出长度11的限制")
    @ApiModelProperty(value = "")
    private Long allUserId;
    /**
     * 意见建议
     */
    @ApiModelProperty(value = "意见建议")
    private String opinion;
    /**
     * 附件
     */
    @ApiModelProperty(value = "附件")
    private String attachment;
    /**
     * 是否被读（1.已读2.未读）
     */
    @Size(max = 4, message = "是否被读（1.已读2.未读）超出长度4的限制")
    @ApiModelProperty(value = "是否被读（1.已读2.未读）")
    private Integer isRead;
    @ApiModelProperty(value = "附件视频")
    private String video;
}
