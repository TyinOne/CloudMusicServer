package com.tyin.cloud.controller.admin;

import com.tyin.cloud.core.annotations.Auth;
import com.tyin.cloud.core.api.PageResult;
import com.tyin.cloud.core.api.Result;
import com.tyin.cloud.core.auth.AuthAdminUser;
import com.tyin.cloud.model.res.AdminDictRes;
import com.tyin.cloud.model.valid.SaveDictValid;
import com.tyin.cloud.service.admin.IAdminDictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author Tyin
 * @date 2022/5/8 22:46
 * @description ...
 */
@Api(tags = "字典管理-字典相关接口")
@RestController
@RequestMapping("${cloud.api.prefix.admin}/dict")
@RequiredArgsConstructor
public class AdminDictController {
    private final IAdminDictService adminDictService;

    @GetMapping("/list")
    @ApiOperation("字典列表")
    public Result<PageResult<AdminDictRes, ?>> getDictList(@ApiParam("关键词") @RequestParam(required = false) String keywords,
                                                           @ApiParam("字典key") @RequestParam(required = false) String dictKey,
                                                           @ApiParam("字典type") @RequestParam(required = false) String dictType,
                                                           @ApiParam("分页长度") @RequestParam(required = false, defaultValue = "20") Long size,
                                                           @ApiParam("当前页") @RequestParam(required = false, defaultValue = "1") Long current,
                                                           @Auth("@permission.hasPermission('sys:dict:query')") AuthAdminUser user) {
        PageResult<AdminDictRes, ?> res = adminDictService.getDictList(keywords, dictKey, dictType, size, current);
        return Result.success(res);
    }
    @PostMapping("/save")
    @ApiOperation("保存字典")
    public Result<?> saveDict(@Validated @RequestBody SaveDictValid valid, @Auth("@permission.hasPermission('sys:dict:add')") AuthAdminUser user) {
        adminDictService.save(valid);
        return Result.success();
    }

    @PostMapping("/type/save")
    @ApiOperation("保存字典分类")
    public Result<?> saveDictType(@Auth("@permission.hasPermission('sys:dict:add')") AuthAdminUser user) {
        return Result.success();
    }

}
