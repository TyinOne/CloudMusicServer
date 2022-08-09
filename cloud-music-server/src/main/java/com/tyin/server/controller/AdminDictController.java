package com.tyin.server.controller;

import com.tyin.core.annotations.Auth;
import com.tyin.core.constants.ResMessageConstants;
import com.tyin.core.module.bean.AuthAdminUser;
import com.tyin.core.module.res.admin.AdminDictRes;
import com.tyin.core.module.res.admin.AdminDictTypeRes;
import com.tyin.core.utils.Asserts;
import com.tyin.server.api.PageResult;
import com.tyin.server.api.Result;
import com.tyin.server.components.SystemLoadComponents;
import com.tyin.server.params.valid.IdValid;
import com.tyin.server.params.valid.SaveDictTypeValid;
import com.tyin.server.params.valid.SaveDictValid;
import com.tyin.server.service.IAdminDictService;
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
    private final SystemLoadComponents systemLoadComponents;

    @GetMapping("/list")
    @ApiOperation("字典列表")
    public Result<PageResult<AdminDictRes, ?>> getDictList(@ApiParam("关键词") @RequestParam(required = false) String keywords,
                                                           @ApiParam("字典key") @RequestParam(required = false) String dictKey,
                                                           @ApiParam("字典type") @RequestParam(required = false) String dictType,
                                                           @ApiParam("分页长度") @RequestParam(required = false, defaultValue = "20") Long size,
                                                           @ApiParam("当前页") @RequestParam(required = false, defaultValue = "1") Long current,
                                                           @Auth("@permission.hasPermission('sys:dict:query')") AuthAdminUser ignoredUser) {
        PageResult<AdminDictRes, ?> res = adminDictService.getDictList(keywords, dictKey, dictType, size, current);
        return Result.success(res);
    }

    @GetMapping("/type/list")
    @ApiOperation("字典分类列表")
    public Result<PageResult<AdminDictTypeRes, ?>> getDictTypeList(@ApiParam("关键词") @RequestParam(required = false) String keywords,
                                                                   @ApiParam("是否启用") @RequestParam(required = false, defaultValue = "false") Boolean deleted,
                                                                   @ApiParam("分页长度") @RequestParam(required = false, defaultValue = "20") Long size,
                                                                   @ApiParam("当前页") @RequestParam(required = false, defaultValue = "1") Long current) {
        PageResult<AdminDictTypeRes, ?> res = adminDictService.getDictTypeList(keywords, deleted, size, current);
        return Result.success(res);
    }

    @PostMapping("/save")
    @ApiOperation("保存字典")
    public Result<?> saveDict(@Validated @RequestBody SaveDictValid valid, @Auth("@permission.hasPermission('sys:dict:add')") AuthAdminUser ignoredUser) {
        Integer row = adminDictService.saveDict(valid);
        Asserts.isTrue(row > 0, ResMessageConstants.SAVE_FAILED);
        return Result.success();
    }

    @PostMapping("/remove")
    @ApiOperation("删除字典数据")
    public Result<?> removeDict(@RequestBody IdValid valid) {
        Integer row = adminDictService.removeDict(valid.getId());
        Asserts.isTrue(row > 0, ResMessageConstants.REMOVE_FAILED);
        return Result.success();
    }

    @PostMapping("/type/save")
    @ApiOperation("保存字典分类")
    public Result<?> saveDictType(@RequestBody SaveDictTypeValid valid, @Auth("@permission.hasPermission('sys:dict:add')") AuthAdminUser ignoredUser) {
        Integer row = adminDictService.saveDictType(valid);
        Asserts.isTrue(row > 0, ResMessageConstants.SAVE_FAILED);
        return Result.success();
    }

    @PostMapping("/type/remove")
    @ApiOperation("删除字典数据")
    public Result<?> removeDictType(@RequestBody IdValid valid) {
        Integer row = adminDictService.removeDictType(valid.getId());
        Asserts.isTrue(row > 0, ResMessageConstants.REMOVE_FAILED);
        return Result.success();
    }

    @PutMapping("/update/cache")
    @ApiOperation("更新字典缓存")
    public Result<?> updateDictCache(@Auth AuthAdminUser ignoredUser) {
        systemLoadComponents.onLoad();
        return Result.success();
    }
}
