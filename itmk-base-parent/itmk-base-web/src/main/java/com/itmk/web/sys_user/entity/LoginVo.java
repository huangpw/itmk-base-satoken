package com.itmk.web.sys_user.entity;

import lombok.Data;

/**
 * @Author: AlbertHPW
 * @Date: 2024/11/26 20:27
 */
@Data
public class LoginVo {
    private Long userId;
    private String nickName;
    private String token;
}
