package com.itmk.web.sys_user.entity;

import lombok.Data;

/**
 * 返回用户信息
 * @Author: AlbertHPW
 * @Date: 2024/12/11 22:05
 */
@Data
public class UserInfo {
    private Long userId;
    private String name;
    private Object[] permissons;
}
