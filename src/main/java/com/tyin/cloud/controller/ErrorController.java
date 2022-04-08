package com.tyin.cloud.controller;

import com.tyin.cloud.core.api.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Tyin
 * @date 2022/4/8 15:53
 * @description ...
 */
@RestController
public class ErrorController {

    @GetMapping("/error")
    public Result<?> error() {
        return Result.failed();
    }
}
