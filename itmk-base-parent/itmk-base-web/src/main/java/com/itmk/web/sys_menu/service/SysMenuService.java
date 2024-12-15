package com.itmk.web.sys_menu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itmk.web.sys_menu.entity.AssignTreeParm;
import com.itmk.web.sys_menu.entity.AssignTreeVo;
import com.itmk.web.sys_menu.entity.SysMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: AlbertHPW
 * @Date: 2024/11/23 18:00
 */
public interface SysMenuService extends IService<SysMenu> {

    List<SysMenu> getParent();

    List<SysMenu> getMenuByUserId(Long userId);

    // 根据角色Id查询菜单
    List<SysMenu> getMenuByRoleId(Long roleId);


}
