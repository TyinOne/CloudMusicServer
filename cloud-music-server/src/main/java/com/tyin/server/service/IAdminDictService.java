package com.tyin.server.service;


import com.tyin.core.module.bean.DictLabel;
import com.tyin.core.module.res.admin.AdminDictRes;
import com.tyin.core.module.res.admin.AdminDictTypeRes;
import com.tyin.server.api.PageResult;
import com.tyin.server.params.valid.SaveDictTypeValid;
import com.tyin.server.params.valid.SaveDictValid;

import java.util.List;

/**
 * @author Tyin
 * @date 2022/5/8 22:47
 * @description ...
 */
public interface IAdminDictService {

    /**
     * 获取字典分类下拉框
     *
     * @return list
     */
    List<DictLabel> getDictLabel();

    /**
     * 字典列表数据接口
     *
     * @param keywords 关键词
     * @param dictKey  key
     * @param dictType 字典分类
     * @param size     分页长度
     * @param current  当前页
     * @return result
     */
    PageResult<AdminDictRes, ?> getDictList(String keywords, String dictKey, String dictType, Long size, Long current);

    /**
     * 字典分类列表
     *
     * @param keywords 关键词
     * @param deleted  字典状态
     * @param size     分页长度
     * @param current  当前页
     * @return result
     */
    PageResult<AdminDictTypeRes, ?> getDictTypeList(String keywords, Boolean deleted, Long size, Long current);

    /**
     * 保存字典数据
     *
     * @param valid valid
     * @return 受影响行数
     */
    Integer saveDict(SaveDictValid valid);

    /**
     * 更新字典数据
     *
     * @param type  字典分类
     * @param key   key
     * @param value value
     */
    void updateValueBy(String type, String key, String value);

    /**
     * 查询字典value
     *
     * @param type 字典分类
     * @param key  key
     * @return value
     */
    String selectValueByTypeKey(String type, String key);

    /**
     * 删除字典数据
     *
     * @param id id
     * @return 受影响行数
     */
    Integer removeDict(Long id);

    /**
     * 删除字典分类数据
     *
     * @param id id
     * @return 受影响行数
     */
    Integer removeDictType(Long id);

    /**
     * 保存字典分类
     *
     * @param valid valid
     * @return 受影响行数
     */
    Integer saveDictType(SaveDictTypeValid valid);
}
