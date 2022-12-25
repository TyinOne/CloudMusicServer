package com.tyin.core.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tyin.core.api.PageResult;
import com.tyin.core.module.bean.DictLabel;
import com.tyin.core.module.entity.AdminDict;
import com.tyin.core.module.entity.AdminDictType;
import com.tyin.core.module.res.admin.AdminDictRes;
import com.tyin.core.module.res.admin.AdminDictTypeRes;
import com.tyin.core.module.valid.SaveDictTypeValid;
import com.tyin.core.module.valid.SaveDictValid;
import com.tyin.core.repository.admin.AdminDictRepository;
import com.tyin.core.repository.admin.AdminDictTypeRepository;
import com.tyin.core.service.IAdminDictService;
import com.tyin.core.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * @author Tyin
 * @date 2022/5/8 22:47
 * @description ...
 */
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
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
                .apply("deleted = 0")
                .orderByAsc(AdminDict::getDictType)
                .orderByAsc(AdminDict::getId)
        );
        return PageResult.buildResult(resPage);
    }

    @Override
    public PageResult<AdminDictTypeRes, ?> getDictTypeList(String keywords, Boolean deleted, Long size, Long current) {
        IPage<AdminDictTypeRes> resPage = adminDictTypeRepository.selectTypeListPages(new Page<>(current, size),
                Wrappers.<AdminDictType>lambdaQuery()
                        .eq(AdminDictType::getDeleted, deleted)
                        .apply(" INSTR(`dict_label`, {0}) > 0", keywords)
        );
        return PageResult.buildResult(resPage);
    }

    @Override
    public Integer saveDict(SaveDictValid valid) {
        AdminDict adminDict = AdminDict.builder()
                .id(valid.getId())
                .dictType(valid.getDictType())
                .dictLabel(adminDictTypeRepository.selectLabelByType(valid.getDictType()))
                .dictKey(valid.getDictKey())
                .dictValue(valid.getDictValue())
                .dictDescription(valid.getDictDescription())
                .build();
        return Objects.nonNull(adminDict.getId()) ? adminDictRepository.updateById(adminDict) : adminDictRepository.insert(adminDict);
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

    @Override
    public Integer removeDict(Long id) {
        return adminDictRepository.deleteById(id);
    }

    @Override
    public Integer removeDictType(Long id) {
        return adminDictTypeRepository.deleteById(id);
    }

    @Override
    public Integer saveDictType(SaveDictTypeValid valid) {
        AdminDictType adminDictType = AdminDictType.builder()
                .id(valid.getId())
                .dictType(valid.getDictType())
                .dictLabel(valid.getDictLabel())
                .dictDescription(valid.getDictDescription())
                .build();
        return Objects.nonNull(adminDictType.getId()) ? adminDictTypeRepository.updateById(adminDictType) : adminDictTypeRepository.insert(adminDictType);
    }

    private LambdaQueryWrapper<AdminDict> getTypeKeyWrappers(String type, String key) {
        return Wrappers.<AdminDict>lambdaQuery().eq(AdminDict::getDictType, type)
                .eq(AdminDict::getDictKey, key);
    }
}
