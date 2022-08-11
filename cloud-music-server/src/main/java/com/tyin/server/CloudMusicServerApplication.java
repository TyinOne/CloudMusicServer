package com.tyin.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author Tyin
 * @date 2022/6/14 15:26
 * @description ...
 */
@MapperScan("com.tyin.server.repository")
@ComponentScan({"com.tyin.*"})
@EnableAsync
@SpringBootApplication
public class CloudMusicServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(CloudMusicServerApplication.class, args);
    }
}
