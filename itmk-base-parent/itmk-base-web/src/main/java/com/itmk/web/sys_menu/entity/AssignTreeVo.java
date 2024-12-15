package com.itmk.web.sys_menu.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: AlbertHPW
 * @Date: 2024/11/26 23:07
 */
@Data
public class AssignTreeVo {
    private List<SysMenu> menuList = new ArrayList<>();
    private Object[] checkList;
}
