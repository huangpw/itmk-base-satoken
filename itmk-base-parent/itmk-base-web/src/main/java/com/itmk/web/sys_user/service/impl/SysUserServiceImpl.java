package com.itmk.web.sys_user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itmk.web.sys_menu.entity.AssignTreeParm;
import com.itmk.web.sys_menu.entity.AssignTreeVo;
import com.itmk.web.sys_menu.entity.MakeMenuTree;
import com.itmk.web.sys_menu.entity.SysMenu;
import com.itmk.web.sys_menu.service.SysMenuService;
import com.itmk.web.sys_user.entity.SysUser;
import com.itmk.web.sys_user.mapper.SysUserMapper;
import com.itmk.web.sys_user.service.SysUserService;
import com.itmk.web.sys_user_role.entity.SysUserRole;
import com.itmk.web.sys_user_role.service.SysUserRoleService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @Author: AlbertHPW
 * @Date: 2024/11/22 21:10
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Autowired
    private SysMenuService sysMenuService;

    // 新增
    @Transactional
    @Override
    public void saveUser(SysUser sysUser) {
        // 插入用户信息
        int i = this.baseMapper.insert(sysUser);
        // 设置用户的角色
        if (i > 0) {
            // 把前端逗号分割的字符串转成数组
            String[] split = sysUser.getRoleId().split(",");
            if (split.length > 0) {
                List<SysUserRole> roles = new ArrayList<>();
                for (String s : split) {
                    SysUserRole userRole = new SysUserRole();
                    userRole.setUserId(sysUser.getUserId());
                    userRole.setRoleId(Long.valueOf(s));
                    roles.add(userRole);
                }
                // 保存到用户角色表
                sysUserRoleService.saveBatch(roles);
            }
        }
    }

    // 编辑
    @Transactional
    @Override
    public void editUser(SysUser sysUser) {
        // 编辑用户信息
        int i = this.baseMapper.updateById(sysUser);
        // 设置用户的角色
        if (i > 0) {
            // 把前端逗号分割的字符串转成数组
            String[] split = sysUser.getRoleId().split(",");
            // 删除用户原来的角色
            QueryWrapper<SysUserRole> query = new QueryWrapper<>();
            query.lambda().eq(SysUserRole::getUserId, sysUser.getUserId());
            sysUserRoleService.remove(query);
            // 重新插入
            if (split.length > 0) {
                List<SysUserRole> roles = new ArrayList<>();
                for (String s : split) {
                    SysUserRole userRole = new SysUserRole();
                    userRole.setUserId(sysUser.getUserId());
                    userRole.setRoleId(Long.valueOf(s));
                    roles.add(userRole);
                }
                // 保存到用户角色表
                sysUserRoleService.saveBatch(roles);
            }
        }
    }

    // 删除
    @Transactional
    @Override
    public void deleteUser(Long userId) {
        // 删除用户
        int i = this.baseMapper.deleteById(userId);
        if (i > 0) {
            // 删除角色
            QueryWrapper<SysUserRole> query = new QueryWrapper<>();
            query.lambda().eq(SysUserRole::getUserId, userId);
            sysUserRoleService.remove(query);
        }
    }

    @Override
    public AssignTreeVo getAssignTree(AssignTreeParm parm) {
        // 查询用户信息
        SysUser user = this.baseMapper.selectById(parm.getUserId());
        List<SysMenu> menuList = null;
        // 判断是否是超级管理员
        if(StringUtils.isNotEmpty(user.getIsAdmin()) && "1".equals(user.getIsAdmin())){
            // 超级管理员,查询所有菜单
            menuList = sysMenuService.list();
        } else {
            menuList = sysMenuService.getMenuByUserId(user.getUserId());
        }
        List<SysMenu> makeTree = MakeMenuTree.makeTree(menuList, 0L);
        // 查询角色原来的菜单
        List<SysMenu> roleList = sysMenuService.getMenuByRoleId(parm.getRoleId());
        List<Long> ids = new ArrayList<>();
        Optional.ofNullable(roleList).orElse(new ArrayList<>())
                .stream()
                .filter(item -> item != null)
                .forEach(item -> ids.add(item.getMenuId()));
        // 组装返回数据
        AssignTreeVo vo = new AssignTreeVo();
        vo.setCheckList(ids.toArray());
        vo.setMenuList(makeTree);
        return vo;
    }

}
