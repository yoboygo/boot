/*
 Navicat Premium Data Transfer

 Source Server         : databas-myuser
 Source Server Type    : MySQL
 Source Server Version : 80011
 Source Host           : localhost:3306
 Source Schema         : mydatabase

 Target Server Type    : MySQL
 Target Server Version : 80011
 File Encoding         : 65001

 Date: 23/06/2018 14:45:40
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_department
-- ----------------------------
DROP TABLE IF EXISTS `sys_department`;
CREATE TABLE `sys_department` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL COMMENT '部门名称',
  `del_flag` tinyint(1) DEFAULT NULL COMMENT '删除标志：1，正常，0，删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=MyISAM DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='系统部门表';

-- ----------------------------
-- Table structure for sys_user_department
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_department`;
CREATE TABLE `sys_user_department`  (
  `user_id` int(11) NOT NULL,
  `department_id` int(11) NOT NULL,
  UNIQUE INDEX `uk_user_department`(`user_id`, `department_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;
-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `name` varchar(255) NOT NULL COMMENT '权限名称',
  `pid` int(20) DEFAULT NULL COMMENT '父id',
  `url_pattern` varchar(255) NOT NULL COMMENT 'url pattern',
  `del_flag` tinyint(1) DEFAULT 1 COMMENT '删除标志：1，正常，0，删除；',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=MyISAM DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='系统权限表';
-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL COMMENT '角色名称',
  `del_flag` tinyint(1) DEFAULT '1' COMMENT '删除标志:1,正常，0，删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=MyISAM DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='系统角色表';
-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission`  (
  `role_id` bigint(20) NOT NULL,
  `permission_id` int(11) NOT NULL,
  INDEX `FK1db5xvu0eeo36h9wnu71awtun`(`permission_id`) USING BTREE,
  INDEX `FKj7dnbgvr47c4nvns9yb9bdrfd`(`role_id`) USING BTREE
) ENGINE = MyISAM CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Fixed;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createdate` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间，默认为当前时间',
  `name` varchar(255) DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  `email` varchar(255) DEFAULT NULL COMMENT '邮箱',
  `status` tinyint(1) DEFAULT '1' COMMENT '状态标记位：1，启用，0，禁用;默认启用',
  `del_flag` tinyint(1) DEFAULT '1' COMMENT '删除标志：1，正常，0，删除；默认正常',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk-name` (`name`) USING BTREE,
  KEY `FKa1on9mgwjf26n6xdl0tg3durc` (`email`) USING BTREE
) ENGINE=MyISAM DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='系统用户表';
-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  INDEX `FKhh52n8vd4ny9ff4x9fb8v65qx`(`role_id`) USING BTREE,
  INDEX `FKb40xxfch70f5qnyfw8yme1n1s`(`user_id`) USING BTREE
) ENGINE = MyISAM CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Fixed;

SET FOREIGN_KEY_CHECKS = 1;
