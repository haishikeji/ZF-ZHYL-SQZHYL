
package com.px.modulars.userInfo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.common.core.util.R;
import com.px.basic.alone.security.annotation.Inner;
import com.px.modulars.userInfo.entity.OldManInfo;
import com.px.modulars.userInfo.entity.OldPhrInfo;
import com.px.modulars.userInfo.service.OldManInfoService;
import com.px.modulars.userInfo.service.OldPhrInfoService;
import com.px.basic.alone.core.base.BaseController;
import com.px.common.log.annotation.SysLog;
import com.px.basic.alone.security.util.SecurityUtils;
import com.px.plugins.conversion.excel.util.ExcelBaseUtil;
import io.swagger.annotations.*;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.FileMagic;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;


/**
 * 老人健康档案
 *
 * @author 品讯科技
 * @date 2024-08
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/userInfo/oldphrinfo")
@Api(value = "oldphrinfo", tags = "老人健康档案管理")
public class OldPhrInfoController extends BaseController<OldPhrInfo, OldPhrInfoService> {

    private final OldPhrInfoService oldPhrInfoService;
    private final OldManInfoService oldManInfoService;

    /**
     * 分页查询
     *
     * @param page       分页对象
     * @param oldPhrInfo 老人健康档案
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page")
    @PreAuthorize("@pms.hasPermission('userInfo_oldphrinfo_get')")
    public R getOldPhrInfoPage(Page page, OldPhrInfo oldPhrInfo) {
        Page<OldPhrInfo> oldPhrInfoPage = oldPhrInfoService.oldPhrInfoPage(page, oldPhrInfo);
    /*    Page<OldPhrInfo> oldPhrInfoPage = oldPhrInfoService.lambdaQuery().eq(OldPhrInfo::getEnable, Constants.ENABLE_TRUE)
                .like(StringUtils.isNotEmpty(oldPhrInfo.getName()), OldPhrInfo::getName, oldPhrInfo.getName())
                .eq(StringUtils.isNotEmpty(oldPhrInfo.getSex()), OldPhrInfo::getSex, oldPhrInfo.getSex())
                .page(page);*/
        return R.ok(oldPhrInfoPage);
    }


    /**
     * 通过id查询老人健康档案
     *
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}")
    @PreAuthorize("@pms.hasPermission('userInfo_oldphrinfo_get')")
    public R getById(@PathVariable("id") Integer id) {
        return super.get(id);
    }

    /**
     * 新增老人健康档案
     *
     * @param oldPhrInfo 老人健康档案
     * @return R
     */
    @ApiOperation(value = "新增老人健康档案", notes = "新增老人健康档案")
    @PostMapping
    @SysLog("新增老人健康档案")
    @PreAuthorize("@pms.hasPermission('userInfo_oldphrinfo_add')")
    public R save(@Validated @RequestBody OldPhrInfo oldPhrInfo) {
        System.out.println(SecurityUtils.getUser().getId());
        return super.update(oldPhrInfo, SecurityUtils.getUser().getId());
    }

    /**
     * 修改老人健康档案
     *
     * @param oldPhrInfo 老人健康档案
     * @return R
     */
    @ApiOperation(value = "修改老人健康档案", notes = "修改老人健康档案")
    @PutMapping
    @SysLog("修改老人健康档案")
    @PreAuthorize("@pms.hasPermission('userInfo_oldphrinfo_edit')")
    public R updateById(@Validated @RequestBody OldPhrInfo oldPhrInfo) {
        return super.update(oldPhrInfo, SecurityUtils.getUser().getId());
    }

    /**
     * 通过id删除老人健康档案
     *
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除老人健康档案", notes = "通过id删除老人健康档案")
    @DeleteMapping("/{id}")
    @SysLog("通过id删除老人健康档案")
    @PreAuthorize("@pms.hasPermission('userInfo_oldphrinfo_del')")
    public R removeById(@PathVariable Integer id) {
        OldPhrInfo oldPhrInfo = super.service.queryById(id);
        if (oldPhrInfo == null) {
            return R.failed("ID错误");
        }
        return super.del(oldPhrInfo, SecurityUtils.getUser().getId());
    }


    /**
     * 接收老人健康档案信息
     */
    @PostMapping("/oldPhr_CellBack")
    @ApiOperation("接收老人健康档案信息")
    @Inner(value = false)
    public String oldPhr_CellBack(@Validated @RequestBody String message, HttpServletRequest request, HttpServletResponse response) {
        try {
            JSONObject jsonObject = JSONObject.parseObject(message);
            //转化成实体类
            OldPhrInfo oldPhrInfo = JSON.toJavaObject(jsonObject, OldPhrInfo.class);
            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime time = LocalDateTime.now();
            String localTime = df.format(time);
            LocalDateTime ldt = LocalDateTime.parse(localTime, df);
            oldPhrInfo.setCreateTime(ldt);
            OldManInfo oldManInfo = oldManInfoService.lambdaQuery().eq(OldManInfo::getSex, oldPhrInfo.getUsercode()).one();
            if (oldManInfo != null) {
                oldPhrInfo.setOldId(oldManInfo.getId());
            }
            oldPhrInfoService.save(oldPhrInfo);
            return "{  \"order\": \"answer\",\"result\": \"true\"}";
        } catch (Exception e) {
            return "{  \"order\": \"answer\",\"result\": \"false\"}";
        }


    }

    /**
     * 导出模板
     */
    @GetMapping("/exportExcle")
    @ApiOperation("导出老人健康档案信息模板")
    @Inner(value = false)
    public void exportExcle(HttpServletRequest request, HttpServletResponse response) {
        //创建HSSFWorkbook对象(excel的文档对象)
        HSSFWorkbook wb = new HSSFWorkbook();
        //建立新的sheet对象（excel的表单）
        HSSFSheet sheet = wb.createSheet("老人健康档案表");
        //在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个
        HSSFRow row1 = sheet.createRow(0);
        //创建单元格（excel的单元格，参数为列索引，可以是0～255之间的任何一个
        /*   HSSFCell cell = row1.createCell(0);*/
        //设置单元格内容
        HSSFCellStyle setBorder = wb.createCellStyle();
        /*  cell.setCellValue("老人健康档案表一览表");*/
        HSSFFont font = (HSSFFont) wb.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 20);
        //居中
        setBorder.setAlignment(HorizontalAlignment.CENTER);
        setBorder.setVerticalAlignment(VerticalAlignment.CENTER);
        setBorder.setFont(font);
        Row row0 = sheet.createRow(0);
        row0.setHeightInPoints(24);
        row0.setRowStyle(setBorder);
        row0.createCell(0).setCellValue("老人健康档案表");
        row0.getCell(0).setCellStyle(setBorder);

        //合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 23));
        //在sheet里创建第二行
        HSSFRow row2 = sheet.createRow(1);
        //创建单元格并设置单元格内容
        row2.createCell(0).setCellValue("测量时间");
        row2.createCell(1).setCellValue("客户代码");
        row2.createCell(2).setCellValue("客户姓名");
        row2.createCell(3).setCellValue("客户性别");
        row2.createCell(4).setCellValue("客户年龄");
        row2.createCell(5).setCellValue("备注信息");
        row2.createCell(6).setCellValue("身高");
        row2.createCell(7).setCellValue("体重");
        row2.createCell(8).setCellValue("高压");
        row2.createCell(9).setCellValue("低压");
        row2.createCell(10).setCellValue("心率");
        row2.createCell(11).setCellValue("脂肪率");
        row2.createCell(12).setCellValue("脂肪量");
        row2.createCell(13).setCellValue("基础代谢");
        row2.createCell(14).setCellValue("体水分率");
        row2.createCell(15).setCellValue("总水分");
        row2.createCell(16).setCellValue("去脂体重");
        row2.createCell(17).setCellValue("骨骼肌率");
        row2.createCell(18).setCellValue("蛋白质");
        row2.createCell(19).setCellValue("无机盐");
        row2.createCell(20).setCellValue("内脏脂肪");
        row2.createCell(21).setCellValue("骨矿含量");
        row2.createCell(22).setCellValue("体温");
        row2.createCell(23).setCellValue("血氧饱和度");
        row2.setHeightInPoints(17);//设置行高
        //.....省略部分代码
        //输出Excel文件

        OutputStream output = null;
        try {
            output = response.getOutputStream();
            response.reset();
            response.setHeader("Content-Disposition", "attachment; filename=" + java.net.URLEncoder.encode("老人健康档案表.xls", "UTF-8"));
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            wb.write(output);
            output.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }

    /**
     * 导入
     */
    @Inner(value = false)
    @PostMapping("/importExcle")
    @ApiOperation(value = "导入健康档案记录")
    public R importExcle(@RequestParam MultipartFile file) {
        if (file.isEmpty()) {
            return R.failed("文件内容不能为空");
        }
        try {
            if (isExcelFile(file.getInputStream())) {
                //框架自带的导入导出
                List<OldPhrInfo> dtoList = ExcelBaseUtil.importExcel(file, 0, 2, OldPhrInfo.class);
                //TODO 入库的代码自行补充
                for (OldPhrInfo a : dtoList) {
                    DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    LocalDateTime time = LocalDateTime.now();
                    String localTime = df.format(time);
                    LocalDateTime ldt = LocalDateTime.parse(localTime, df);
                    a.setCreateTime(ldt);
                    oldPhrInfoService.save(a);
                }
            } else {
                return R.failed("请注意上传文件的格式");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return R.ok("导入成功");
    }

    //以魔数的形式判断上传的文件是否为xlsx
    public static Boolean isExcelFile(InputStream inputStream) {
        Boolean resulf = false;
        try {
            FileMagic fileMagic = FileMagic.valueOf(inputStream);
            if (Objects.equals(fileMagic, FileMagic.OLE2) || Objects.equals(fileMagic, FileMagic.OOXML)) {
                resulf = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resulf;
    }

}
