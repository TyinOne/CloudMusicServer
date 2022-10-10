package com.tyin.server.controller;


import com.tyin.core.annotations.NoLog;
import com.tyin.core.module.res.admin.SysInfoRes;
import com.tyin.core.module.res.admin.SysRedisRes;
import com.tyin.server.api.Result;
import com.tyin.server.service.ISysServerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Tyin
 * @date 2022/3/29 15:04
 * @description ...
 */
@RestController
@RequestMapping("${cloud.api.prefix.admin}/sys")
@RequiredArgsConstructor
public class AdminServerController {
    private final ISysServerService sysServerService;

    @GetMapping("/server/config")
    @NoLog
    public Result<SysInfoRes> getServerConfig() {
        return Result.success(sysServerService.getSysInfo());
    }

    @GetMapping("/server/redis")
    public Result<SysRedisRes> getServerRedis() {
        return Result.success(sysServerService.getSysRedisInfo());
    }
}
