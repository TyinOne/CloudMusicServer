package com.tyin.core.module.res.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Tyin
 * @date 2022/5/22 13:54
 * @description ...
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminRegionListRes {
    private List<AdminRegionRes> list;
}
