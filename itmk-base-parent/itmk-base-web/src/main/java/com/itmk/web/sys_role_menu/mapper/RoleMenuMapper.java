package com.itmk.web.sys_role_menu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itmk.web.sys_role_menu.entity.RoleMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: AlbertHPW
 * @Date: 2024/12/4 21:06
 */
@Mapper
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {
    // 保存角色菜单
    boolean saveRoleMenu(@Param("roleId") Long roleId, @Param("menuIds") List<Long> menuIds);
}
