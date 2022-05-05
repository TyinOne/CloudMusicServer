package com.tyin.cloud.controller.admin;

import com.tyin.cloud.core.annotations.Open;
import com.tyin.cloud.core.api.Result;
import com.tyin.cloud.model.res.AdminUpdateRes;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Tyin
 * @date 2022/4/28 0:44
 * @description ...
 */
@RestController
@RequestMapping("${cloud.api.prefix.admin}/update")
@RequiredArgsConstructor
public class AdminUpdateController {
    @Open
    @GetMapping("/check")
    public Result<AdminUpdateRes> checkUpdate() {
        return Result.success(AdminUpdateRes.builder()
                        .version("1.0.01")
                        .name("309a5.zip")
                        .path("http://1.14.43.64/update-hot/v1.0.1/309a5.zip")
                        .hash("fe20e4e309a58021a2048d18fb4468d7610688db1698144f43f0dab0b5fa3d36")
                .build());
    }
}
