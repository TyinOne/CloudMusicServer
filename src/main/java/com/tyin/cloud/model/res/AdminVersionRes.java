package com.tyin.cloud.model.res;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tyin.cloud.core.utils.DateUtils;
import com.tyin.cloud.core.utils.VersionUtils;
import lombok.Data;

import java.util.Date;

/**
 * @author Tyin
 * @date 2022/6/2 13:39
 * @description ...
 */
@Data
public class AdminVersionRes {
    private Long id;
    private String version;
    private String src;
    @JsonFormat(pattern = DateUtils.YYYY_MM_DD_HH_MM_SS)
    private Date releaseTime;
    private Boolean forced;
    private Boolean latest;
    private String hash;
    private String  md5;
    private String updateLog;

    public void setVersion(Long version) {
        this.version = VersionUtils.longToString(version);
    }
}
