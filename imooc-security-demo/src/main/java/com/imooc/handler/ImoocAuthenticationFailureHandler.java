package com.imooc.handler;

import com.imooc.dto.LoginType;
import com.imooc.security.securityProperties.SecurityProperties;
import com.imooc.support.SimpleResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
//SimpleUrlAuthenticationFailureHandler是springsecurity默认的验证失败的处理器
@Component
public class ImoocAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    SecurityProperties securityProperties;
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        logger.error(exception.getMessage());
        SimpleResponse simpleResponse=new SimpleResponse(exception.getMessage());
        if (LoginType.JSON.equals(securityProperties.getBrowser().getLoginType())){
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(exception.getMessage());}
        else {
//            super.onAuthenticationFailure(request,response,exception);
            response.sendRedirect("/imooc-login(withValidateCode).html");
        }

    }

}
