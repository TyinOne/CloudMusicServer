package com.tyin.cloud.model.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Tyin
 * @date 2022/4/28 0:45
 * @description ...
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminUpdateRes {
    private String version;
    private String name;
    private String path;
    private String hash;

}
