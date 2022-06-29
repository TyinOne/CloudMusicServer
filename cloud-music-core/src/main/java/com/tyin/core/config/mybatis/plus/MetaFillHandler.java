package com.tyin.core.config.mybatis.plus;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author Tyin
 * @date 2022/4/19 9:50
 * @description ...
 */
@Component
public class MetaFillHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        setFieldValByName("created", new Date(), metaObject);
        setFieldValByName("modified", new Date(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        setFieldValByName("modified", new Date(), metaObject);
    }

}