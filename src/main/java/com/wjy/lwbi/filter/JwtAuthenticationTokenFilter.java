package com.wjy.lwbi.filter;

import com.alibaba.fastjson.JSON;
import com.wjy.lwbi.common.ErrorCode;
import com.wjy.lwbi.exception.BusinessException;
import com.wjy.lwbi.exception.SelfAuthenticationException;
import com.wjy.lwbi.exception.SelfSecurityException;
import com.wjy.lwbi.model.domain.LoginUser;
import com.wjy.lwbi.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.Collection;
import java.util.Objects;

/**
 * @author 吴峻阳
 * @version 1.0
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Resource
    private RedisTemplate redisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("token");
        if(!StringUtils.hasText(token)) {
            filterChain.doFilter(request, response);
            return;
        }
        //开始解析token
        String userId;
        try {
            Claims claims = JwtUtil.parseJWT(token);
            userId = claims.getSubject();
        } catch (Exception e) {
//            e.printStackTrace();
            throw new SelfAuthenticationException(ErrorCode.SYSTEM_ERROR.getCode(), "token解析异常");
        }
        //根据userId从redis中查询用户信息
        Object o = redisTemplate.opsForValue().get("login:" + userId);
        LoginUser loginUser = JSON.parseObject(JSON.toJSONString(o), LoginUser.class);
        if(Objects.isNull(loginUser)) {
            throw new SelfAuthenticationException(ErrorCode.SYSTEM_ERROR.getCode(), "用户查询异常");
        }
        //将用户信息、权限信息存入SecurityContextHolder中
        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }
}
