package com.itmk.web.sys_menu.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itmk.utils.ResultUtils;
import com.itmk.utils.ResultVo;
import com.itmk.web.sys_menu.entity.MakeMenuTree;
import com.itmk.web.sys_menu.entity.RouterVo;
import com.itmk.web.sys_menu.entity.SysMenu;
import com.itmk.web.sys_menu.service.SysMenuService;
import com.itmk.web.sys_user.entity.SysUser;
import com.itmk.web.sys_user.service.SysUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Author: AlbertHPW
 * @Date: 2024/11/23 18:03
 */
@RequestMapping("/api/sysMenu")
@RestController
public class SysMenuController {

    @Autowired
    private SysMenuService sysMenuService;
    @Autowired
    private SysUserService sysUserService;

    // 新增
    @PostMapping
    @SaCheckPermission("sys:menu:add")
    public ResultVo add(@RequestBody SysMenu sysMenu) {

        sysMenu.setCreateTime(new Date());
        if (sysMenuService.save(sysMenu)) {
            return ResultUtils.success("新增成功");
        }
        return ResultUtils.error("新增失败");
    }

    // 编辑
    @PutMapping
    @SaCheckPermission("sys:menu:update")
    public ResultVo edit(@RequestBody SysMenu sysMenu) {
        sysMenu.setUpdateTime(new Date());
        if (sysMenuService.updateById(sysMenu)) {
            return ResultUtils.success("编辑成功");
        }
        return ResultUtils.error("编辑失败");
    }

    // 删除
    @DeleteMapping("/{menuId}")
    @SaCheckPermission("sys:menu:delete")
    public ResultVo delete(@PathVariable("menuId") Long menuId) {
        // 如果存在下级，不能删除
        QueryWrapper<SysMenu> query = new QueryWrapper<>();
        query.lambda().eq(SysMenu::getParentId, menuId);
        List<SysMenu> list = sysMenuService.list(query);
        if(list.size() > 0) {
            return ResultUtils.error("该菜单存在下级菜单，不能删除");
        }
        if (sysMenuService.removeById(menuId)) {
            return ResultUtils.success("删除成功");
        }
        return ResultUtils.error("删除失败");
    }

    //列表
    @GetMapping("/list")
    public ResultVo getList() {
        // 排序
        QueryWrapper<SysMenu> query = new QueryWrapper<>();
        query.lambda().orderByAsc(SysMenu::getOrderNum);
        // 查询出所有的菜单
        List<SysMenu> list = sysMenuService.list(query);
        // 组装树数据
        List<SysMenu> menuList = MakeMenuTree.makeTree(list, 0L);
        return ResultUtils.success("查询成功", menuList);
    }
    // 上级菜单
    @GetMapping("/getParent")
    public ResultVo getParent() {
        List<SysMenu> list = sysMenuService.getParent();
        return ResultUtils.success("查询成功", list);
    }


    // 获取路由菜单
    @GetMapping("/getMenuList")
    public ResultVo getMenuList(Long userId) {
        // 获取用户信息
        SysUser user = sysUserService.getById(userId);
        // 菜单数据
        List<SysMenu> menuList = null;
        // 判断是否是超级管理员
        if(StringUtils.isNotEmpty(user.getIsAdmin()) && "1".equals(user.getIsAdmin())){
            QueryWrapper<SysMenu> query = new QueryWrapper<>();
            query.lambda().orderByAsc(SysMenu::getOrderNum);
            menuList = sysMenuService.list(query);
        } else {
            menuList = sysMenuService.getMenuByUserId(userId);
        }
        // 过滤菜单数据,去掉按钮数据
        List<SysMenu> collect = Optional.ofNullable(menuList).orElse(new ArrayList<>())
                .stream()
                .filter(item -> item != null && StringUtils.isNotEmpty(item.getType()) && !"2".equals(item.getType()))
                .collect(Collectors.toList());
        // 组装路由数据
        List<RouterVo> router = MakeMenuTree.makeRouter(collect, 0L);

        return  ResultUtils.success("查询成功！", router);
    }
}
