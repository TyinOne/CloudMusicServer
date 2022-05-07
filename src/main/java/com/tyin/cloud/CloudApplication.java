package com.tyin.cloud;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author Tyin
 * @date 2022/3/26 2:22
 * @description ...
 */
@SpringBootApplication
@MapperScan("com.tyin.cloud.repository.*")
@EnableAsync
public class CloudApplication {
    public static void main(String[] args) {
        SpringApplication.run(CloudApplication.class, args);
    }
}
