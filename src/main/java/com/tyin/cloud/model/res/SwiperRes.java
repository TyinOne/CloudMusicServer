package com.tyin.cloud.model.res;

import lombok.Data;

import java.util.List;

/**
 * @author Tyin
 * @date 2022/3/29 22:28
 * @description ...
 */
@Data
public class SwiperRes {

    private List<SwiperResBean> list;

    @Data
    public static class SwiperResBean {
        private String uuid;
        private String url;
        private String type;
        private String target;
    }
}
