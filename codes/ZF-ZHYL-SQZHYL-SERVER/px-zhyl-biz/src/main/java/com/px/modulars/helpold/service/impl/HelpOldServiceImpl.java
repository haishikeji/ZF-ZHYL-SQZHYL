package com.px.modulars.helpold.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.px.modulars.helpold.entity.HelpOld;
import com.px.modulars.helpold.mapper.HelpOldMapper;
import com.px.modulars.helpold.service.HelpOldService;
import com.px.basic.alone.core.base.BaseServiceImpl;
import com.px.modulars.upms.dto.DeptTree;
import com.px.modulars.upms.service.SysDeptRelationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 智慧助老
 *
 * @author 品讯科技
 * @date 2024-08
 */
@Service
public class HelpOldServiceImpl extends BaseServiceImpl<HelpOld, HelpOldMapper> implements HelpOldService {

    @Autowired
    private HelpOldMapper helpOldMapper;


    /**
     * 查询全部智慧助老数据
     *
     * @return 数据
     */
    @Override
    public List<HelpOld> selectHelpoldList() {
        return helpOldMapper.selectHelpoldList();
    }
}
