package com.itmk.web.sys_user.entity;

import lombok.Data;

/**
 * @Author: AlbertHPW
 * @Date: 2024/11/26 20:18
 */
@Data
public class LoginParm {
    private String username;
    private String password;
    private String code;
}
