package com.tyin.cloud.controller.admin;

import com.tyin.cloud.core.annotations.Auth;
import com.tyin.cloud.core.annotations.NoLog;
import com.tyin.cloud.core.api.PageResult;
import com.tyin.cloud.core.api.Result;
import com.tyin.cloud.core.auth.AuthAdminUser;
import com.tyin.cloud.model.res.AdminLogRes;
import com.tyin.cloud.service.common.IRequestLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
@Api(tags = "日志管理-访问日志接口")
@NoLog
@RestController
@RequestMapping("${cloud.api.prefix.admin}/log")
@RequiredArgsConstructor
public class AdminLogController {
    private final IRequestLogService requestLogService;

    @ApiOperation("日志查询接口")
    @GetMapping("/list")
    public Result<PageResult<AdminLogRes, ?>> queryLog(@ApiParam(value = "分页长度", defaultValue = "20") @RequestParam(required = false, defaultValue = "20") Long size,
                                                       @ApiParam(value = "当前页", defaultValue = "1") @RequestParam(required = false, defaultValue = "1") Long current,
                                                       @Auth("@permission.hasPermission('sys:log:query')") AuthAdminUser user) {

        PageResult<AdminLogRes, ?> pageResult = requestLogService.queryLog(size, current);
        return Result.success(pageResult);
    }
}
