package com.tyin.cloud.controller.admin;

import com.tyin.cloud.core.annotations.Auth;
import com.tyin.cloud.core.annotations.NoLog;
import com.tyin.cloud.core.annotations.Open;
import com.tyin.cloud.core.api.Result;
import com.tyin.cloud.core.auth.AuthAdminUser;
import com.tyin.cloud.core.components.UploadComponents;
import com.tyin.cloud.core.utils.JsonUtils;
import com.tyin.cloud.model.res.UpdateJsonUploadRes;
import com.tyin.cloud.model.res.UploadTmpRes;
import com.tyin.cloud.service.admin.IAdminRegionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
@Api(tags = "公共接口-OpenSDK")
public class AdminCommonController {
    private final IAdminRegionService adminRegionService;
    private final UploadComponents uploadComponents;

    @Open
    @GetMapping("/area")
    @ApiOperation("更新省市区截数据")
    public Result<?> getAreaForTencent() {
        adminRegionService.getAreaForTencent();
        return Result.success();
    }

    @NoLog
    @ApiOperation("上传图片至临时目录")
    @PostMapping("/images/upload/tmp")
    public Result<UploadTmpRes> uploadImage(@ApiParam("文件参数") @RequestParam MultipartFile file, @Auth AuthAdminUser user) {
        UploadTmpRes tmpRes = uploadComponents.uploadTmp(file, user);
        return Result.success(tmpRes);
    }
    @NoLog
    @ApiOperation("上传更新包至临时目录")
    @PostMapping("/package/upload/tmp")
    public Result<UploadTmpRes> uploadPackage(@ApiParam("文件参数") @RequestParam MultipartFile updatePackage, @Auth AuthAdminUser user) {
        UploadTmpRes tmpRes = uploadComponents.uploadPackageTmp(updatePackage, user);
        return Result.success(tmpRes);
    }
    @NoLog
    @ApiOperation("解析更新Json文件")
    @PostMapping("/parse/update")
    public Result<UpdateJsonUploadRes> parseUpdateJson(@ApiParam("文件参数") @RequestParam MultipartFile updateJson, @Auth AuthAdminUser user) {
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
    @Open
    @ApiOperation("测试接口")
    @GetMapping("/test")
    public Result<?> testController() {
        return Result.success();
    }
}
