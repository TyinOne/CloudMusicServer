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
                .version("1.0.2")
                .name("0a503.zip")
                .path("https://file.tyin.vip/downloads/hotUpdate/0a503.zip")
                .hash("1a05a850a503d41adc2688bf0626bfa0860431747db3023b944253ae242d0760")
                .build());
    }
}
