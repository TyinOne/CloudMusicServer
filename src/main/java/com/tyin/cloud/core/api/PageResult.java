package com.tyin.cloud.core.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;

import java.util.List;

/**
 * @author Tyin
 * @date 2022/3/26 3:05
 * @description ...
 */
@Data
public class PageResult<T, E> {

    private List<T> list;
    private Long size;
    private Long pages;
    private Long current;
    private Long total;
    private E extra;

    private Long timestamp = System.currentTimeMillis();

    private PageResult(IPage<T> page, E extra) {
        this.list = page.getRecords();
        this.size = page.getSize();
        this.current = page.getCurrent();
        this.total = page.getTotal();
        this.extra = extra;
        this.pages = (long) (int) Math.ceil((double) page.getTotal() / (double) page.getSize());
    }

    private PageResult(IPage<T> page) {
        this.list = page.getRecords();
        this.size = page.getSize();
        this.current = page.getCurrent();
        this.total = page.getTotal();
        this.pages = (long) (int) Math.ceil((double) page.getTotal() / (double) page.getSize());
    }

    public static <T, E> PageResult<T, E> buildResult(IPage<T> resPage, E extra) {
        return new PageResult<>(resPage, extra);
    }

    public static <T, E> PageResult<T, E> buildResult(IPage<T> resPage) {
        return new PageResult<>(resPage);
    }
}
