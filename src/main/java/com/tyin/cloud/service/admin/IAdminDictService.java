package com.tyin.cloud.service.admin;

import com.tyin.cloud.core.api.PageResult;
import com.tyin.cloud.model.bean.DictLabel;
import com.tyin.cloud.model.res.AdminDictRes;

import java.util.List;

/**
 * @author Tyin
 * @date 2022/5/8 22:47
 * @description ...
 */
public interface IAdminDictService {

    List<DictLabel> getDictLabel();

    PageResult<AdminDictRes, ?> getDictList(String keywords, String dictKey, String dictType, Long size, Long current);
}
