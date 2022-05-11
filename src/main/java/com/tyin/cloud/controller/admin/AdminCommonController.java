package com.tyin.cloud.controller.admin;

import com.tyin.cloud.core.annotations.Auth;
import com.tyin.cloud.core.annotations.NoLog;
import com.tyin.cloud.core.annotations.Open;
import com.tyin.cloud.core.api.Result;
import com.tyin.cloud.core.auth.AuthAdminUser;
import com.tyin.cloud.core.components.UploadComponents;
import com.tyin.cloud.model.res.UploadTmpRes;
import com.tyin.cloud.service.admin.IAdminRegionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    private final UploadComponents uploadComponents;

    @Open
    @GetMapping("/area")
    public Result<?> getAreaForTencent() {
        adminRegionService.getAreaForTencent();
        return Result.success();
    }

    @NoLog
    @PostMapping("/images/upload/tmp")
    public Result<UploadTmpRes> uploadImage(@RequestParam MultipartFile file, @Auth AuthAdminUser user) {
        UploadTmpRes tmpRes = uploadComponents.upload(file, user);
        return Result.success(tmpRes);
    }

    @NoLog
    @Open
    @GetMapping("/test")
    public Result<?> testController() {
        return Result.success();
    }
}
