package com.tyin.server.controller;


import com.tyin.core.annotations.Auth;
import com.tyin.core.api.Result;
import com.tyin.core.module.bean.AuthAdminUser;
import com.tyin.core.module.res.admin.AdminRegionListRes;
import com.tyin.core.module.res.admin.AdminRegionRes;
import com.tyin.core.service.IAdminRegionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Tyin
 * @date 2022/5/22 13:50
 * @description ...
 */
@RestController
@RequestMapping("${cloud.api.prefix.admin}/region")
@RequiredArgsConstructor
@Tag(name = "地区管理-省市区相关接口")
public class AdminRegionController {
    private final IAdminRegionService regionService;

    @Operation(description = "省市区列表")
    @GetMapping(value = {"/list/{parentId}"})
    public Result<AdminRegionListRes> getRegionList(@Parameter(description = "父级ID") @PathVariable(name = "parentId") Long parentId,
                                                    @Parameter(description = "关键词") @RequestParam(required = false) String keywords,
                                                    @Parameter(description = "查询级别") @RequestParam(required = false, defaultValue = "0") Integer level,
                                                    @Parameter(hidden = true) @Auth AuthAdminUser ignoredUser) {
        List<AdminRegionRes> list = regionService.selectListBy(parentId, keywords, level);
        return Result.success(AdminRegionListRes.builder().list(list).build());
    }
}
