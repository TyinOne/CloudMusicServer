package com.tyin.cloud.model.base;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.util.Date;

/**
 * @author Tyin
 * @date 2022/3/30 10:11
 * @description ...
 */
@Data
public class BaseEntity {
    @TableField(fill = FieldFill.INSERT)
    protected Date created;

    /**
     * modified
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    protected Date modified;

    /**
     * deleted
     */
    @TableLogic
    protected Boolean deleted;
}
