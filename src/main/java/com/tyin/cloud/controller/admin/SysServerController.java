package com.tyin.cloud.controller.admin;

import com.tyin.cloud.core.annotations.Open;
import com.tyin.cloud.core.api.Result;
import com.tyin.cloud.model.res.SysInfoRes;
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
@RequestMapping("${cloud.api.prefix.admin}")
@RequiredArgsConstructor
public class SysServerController {
    private final ISysServerService sysServerService;

    @GetMapping("/sys/config")
    @Open
    public Result<SysInfoRes> getServerConfig() {
        return Result.success(sysServerService.getSysInfo());
    }
}
