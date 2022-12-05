package com.tyin.client;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author Tyin
 * @date ${DATE} ${TIME}
 * @description ...
 */
@MapperScan("com.tyin.client.repository")
@ComponentScan({"com.tyin.*"})
@EnableAsync
@SpringBootApplication
public class CloudClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(CloudClientApplication.class, args);
    }
}