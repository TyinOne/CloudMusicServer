package com.tyin.cloud.controller.admin;

import com.tyin.cloud.core.annotations.Auth;
import com.tyin.cloud.core.api.PageResult;
import com.tyin.cloud.core.api.Result;
import com.tyin.cloud.core.auth.AuthAdminUser;
import com.tyin.cloud.model.res.AdminLogRes;
import com.tyin.cloud.service.common.IRequestLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Tyin
 * @date 2022/5/5 9:00
 * @description ...
 */
@RestController
@RequestMapping("${cloud.api.prefix.admin}/log")
@RequiredArgsConstructor
public class AdminLogController {
    private final IRequestLogService requestLogService;

    @GetMapping("/list")
    public Result<PageResult<AdminLogRes, ?>> queryLog(@RequestParam(required = false, defaultValue = "20") Long size,
                                                       @RequestParam(required = false, defaultValue = "1") Long current,
                                                       @Auth("@permission.hasPermission('sys:log:query')") AuthAdminUser user) {

        PageResult<AdminLogRes, ?> pageResult = requestLogService.queryLog(size, current);
        return Result.success(pageResult);
    }
}
