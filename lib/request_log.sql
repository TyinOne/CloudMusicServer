/*
 Navicat Premium Data Transfer

 Source Server         : Tencent
 Source Server Type    : MySQL
 Source Server Version : 50737
 Source Host           : rds.tyin.vip:3306
 Source Schema         : cloud_music

 Target Server Type    : MySQL
 Target Server Version : 50737
 File Encoding         : 65001

 Date: 28/06/2022 09:24:55
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for request_log
-- ----------------------------
DROP TABLE IF EXISTS `request_log`;
CREATE TABLE `request_log`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `uri` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `ip` bigint(11) UNSIGNED NOT NULL DEFAULT 0,
  `method` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '运行方法',
  `params` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '接口参数',
  `result` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '接口返回',
  `elapsed` bigint(20) NOT NULL DEFAULT 0,
  `created` datetime(0) NOT NULL,
  `modified` datetime(0) NOT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11606 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
