package com.px.modulars.replace.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.px.modulars.replace.entity.ReplaceBuy;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Date;

@Data
public class ReplaceExcelVo {
    @Excel(name = "代买代办类型",width =12)
    private String  replaceBuyName;

    @Excel(name = "代买代办说明",width =20)
    private String instructions;
    @Excel(name = "最后时限",width =20)
    private String endTimedate;
    @Excel(name = "所在楼宇",width =20)
    private String  buildingInfoName;
    @Excel(name = "老人姓名",width =20)
    private String oldName;
    @Excel(name = "联系方式",width =20)
    private String phone;
    @Excel(name = "完成状态",width =20)
    private String stateName;
    @Excel(name = "创建时间",width =20)
    private String createTimedate;


}
