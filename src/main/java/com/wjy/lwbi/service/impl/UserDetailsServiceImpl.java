package com.wjy.lwbi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wjy.lwbi.common.ErrorCode;
import com.wjy.lwbi.exception.BusinessException;
import com.wjy.lwbi.exception.SelfSecurityException;
import com.wjy.lwbi.mapper.UserMapper;
import com.wjy.lwbi.model.domain.LoginUser;
import com.wjy.lwbi.model.entity.User;
import com.wjy.lwbi.utils.JwtUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @author 吴峻阳
 * @version 1.0
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserAccount, username);
        User user = userMapper.selectOne(queryWrapper);
        if (Objects.isNull(user)) {
            throw new SelfSecurityException(ErrorCode.PARAMS_ERROR.getCode(), "用户名输入有误");
        }

        //从数据库查询权限信息
        List<String> perms = userMapper.getPermsById(user.getId());
        //封装进UserDetails实现类对象
        LoginUser loginUser = new LoginUser(user, perms);
        return loginUser;
    }
}
