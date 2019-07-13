package com.imooc.validator.sms;

import com.imooc.validator.ImageCode;
import com.imooc.validator.SmsCode;
import com.imooc.validator.ValidateCodeException;
import com.imooc.web.controller.ValidateCodeController;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 放在自定义的SmsCodeAuthenticationFilter的前面，用来进行短信验证码校验的逻辑实现
 * 与图形验证码的filter：ValidateCodeFilter是类似的
 */
public class SmsCodeFilter extends OncePerRequestFilter {
    private AuthenticationFailureHandler authenticationFailureHandler;//这个类用来处理验证过程中出现异常
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response
            , FilterChain filterChain) throws ServletException, IOException {
        if (StringUtils.equals("/authentication/mobile", request.getRequestURI())
                && StringUtils.equalsIgnoreCase(request.getMethod(), "post")) {
            try {
                validateSmsCode(new ServletWebRequest(request));
                filterChain.doFilter(request, response);
            } catch (ValidateCodeException e) {
                authenticationFailureHandler
                        .onAuthenticationFailure(request, response, e);
            }
        } else {
            //如果登录验证，就继续后面的过滤器过滤
            filterChain.doFilter(request, response);
        }
    }

    private void validateSmsCode(ServletWebRequest request) {
        String smsCodeInRequest = request.getParameter("smsCode");
        SmsCode smsCodeInSession =  (SmsCode) sessionStrategy.getAttribute(request, ValidateCodeController.SESSION_MS_KEY);
        if (StringUtils.isBlank(smsCodeInRequest)) {
            throw new ValidateCodeException("验证码不能为空");
        }
        if (smsCodeInSession == null) {
            throw new ValidateCodeException("验证码不存在");
        }
        if (!StringUtils.equals(smsCodeInRequest, smsCodeInSession.getCode())) {
            throw new ValidateCodeException("验证码不匹配");
        }
        if (smsCodeInSession.isExpire()) {
            sessionStrategy.removeAttribute(request, ValidateCodeController.SESSION_MS_KEY);
            throw new ValidateCodeException("验证码已过期");
        }
        if (smsCodeInRequest == smsCodeInSession.getCode()) {
            sessionStrategy.removeAttribute(request, ValidateCodeController.SESSION_MS_KEY);
            return;
        }
    }

    public AuthenticationFailureHandler getAuthenticationFailureHandler() {
        return authenticationFailureHandler;
    }

    public void setAuthenticationFailureHandler(AuthenticationFailureHandler authenticationFailureHandler) {
        this.authenticationFailureHandler = authenticationFailureHandler;
    }
}
