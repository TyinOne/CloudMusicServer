package com.tyin.server.auth.security.point;

import com.tyin.core.utils.JsonUtils;
import com.tyin.server.api.Result;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.tyin.core.enums.ResultCode.SIGNATURE_NOT_MATCH;

/**
 * @author Tyin
 * @date 2022/9/29 16:57
 * @description ...
 */
public class Http401AuthenticationEntryPoint implements AuthenticationEntryPoint {


    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setStatus(200);
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        String msg = String.format("请求访问: %s，认证失败, 请重新登录", request.getRequestURI());
        response.getWriter().println(JsonUtils.toJSONString(Result.failed(SIGNATURE_NOT_MATCH.getCode(), msg)));
    }
}
