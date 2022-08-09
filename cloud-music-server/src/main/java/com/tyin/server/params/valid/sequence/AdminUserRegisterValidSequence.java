package com.tyin.server.params.valid.sequence;

import javax.validation.GroupSequence;

/**
 * @author Tyin
 * @date 2022/7/27 10:05
 * @description ...
 */
@GroupSequence({InviteCheck.class, UsernameBankCheck.class, UsernameLengthCheck.class, PasswordBankCheck.class, PasswordLengthCheck.class})
public interface AdminUserRegisterValidSequence {
}
