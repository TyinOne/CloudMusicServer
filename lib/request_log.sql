CREATE TABLE `request_log`
(
    `id`       bigint                                                        NOT NULL AUTO_INCREMENT,
    `uri`      varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
    `ip`       int unsigned NOT NULL DEFAULT '0',
    `method`   varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '运行方法',
    `params`   longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '接口参数',
    `result`   longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '接口返回',
    `elapsed`  bigint                                                        NOT NULL DEFAULT '0',
    `created`  datetime                                                      NOT NULL,
    `modified` datetime                                                      NOT NULL,
    `deleted`  tinyint(1) NOT NULL DEFAULT '0',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;