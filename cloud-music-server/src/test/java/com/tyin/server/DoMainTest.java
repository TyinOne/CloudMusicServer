package com.tyin.server;

import com.tyin.server.auth.security.service.TokenService;
import com.tyin.server.loader.SystemLoader;
import com.tyin.server.repository.AdminDictRepository;
import com.tyin.server.repository.AdminUserRepository;
import io.jsonwebtoken.Claims;
import org.apache.commons.codec.digest.Md5Crypt;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;

/**
 * @author Tyin
 * @date 2022/9/27 17:16
 * @description ...
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DoMainTest {
    @Resource
    private AdminDictRepository adminDictRepository;
    @Resource
    private AdminUserRepository adminUserRepository;

    @Resource
    private SystemLoader systemLoader;
    @Resource
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Resource
    private TokenService tokenService;

    @Test
    public void commonTest() {
        String passwordMd5 = Md5Crypt.md5Crypt("tyinzero".getBytes(StandardCharsets.UTF_8), "$1$tyinzero");
//        Md5Crypt.apr1Crypt()
//        AdminUser user = adminUserRepository.selectById(1);
        System.out.println("pwd: " + bCryptPasswordEncoder.encode(passwordMd5));
        System.out.println(bCryptPasswordEncoder.matches(passwordMd5, "$2a$10$svr94D3l5eo7IWPdQVDd8excd87I4S6pNJ3qZPM6uUSQrq5CcO41G"));
    }

    @Test
    public void getPasswordByMD5() {
        Claims claims = tokenService.parseToken("eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJUeWluWmVybyIsImV4cCI6MTY2NTMwNzk3OSwiaWF0IjoxNjY1Mjg2Mzc5LCJsb2dpbl91c2VyX2tleSI6IlR5aW5aZXJvOkFBNjZERUUzQzZDMzQ3RjVBRURERTJCQzA1NDQwMDdEIn0.GD7SwV7j9DTR8SbW39-pQ5e2OnISPkywMkAxYUuLRUf6wnDap6tzLOyjLO2XhrEDF5nbnjqltCPNPxnwNH1UVA");
        System.out.println(claims);
    }
}
