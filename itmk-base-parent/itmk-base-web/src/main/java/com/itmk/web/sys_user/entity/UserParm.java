package com.itmk.web.sys_user.entity;

import lombok.Data;

/**
 * @Author: AlbertHPW
 * @Date: 2024/11/22 1:25
 */
@Data
public class UserParm {
    // 当前第几页
    private Long currentPage;
    // 每页显示多少条
    private Long pageSize;
    private String phone;
    private String nickName;
}
