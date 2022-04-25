package com.tyin.cloud.model.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

/**
 * @author Tyin
 * @date 2022/3/31 13:47
 * @description ...
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminUserLoginRes {
    private String token;
    private String avatar;
    private String nickName;
    private List<String> btn;
    private Set<String> roles;
}
