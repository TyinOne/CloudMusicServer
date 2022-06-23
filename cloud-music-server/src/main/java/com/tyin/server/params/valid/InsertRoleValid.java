package com.tyin.server.params.valid;

import lombok.Data;

import java.util.List;

/**
 * @author Tyin
 * @date 2022/4/9 17:57
 * @description ...
 */
@Data
public class InsertRoleValid {
    private String name;
    private String value;
    private Integer sort;
    private String description;
    private Boolean disabled;
    private List<String> menu;
    private List<String> half;
}
