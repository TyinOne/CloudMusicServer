package com.tyin.server.controller;

import com.tyin.core.annotations.Auth;
import com.tyin.core.constants.ResMessageConstants;
import com.tyin.core.module.bean.AuthAdminUser;
import com.tyin.core.utils.Asserts;
import com.tyin.server.api.Result;
import com.tyin.server.params.valid.InsertScheduledValid;
import com.tyin.server.service.IAdminScheduledService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/add")
    public Result<?> addScheduled(@RequestBody InsertScheduledValid valid, @Auth AuthAdminUser ignoredUser) {
        Integer row = adminScheduledService.addScheduled(valid);
        Asserts.isTrue(row > 0, ResMessageConstants.ADD_FAILED);
        return Result.success();
    }
}
