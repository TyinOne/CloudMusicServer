package com.tyin.cloud.controller.admin;

import com.tyin.cloud.core.api.PageResult;
import com.tyin.cloud.core.api.Result;
import com.tyin.cloud.model.res.AdminDictRes;
import com.tyin.cloud.service.admin.IAdminDictService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Tyin
 * @date 2022/5/8 22:46
 * @description ...
 */
@RestController
@RequestMapping("${cloud.api.prefix.admin}/dict")
@RequiredArgsConstructor
public class AdminDictController {
    private final IAdminDictService adminDictService;

    @GetMapping("/list")
    public Result<PageResult<AdminDictRes, ?>> getDictList(@RequestParam(required = false) String keywords,
                                                           @RequestParam(required = false) String dictKey,
                                                           @RequestParam(required = false) String dictType,
                                                           @RequestParam(required = false, defaultValue = "20") Long size,
                                                           @RequestParam(required = false, defaultValue = "1") Long current) {
        PageResult<AdminDictRes, ?> res = adminDictService.getDictList(keywords, dictKey, dictType, size, current);
        return Result.success(res);
    }
}
