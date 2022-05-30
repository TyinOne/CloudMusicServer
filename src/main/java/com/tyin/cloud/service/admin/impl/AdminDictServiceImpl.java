package com.tyin.cloud.service.admin.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tyin.cloud.core.api.PageResult;
import com.tyin.cloud.core.utils.Asserts;
import com.tyin.cloud.model.bean.DictLabel;
import com.tyin.cloud.model.entity.AdminDict;
import com.tyin.cloud.model.res.AdminDictRes;
import com.tyin.cloud.model.valid.SaveDictValid;
import com.tyin.cloud.repository.admin.AdminDictRepository;
import com.tyin.cloud.repository.admin.AdminDictTypeRepository;
import com.tyin.cloud.service.admin.IAdminDictService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author Tyin
 * @date 2022/5/8 22:47
 * @description ...
 */
@Service
@RequiredArgsConstructor
public class AdminDictServiceImpl implements IAdminDictService {
    private final AdminDictRepository adminDictRepository;
    private final AdminDictTypeRepository adminDictTypeRepository;

    @Override
    public List<DictLabel> getDictLabel() {
        return adminDictTypeRepository.selectDictLabel();
    }

    @Override
    public PageResult<AdminDictRes, ?> getDictList(String keywords, String dictKey, String dictType, Long size, Long current) {
        IPage<AdminDictRes> resPage = adminDictRepository.selectListPages(new Page<>(current, size), Wrappers.<AdminDict>lambdaQuery()
                .eq(StringUtils.isNotBlank(dictKey), AdminDict::getDictKey, dictKey)
                .eq(StringUtils.isNotBlank(dictType), AdminDict::getDictType, dictType)
                .and(StringUtils.isNotBlank(keywords), i -> i.apply("INSTR(`dict_label`, {0}) > 0", keywords)
                        .or().apply(" INSTR(`dict_type`, {0}) > 0", keywords)
                        .or().apply(" INSTR(`dict_key`, {0}) > 0", keywords))
                .orderByAsc(AdminDict::getDictType)
                .orderByAsc(AdminDict::getId)
        );
        return PageResult.buildResult(resPage);
    }

    @Override
    public void save(SaveDictValid valid) {
        AdminDict adminDict = AdminDict.builder()
                .id(valid.getId())
                .dictType(valid.getDictType())
                .dictLabel(adminDictTypeRepository.selectLabelByType(valid.getDictType()))
                .dictKey(valid.getDictKey())
                .dictValue(valid.getDictValue())
                .dictDescription(valid.getDictDescription())
                .build();
        if (Objects.nonNull(valid.getId())) {
            adminDictRepository.updateById(adminDict);
        } else {
            Asserts.isTrue(adminDictRepository.insert(adminDict) == 1, "操作失败");
        }
    }

    @Override
    public void updateValueBy(String type, String key, String value) {
        AdminDict adminDict = AdminDict.builder()
                .dictValue(value)
                .build();
        adminDictRepository.update(adminDict, getTypeKeyWrappers(type, key)
        );
    }

    @Override
    public String selectValueByTypeKey(String type, String key) {
        return adminDictRepository.selectOne(getTypeKeyWrappers(type, key)).getDictValue();
    }

    private LambdaQueryWrapper<AdminDict> getTypeKeyWrappers(String type, String key) {
        return Wrappers.<AdminDict>lambdaQuery().eq(AdminDict::getDictType, type)
                .eq(AdminDict::getDictKey, key);
    }
}
