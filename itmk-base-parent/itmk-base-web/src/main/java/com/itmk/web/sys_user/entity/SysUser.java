package com.itmk.web.sys_user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @Author: AlbertHPW
 * @Date: 2024/11/22 21:02
 */
@Data
@TableName("sys_user")
public class SysUser {
    @TableId(type = IdType.AUTO)
    private Long userId;
    private String username;
    private String password;
    private String phone;
    private String email;
    private String sex;
    // 是否是超级管理员(1:是   0:否)
    private String isAdmin;
    // 账户是否过期(1:未过期，0:已过期)
    private boolean isAccountNonExpired = true;
    // 账户是否被锁定(1:未锁定，0:已锁定)
    private boolean isAccountNonLocked = true;
    // 密码是否被锁定(1:未过期，0:已过期)
    private boolean isCredentialsNonExpired = true;
    // 账户是否可用(1:可用，0:删除用户)
    private boolean isEnabled = true;
    private String nickName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    // 角色Id，不属于用户表，需要排除
    @TableField(exist = false)
    private String roleId;
}
