package com.tyin.core.module.res.admin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tyin.core.module.bean.AuthAdminUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

/**
 * @author Tyin
 * @date 2022/3/31 13:47
 * @description ...
 */
@NoArgsConstructor
@JsonIgnoreProperties({"accountNonLocked", "credentialsNonExpired", "accountNonExpired", "enabled"})
public class AdminUserLoginRes extends AuthAdminUser implements UserDetails {
    @JsonIgnore
//    @Getter
//    @Setter
//    private String key;
    @Getter
    @Setter
    private Long loginTime;
    @Getter
    @Setter
    private Long expireTime;

    public AdminUserLoginRes(Long id, String token, String uuid, String nickName, String account, String avatar, Set<String> roles, Set<String> permissions, Boolean disabled) {
        super(id, token, uuid, nickName, account, avatar, roles, permissions, disabled, "");
//        this.key = key;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return super.getPassword();
    }

    public void setPassword(String password) {
        super.setPassword(password);
    }

    @Override
    public String getUsername() {
        return super.getAccount();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return !super.getDisabled();
    }
}
