

package com.px.modulars.upms.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 部门关系表
 * </p>
 *
 * @author 品讯科技
 * @since 2019/2/1
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysDeptRelation extends Model<com.px.modulars.upms.entity.SysDeptRelation> {

	private static final long serialVersionUID = 1L;

	/**
	 * 祖先节点
	 */
	@ApiModelProperty(value = "祖先节点")
	private Integer ancestor;

	/**
	 * 后代节点
	 */
	@ApiModelProperty(value = "后代节点")
	private Integer descendant;

}
