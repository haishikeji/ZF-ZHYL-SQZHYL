package com.px.modulars.miniusers.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.time.LocalDateTime;

/**
 *  小程序用户
 */
@Data
@TableName("mini_users")
public class MiniUsers extends Model<MiniUsers> {

    private Integer id;

    private String openId;

    private String nickName;

    private String phone;

    private String avatar;

    private Integer sex;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String name;


}
