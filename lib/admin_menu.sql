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

 Date: 28/06/2022 09:23:49
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin_menu
-- ----------------------------
DROP TABLE IF EXISTS `admin_menu`;
CREATE TABLE `admin_menu`
(
    `id`                 bigint(20)                                                    NOT NULL AUTO_INCREMENT,
    `parent_id`          bigint(20)                                                    NOT NULL DEFAULT 0,
    `sort`               int(11)                                                       NOT NULL DEFAULT 0,
    `type`               tinyint(1)                                                    NOT NULL DEFAULT 1 COMMENT '0：目录；1：菜单；2：按钮',
    `security`           varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL DEFAULT '',
    `name`               varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL DEFAULT '',
    `path`               varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
    `redirect`           varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
    `component`          varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
    `meta_title`         varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL DEFAULT '',
    `meta_icons`         varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL DEFAULT '',
    `meta_is_link`       varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
    `meta_roles`         varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
    `meta_is_hide`       tinyint(1)                                                    NOT NULL DEFAULT 0,
    `meta_is_affix`      tinyint(1)                                                    NOT NULL DEFAULT 0,
    `meta_is_iframe`     tinyint(1)                                                    NOT NULL DEFAULT 0,
    `meta_is_keep_alive` tinyint(1)                                                    NOT NULL DEFAULT 0,
    `disabled`           tinyint(1)                                                    NOT NULL DEFAULT 0,
    `created`            datetime(0)                                                   NOT NULL,
    `modified`           datetime(0)                                                   NOT NULL,
    `deleted`            tinyint(1)                                                    NOT NULL DEFAULT 0,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 46
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin_menu
-- ----------------------------
INSERT INTO `admin_menu`
VALUES (1, 0, 0, 1, '', 'home', '/home', '', '/home/index', '首页', 'ele-HomeFilled', '', '', 0, 1, 0, 1, 0,
        '2022-04-08 10:23:05', '2022-04-08 10:23:07', 0);
INSERT INTO `admin_menu`
VALUES (2, 0, 0, 0, '', 'system', '/system', '/system/menu', '/layout/index', '系统设置', 'fa-server fa', '', '', 0, 0,
        0, 1, 0, '2022-04-08 10:25:30', '2022-06-13 16:23:22', 0);
INSERT INTO `admin_menu`
VALUES (3, 2, 0, 0, '', 'systemServer', '/system/server', '/system/server/view', '/layout/index', '系统状态',
        'fa-heartbeat fa', '', '', 0, 0, 0, 1, 0, '2022-04-08 10:29:13', '2022-04-08 10:29:15', 0);
INSERT INTO `admin_menu`
VALUES (4, 2, 0, 1, '', 'systemMenu', '/system/menu', '', '/menu/index', '菜单设置', 'ele-Menu', '', '', 0, 0, 0, 1, 0,
        '2022-04-08 10:33:30', '2022-04-08 10:33:32', 0);
INSERT INTO `admin_menu`
VALUES (5, 2, 0, 1, '', 'systemRole', '/system/role', '', '/role/index', '角色设置', 'fa-user-group fa', '', '', 0, 0,
        0, 1, 0, '2022-04-08 10:34:59', '2022-05-19 17:41:40', 0);
INSERT INTO `admin_menu`
VALUES (6, 3, 0, 1, '', 'systemServerView', '/system/server/view', '', '/system/server/index', '性能监控',
        'fa-desktop fa', '', '', 0, 0, 0, 1, 0, '2022-04-08 10:38:23', '2022-04-08 10:38:21', 0);
INSERT INTO `admin_menu`
VALUES (7, 3, 2, 1, '', 'systemRedisView', '/system/redis/view', '', '/system/redis/index', '缓存监控', 'fa-coins fa',
        '', '', 0, 0, 0, 1, 0, '2022-05-11 23:20:31', '2022-05-22 05:03:10', 0);
INSERT INTO `admin_menu`
VALUES (8, 3, 3, 1, '', 'systemLogView', '/system/log/view', '', '/system/log/index', '日志管理', 'fa-bar-chart fa', '',
        '', 0, 0, 0, 1, 0, '2022-05-11 23:22:11', '2022-05-22 05:03:18', 0);
INSERT INTO `admin_menu`
VALUES (9, 3, 6, 1, '', 'systemDictData', '/system/dict/view/:dict_type', '', '/system/dict/dictData', '字典管理',
        'fa-book fa', '', '', 1, 0, 0, 1, 0, '2022-05-11 23:23:13', '2022-06-09 11:13:55', 0);
INSERT INTO `admin_menu`
VALUES (10, 2, 0, 1, '', 'systemUser', '/system/user', '', '/user/index', '用户管理', 'fa-user-cog fa', '', '', 0, 0, 0,
        1, 0, '2022-05-11 23:25:07', '2022-05-19 17:42:22', 0);
INSERT INTO `admin_menu`
VALUES (11, 0, 0, 1, '', 'personal', '/personal', '', '/personal/index', '个人中心', 'fa-user fa', '', '', 1, 0, 0, 1,
        0, '2022-05-11 23:26:47', '2022-05-19 17:44:23', 0);
INSERT INTO `admin_menu`
VALUES (12, 0, 0, 1, '', 'setting', '/setting', '', '/setting/index', '设置', 'fa-cog fa', '', '', 1, 0, 0, 1, 0,
        '2022-05-11 23:27:30', '2022-06-01 10:48:38', 0);
INSERT INTO `admin_menu`
VALUES (13, 0, 0, 1, '', 'layoutIframeGit', '/github', '', '/layout/routerView/iframes', 'GitHub', 'bi-github bi',
        'https://github.com/TyinOne/CloudMusicServer', '', 1, 0, 1, 1, 0, '2022-05-11 23:28:36', '2022-06-13 16:25:48',
        0);
INSERT INTO `admin_menu`
VALUES (14, 0, 998, 0, '', 'iconView', '/icons', '/icons/element', '/layout/index', '图标库', 'bi-slack bi', '', '', 0,
        0, 0, 1, 0, '2022-04-08 10:23:05', '2022-06-13 16:25:33', 0);
INSERT INTO `admin_menu`
VALUES (15, 14, 0, 1, '', 'iconViewElement', '/icons/element', '', '/icon/element/index', 'Element', 'ele-ElementPlus',
        '', '', 0, 0, 0, 1, 0, '2022-04-08 10:23:05', '2022-04-08 10:23:07', 0);
INSERT INTO `admin_menu`
VALUES (16, 14, 0, 1, '', 'iconViewBootstrap', '/icons/bootstrap', '', '/icon/bootstrap/index', 'Bootstrap',
        'bi-bootstrap bi', '', '', 0, 0, 0, 1, 0, '2022-04-08 10:23:05', '2022-04-08 10:23:07', 0);
INSERT INTO `admin_menu`
VALUES (17, 14, 0, 1, '', 'iconViewAwesome', '/icons/awesome', '', '/icon/fontawesome/index', 'Awesome',
        'bi-pinterest bi', '', '', 0, 0, 0, 1, 0, '2022-04-08 10:23:05', '2022-05-22 01:21:44', 0);
INSERT INTO `admin_menu`
VALUES (18, 5, 0, 2, 'sys:role:query', 'queryRole', 'systemRole', '', '', '查询', '', '', '', 0, 0, 0, 0, 0,
        '2022-05-13 10:52:41', '2022-05-17 09:33:51', 0);
INSERT INTO `admin_menu`
VALUES (19, 5, 0, 2, 'sys:role:add', 'addRole', 'systemRole', '', '', '新增角色', '', '', '', 0, 0, 0, 0, 0,
        '2022-05-13 16:59:33', '2022-05-17 09:33:57', 0);
INSERT INTO `admin_menu`
VALUES (20, 2, 0, 1, '', 'regionIndex', '/system/region', '', '/region/index', '区域管理', 'fa-location-arrow fa', '',
        '', 0, 0, 0, 1, 0, '2022-05-17 00:50:28', '2022-05-19 17:43:25', 0);
INSERT INTO `admin_menu`
VALUES (21, 5, 0, 2, 'sys:role:update', 'updateRole', 'systemRole', '', '', '修改角色', '', '', '', 0, 0, 0, 1, 0,
        '2022-05-17 09:26:04', '2022-05-17 09:34:06', 0);
INSERT INTO `admin_menu`
VALUES (22, 5, 0, 2, 'sys:role:remove', 'removeRole', 'systemRole', '', '', '删除', '', '', '', 0, 0, 0, 1, 0,
        '2022-05-17 09:33:13', '2022-05-17 09:34:12', 0);
INSERT INTO `admin_menu`
VALUES (23, 8, 0, 2, 'sys:log:query', 'queryLog', 'systemLogView', '', '', '查询', '', '', '', 0, 0, 0, 1, 0,
        '2022-05-18 21:21:50', '2022-05-18 21:21:50', 0);
INSERT INTO `admin_menu`
VALUES (24, 8, 0, 2, 'sys:log:detail', 'detail', 'systemLogView', '', '', '详情', '', '', '', 0, 0, 0, 1, 0,
        '2022-05-18 21:22:20', '2022-05-18 21:22:20', 0);
INSERT INTO `admin_menu`
VALUES (25, 9, 0, 2, 'sys:dict:query', 'queryDict', 'systemDictView', '', '', '查询', '', '', '', 0, 0, 0, 1, 0,
        '2022-05-18 21:23:16', '2022-05-18 21:23:16', 0);
INSERT INTO `admin_menu`
VALUES (26, 9, 0, 2, 'sys:dict:add', 'addDict', 'systemDictView', '', '', '新增字典', '', '', '', 0, 0, 0, 1, 0,
        '2022-05-18 21:23:51', '2022-05-18 21:23:51', 0);
INSERT INTO `admin_menu`
VALUES (27, 9, 0, 2, 'sys:dict:add', 'addDictType', 'systemDictView', '', '', '新增分类', '', '', '', 0, 0, 0, 1, 0,
        '2022-05-18 21:24:18', '2022-05-18 22:40:25', 0);
INSERT INTO `admin_menu`
VALUES (28, 9, 0, 2, 'sys:dict:update', 'updateDict', 'systemDictView', '', '', '修改字典', '', '', '', 0, 0, 0, 1, 0,
        '2022-05-18 21:24:46', '2022-05-18 21:24:46', 0);
INSERT INTO `admin_menu`
VALUES (29, 9, 0, 2, 'sys:dict:remove', 'removeDict', 'systemDictView', '', '', '删除', '', '', '', 0, 0, 0, 1, 0,
        '2022-05-18 21:25:13', '2022-05-18 21:25:13', 0);
INSERT INTO `admin_menu`
VALUES (30, 10, 0, 2, 'sys:account:query', 'queryAccount', 'systemUser', '', '', '查询', '', '', '', 0, 0, 0, 1, 0,
        '2022-05-18 23:13:01', '2022-05-18 23:13:01', 0);
INSERT INTO `admin_menu`
VALUES (31, 10, 0, 2, 'sys:account:detail', 'detailAccount', 'systemUser', '', '', '详情', '', '', '', 0, 0, 0, 1, 0,
        '2022-05-18 23:13:37', '2022-05-18 23:13:37', 0);
INSERT INTO `admin_menu`
VALUES (32, 10, 0, 2, 'sys:account:save', 'saveAccount', 'systemUser', '', '', '保存', '', '', '', 0, 0, 0, 1, 0,
        '2022-05-18 23:14:23', '2022-05-18 23:14:23', 0);
INSERT INTO `admin_menu`
VALUES (33, 3, 4, 1, '', 'layoutIframeDoc', '/system/server/api-doc', '', '/layout/routerView/iframes', 'API 文档',
        'fa-cloud fa', 'http://localhost:8888/doc.html', '', 0, 0, 1, 1, 0, '2022-05-11 23:28:36',
        '2022-05-22 05:03:30', 0);
INSERT INTO `admin_menu`
VALUES (34, 3, 1, 1, '', 'layoutIframeDruid', '/system/server/druid', '', '/layout/routerView/iframes', 'DB数据源',
        'bi-server bi', 'http://localhost:8888/druid/index.html', '', 0, 0, 1, 1, 0, '2022-05-11 23:28:36',
        '2022-05-22 05:03:02', 0);
INSERT INTO `admin_menu`
VALUES (35, 4, 0, 2, 'sys:menu:query', 'queryMenu', 'systemMenu', '', '', '查询', '', '', '', 0, 0, 0, 1, 0,
        '2022-05-23 10:18:54', '2022-05-23 10:19:12', 0);
INSERT INTO `admin_menu`
VALUES (36, 4, 1, 2, 'sys:menu:detail', 'detailMenu', 'systemMenu', '', '', '详情', '', '', '', 0, 0, 0, 1, 0,
        '2022-05-23 10:21:03', '2022-05-23 10:22:58', 0);
INSERT INTO `admin_menu`
VALUES (37, 4, 4, 2, 'sys:menu:remove', 'removeMenu', 'systemMenu', '', '', '删除', '', '', '', 0, 0, 0, 1, 0,
        '2022-05-23 10:21:41', '2022-05-23 10:23:04', 0);
INSERT INTO `admin_menu`
VALUES (38, 4, 2, 2, 'sys:menu:save', 'saveMenu', 'systemMenu', '', '', '保存菜单', '', '', '', 0, 0, 0, 1, 0,
        '2022-05-23 10:22:37', '2022-05-23 10:23:12', 0);
INSERT INTO `admin_menu`
VALUES (39, 2, 0, 1, '', 'systemVersion', '/system/version', '', '/system/version/index', '版本管理', 'fa-sliders-h fa',
        '', '', 0, 0, 0, 1, 0, '2022-05-23 17:19:47', '2022-05-23 17:57:59', 0);
INSERT INTO `admin_menu`
VALUES (40, 0, 999, 0, '', 'resourceIndex', '/resource', '', '/resource/index', '资源库', 'fa-cube fa', '', '', 0, 0, 0,
        1, 0, '2022-06-01 09:37:00', '2022-06-13 16:25:22', 0);
INSERT INTO `admin_menu`
VALUES (41, 3, 5, 1, '', 'systemDictType', '/system/dict/type/view', '', '/system/dict/dictType', '字典类型',
        'fa-book fa', '', '', 0, 0, 0, 1, 0, '2022-06-08 13:44:37', '2022-06-09 10:57:42', 0);
INSERT INTO `admin_menu`
VALUES (42, 0, 0, 0, '', 'client', '/client', '', '/layout/index', '客户端管理', 'fa-laptop fa', '', '', 0, 0, 0, 1, 0,
        '2022-06-13 16:27:20', '2022-06-13 16:59:02', 0);
INSERT INTO `admin_menu`
VALUES (43, 42, 0, 1, '', 'clientUserManager', '/client/user', '', '/client/user', '用户管理', 'ele-User', '', '', 0, 0,
        0, 1, 0, '2022-06-13 16:36:17', '2022-06-13 16:38:50', 0);
INSERT INTO `admin_menu`
VALUES (44, 42, 0, 1, '', 'clientMessage', '/client/message', '', '/client/message', '推送管理', 'ele-Message', '', '',
        0, 0, 0, 1, 0, '2022-06-13 16:54:45', '2022-06-13 16:54:45', 0);
INSERT INTO `admin_menu`
VALUES (45, 42, 0, 0, '', 'clientResource', '/client/resource', '', '/client/resource', '资源管理', 'fa-dice-d6 fa', '',
        '', 0, 0, 0, 1, 0, '2022-06-13 16:57:27', '2022-06-13 16:57:27', 0);

SET FOREIGN_KEY_CHECKS = 1;
