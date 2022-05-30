package com.tyin.cloud.controller.admin;

import com.tyin.cloud.core.annotations.Open;
import com.tyin.cloud.core.api.Result;
import com.tyin.cloud.core.utils.Asserts;
import com.tyin.cloud.core.utils.JsonUtils;
import com.tyin.cloud.model.res.AdminUpdateRes;
import com.tyin.cloud.service.admin.IAdminDictService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * @author Tyin
 * @date 2022/4/28 0:44
 * @description ...
 */
@RestController
@RequestMapping("${cloud.api.prefix.admin}/update")
@RequiredArgsConstructor
public class AdminUpdateController {
    private final IAdminDictService adminDictService;
    /**
     * path = oss_file_host + oss_update_uri + name
     */
    @Open
    @GetMapping("/check")
    public Result<AdminUpdateRes> checkUpdate() {
        String value = adminDictService.selectValueByTypeKey("hotUpdate", "update:latest");
        AdminUpdateRes adminUpdateRes = JsonUtils.toJavaObject(value, AdminUpdateRes.class);
        Asserts.isTrue(Objects.nonNull(adminUpdateRes), "获取版本信息失败");
        String ossFileHost = adminDictService.selectValueByTypeKey("oss", "oss_file_host");
        String ossUpdateUri = adminDictService.selectValueByTypeKey("oss", "oss_update_uri");
        adminUpdateRes.setPath(ossFileHost + ossUpdateUri + adminUpdateRes.getName());
        return Result.success(adminUpdateRes);
    }
}
