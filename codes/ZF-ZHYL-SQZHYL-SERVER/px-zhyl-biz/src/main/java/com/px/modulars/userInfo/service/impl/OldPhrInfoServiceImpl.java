package com.px.modulars.userInfo.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.px.modulars.userInfo.entity.OldPhrInfo;
import com.px.modulars.userInfo.mapper.OldPhrInfoMapper;
import com.px.modulars.userInfo.service.OldPhrInfoService;
import com.px.basic.alone.core.base.BaseServiceImpl;
import com.px.modulars.userInfo.vo.WxOldPhrVo;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 老人健康档案
 *
 * @author 品讯科技
 * @date 2024-08
 */
@Service
public class OldPhrInfoServiceImpl extends BaseServiceImpl<OldPhrInfo, OldPhrInfoMapper> implements OldPhrInfoService {
    @Autowired
    private OldPhrInfoMapper oldPhrInfoMapper;

    @Override
    public Page<OldPhrInfo> oldPhrInfoPage(Page<OldPhrInfo> page, OldPhrInfo oldPhrInfo) {
        return oldPhrInfoMapper.oldPhrInfoPage(page, oldPhrInfo);
    }

    @Override
    public WxOldPhrVo oldPhrInfoGetOne(Integer id) {
        return oldPhrInfoMapper.oldPhrInfoGetOne(id);
    }


}
