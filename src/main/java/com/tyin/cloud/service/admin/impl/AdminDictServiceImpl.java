package com.tyin.cloud.service.admin.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tyin.cloud.core.api.PageResult;
import com.tyin.cloud.model.bean.DictLabel;
import com.tyin.cloud.model.entity.AdminDict;
import com.tyin.cloud.model.res.AdminDictRes;
import com.tyin.cloud.repository.admin.AdminDictRepository;
import com.tyin.cloud.service.admin.IAdminDictService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Tyin
 * @date 2022/5/8 22:47
 * @description ...
 */
@Service
@RequiredArgsConstructor
public class AdminDictServiceImpl implements IAdminDictService {
    private final AdminDictRepository adminDictRepository;

    @Override
    public List<DictLabel> getDictLabel() {
        return adminDictRepository.selectDictLabel();
    }

    @Override
    public PageResult<AdminDictRes, ?> getDictList(String keywords, String dictKey, String dictType, Long size, Long current) {
        IPage<AdminDictRes> resPage = adminDictRepository.selectListPages(new Page<>(current, size), Wrappers.<AdminDict>lambdaQuery()
                .eq(StringUtils.isNotBlank(dictKey), AdminDict::getDictKey, dictKey)
                .eq(StringUtils.isNotBlank(dictType), AdminDict::getDictType, dictType)
                .and(StringUtils.isNotBlank(keywords), i -> i.apply("INSTR(`dict_label`, {0}) > 0", keywords)
                        .or().apply(" INSTR(`dict_type`, {0}) > 0", keywords)
                        .or().apply(" INSTR(`dict_key`, {0}) > 0", keywords)));
        return PageResult.buildResult(resPage);
    }
}
