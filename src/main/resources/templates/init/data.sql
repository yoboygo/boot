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
INSERT INTO `sys_deparment` (`id`,`name`,`del_flag`) VALUES (1, '开发部',1);
INSERT INTO `sys_deparment` (`id`,`name`,`del_flag`) VALUES (2, '测试部',1);

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` (`id`,`description`,`name`,`pid`,`url_pattern`,`del_flag`) VALUES (1, '管理', '管理', NULL, '/manage/**',1);
INSERT INTO `sys_permission` (`id`,`description`,`name`,`pid`,`url_pattern`,`del_flag`) VALUES (2, '用户', '用户', NULL, '/user/**',1);

-- ----------------------------
-- Records of sys_permission_role
-- ----------------------------
INSERT INTO `sys_role_permission` (`role_id`,`permission_id`) VALUES (1, 1);
INSERT INTO `sys_role_permission` (`role_id`,`permission_id`) VALUES (1, 2);
INSERT INTO `sys_role_permission` (`role_id`,`permission_id`) VALUES (2, 1);
INSERT INTO `sys_role_permission` (`role_id`,`permission_id`) VALUES (2, 2);

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` (`id`,`name`,`del_flag`) VALUES (1, 'ROLE_ADMIN',1);
INSERT INTO `sys_role` (`id`,`name`,`del_flag`) VALUES (2, 'ROLE_USER',1);

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` (`id`,`createdate`,`name`,`password`,`email`,`status` ,`del_flag`) VALUES (1, '2018-05-26 08:21:43', 'admin', '$2a$10$fTVH/G/82pT2kXVyxWWHbOZg6Gonb4DBAZmtTJ4fgo1RZXuB1ugYC', 'admin@idream.com','1','1');
INSERT INTO `sys_user` (`id`,`createdate`,`name`,`password`,`email`,`status` ,`del_flag`) VALUES (2, '2018-05-27 08:06:20', 'user', '$2a$10$fTVH/G/82pT2kXVyxWWHbOZg6Gonb4DBAZmtTJ4fgo1RZXuB1ugYC', 'user@idream.com','1','1');

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1, 1);
INSERT INTO `sys_user_role` VALUES (1, 2);
INSERT INTO `sys_user_role` VALUES (2, 1);
INSERT INTO `sys_user_role` VALUES (2, 2);

