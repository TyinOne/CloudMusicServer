package com.tyin.core.components.properties;

/**
 * @author Tyin
 * @date 2022/9/28 11:25
 * @description ...
 * map
 * oss
 * sys_test
 * admin
 * ok_http
 * update
 * sched_group
 */
public enum PropertiesEnum {
    /**
     * 系统配置
     */
    ADMIN("admin"),
    /**
     * 文件存储
     */
    OSS("oss"),
    /**
     * 地图区域
     */
    MAP("map"),
    /**
     * OkHttp
     */
    OK_HTTP("ok_http"),
    /**
     * 版本管理
     */
    UPDATE("update"),
    /**
     * 任务分组
     */
    SCHED_GROUP("sched_group"),
    ;
    private String type;

    PropertiesEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
