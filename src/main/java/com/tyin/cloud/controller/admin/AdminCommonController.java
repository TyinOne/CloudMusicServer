package com.tyin.cloud.controller.admin;

import com.tyin.cloud.core.annotations.Open;
import com.tyin.cloud.core.api.Result;
import com.tyin.cloud.service.admin.IAdminRegionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Tyin
 * @date 2022/4/22 1:40
 * @description ...
 */
@RestController
@RequestMapping("${cloud.api.prefix.admin}/common")
@RequiredArgsConstructor
public class AdminCommonController {
    private final IAdminRegionService adminRegionService;

    @Open
    @GetMapping("/area")
    public Result<?> getAreaForTencent() {
        adminRegionService.getAreaForTencent();
        return Result.success();
    }
}
