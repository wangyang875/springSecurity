package com.imooc.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class MyUSerDetailsService implements UserDetailsService {
    private Logger logger= LoggerFactory.getLogger(getClass());
    @Autowired
    PasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("登录用户名:  "+username);
        //实际上应该是从数据库里面查询用户名、密码的
        //根据查找到的用户信息确定是否用户被冻结
        String pwd=passwordEncoder.encode("123456");
        logger.info("登录密码是："+pwd);
        return new User(username,pwd,true,true,true,true,
                AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
    }
}
