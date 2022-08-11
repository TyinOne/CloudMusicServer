package com.tyin.core.module.res.admin;


import com.tyin.core.module.bean.DictLabel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Tyin
 * @date 2022/5/8 22:49
 * @description ...
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DictLabelRes {
    private List<DictLabel> list;
}
