package com.itmk.web.sys_user.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.annotation.SaMode;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.itmk.jwt.JwtUtils;
import com.itmk.utils.ResultUtils;
import com.itmk.utils.ResultVo;
import com.itmk.web.sys_menu.entity.AssignTreeParm;
import com.itmk.web.sys_menu.entity.AssignTreeVo;
import com.itmk.web.sys_menu.entity.SysMenu;
import com.itmk.web.sys_menu.service.SysMenuService;
import com.itmk.web.sys_user.entity.*;
import com.itmk.web.sys_user.service.SysUserService;
import com.itmk.web.sys_user_role.entity.SysUserRole;
import com.itmk.web.sys_user_role.service.SysUserRoleService;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: AlbertHPW
 * @Date: 2024/11/22 21:12
 */
@RequestMapping("/api/sysUser")
@RestController
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private DefaultKaptcha defaultKaptcha;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private SysMenuService sysMenuService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    // 新增
    @PostMapping
    @SaCheckPermission("sys:user:add") // 权限
    //@SaCheckRole("super") // 角色
    public ResultVo add(@RequestBody SysUser sysUser) {
        QueryWrapper<SysUser> query = new QueryWrapper<>();
        query.lambda().eq(SysUser::getUsername, sysUser.getUsername());
        // 检查用户名是否存在
        if(sysUserService.getOne(query) != null) {
            return ResultUtils.error("用户名已存在");
        }

        sysUser.setCreateTime(new Date());
        String encode = passwordEncoder.encode(sysUser.getPassword());
        System.out.println("加密之后：" + encode);
        sysUser.setPassword(encode);
        sysUserService.saveUser(sysUser);
        return ResultUtils.success("新增成功");
    }

    // 编辑
    @PutMapping
    @SaCheckPermission(value = {"sys:user:update", "sys:user:update"}, mode = SaMode.OR) // 多权限
    public ResultVo edit(@RequestBody SysUser sysUser) {
        LambdaQueryWrapper<SysUser> query = new LambdaQueryWrapper<>();
        // 设置查询条件，比较用户名和用户ID
        query.eq(SysUser::getUsername, sysUser.getUsername()).ne(SysUser::getUserId, sysUser.getUserId());
        // 检查用户名是否存在
        if(sysUserService.getOne(query) != null) {
            return ResultUtils.error("用户名已存在");
        }
        sysUser.setUpdateTime(new Date());
        sysUserService.editUser(sysUser);
        return ResultUtils.success("编辑成功");
    }

    // 删除
    @DeleteMapping("/{userId}")
    @SaCheckPermission("sys:user:delete") // 权限
    public ResultVo delete(@PathVariable("userId") Long userId) {
        sysUserService.deleteUser(userId);
        return ResultUtils.success("删除成功");
    }

    //列表
    @GetMapping("/list")
    public ResultVo getList(UserParm parm) {
        // 构造分页对象
        IPage<SysUser> page = new Page<>(parm.getCurrentPage(), parm.getPageSize());
        // 构造查询条件
        QueryWrapper<SysUser> query = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(parm.getNickName())) {
            query.lambda().like(SysUser::getNickName, parm.getNickName());
        }
        if (StringUtils.isNotEmpty(parm.getPhone())) {
            query.lambda().like(SysUser::getPhone, parm.getPhone());
        }
        query.lambda().orderByDesc(SysUser::getCreateTime);
        IPage<SysUser> list = sysUserService.page(page, query);
        return ResultUtils.success("查询成功", list);
    }

    // 根据用户id查询用户的角色
    @GetMapping("/getRoleList")
    public ResultVo getRoleByUserId(Long userId) {
        QueryWrapper<SysUserRole> query = new QueryWrapper<>();
        query.lambda().eq(SysUserRole::getUserId, userId);
        List<SysUserRole> list = sysUserRoleService.list(query);
        // 角色Id
        List<Long> roleList = new ArrayList<>();
        Optional.ofNullable(list).orElse(new ArrayList<>())
                .forEach(item -> roleList.add(item.getRoleId()));
        return ResultUtils.success("查询成功", roleList);
    }

    // 重置密码
    @PostMapping("/resetPassword")
    @SaCheckPermission("sys:user:updatePassword") // 权限resetPassword
    public ResultVo resetPassword(@RequestBody SysUser sysUser) {
        String encode = passwordEncoder.encode("666666");
        UpdateWrapper<SysUser> query = new UpdateWrapper<>();
        query.lambda().eq(SysUser::getUserId, sysUser.getUserId()).set(SysUser::getPassword, encode);
        if (sysUserService.update(query)) {
            return ResultUtils.success("密码重置成功");
        }
        return ResultUtils.error("密码重置失败");
    }

    // 图片验证码
    @GetMapping("/captcha")
    public ResultVo imageCode(HttpServletRequest request) {
        // 生成验证码
        String text = defaultKaptcha.createText();
        //验证码存到session
        HttpSession session = request.getSession();
        session.setAttribute("code", text);
        // 生成图片，转换为base64
        BufferedImage bufferedImage = defaultKaptcha.createImage(text);
        ByteArrayOutputStream outputStream = null;
        try {
            outputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "jpg", outputStream);
            //BASE64Encoder encoder = new BASE64Encoder();
            //String base64 = encoder.encode(outputStream.toByteArray());
            String base64 = Base64.encodeBase64String(outputStream.toByteArray());
            String captchaBase64 = "data:image/jpeg;base64," + base64.replaceAll("\r\n", "");
            return ResultUtils.success("验证码生成成功", 200, captchaBase64);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    // 登录
    @PostMapping("/login")
    public ResultVo login(@RequestBody LoginParm parm, HttpServletRequest request) {
        // 从session里面获取code验证码
        HttpSession session = request.getSession();
        String code = (String) session.getAttribute("code");
        // 获取从前端传递过来的验证码
        String codeParm = parm.getCode();
        if (StringUtils.isEmpty(code)) {
            return ResultUtils.error("验证码已失效");
        }
        // 对比验证码
        if (!codeParm.equals(code)) {
            return ResultUtils.error("验证码错误");
        }
        // 加密密码
        String encode = passwordEncoder.encode(parm.getPassword());
        System.out.println("加密之后：" + encode);
        // 验证用户信息
        // DigestUtils.md5DigestAsHex(parm.getPassword().getBytes())
        QueryWrapper<SysUser> query = new QueryWrapper<>();
        query.lambda().eq(SysUser::getUsername, parm.getUsername());
                //.eq(SysUser::getPassword, encode);
        SysUser user = sysUserService.getOne(query);
        if (user == null) {
            return ResultUtils.error("用户名错误！");
        }

        if(!passwordEncoder.matches(parm.getPassword(), user.getPassword())){
            return ResultUtils.error("密码错误！");
        }
        if(!user.isEnabled()) {
            return ResultUtils.error("账户已经被禁用，请联系系统管理员！");
        }

        // 登录成功
        StpUtil.login(user.getUserId(), "PC");
        // 返回登录信息
        LoginVo vo = new LoginVo();
        vo.setUserId(user.getUserId());
        vo.setNickName(user.getNickName());
        // 生成token,非安全框架
        //Map<String, String> map = new HashMap<>();
        //map.put("userId", user.getUserId().toString());
        //String token = jwtUtils.generateToken(map);
        //vo.setToken(token);
        // sa-token生成token
        SaTokenInfo saTokenInfo = StpUtil.getTokenInfo();
        vo.setToken(saTokenInfo.getTokenValue());
        return ResultUtils.success("登录成功", vo);
    }

    // 查询菜单树
    @GetMapping("/getAssignTree")
    public ResultVo getAssignTree(AssignTreeParm parm) {
        AssignTreeVo assignTree = sysUserService.getAssignTree(parm);
        return ResultUtils.success("查询成功", assignTree);
    }

    // 修改密码
    @PostMapping("/updatePassword")
    public ResultVo updatePassword(@RequestBody UpdatePasswordPram parm) {
        SysUser user = sysUserService.getById(parm.getUserId());
        //if (!parm.getOldPassword().equals(user.getPassword())) {
        //    return ResultUtils.error("原密码不正确！");
        //}
        if(!passwordEncoder.matches(parm.getOldPassword(), user.getPassword())){
            return ResultUtils.error("原密码不正确！");
        }
        // 更新条件
        String encode = passwordEncoder.encode(parm.getPassword());
        UpdateWrapper<SysUser> query = new UpdateWrapper<>();
        query.lambda().set(SysUser::getPassword, encode).eq(SysUser::getUserId, parm.getUserId());
        if (sysUserService.update(query)) {
            return ResultUtils.success("密码修改成功！");
        }
        return ResultUtils.error("密码修改失败！");
    }
    // 获取用户信息
    @GetMapping("/getInfo")
    public ResultVo getInfo(Long userId){
        // 根据用户ID查询用户信息
        SysUser user = sysUserService.getById(userId);
        List<SysMenu> menuList = null;
        // 判断是否是超级管理员
        if(StringUtils.isNotEmpty(user.getIsAdmin()) && "1".equals(user.getIsAdmin())){
            // 超级管理员,查询所有菜单
            menuList = sysMenuService.list();
        } else {
            menuList = sysMenuService.getMenuByUserId(userId);
        }
        // 获取菜单的code
        List<String> collect = Optional.ofNullable(menuList).orElse(new ArrayList<>())
                .stream()
                .filter(item -> item != null && StringUtils.isNotEmpty(item.getCode()))
                .map(item -> item.getCode())
                .collect(Collectors.toList());
        // 转换为数组
        //String[] strings = collect.toArray(new String[collect.size()]);
        // 设置返回值
        UserInfo userInfo = new UserInfo();
        userInfo.setName(user.getNickName());
        userInfo.setUserId(userId);
        userInfo.setPermissons(collect.toArray());
        return ResultUtils.success("查询成功！", userInfo);
    }
}
