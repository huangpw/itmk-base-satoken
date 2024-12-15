package com.itmk.web.sys_menu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itmk.web.sys_menu.entity.SysMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: AlbertHPW
 * @Date: 2024/11/23 17:58
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    // 根据用户Id查询菜单
    List<SysMenu> getMenuByUserId(@Param("userId") Long userId);

    // 根据角色Id查询菜单
    List<SysMenu> getMenuByRoleId(@Param("roleId") Long roleId);

}
