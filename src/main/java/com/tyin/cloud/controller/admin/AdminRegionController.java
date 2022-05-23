package com.tyin.cloud.controller.admin;

import com.tyin.cloud.core.api.Result;
import com.tyin.cloud.model.res.AdminRegionListRes;
import com.tyin.cloud.model.res.AdminRegionRes;
import com.tyin.cloud.service.admin.IAdminRegionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
@Api(tags = "地区管理-省市区相关接口")
public class AdminRegionController {
    private final IAdminRegionService regionService;

    @ApiOperation("省市区列表")
    @GetMapping(value = {"/list/{parentId}"})
    public Result<AdminRegionListRes> getRegionList(@ApiParam("父级ID") @PathVariable(name = "parentId") Long parentId,
                                                    @ApiParam("关键词") @RequestParam(required = false) String keywords,
                                                    @ApiParam("查询级别") @RequestParam(required = false, defaultValue = "0") Integer level) {
        List<AdminRegionRes> list = regionService.selectListBy(parentId, keywords, level);
        return Result.success(AdminRegionListRes.builder().list(list).build());
    }
}
