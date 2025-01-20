
package com.px.modulars.upms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.px.modulars.upms.entity.SysDictItem;
import com.pig4cloud.pig.common.core.util.R;

/**
 * 字典项
 *
 * @author 品讯科技
 * @date 2024-08
 */
public interface SysDictItemService extends IService<SysDictItem> {

	/**
	 * 删除字典项
	 * @param id 字典项ID
	 * @return
	 */
	R removeDictItem(Integer id);

	/**
	 * 更新字典项
	 * @param item 字典项
	 * @return
	 */
	R updateDictItem(SysDictItem item);

}
