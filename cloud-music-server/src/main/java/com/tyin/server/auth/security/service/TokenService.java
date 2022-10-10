package com.tyin.server.auth.security.service;

import com.tyin.core.components.RedisComponents;
import com.tyin.core.module.res.admin.AdminUserLoginRes;
import com.tyin.core.utils.JsonUtils;
import com.tyin.core.utils.StringUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.tyin.server.auth.security.constant.ConstantKey.*;

/**
 * @author Tyin
 * @date 2022/9/30 10:55
 * @description ...
 */
@Component
@RequiredArgsConstructor
public class TokenService {
    public final RedisComponents redisComponents;

    public String createToken(Map<String, Object> claims, String account) {
        long now = System.currentTimeMillis();
        long expiration = now + EXPIRE_TIME * MILLIS_MINUTE;
        return Jwts.builder().setClaims(claims).setIssuer(account).setIssuedAt(new Date(now)).setExpiration(new Date(expiration)).signWith(SignatureAlgorithm.HS512, SIGNING_KEY).compact();
    }

    public Claims parseToken(String token) {
        return Jwts.parser().setSigningKey(SIGNING_KEY).parseClaimsJws(token).getBody();
    }

    public AdminUserLoginRes getLoginAdminUser(HttpServletRequest request) {
        // 获取请求携带的令牌
        String token = getToken(request);
        if (StringUtils.isNotEmpty(token)) {
            try {
                Claims claims = parseToken(token);
                // 解析对应的权限以及用户信息
                String uuid = (String) claims.get(LOGIN_USER_KEY);
                String userKey = getTokenKey(uuid);
                return JsonUtils.toJavaObject(redisComponents.get(userKey), AdminUserLoginRes.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void verifyToken(AdminUserLoginRes adminUserLoginRes) {
        long expireTime = adminUserLoginRes.getExpireTime();
        long currentTime = System.currentTimeMillis();
        if (expireTime - currentTime <= MILLIS_MINUTE_TEN) {
            refreshToken(adminUserLoginRes);
        }
    }

    public void refreshToken(AdminUserLoginRes adminUserLoginRes) {
        adminUserLoginRes.setLoginTime(System.currentTimeMillis());
        adminUserLoginRes.setExpireTime(adminUserLoginRes.getLoginTime() + EXPIRE_TIME * MILLIS_MINUTE);
        // 根据uuid将loginUser缓存
        String userKey = getTokenKey(adminUserLoginRes.getToken());
        redisComponents.save(userKey, JsonUtils.toJSONString(adminUserLoginRes), EXPIRE_TIME, TimeUnit.MINUTES);
    }

    private String getToken(HttpServletRequest request) {
        String token = request.getHeader(HEADER);
        if (StringUtils.isNotEmpty(token) && token.startsWith(TOKEN_PREFIX)) {
            token = token.replace(TOKEN_PREFIX, "");
        }
        return token;
    }

    public String getTokenKey(String token) {
        return LOGIN_TOKEN_KEY + token;
    }
}