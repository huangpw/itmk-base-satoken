package com.itmk.web.sys_role_menu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itmk.web.sys_role.entity.SaveMenuParm;
import com.itmk.web.sys_role_menu.entity.RoleMenu;

/**
 * @Author: AlbertHPW
 * @Date: 2024/12/4 21:17
 */
public interface RoleMenuService extends IService<RoleMenu> {
    void saveRoleMenu(SaveMenuParm parm);
}
