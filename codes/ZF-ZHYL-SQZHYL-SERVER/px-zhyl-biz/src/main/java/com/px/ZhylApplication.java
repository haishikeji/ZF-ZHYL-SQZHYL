package com.px;

import com.px.basic.alone.security.annotation.EnablePigResourceServer;

import com.px.msg.config.propertis.MessageProperties;
import com.px.plugins.conversion.core.config.ConversionConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author 品讯科技
 * <p>
 * 项目启动类
 */
@EnablePigResourceServer
@ServletComponentScan
@SpringBootApplication
@ComponentScan(basePackages = {"com.pig4cloud.pig", "com.px"})
@EnableConfigurationProperties({ ConversionConfig.class, MessageProperties.class}) //AOP 增强代理
@EnableAspectJAutoProxy//打开注解启动服务器监控
public class TemplateApplication {
    public static void main(String[] args) {
        try {
            SpringApplication.run(TemplateApplication.class, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
