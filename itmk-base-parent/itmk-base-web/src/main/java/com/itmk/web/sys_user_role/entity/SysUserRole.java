package com.itmk.web.sys_user_role.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Author: AlbertHPW
 * @Date: 2024/11/22 23:04
 */
@Data
@TableName("sys_user_role")
public class SysUserRole {
    @TableId(type = IdType.AUTO)
    private Long userRoleId;
    private Long userId;
    private Long roleId;
}
