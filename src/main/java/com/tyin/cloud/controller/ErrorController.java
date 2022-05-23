package com.tyin.cloud.controller;

import com.tyin.cloud.core.annotations.Open;
import com.tyin.cloud.core.api.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import static com.tyin.cloud.core.constants.CommonConstants.ERROR_URI;

/**
 * @author Tyin
 * @date 2022/4/8 15:53
 * @description ...
 */
@RestController
@ApiIgnore
public class ErrorController {
    @Open
    @GetMapping(ERROR_URI)
    public Result<?> error() {
        return Result.failed();
    }
}
