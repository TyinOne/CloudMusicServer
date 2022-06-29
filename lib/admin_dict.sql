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

 Date: 28/06/2022 09:23:32
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin_dict
-- ----------------------------
DROP TABLE IF EXISTS `admin_dict`;
CREATE TABLE `admin_dict`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `dict_type` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `dict_label` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `dict_key` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `dict_value` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `dict_description` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `created` datetime(0) NOT NULL,
  `modified` datetime(0) NOT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
