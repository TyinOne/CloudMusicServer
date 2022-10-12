package com.tyin.server.controller;


import com.tyin.core.annotations.Auth;
import com.tyin.core.annotations.NoLog;
import com.tyin.core.module.bean.AuthAdminUser;
import com.tyin.core.module.res.admin.UpdateJsonUploadRes;
import com.tyin.core.module.res.admin.UploadTmpRes;
import com.tyin.core.utils.JsonUtils;
import com.tyin.server.api.Result;
import com.tyin.server.components.UploadComponents;
import com.tyin.server.service.IAdminRegionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * @author Tyin
 * @date 2022/4/22 1:40
 * @description ...
 */
@RestController
@RequestMapping("${cloud.api.prefix.admin}/common")
@RequiredArgsConstructor
@Tag(name = "公共接口-OpenSDK")
public class AdminCommonController {
    private final IAdminRegionService adminRegionService;
    private final UploadComponents uploadComponents;

    @GetMapping("/area")
    @Operation(description = "更新省市区截数据")
    public Result<?> getAreaForTencent() {
        adminRegionService.getAreaForTencent();
        return Result.success();
    }

    @NoLog
    @Operation(description = "上传图片至临时目录")
    @PostMapping("/images/upload/tmp")
    public Result<UploadTmpRes> uploadImage(@Parameter(description = "文件参数") @RequestParam MultipartFile file, @Parameter(hidden = true) @Auth AuthAdminUser user) {
        UploadTmpRes tmpRes = uploadComponents.uploadTmp(file, user);
        return Result.success(tmpRes);
    }

    @NoLog
    @Operation(description = "上传更新包至临时目录")
    @PostMapping("/package/upload/tmp")
    public Result<UploadTmpRes> uploadPackage(@Parameter(description = "文件参数") @RequestParam MultipartFile updatePackage, @Parameter(hidden = true) @Auth AuthAdminUser user) {
        UploadTmpRes tmpRes = uploadComponents.uploadPackageTmp(updatePackage, user);
        return Result.success(tmpRes);
    }

    @NoLog
    @Operation(description = "解析更新Json文件")
    @PostMapping("/parse/update")
    public Result<UpdateJsonUploadRes> parseUpdateJson(@Parameter(description = "文件参数") @RequestParam MultipartFile updateJson, @Parameter(hidden = true) @Auth AuthAdminUser ignoredUser) {
        StringBuilder stringBuilder;
        String json = "";
        try {
            /*
             * 文件有内容才去读文件
             */
            InputStreamReader streamReader = new InputStreamReader(updateJson.getInputStream(), StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(streamReader);
            String line;
            stringBuilder = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            json = stringBuilder.toString();
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.success(JsonUtils.toJavaObject(json, UpdateJsonUploadRes.class));
    }

    @NoLog
    @Operation(description = "测试接口")
    @GetMapping("/test")
    public Result<?> testController() {
        return Result.success();
    }
}
