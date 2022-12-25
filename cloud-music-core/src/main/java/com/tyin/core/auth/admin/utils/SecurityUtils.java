package com.tyin.core.auth.admin.utils;

import com.tyin.core.exception.ApiException;
import com.tyin.core.module.res.admin.AdminUserLoginRes;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import static com.tyin.core.enums.ResultCode.SIGNATURE_NOT_MATCH;

/**
 * @author Tyin
 * @date 2022/10/9 10:10
 * @description ...
 */
public class SecurityUtils {

    public static AdminUserLoginRes getLoginUser() {
        try {
            return (AdminUserLoginRes) getAuthentication().getPrincipal();
        } catch (Exception e) {
            throw new ApiException(SIGNATURE_NOT_MATCH);
        }
    }

    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
