package com.tyin.cloud.core.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author Tyin
 * @date 2022/3/26 3:05
 * @description ...
 */
@Data
public class PageResult<T> {
    private List<T> list;
    private Long size;
    private Long pages;
    private Long current;
    private Long total;
    private Map<String, String> extra;

    public PageResult(IPage<T> page, Map<String, String> extra) {
        this.list = page.getRecords();
        this.size = page.getSize();
        this.current = page.getCurrent();
        this.total = page.getTotal();
        this.extra = extra;
        this.pages = (long) (int) Math.ceil((double) page.getTotal() / (double) page.getSize());
    }

    public PageResult(IPage<T> page) {
        this.list = page.getRecords();
        this.size = page.getSize();
        this.current = page.getCurrent();
        this.total = page.getTotal();
        this.pages = (long) (int) Math.ceil((double) page.getTotal() / (double) page.getSize());
    }
}
