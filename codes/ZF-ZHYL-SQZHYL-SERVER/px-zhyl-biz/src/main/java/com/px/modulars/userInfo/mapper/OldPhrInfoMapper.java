

package com.px.modulars.userInfo.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.px.modulars.callLocation.entity.MealEcordTableInfo;
import com.px.modulars.userInfo.entity.OldPhrInfo;
import com.px.modulars.userInfo.vo.WxOldPhrVo;
import org.apache.ibatis.annotations.Mapper;
import com.px.basic.alone.core.base.BaseMapperImpl;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

/**
 * 老人健康档案
 *
 * @author 品讯科技
 * @date 2024-08
 */
@Mapper
public interface OldPhrInfoMapper extends BaseMapperImpl<OldPhrInfo> {

    Page<OldPhrInfo> oldPhrInfoPage(Page<OldPhrInfo> page, @Param("oldPhrInfo") OldPhrInfo oldPhrInfo);

    WxOldPhrVo oldPhrInfoGetOne(@Param("id") Integer id);

}
