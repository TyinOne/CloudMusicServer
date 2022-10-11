package com.tyin.server.controller;

import com.tyin.core.annotations.Auth;
import com.tyin.core.annotations.NoLog;
import com.tyin.core.module.bean.AuthAdminUser;
import com.tyin.core.module.res.admin.AdminLogRes;
import com.tyin.server.api.PageResult;
import com.tyin.server.api.Result;
import com.tyin.server.service.IRequestLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "日志管理-访问日志接口")
@NoLog
@RestController
@RequestMapping("${cloud.api.prefix.admin}/log")
@RequiredArgsConstructor
public class AdminLogController {
    private final IRequestLogService requestLogService;

    @Operation(description = "日志查询接口")
    @GetMapping("/list")
    public Result<PageResult<AdminLogRes, ?>> queryLog(@Parameter(description = "分页长度", example = "20") @RequestParam(required = false, defaultValue = "20") Long size,
                                                       @Parameter(description = "当前页", example = "1") @RequestParam(required = false, defaultValue = "1") Long current,
                                                       @Auth("@permission.hasPermission('sys:log:query')") AuthAdminUser ignoredUser) {

        PageResult<AdminLogRes, ?> pageResult = requestLogService.queryLog(size, current);
        return Result.success(pageResult);
    }
}
