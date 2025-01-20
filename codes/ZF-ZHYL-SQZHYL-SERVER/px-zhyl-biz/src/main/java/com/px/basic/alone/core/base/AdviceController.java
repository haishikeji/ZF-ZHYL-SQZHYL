package com.px.basic.alone.core.base;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//


import com.alibaba.fastjson.JSON;
import com.pig4cloud.pig.common.core.exception.BaseException;
import com.pig4cloud.pig.common.core.exception.IllegalParameterException;
import com.pig4cloud.pig.common.core.exception.LoginException;
import com.pig4cloud.pig.common.core.support.DateFormat;
import com.pig4cloud.pig.common.core.support.context.StringEscapeEditor;
import com.pig4cloud.pig.common.core.support.http.HttpCode;
import com.pig4cloud.pig.common.core.util.PropertiesUtil;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;

@ControllerAdvice
public class AdviceController {
    private static final Logger log = LoggerFactory.getLogger(AdviceController.class);

    public AdviceController() {
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new DateFormat("yyyy-MM-dd HH:mm:ss"), true));
        binder.registerCustomEditor(String.class, new StringEscapeEditor(PropertiesUtil.getBoolean("spring.mvc.htmlEscape", false), PropertiesUtil.getBoolean("spring.mvc.javaScriptEscape", false)));
    }

    @ExceptionHandler({Throwable.class})
    public ResponseEntity<ModelMap> exceptionHandler(HttpServletRequest request, HttpServletResponse response, Throwable ex) {
        log.error("OH,MY GOD! SOME ERRORS OCCURED! AS FOLLOWS :", ex);
        Integer code = null;
        String msg = null;
        Object data = null;
        ModelMap modelMap = new ModelMap();
        BindingResult bindingResult = null;
        boolean paramExceptionFlag = false;
        if (ex instanceof LoginException) {
            code = HttpCode.UNAUTHORIZED.value();
            msg = "请登录";
        } else if (ex instanceof IllegalArgumentException) {
            (new IllegalParameterException(ex.getMessage())).handler(modelMap);

        } else if ("org.apache.shiro.authz.UnauthorizedException".equals(ex.getClass().getName())) {
            code = HttpCode.FORBIDDEN.value();
//            msg = HttpCode.FORBIDDEN.msg();
            msg="没有权限";
        } else if (ex instanceof MethodArgumentNotValidException) {
            bindingResult = ((MethodArgumentNotValidException)ex).getBindingResult();
            paramExceptionFlag = true;
        } else if (ex instanceof BindException) {
            bindingResult = ((BindException)ex).getBindingResult();
            paramExceptionFlag = true;
        } else if (ex instanceof AccessDeniedException) {
            code = HttpCode.FORBIDDEN.value();
//            msg = HttpCode.FORBIDDEN.msg();
            msg="没有权限";
        }else  if (ex instanceof BaseException) {
            BaseException be = (BaseException)ex;
            be.handler(modelMap);
            code = Integer.parseInt(modelMap.get("code").toString());
            msg = modelMap.get("msg").toString();
        }

        if (paramExceptionFlag) {
            Map<String, String> errorMap = new HashMap(16);
            bindingResult.getFieldErrors().forEach((fieldError) -> {
                String var10000 = (String)errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
            });
            code = HttpCode.BAD_REQUEST.value();
//            msg = HttpCode.BAD_REQUEST.msg();
            msg="参数有误";
        }

        log.info("response===>" + JSON.toJSON(modelMap));
        code = code == null ? HttpCode.INTERNAL_SERVER_ERROR.value() : code;
        msg = msg == null ? (String)StringUtils.defaultIfBlank(ex.getMessage(), HttpCode.INTERNAL_SERVER_ERROR.msg()) : msg;
        modelMap.put("code", code);
        modelMap.put("msg", msg.length() > 100 ? "系统繁忙，请稍候再试." : msg);
        return ResponseEntity.ok(modelMap);
    }
}

