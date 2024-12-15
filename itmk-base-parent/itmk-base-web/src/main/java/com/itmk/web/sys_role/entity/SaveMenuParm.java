package com.itmk.web.sys_role.entity;

import lombok.Data;

import java.util.List;

/**
 * @Author: AlbertHPW
 * @Date: 2024/12/3 20:16
 */
@Data
public class SaveMenuParm {
    private Long roleId;
    private List<Long> list;
}
