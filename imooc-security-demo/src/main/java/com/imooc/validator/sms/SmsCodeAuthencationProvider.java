package com.imooc.validator.sms;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class SmsCodeAuthencationProvider implements AuthenticationProvider {
    private UserDetailsService userDetailsService ;

    public UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SmsCodeAuthenticationToken token=(SmsCodeAuthenticationToken) authentication;
        UserDetails userDetails=this.getUserDetailsService().loadUserByUsername( (String)token.getPrincipal());//这里获取的就是mobile
        if (userDetails==null){
            throw new InternalAuthenticationServiceException("无法获取用户信息");
        }
        SmsCodeAuthenticationToken authenticationResult=new SmsCodeAuthenticationToken(userDetails,userDetails.getAuthorities());
        authenticationResult.setDetails(token.getDetails());
        return authenticationResult;
    }
/*
AuthenticationManager根据support里面的support支持的token的类型，比如现在就是SmsCodeAuthenticationToken
 */
    @Override
    public boolean supports(Class<?> authentication) {
        return SmsCodeAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
