package com.tyin.cloud.core.configs.api.res;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Tyin
 * @date 2022/4/22 9:02
 * @description ...
 */
@Data
public class TencentMapDistrictRes {
    private Integer status;
    private String message;
    @JsonProperty("data_version")
    private Long dataVersion;
    private List<List<DistrictResultItem>> result;

    @Data
    public static class DistrictResult {
        private List<DistrictResultItems> list;
    }

    @Data
    public static class DistrictResultItems {
        private List<DistrictResultItem> list;
    }

    @Data
    public static class DistrictResultItem {
        private String id;
        private String parentId;
        private String name;
        private String fullname;
        private Integer level;
        private Location location;
        private List<Integer> cidx;
        private List<DistrictResultItem> children;
    }

    @Data
    public static class Location {
        private BigDecimal lat;
        private BigDecimal lng;
    }
}
