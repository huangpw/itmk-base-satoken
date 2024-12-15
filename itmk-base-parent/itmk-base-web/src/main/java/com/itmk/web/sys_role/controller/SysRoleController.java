package com.itmk.web.sys_role.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itmk.utils.ResultUtils;
import com.itmk.utils.ResultVo;
import com.itmk.web.sys_role.entity.RoleParm;
import com.itmk.web.sys_role.entity.SaveMenuParm;
import com.itmk.web.sys_role.entity.SelectItem;
import com.itmk.web.sys_role.entity.SysRole;
import com.itmk.web.sys_role.service.SysRoleService;
import com.itmk.web.sys_role_menu.service.RoleMenuService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @Author: AlbertHPW
 * @Date: 2024/11/22 1:16
 */
@RequestMapping("/api/role")
@RestController
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private RoleMenuService roleMenuService;

    //新增
    @PostMapping
    @SaCheckPermission("sys:role:add")
    public ResultVo add(@RequestBody SysRole sysRole) {
        sysRole.setCreateTime(new Date());
        if(sysRoleService.save(sysRole)) {
            return ResultUtils.success("新增成功");
        }
        return ResultUtils.error("新增失败");
    }

    //编辑
    @PutMapping
    @SaCheckPermission("sys:role:update")
    public ResultVo edit(@RequestBody SysRole sysRole) {
         sysRole.setUpdateTime(new Date());
        if(sysRoleService.updateById(sysRole)) {
            return ResultUtils.success("编辑成功");
        }
        return ResultUtils.error("编辑失败");
    }

    //删除
    @DeleteMapping("/{roleId}")
    @SaCheckPermission("sys:role:delete")
    public ResultVo delete(@PathVariable("roleId") Long roleId) {
        if(sysRoleService.removeById(roleId)) {
            return ResultUtils.success("删除成功");
        }
        return ResultUtils.error("删除失败");
    }

    //列表
    @GetMapping("/getList")
    public ResultVo getList(RoleParm parm) {
        // 构造分页对象
        IPage<SysRole> page = new Page<>(parm.getCurrentPage(), parm.getPageSize());
        // 构造查询条件
        QueryWrapper<SysRole> query = new QueryWrapper<>();
        if(StringUtils.isNotEmpty(parm.getRoleName())) {
            query.lambda().like(SysRole::getRoleName, parm.getRoleName());
        }
        query.lambda().orderByDesc(SysRole::getCreateTime);
        IPage<SysRole> list = sysRoleService.page(page, query);
        return ResultUtils.success("查询成功",list);
    }

    // 角色下拉列表
    @GetMapping("/selectRoleList")
    public ResultVo selectRoleList() {
        List<SysRole> list = sysRoleService.list();
        // 返回的值
        List<SelectItem> selectItems = new ArrayList<>();
        Optional.ofNullable(list).orElse(new ArrayList<>())
                .forEach(item -> {
                    SelectItem vo = new SelectItem();
                    vo.setCheck(false);
                    vo.setLabel(item.getRoleName());
                    vo.setValue(item.getRoleId());
                    selectItems.add(vo);
                });
        return ResultUtils.success("查询成功",selectItems);
    }

    // 保存角色菜单
    @PostMapping("/saveRoleMenu")
    @SaCheckPermission("sys:role:allotMenu")
    public ResultVo saveRoleMenu(@RequestBody SaveMenuParm parm) {
        roleMenuService.saveRoleMenu(parm);
        return ResultUtils.success("分配成功");
    }
}
