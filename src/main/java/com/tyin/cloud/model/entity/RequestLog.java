package com.tyin.cloud.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

/**
 * @author Tyin
 * @date 2022/3/30 23:21
 * @description ...
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestLog extends BaseEntity {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String uri;
    private Integer ip;
    private String method;
    private String params;
    private String result;
    private Long elapsed;
}
