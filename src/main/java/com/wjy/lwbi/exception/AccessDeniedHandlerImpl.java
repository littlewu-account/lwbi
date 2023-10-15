package com.wjy.lwbi.exception;

import com.alibaba.fastjson.JSON;
import com.wjy.lwbi.common.BaseResponse;
import com.wjy.lwbi.utils.WebUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
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
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        BaseResponse baseResponse = new BaseResponse(HttpStatus.FORBIDDEN.value(), "您的权限不足");
        String json = JSON.toJSONString(baseResponse);
        WebUtils.renderString(response, json);
    }
}
