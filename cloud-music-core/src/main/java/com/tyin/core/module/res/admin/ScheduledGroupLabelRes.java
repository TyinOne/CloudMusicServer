package com.tyin.core.module.res.admin;

import com.tyin.core.module.bean.ScheduledGroupLabel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Tyin
 * @date 2022/11/7 14:03
 * @description ...
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScheduledGroupLabelRes {
    private List<ScheduledGroupLabel> list;
}
