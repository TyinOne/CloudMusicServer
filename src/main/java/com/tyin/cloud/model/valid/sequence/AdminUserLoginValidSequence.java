package com.tyin.cloud.model.valid.sequence;

import jakarta.validation.GroupSequence;

/**
 * @author Tyin
 * @date 2022/3/31 11:34
 * @description ...
 */
@GroupSequence({UsernameCheck.class, PasswordCheck.class})
public interface AdminUserLoginValidSequence {
}
