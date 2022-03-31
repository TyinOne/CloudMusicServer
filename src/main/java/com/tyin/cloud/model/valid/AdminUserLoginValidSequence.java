package com.tyin.cloud.model.valid;

import com.tyin.cloud.model.valid.sequence.PasswordCheck;
import com.tyin.cloud.model.valid.sequence.UsernameCheck;
import jakarta.validation.GroupSequence;

/**
 * @author Tyin
 * @date 2022/3/31 11:34
 * @description ...
 */
@GroupSequence({UsernameCheck.class, PasswordCheck.class})
public interface AdminUserLoginValidSequence {
}
