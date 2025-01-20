
package com.px.modulars.upms.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.px.modulars.upms.entity.SysDict;
import com.px.modulars.upms.entity.SysDictItem;
import com.px.modulars.upms.mapper.SysDictItemMapper;
import com.px.modulars.upms.mapper.SysDictMapper;
import com.px.modulars.upms.service.SysDictService;
import com.pig4cloud.pig.common.core.constant.CacheConstants;
import com.pig4cloud.pig.common.core.constant.enums.DictTypeEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * 字典表
 *
 * @author 品讯科技
 * @date 2024-08
 */
@Service
@RequiredArgsConstructor
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper, SysDict> implements SysDictService {

	private final SysDictItemMapper dictItemMapper;

	/**
	 * 根据ID 删除字典
	 * @param id 字典ID
	 * @return
	 */
	@Override
	@CacheEvict(value = CacheConstants.DICT_DETAILS, allEntries = true)
	@Transactional(rollbackFor = Exception.class)
	public void removeDict(Integer id) {
		SysDict dict = this.getById(id);
		// 系统内置
		Assert.state(!DictTypeEnum.SYSTEM.getType().equals(dict.getSystem()), "系统内置字典项目不能删除");
		baseMapper.deleteById(id);
		dictItemMapper.delete(Wrappers.<SysDictItem>lambdaQuery().eq(SysDictItem::getDictId, id));
	}

	/**
	 * 更新字典
	 * @param dict 字典
	 * @return
	 */
	@Override
	public void updateDict(SysDict dict) {
		SysDict sysDict = this.getById(dict.getId());
		// 系统内置
		Assert.state(!DictTypeEnum.SYSTEM.getType().equals(sysDict.getSystem()), "系统内置字典项目不能修改");
		this.updateById(dict);
	}

}
