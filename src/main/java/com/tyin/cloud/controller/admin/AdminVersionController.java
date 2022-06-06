package com.tyin.cloud.controller.admin;

import com.tyin.cloud.core.annotations.Open;
import com.tyin.cloud.core.api.PageResult;
import com.tyin.cloud.core.api.Result;
import com.tyin.cloud.core.utils.Asserts;
import com.tyin.cloud.core.utils.DateUtils;
import com.tyin.cloud.core.utils.JsonUtils;
import com.tyin.cloud.model.res.AdminUpdateRes;
import com.tyin.cloud.model.res.AdminVersionRes;
import com.tyin.cloud.model.valid.InsertVersionValid;
import com.tyin.cloud.service.admin.IAdminDictService;
import com.tyin.cloud.service.admin.IAdminVersionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Objects;

/**
 * @author Tyin
 * @date 2022/4/28 0:44
 * @description ...
 */
@RestController
@RequestMapping("${cloud.api.prefix.admin}/version")
@RequiredArgsConstructor
@Api(tags = "版本控制-版本控制相关接口")
public class AdminVersionController {
    private final IAdminDictService adminDictService;
    private final IAdminVersionService adminVersionService;

    @Open
    @GetMapping("/check")
    @ApiOperation("检查更新-热更新")
    public Result<AdminUpdateRes> checkUpdate() {
        String value = adminDictService.selectValueByTypeKey("hotUpdate", "update:latest");
        AdminUpdateRes adminUpdateRes = JsonUtils.toJavaObject(value, AdminUpdateRes.class);
        Asserts.isTrue(Objects.nonNull(adminUpdateRes), "获取版本信息失败");
        String ossFileHost = adminDictService.selectValueByTypeKey("oss", "oss_file_host");
        String ossUpdateUri = adminDictService.selectValueByTypeKey("oss", "oss_update_uri");
        adminUpdateRes.setPath(ossFileHost + ossUpdateUri + adminUpdateRes.getName());
        return Result.success(adminUpdateRes);
    }

    @GetMapping("/list")
    @ApiOperation("版本控制列表")
    public Result<PageResult<AdminVersionRes, ?>> getVersionList(@RequestParam(required = false) @DateTimeFormat(pattern = DateUtils.YYYY_MM_DD) Date startTime,
                                                                 @RequestParam(required = false) @DateTimeFormat(pattern = DateUtils.YYYY_MM_DD) Date stopTime,
                                                                 @RequestParam(required = false, defaultValue = "1") Long current,
                                                                 @RequestParam(required = false, defaultValue = "20") Long size) {
        PageResult<AdminVersionRes, ?> pageResult = adminVersionService.getVersionList(startTime, stopTime, current, size);
        return Result.success(pageResult);
    }

    @PostMapping("/add")
    @ApiOperation("新建版本")
    public Result<?> addVersion(@RequestBody InsertVersionValid valid) {
        Integer row = adminVersionService.addVersion(valid);
        Asserts.isTrue(row > 0, "新建版本失败");
        return Result.success();
    }
}
