package com.itmk.web.sys_user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itmk.web.sys_menu.entity.AssignTreeParm;
import com.itmk.web.sys_menu.entity.AssignTreeVo;
import com.itmk.web.sys_user.entity.SysUser;

/**
 * @Author: AlbertHPW
 * @Date: 2024/11/22 21:09
 */
public interface SysUserService extends IService<SysUser> {
    // 新增
    void saveUser(SysUser sysUser);
    // 编辑
    void editUser(SysUser sysUser);
    // 删除
    void deleteUser(Long userId);
    // 查询菜单树
    AssignTreeVo getAssignTree(AssignTreeParm parm);
}
