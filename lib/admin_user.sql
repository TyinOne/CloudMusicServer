CREATE TABLE `admin_user`
(
    `id`       bigint                                  NOT NULL AUTO_INCREMENT COMMENT '主键',
    `name`     varchar(10) COLLATE utf8mb4_general_ci  NOT NULL DEFAULT '',
    `avatar`   varchar(255) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '头像',
    `account`  varchar(11) COLLATE utf8mb4_general_ci  NOT NULL DEFAULT '' COMMENT '用户名',
    `phone`    varchar(15) COLLATE utf8mb4_general_ci  NOT NULL DEFAULT '' COMMENT '手机号',
    `mail`     varchar(30) COLLATE utf8mb4_general_ci  NOT NULL DEFAULT '' COMMENT '邮箱',
    `password` varchar(100) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
    `created`  datetime                                NOT NULL,
    `modified` datetime                                NOT NULL,
    `deleted`  tinyint                                 NOT NULL DEFAULT '0',
    PRIMARY KEY (`id`),
    UNIQUE KEY `admin_user_account_uindex` (`account`),
    UNIQUE KEY `admin_user_mail_uindex` (`mail`),
    UNIQUE KEY `admin_user_phone_uindex` (`phone`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;