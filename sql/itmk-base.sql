/*
 Navicat Premium Data Transfer

 Source Server         : 本地数据库123456
 Source Server Type    : MySQL
 Source Server Version : 80030
 Source Host           : localhost:3306
 Source Schema         : itmk-base

 Target Server Type    : MySQL
 Target Server Version : 80030
 File Encoding         : 65001

 Date: 15/12/2024 21:10:01
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `menu_id` int(0) NOT NULL AUTO_INCREMENT COMMENT '主键Id',
  `parent_id` int(0) NULL DEFAULT NULL COMMENT '父级菜单Id',
  `title` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '菜单名称',
  `code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '权限字段',
  `name` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '路由name',
  `path` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '路由path',
  `url` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '组件路径',
  `type` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '类型(0:目录 1:菜单  2:按钮)',
  `icon` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '菜单图标',
  `parent_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '上级菜单名称',
  `order_num` int(0) NULL DEFAULT NULL COMMENT '序号',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '菜单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, 0, '系统管理', 'sys:manage', 'system', '/system', '', '0', 'Setting', '顶级菜单', 1, '2024-11-23 16:28:17', '2024-11-24 13:41:37');
INSERT INTO `sys_menu` VALUES (2, 1, '用户管理', 'sys:user:index', 'userList', '/userList', 'views/system/User/UserList.vue', '1', 'UserFilled', '系统管理', 2, '2024-11-23 16:30:48', NULL);
INSERT INTO `sys_menu` VALUES (3, 1, '角色管理', 'sys:role:index', 'roleList', '/roleList', 'views/system/Role/RoleList.vue', '1', 'Wallet', '系统管理', 3, '2024-11-23 16:35:53', NULL);
INSERT INTO `sys_menu` VALUES (4, 1, '菜单管理', 'sys:menu:index', 'menuList', '/menuList', 'views/system/Menu/MenuList.vue', '1', 'Menu', '系统管理', 4, '2024-11-23 16:37:20', NULL);
INSERT INTO `sys_menu` VALUES (5, 0, '商品管理', 'sys:goodsRoot', 'goodsRoot', '/goodsRoot', '', '0', 'Setting', '顶级菜单', 5, '2024-11-23 16:38:57', NULL);
INSERT INTO `sys_menu` VALUES (6, 5, '商品类型', 'sys:category', 'category', '/category', 'views/category/CategoryList.vue', '1', 'UserFilled', '商品管理', 6, '2024-11-23 16:40:21', NULL);
INSERT INTO `sys_menu` VALUES (7, 5, '商品信息', 'sys:goodsList', 'goodsList', '/goodsList', 'views/goods/GoodsList.vue', '1', 'Wallet', '商品管理', 7, '2024-11-23 16:41:51', NULL);
INSERT INTO `sys_menu` VALUES (8, 2, '新增', 'sys:user:add', '', '', '', '2', '', '用户管理', 21, '2024-11-23 16:43:39', NULL);
INSERT INTO `sys_menu` VALUES (10, 2, '编辑', 'sys:user:update', '', '', '', '2', '', '用户管理', 22, '2024-12-13 12:39:08', NULL);
INSERT INTO `sys_menu` VALUES (11, 2, '删除', 'sys:user:delete', '', '', '', '2', '', '用户管理', 23, '2024-12-13 12:39:37', NULL);
INSERT INTO `sys_menu` VALUES (12, 2, '重置密码', 'sys:user:updatePassword', '', '', '', '2', '', '用户管理', 24, '2024-12-13 12:40:41', NULL);
INSERT INTO `sys_menu` VALUES (13, 3, '新增', 'sys:role:add', '', '', '', '2', '', '角色管理', 31, '2024-12-13 12:42:04', NULL);
INSERT INTO `sys_menu` VALUES (14, 3, '编辑', 'sys:role:update', '', '', '', '2', '', '角色管理', 32, '2024-12-13 12:42:27', NULL);
INSERT INTO `sys_menu` VALUES (15, 3, '删除', 'sys:role:delete', '', '', '', '2', '', '角色管理', 33, '2024-12-13 12:42:45', NULL);
INSERT INTO `sys_menu` VALUES (16, 3, '分配菜单', 'sys:role:allotMenu', '', '', '', '2', '', '角色管理', 34, '2024-12-13 12:43:58', NULL);
INSERT INTO `sys_menu` VALUES (17, 4, '新增', 'sys:menu:add', '', '', '', '2', '', '菜单管理', 41, '2024-12-13 12:44:26', NULL);
INSERT INTO `sys_menu` VALUES (18, 4, '编辑', 'sys:menu:update', '', '', '', '2', '', '菜单管理', 42, '2024-12-13 12:44:44', NULL);
INSERT INTO `sys_menu` VALUES (19, 4, '删除', 'sys:menu:delete', '', '', '', '2', '', '菜单管理', 43, '2024-12-13 12:45:02', '2024-12-13 14:59:35');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `role_id` int(0) NOT NULL AUTO_INCREMENT COMMENT '角色Id',
  `role_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '角色名称',
  `type` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '扩展字段',
  `remark` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '部门管理人员', NULL, '部门管理人员', '2024-11-22 14:04:19', NULL);
INSERT INTO `sys_role` VALUES (2, '副总', NULL, '副总', '2024-11-22 14:04:28', NULL);
INSERT INTO `sys_role` VALUES (3, '系统管理员', NULL, '系统管理员', '2024-11-22 14:04:39', NULL);
INSERT INTO `sys_role` VALUES (4, '总裁', NULL, '总裁', '2024-11-22 14:04:51', NULL);

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `role_menu_id` int(0) NOT NULL AUTO_INCREMENT COMMENT '主键Id',
  `menu_id` int(0) NULL DEFAULT NULL COMMENT '菜单Id',
  `role_id` int(0) NULL DEFAULT NULL COMMENT '角色Id',
  PRIMARY KEY (`role_menu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 38 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '角色菜单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (10, 8, 4);
INSERT INTO `sys_role_menu` VALUES (11, 10, 4);
INSERT INTO `sys_role_menu` VALUES (12, 5, 4);
INSERT INTO `sys_role_menu` VALUES (13, 6, 4);
INSERT INTO `sys_role_menu` VALUES (14, 7, 4);
INSERT INTO `sys_role_menu` VALUES (15, 1, 4);
INSERT INTO `sys_role_menu` VALUES (16, 2, 4);
INSERT INTO `sys_role_menu` VALUES (27, 8, 3);
INSERT INTO `sys_role_menu` VALUES (28, 13, 3);
INSERT INTO `sys_role_menu` VALUES (29, 16, 3);
INSERT INTO `sys_role_menu` VALUES (30, 17, 3);
INSERT INTO `sys_role_menu` VALUES (31, 5, 3);
INSERT INTO `sys_role_menu` VALUES (32, 6, 3);
INSERT INTO `sys_role_menu` VALUES (33, 7, 3);
INSERT INTO `sys_role_menu` VALUES (34, 1, 3);
INSERT INTO `sys_role_menu` VALUES (35, 2, 3);
INSERT INTO `sys_role_menu` VALUES (36, 3, 3);
INSERT INTO `sys_role_menu` VALUES (37, 4, 3);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `user_id` int(0) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `username` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '登录账户',
  `password` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '登录密码',
  `phone` varchar(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '用户电话',
  `email` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '邮箱',
  `sex` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '性别(0:男 1:女)',
  `is_admin` tinyint(0) NULL DEFAULT NULL COMMENT '是否是超级管理员(1:是   0:否)',
  `is_account_non_expired` tinyint(0) NULL DEFAULT NULL COMMENT '账户是否过期(1:未过期，0:已过期)',
  `is_account_non_locked` tinyint(0) NULL DEFAULT NULL COMMENT '账户是否被锁定(1:未锁定，0:已锁定)',
  `is_credentials_non_expired` tinyint(0) NULL DEFAULT NULL COMMENT '密码是否被锁定(1:未过期，0:已过期)',
  `is_enabled` tinyint(0) NULL DEFAULT NULL COMMENT '账户是否可用(1:可用，0:删除用户)',
  `nick_name` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '姓名',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'zhangsan', '$2a$10$erpw6u.nJ1k9WMu9ZvBNxeP59pU9zXXK16tX//qUZVGx5NDwXSlIe', '10086', '10086@qq.com', '0', 1, 1, 1, 1, 1, '张三', '2024-11-22 15:52:44', NULL);
INSERT INTO `sys_user` VALUES (2, 'lisi', '$2a$10$erpw6u.nJ1k9WMu9ZvBNxeP59pU9zXXK16tX//qUZVGx5NDwXSlIe', '10010', '10010@qq.com', '0', NULL, 1, 1, 1, 1, '李四', '2024-11-22 15:53:58', NULL);
INSERT INTO `sys_user` VALUES (3, 'wangwu', '$2a$10$erpw6u.nJ1k9WMu9ZvBNxeP59pU9zXXK16tX//qUZVGx5NDwXSlIe', '*****', '*****111', '1', NULL, 1, 1, 1, 1, '王五', '2024-11-22 15:59:44', '2024-12-15 12:58:51');
INSERT INTO `sys_user` VALUES (5, 'zhaoliu', '$2a$10$bVS500qqBUcC9ulxSBy9Mu3yZSHqq7.d4h7KqCPQNHODcMBpCbqVO', '17875245116', '17875245116@qq.com', '0', NULL, 1, 1, 1, 1, '赵六', '2024-12-15 10:29:50', NULL);

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `user_role_id` int(0) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` int(0) NULL DEFAULT NULL COMMENT '用户Id',
  `role_id` int(0) NULL DEFAULT NULL COMMENT '角色Id',
  PRIMARY KEY (`user_role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '用户角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1, 1, 1);
INSERT INTO `sys_user_role` VALUES (2, 1, 2);
INSERT INTO `sys_user_role` VALUES (3, 2, 4);
INSERT INTO `sys_user_role` VALUES (14, 5, 1);
INSERT INTO `sys_user_role` VALUES (17, 3, 3);
INSERT INTO `sys_user_role` VALUES (18, 3, 2);

SET FOREIGN_KEY_CHECKS = 1;
