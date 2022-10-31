package com.tyin.core.components.properties;

import com.tyin.core.components.properties.models.AdminConfig;
import com.tyin.core.components.properties.models.OssConfig;
import com.tyin.core.components.properties.models.ScheduledGroupConfig;
import com.tyin.core.components.properties.models.TencentMapConfig;

/**
 * @author Tyin
 * @date 2022/9/28 11:25
 * @description ...
 */
public enum PropertiesEnum {
    /**
     * 腾讯云地图sdk配置
     */
    MAP("map", TencentMapConfig.class),
    OSS("oss", OssConfig.class),
    ADMIN("admin", AdminConfig.class),
    SCHEDULED_GROUP("sched_group", ScheduledGroupConfig.class),
    ;
    private String type;
    private Class<?> clazz;

    PropertiesEnum(String type, Class<?> clazz) {
        this.type = type;
        this.clazz = clazz;
    }

    public static PropertiesEnum getClazzByType(String type) {
        for (PropertiesEnum item : PropertiesEnum.values()) {
            if (item.getType().equals(type)) {
                return item;
            }
        }
        return null;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }
}
