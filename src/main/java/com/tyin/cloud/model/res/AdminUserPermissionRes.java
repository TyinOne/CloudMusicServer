package com.tyin.cloud.model.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Tyin
 * @date 2022/3/31 17:09
 * @description ...
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminUserPermissionRes {
    private String token;
    private String name;
    private String roleName;
    //（未读）消息
    //（）
}
