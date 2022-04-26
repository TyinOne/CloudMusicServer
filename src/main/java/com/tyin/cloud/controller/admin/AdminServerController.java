package com.tyin.cloud.controller.admin;

import com.tyin.cloud.core.annotations.Open;
import com.tyin.cloud.core.api.Result;
import com.tyin.cloud.model.res.SysInfoRes;
import com.tyin.cloud.model.res.SysRedisRes;
import com.tyin.cloud.service.system.ISysServerService;
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

    @Open
    @GetMapping("/server/config")
    public Result<SysInfoRes> getServerConfig() {
        return Result.success(sysServerService.getSysInfo());
    }

    @Open
    @GetMapping("/server/redis")
    public Result<SysRedisRes> getServerRedis() {
        return Result.success(sysServerService.getSysRedisInfo());
    }
}
