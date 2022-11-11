package com.tyin.server.controller;

import com.tyin.core.annotations.Auth;
import com.tyin.core.module.bean.AuthAdminUser;
import com.tyin.core.module.res.admin.AdminScheduleRes;
import com.tyin.core.module.res.admin.AdminScheduledDetail;
import com.tyin.core.utils.Asserts;
import com.tyin.server.api.PageResult;
import com.tyin.server.api.Result;
import com.tyin.server.params.valid.IdValid;
import com.tyin.server.params.valid.SaveScheduledValid;
import com.tyin.server.service.IAdminScheduledService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.tyin.core.constants.ResMessageConstants.*;

/**
 * @author Tyin
 * @date 2022/7/14 10:40
 * @description ...
 */
@RestController
@RequestMapping("${cloud.api.prefix.admin}/scheduled")
@RequiredArgsConstructor
public class AdminScheduledController {
    private final IAdminScheduledService adminScheduledService;

    @PostMapping("/save")
    @Operation(description = "新增任务调度")
    public Result<?> addScheduled(@RequestBody SaveScheduledValid valid, @Parameter(hidden = true) @Auth AuthAdminUser ignoredUser) throws Exception {
        Integer row = adminScheduledService.saveScheduled(valid);
        Asserts.isTrue(row > 0, ADD_FAILED);
        return Result.success();
    }

    @GetMapping("/list")
    @Operation(description = "任务调度列表")
    public Result<PageResult<AdminScheduleRes, ?>> getScheduledList(@RequestParam(required = false) String keywords, @RequestParam(required = false) String group, @RequestParam Boolean disabled, @RequestParam(required = false, defaultValue = "20") Long size, @RequestParam(required = false, defaultValue = "1") Long current, @Auth AuthAdminUser ignoredUser) {
        PageResult<AdminScheduleRes, ?> pageResult = adminScheduledService.getSchedulePageResult(keywords, group, disabled, size, current);
        return Result.success(pageResult);
    }

    @GetMapping("/detail/{id}")
    @Operation(description = "定时任务详情")
    public Result<AdminScheduledDetail> getScheduled(@PathVariable Long id, @Auth AuthAdminUser ignoredUser) {
        AdminScheduledDetail res = adminScheduledService.getScheduled(id);
        return Result.success(res);
    }

    @PutMapping("/disable")
    @Operation(description = "修改任务执行状态")
    public Result<?> updateScheduledDisable(@RequestBody IdValid valid, @Auth AuthAdminUser ignoredUser) {
        Integer row = 0;
        Asserts.isTrue(row == 1, UPDATE_FAILED);
        return Result.success();
    }

    @DeleteMapping("/remove")
    @Operation(description = "删除任务")
    public Result<?> removeScheduled(@RequestBody IdValid valid, @Auth AuthAdminUser ignoredUser) {
        Integer row = 0;
        Asserts.isTrue(row == 1, REMOVE_FAILED);
        return Result.success();
    }

    @DeleteMapping("/run")
    @Operation(description = "执行一次")
    public Result<?> runScheduled(@RequestBody IdValid valid, @Auth AuthAdminUser ignoredUser) {
        return Result.success();
    }
}
