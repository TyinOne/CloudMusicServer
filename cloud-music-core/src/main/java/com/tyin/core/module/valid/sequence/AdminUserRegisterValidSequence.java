package com.tyin.core.module.valid.sequence;

import jakarta.validation.GroupSequence;

/**
 * @author Tyin
 * @date 2022/7/27 10:05
 * @description ...
 */
@GroupSequence({InviteCheck.class, UsernameBankCheck.class, UsernameLengthCheck.class, PasswordBankCheck.class, PasswordLengthCheck.class})
public interface AdminUserRegisterValidSequence {
}
