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

 Date: 28/06/2022 09:24:49
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin_version
-- ----------------------------
DROP TABLE IF EXISTS `admin_version`;
CREATE TABLE `admin_version`
(
    `id`           bigint(20)                                                    NOT NULL AUTO_INCREMENT,
    `version`      bigint(11)                                                    NOT NULL DEFAULT 0,
    `src`          longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci     NULL,
    `release_time` datetime(0)                                                   NOT NULL,
    `forced`       tinyint(1)                                                    NOT NULL DEFAULT 0,
    `latest`       tinyint(1)                                                    NOT NULL DEFAULT 0,
    `hash`         varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL DEFAULT '',
    `md5`          varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
    `update_log`   longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci     NULL,
    `created`      datetime(0)                                                   NOT NULL,
    `modified`     datetime(0)                                                   NOT NULL,
    `deleted`      tinyint(1)                                                    NOT NULL DEFAULT 0,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 5
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
