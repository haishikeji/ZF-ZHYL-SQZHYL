

package com.px.modulars.helpold.mapper;

import com.px.modulars.helpold.entity.HelpOld;
import org.apache.ibatis.annotations.Mapper;
import com.px.basic.alone.core.base.BaseMapperImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * 智慧助老
 *
 * @author 品讯科技
 * @date 2024-08
 */
@Mapper
public interface HelpOldMapper extends BaseMapperImpl<HelpOld> {
    /**
     * 查询全部智慧助老数据
     * @return 数据
     */
    List<HelpOld> selectHelpoldList();

}
