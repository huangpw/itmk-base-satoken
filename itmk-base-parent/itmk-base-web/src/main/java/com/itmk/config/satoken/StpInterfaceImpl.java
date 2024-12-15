package com.itmk.config.satoken;

import cn.dev33.satoken.stp.StpInterface;
import com.itmk.config.exception.BusinessException;
import com.itmk.web.sys_menu.entity.SysMenu;
import com.itmk.web.sys_menu.service.SysMenuService;
import com.itmk.web.sys_user.entity.SysUser;
import com.itmk.web.sys_user.service.SysUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 定义权限认证接口扩展，Sa-Token将从此实现类获取每个账号拥有的权限码
 * @Author: AlbertHPW
 * @Date: 2024/12/15 16:58
 */
@Component
public class StpInterfaceImpl implements StpInterface {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysMenuService sysMenuService;

    /**
     * 查询当前账号拥有的权限码集合
     * @param loginId
     * @param loginType
     * @return
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        System.out.println("查询用户权限了");
        // 根据id查询用户用户信息
        SysUser user = sysUserService.getById((Serializable) loginId);
        List<SysMenu> menuList = null;
        // 判断是否是超级管理员
        if(StringUtils.isNotEmpty(user.getIsAdmin()) && "1".equals(user.getIsAdmin())) {
            menuList = sysMenuService.list();
        } else {
            menuList = sysMenuService.getMenuByUserId(user.getUserId());
        }
        if(menuList.stream().filter(Objects::nonNull).collect(Collectors.toList()).isEmpty()) {
            throw new BusinessException(500,"该用户对应的角色未分配菜单权限，请用管理员账号登录分配菜单");
        }
        // 获取菜单表的code字段
        List<String> collect = Optional.ofNullable(menuList).orElse(new ArrayList<>())
                .stream()
                .filter(item -> item != null && StringUtils.isNotEmpty(item.getCode()))
                .map(item -> item.getCode())
                .collect(Collectors.toList());
        // 设置返回值
        return collect;
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        return null;
    }
}
