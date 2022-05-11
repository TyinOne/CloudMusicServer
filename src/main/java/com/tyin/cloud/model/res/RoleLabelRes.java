package com.tyin.cloud.model.res;

import com.tyin.cloud.model.bean.RoleLabel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Tyin
 * @date 2022/4/12 15:02
 * @description ...
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleLabelRes {
    private List<RoleLabel> list;
}
