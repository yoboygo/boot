/*
 Navicat Premium Data Transfer

 Source Server         : myDatabase
 Source Server Type    : MySQL
 Source Server Version : 80011
 Source Host           : localhost:3306
 Source Schema         : mydatabase

 Target Server Type    : MySQL
 Target Server Version : 80011
 File Encoding         : 65001

 Date: 27/05/2018 15:16:51
*/

-- ----------------------------
-- Records of sys_deparment
-- ----------------------------
INSERT INTO `sys_deparment` VALUES (1, '开发部');
INSERT INTO `sys_deparment` VALUES (2, '测试部');

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` VALUES (1, '管理', '管理', NULL, '/manage/**');
INSERT INTO `sys_permission` VALUES (2, '用户', '用户', NULL, '/user/**');

-- ----------------------------
-- Records of sys_permission_role
-- ----------------------------
INSERT INTO `sys_permission_role` VALUES (1, 1);
INSERT INTO `sys_permission_role` VALUES (1, 2);
INSERT INTO `sys_permission_role` VALUES (2, 1);
INSERT INTO `sys_permission_role` VALUES (2, 2);

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, 'ROLE_ADMIN');
INSERT INTO `sys_role` VALUES (2, 'ROLE_USER');

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, '2018-05-26 08:21:43', 'admin', '$10$xzd3rbD5bcGws7ZiYMhckOD/nsT7VOk08GQzrHLcNS5tqFZtjC5ji', '1');
INSERT INTO `sys_user` VALUES (2, '2018-05-27 08:06:20', 'user', '$10$xzd3rbD5bcGws7ZiYMhckOD/nsT7VOk08GQzrHLcNS5tqFZtjC5ji', '2');

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1, 1);
INSERT INTO `sys_user_role` VALUES (1, 2);
INSERT INTO `sys_user_role` VALUES (2, 1);
INSERT INTO `sys_user_role` VALUES (2, 2);

