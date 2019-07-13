package com.imooc.security;

import com.imooc.handler.ImoocAuthenticationFailureHandler;
import com.imooc.validator.sms.SmsCodeAuthencationProvider;
import com.imooc.validator.sms.SmsCodeAuthenticationFilter;
import com.imooc.validator.sms.SmsCodeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
public class SmsCodeAuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    @Autowired(required = false)
    private UserDetailsService userDetailsService;
    @Autowired
    private ImoocAuthenticationFailureHandler imoocAuthenticationFailureHandler;
    @Override
    public void configure(HttpSecurity http) throws Exception {
        SmsCodeAuthenticationFilter smsCodeAuthenticationFilter=new SmsCodeAuthenticationFilter();
        smsCodeAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        smsCodeAuthenticationFilter.setAuthenticationFailureHandler(imoocAuthenticationFailureHandler);


        SmsCodeAuthencationProvider smsCodeAuthencationProvider=new SmsCodeAuthencationProvider();
        smsCodeAuthencationProvider.setUserDetailsService(userDetailsService);
        SmsCodeFilter smsCodeFilter=new SmsCodeFilter();
        http.authenticationProvider(smsCodeAuthencationProvider)
                .addFilterAfter(smsCodeAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
