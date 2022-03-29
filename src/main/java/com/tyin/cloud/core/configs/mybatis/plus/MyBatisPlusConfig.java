package com.tyin.cloud.core.configs.mybatis.plus;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author Tyin
 * @date 2022/3/26 2:40
 * @description ...
 */
@Configuration
public class MyBatisPlusConfig {
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

    @Component
    public static class MetaFillHandler implements MetaObjectHandler {
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
}
