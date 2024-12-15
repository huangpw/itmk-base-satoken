package com.itmk.web.sys_menu.entity;

import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @Author: AlbertHPW
 * @Date: 2024/11/23 18:12
 */
public class MakeMenuTree {
    // 组装树工具
    public static List<SysMenu> makeTree(List<SysMenu> menuList, Long pid) {
        // 存放组装的树数据
        List<SysMenu> list = new ArrayList<>();
        // 组装树
        Optional.ofNullable(menuList).orElse(new ArrayList<>())
                .stream()
                .filter(item -> item != null && item.getParentId().equals(pid))
                .forEach(item -> {
                    SysMenu menu = new SysMenu();
                    BeanUtils.copyProperties(item, menu);
                    menu.setLabel(item.getTitle());
                    menu.setValue(item.getMenuId());
                    // 查找下级：递归调用
                    List<SysMenu> children = makeTree(menuList, item.getMenuId());
                    menu.setChildren(children);
                    list.add(menu);
                });
        return list;
    }

    // 构造路由数据
    public static List<RouterVo> makeRouter(List<SysMenu> menuList, Long pid) {
        // 构建存放路由数据的容器
        List<RouterVo> list = new ArrayList<>();
        Optional.ofNullable(menuList).orElse(new ArrayList<>())
                .stream()
                .filter(item -> item != null && item.getParentId().equals(pid))
                .forEach(item -> {
                    // 组装
                    RouterVo router = new RouterVo();
                    // 设置路由名称
                    router.setName(item.getName());
                    // 设置路由路径
                    router.setPath(item.getPath());
                    // 设置children 递归调用：自己调用自己
                    List<RouterVo> children = makeRouter(menuList, item.getMenuId());
                    router.setChildren(children);
                    if (item.getParentId() == 0L) { // 如果上级是0，那么他的Component是Layout
                        router.setComponent("Layout");
                        // 判断该数据是目录还是菜单
                        if ("1".equals(item.getType())) { // 如果一级菜单是菜单类型，单独处理
                            router.setRedirect(item.getPath());
                            // 菜单需要设置children
                            List<RouterVo> listChild = new ArrayList<>();
                            RouterVo child = new RouterVo();
                            child.setName(item.getName());
                            child.setPath(item.getPath());
                            child.setComponent(item.getUrl());
                            child.setMeta(child.new Meta(
                                    item.getTitle(),
                                    item.getIcon(),
                                    item.getCode().split(",")
                            ));
                            listChild.add(child);
                            router.setChildren(listChild);
                            router.setPath(item.getPath() + "parent");
                            router.setName(item.getName() + "parent");
                        }
                    } else {
                        router.setComponent(item.getUrl());
                    }
                    router.setMeta(router.new Meta(
                            item.getTitle(),
                            item.getIcon(),
                            item.getCode().split(",")
                    ));
                    list.add(router);
                });
        return list;
    }
}
