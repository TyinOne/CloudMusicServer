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

 Date: 28/06/2022 09:24:37
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin_user_extra
-- ----------------------------
DROP TABLE IF EXISTS `admin_user_extra`;
CREATE TABLE `admin_user_extra`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `sex` tinyint(1) NOT NULL DEFAULT 0 COMMENT '0：保密；1：男；2：女',
  `birth` date NOT NULL,
  `region` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `id_card_no` varchar(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `id_card_name` varchar(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `id_card_address` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `created` datetime(0) NOT NULL,
  `modified` datetime(0) NOT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
