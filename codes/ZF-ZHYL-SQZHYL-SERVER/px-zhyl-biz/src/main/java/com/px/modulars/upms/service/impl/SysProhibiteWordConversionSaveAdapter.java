package com.px.modulars.upms.service.impl;

import cn.hutool.core.thread.ThreadUtil;
import com.px.modulars.upms.entity.SysProhibiteWord;
import com.px.modulars.upms.service.SysProhibiteWordService;
import com.px.plugins.conversion.api.adapter.AbsFileSaveDbAdapter;
import com.px.plugins.conversion.core.adapter.AbsFileSaveAdapter;
import com.px.plugins.conversion.core.entity.ConversionTaskEntity;
import com.px.plugins.conversion.core.vo.SaveErrorsResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 关键字数据导入的结果适配器
 *
 * @author 品讯科技
 * @date 2024-08
 * @Description
 */
@Component(value = "prohibiteWord")
public class SysProhibiteWordConversionSaveAdapter extends AbsFileSaveDbAdapter<SysProhibiteWord> {
    @Autowired
    private SysProhibiteWordService prohibiteWordService;

    @Override
    protected ConversionTaskEntity start(ConversionTaskEntity conversionTaskEntity) {
        return conversionTaskEntity;
    }

    @Override
    protected void end(ConversionTaskEntity conversionTaskEntity) {

    }

    @Override
    protected SaveErrorsResult saveData(List<SysProhibiteWord> list, String s, Map<String, Object> map) {
        SaveErrorsResult result = new SaveErrorsResult();
        for (SysProhibiteWord item : list) {
            if (s != null) {
                item.setCreateBy(Integer.parseInt(s));
            }

            //TODO 如果有其他需求，存储其他信息
            //TODO 验证参数的其他合法性
            try {
                this.prohibiteWordService.update(item);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return result;
    }

    @Override
    protected String getKey() {
        return "prohibiteWord";
    }

    @Override
    protected Class<SysProhibiteWord> getClassT() {
        return SysProhibiteWord.class;
    }
}
