package com.tyin.cloud.controller;

import com.tyin.cloud.core.annotations.Open;
import com.tyin.cloud.core.api.Result;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Tyin
 * @date 2022/4/8 15:53
 * @description ...
 */
@RestController
@Hidden
public class ErrorController {
    @Open
    @GetMapping("/error")
    public Result<?> error() {
        return Result.failed();
    }
}
