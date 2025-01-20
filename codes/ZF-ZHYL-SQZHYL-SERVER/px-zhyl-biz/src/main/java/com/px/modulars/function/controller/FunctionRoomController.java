
package com.px.modulars.function.controller;

import cn.hutool.core.io.FileUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.common.core.constant.Constants;
import com.pig4cloud.pig.common.core.util.R;
import com.px.basic.alone.security.annotation.Inner;
import com.px.fastfile.service.FastfileService;
import com.px.modulars.function.entity.FunctionRoom;
import com.px.modulars.function.service.FunctionRoomService;
import com.px.basic.alone.core.base.BaseController;
import com.px.common.log.annotation.SysLog;
import com.px.basic.alone.security.util.SecurityUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ws.schild.jave.MultimediaInfo;
import ws.schild.jave.MultimediaObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * 功能室基本信息
 *
 * @author 品讯科技
 * @date 2024-08
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/functionRoom/functionroom")
@Api(value = "functionroom", tags = "功能室基本信息管理")
public class FunctionRoomController extends BaseController<FunctionRoom, FunctionRoomService> {

    private final FunctionRoomService functionRoomService;

    @Autowired
    private FastfileService fastfileService;

    /**
     * 分页查询
     *
     * @param page         分页对象
     * @param functionRoom 功能室基本信息
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page")
    @PreAuthorize("@pms.hasPermission('functionRoom_functionroom_get')")
    public R getFunctionRoomPage(Page page, FunctionRoom functionRoom) {
        Page<FunctionRoom> functionRoomPage = functionRoomService.lambdaQuery().eq(FunctionRoom::getEnable, Constants.ENABLE_TRUE)
                .like(StringUtils.isNotEmpty(functionRoom.getTitle()), FunctionRoom::getTitle, functionRoom.getTitle())
                .eq(functionRoom.getRoomClass() != null, FunctionRoom::getRoomClass, functionRoom.getRoomClass())
                .orderByDesc(FunctionRoom::getCreateTime).page(page);
        return R.ok(functionRoomPage);
    }


    /**
     * 通过id查询功能室基本信息
     *
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}")
    @PreAuthorize("@pms.hasPermission('functionRoom_functionroom_get')")
    public R getById(@PathVariable("id") Integer id) {
        return super.get(id);
    }

    /**
     * 新增功能室基本信息
     *
     * @param functionRoom 功能室基本信息
     * @return R
     */
    @ApiOperation(value = "新增功能室基本信息", notes = "新增功能室基本信息")
    @PostMapping
    @SysLog("新增功能室基本信息")
    @PreAuthorize("@pms.hasPermission('functionRoom_functionroom_add')")
    public R save(@RequestBody FunctionRoom functionRoom) {
        return super.update(functionRoom, SecurityUtils.getUser().getId());
    }

    /**
     * 修改功能室基本信息
     *
     * @param functionRoom 功能室基本信息
     * @return R
     */
    @ApiOperation(value = "修改功能室基本信息", notes = "修改功能室基本信息")
    @PutMapping
    @SysLog("修改功能室基本信息")
    @PreAuthorize("@pms.hasPermission('functionRoom_functionroom_edit')")
    public R updateById(@RequestBody FunctionRoom functionRoom) {
        return super.update(functionRoom, SecurityUtils.getUser().getId());
    }

    /**
     * 通过id删除功能室基本信息
     *
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除功能室基本信息", notes = "通过id删除功能室基本信息")
    @DeleteMapping("/{id}")
    @SysLog("通过id删除功能室基本信息")
    @PreAuthorize("@pms.hasPermission('functionRoom_functionroom_del')")
    public R removeById(@PathVariable Integer id) {
        FunctionRoom functionRoom = super.service.queryById(id);
        if (functionRoom == null) {
            return R.failed("ID错误");
        }
        return super.del(functionRoom, SecurityUtils.getUser().getId());
    }


    @ApiOperation(value = "上传文件，根据key去找对应的配置文件")
    @PostMapping(value = "/fastfile/upload/{key}")
    @Inner(value = false)
    public Object imageUp(@RequestParam MultipartFile file, HttpServletRequest request, HttpServletResponse response, @PathVariable("key") String key) {
        Map<String, Object> upResult = this.fastfileService.uploadFile(key, file);
        return R.ok(upResult);
    }

    @ApiOperation(value = "小程序页面列表")
    @GetMapping(value = "/wx/list")
    @Inner(value = false)
    public Object getFunctionRoomList(@RequestParam("pageNo") Integer pageNo) {

        return R.ok();
    }

}
