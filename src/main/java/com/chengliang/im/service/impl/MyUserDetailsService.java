package com.chengliang.im.service.impl;

import com.chengliang.im.bean.User;
import com.chengliang.im.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author chengliang
 */
@Slf4j
@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        log.info("{}:尝试登陆",s);
        User user = userMapper.selectUserByLoginName(s);
        if(user == null){
            log.error("{}登陆失败",s);
            throw new UsernameNotFoundException("用户名或密码错误");
        }
        log.info("{}用户存在",s);
        return user;
    }

}
