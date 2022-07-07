package com.tyin.core.config.database.druid;

import javax.sql.DataSource;

/**
 * @author Tyin
 * @date 2022/5/22 1:29
 * @description ...
 */

public abstract class DruidConfig {
    /**
     * 实例化Druid数据源
     * @return DruidDataSource
     */
    public abstract DataSource druid();
}
