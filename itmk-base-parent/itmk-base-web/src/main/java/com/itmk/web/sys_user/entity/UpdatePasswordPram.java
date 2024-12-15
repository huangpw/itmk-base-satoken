package com.itmk.web.sys_user.entity;

import lombok.Data;

/**
 * @Author: AlbertHPW
 * @Date: 2024/12/10 23:20
 */
@Data
public class UpdatePasswordPram {
    private Long userId;
    private String oldPassword;
    private String password;
}
