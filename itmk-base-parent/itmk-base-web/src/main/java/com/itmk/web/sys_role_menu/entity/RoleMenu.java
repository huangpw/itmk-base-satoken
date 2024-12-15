package com.itmk.web.sys_role_menu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Author: AlbertHPW
 * @Date: 2024/12/3 20:14
 */
@Data
@TableName("sys_role_menu")
public class RoleMenu {
    @TableId(type = IdType.AUTO)
    private Long roleMenuId;
    private Long roleId;
    private Long menuId;
}
