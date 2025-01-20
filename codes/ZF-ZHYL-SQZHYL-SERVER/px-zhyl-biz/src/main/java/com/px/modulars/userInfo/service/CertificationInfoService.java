package com.px.modulars.userInfo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.px.apis.device.vo.OldManHealthVo;
import com.px.basic.alone.core.base.BaseService;
import com.px.modulars.userInfo.entity.CertificationInfo;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 认证信息表
 *
 * @author 品讯科技
 * @date 2024-08
 */
public interface CertificationInfoService extends IService<CertificationInfo>, BaseService<CertificationInfo> {
    public void Certification(Integer id);

    /**
     * 修改认证状态
     * @param id
     * @param enable
     * @param state
     * @return
     */
    int updateCertificationState(Integer id, Integer enable,Integer state);


    Page<OldManHealthVo> getOldManHealthPage(Page page, Map map);
}
