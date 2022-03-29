package com.tyin.cloud.controller.client;

import com.tyin.cloud.core.api.Result;
import com.tyin.cloud.model.res.SwiperRes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Tyin
 * @date 2022/3/29 22:27
 * @description ...
 */
@RestController
@RequestMapping("${cloud.api.prefix.client}")
public class SwiperController {
    @GetMapping("/swiper/list")
    public Result<SwiperRes> getList() {
        SwiperRes res = new SwiperRes();
        return Result.success(res);
    }
}
