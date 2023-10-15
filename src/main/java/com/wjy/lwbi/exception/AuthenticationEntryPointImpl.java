package com.wjy.lwbi.exception;

import com.alibaba.fastjson.JSON;
import com.wjy.lwbi.common.BaseResponse;
import com.wjy.lwbi.utils.WebUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 吴峻阳
 * @version 1.0
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        //处理认证异常
        BaseResponse baseResponse = new BaseResponse(HttpStatus.UNAUTHORIZED.value(), null, "用户认证失败请重新登录");
        String json = JSON.toJSONString(baseResponse);
        WebUtils.renderString(response, json);
    }
}
