package com.imooc.security;

import com.imooc.security.securityProperties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    SecurityProperties securityProperties;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()//表单登录界面的形式认证
                .loginPage("/authentication/require")//自己指定表单验证的页面
                .loginProcessingUrl("/authentication/form")//默认是处理/login 这个url，这里是自定义进行验证的url,即自定义的表单对应的url
//        http.httpBasic()//默认的弹窗形式的认证
                .and()
                .authorizeRequests()
                .antMatchers("/authentication/require",securityProperties.getBrowserProperties().getLoginPage()).permitAll()//表示这个url不需要验证
                .anyRequest()
                .authenticated()
                .and()
        .csrf().disable();//先把springsecurity的防止跨站请求服务的功能关掉
    }
}
