package com.tyin.server.controller;

import com.tyin.core.annotations.Auth;
import com.tyin.core.annotations.NoLog;
import com.tyin.core.api.PageResult;
import com.tyin.core.api.Result;
import com.tyin.core.module.bean.AuthAdminUser;
import com.tyin.core.module.res.admin.AdminLogDetailRes;
import com.tyin.core.module.res.admin.AdminLogRes;
import com.tyin.core.service.IAdminRequestLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

import static com.tyin.core.utils.DateUtils.YYYY_MM_DD;

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
    private final IAdminRequestLogService requestLogService;

    @Operation(description = "日志查询接口")
    @GetMapping("/list")
    public Result<PageResult<AdminLogRes, ?>> queryLog(@RequestParam(required = false) @DateTimeFormat(pattern = YYYY_MM_DD) Date startDate,
                                                       @RequestParam(required = false) @DateTimeFormat(pattern = YYYY_MM_DD) Date endDate,
                                                       @RequestParam(required = false) String keywords,
                                                       @RequestParam(required = false) String method,
                                                       @RequestParam(required = false) Boolean status,
                                                       @Parameter(description = "分页长度", example = "20") @RequestParam(required = false, defaultValue = "20") Long size,
                                                       @Parameter(description = "当前页", example = "1") @RequestParam(required = false, defaultValue = "1") Long current,
                                                       @Parameter(hidden = true) @Auth("@permission.hasPermission('sys:log:query')") AuthAdminUser ignoredUser) {

        PageResult<AdminLogRes, ?> pageResult = requestLogService.queryLog(startDate, endDate, method, status, keywords, size, current);
        return Result.success(pageResult);
    }

    @Operation(description = "日志详情接口")
    @GetMapping("/detail/{logId}")
    public Result<AdminLogDetailRes> queryLogDetail(@Parameter(description = "记录id") @PathVariable Long logId) {
        AdminLogDetailRes vos = requestLogService.queryLogDetail(logId);
        return Result.success(vos);
    }
}
