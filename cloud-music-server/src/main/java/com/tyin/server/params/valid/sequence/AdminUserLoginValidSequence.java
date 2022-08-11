package com.tyin.server.params.valid.sequence;

import javax.validation.GroupSequence;

/**
 * @author Tyin
 * @date 2022/3/31 11:34
 * @description ...
 */
@GroupSequence({UsernameBankCheck.class, UsernameLengthCheck.class, PasswordBankCheck.class, PasswordLengthCheck.class})
public interface AdminUserLoginValidSequence {
}
