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
                .version("1.0.1")
                .name("6eb17.zip")
                .path("https://file.tyin.vip/downloads/hotUpdate/6eb17.zip")
                .hash("e19a39e6eb17f641be8d26ac1a839c8abab159cf90be3e23d0ee165b0d05a572")
                .build());
    }
}
