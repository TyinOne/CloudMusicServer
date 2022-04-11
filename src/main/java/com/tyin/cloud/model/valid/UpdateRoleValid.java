package com.tyin.cloud.model.valid;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Tyin
 * @date 2022/4/9 18:28
 * @description ...
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UpdateRoleValid extends InsertRoleValid{
    private Long id;
}
