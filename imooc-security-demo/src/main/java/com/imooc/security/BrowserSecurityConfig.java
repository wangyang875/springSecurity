package com.imooc.security;

import com.imooc.handler.ImoocAuthenticationFailureHandler;
import com.imooc.handler.ImoocAuthenticationSuccessHandler;
import com.imooc.security.securityProperties.SecurityProperties;
import com.imooc.validator.ValidateCodeFilter;
import com.imooc.validator.sms.SmsCodeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    SecurityProperties securityProperties;
    @Autowired
    SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;
    @Autowired
    ImoocAuthenticationFailureHandler imoocAuthenticationFailureHandler;
    @Autowired
    ImoocAuthenticationSuccessHandler imoocAuthenticationSuccessHandler;
//声明密码加密的方式
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        ValidateCodeFilter validateCodeFilter = new ValidateCodeFilter();
        validateCodeFilter.setAuthenticationFailureHandler(imoocAuthenticationFailureHandler);
        SmsCodeFilter smsCodeFilter=new SmsCodeFilter();
        smsCodeFilter.setAuthenticationFailureHandler(imoocAuthenticationFailureHandler);
        http.addFilterBefore(smsCodeFilter,UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)//UsernamePasswordAuthenticationFilter之前配置我们自己定义的用于校验验证码的过滤器
                .formLogin()//表单登录界面的形式认证
                .loginPage("/authentication/require")//自己指定表单验证的页面
                .loginProcessingUrl("/authentication/form")//默认是处理/login 这个url，这里是自定义进行验证的url,即自定义的表单对应的url
//        http.httpBasic()//默认的弹窗形式的认证
                .successHandler(imoocAuthenticationSuccessHandler)
                .failureHandler(imoocAuthenticationFailureHandler)
                .and()
                .authorizeRequests()
                .antMatchers("/authentication/require", securityProperties.getBrowser().getLoginPage()
                        , "/code/*").permitAll()//表示这个url不需要验证
                .anyRequest()
                .authenticated()
                .and()
                .csrf().disable()//先把springsecurity的防止跨站请求服务的功能关掉
                .apply(smsCodeAuthenticationSecurityConfig);//等于把我在smsCodeAuthenticationSecurityConfig里面写的配置接着写在下面
    }
}
