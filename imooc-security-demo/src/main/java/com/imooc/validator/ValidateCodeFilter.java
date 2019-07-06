package com.imooc.validator;
import com.imooc.web.controller.ValidateCodeController;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//自定义的过滤器，用于处理检验返回的图形验证码结果，OncePerRequestFilter就是每个请求只过滤一次
public class ValidateCodeFilter extends OncePerRequestFilter {
    private AuthenticationFailureHandler authenticationFailureHandler;//这个类用来处理验证过程中出现异常
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    @Override
    protected void doFilterInternal(HttpServletRequest request
            , HttpServletResponse response
            , FilterChain filterChain) throws ServletException, IOException {
        if (StringUtils.equals("/authentication/form", request.getRequestURI())
                && StringUtils.equalsIgnoreCase(request.getMethod(), "post")) {
            try {
                validate(new ServletWebRequest(request));
                filterChain.doFilter(request, response);
            } catch (ValidateCodeException e) {
                authenticationFailureHandler
                        .onAuthenticationFailure(request, response, e);
            }
        }else {
            //如果登录验证，就继续后面的过滤器过滤
            filterChain.doFilter(request, response);
        }
    }

    private void validate(ServletWebRequest request) {
        String imageCodeInRequest = request.getParameter("imageCode");
        ImageCode ImageCodeInSession = (ImageCode) sessionStrategy.getAttribute(request, ValidateCodeController.SESSION_KEY);
        if (StringUtils.isBlank(imageCodeInRequest)) {
            throw new ValidateCodeException("验证码不能为空");
        }
        if (ImageCodeInSession==null){
            throw new ValidateCodeException("验证码不存在");
        }
        if (!StringUtils.equals(imageCodeInRequest,ImageCodeInSession.getCode())){
            throw new ValidateCodeException("验证码不匹配");
        }
        if (ImageCodeInSession.isExpire()){
            sessionStrategy.removeAttribute(request,ValidateCodeController.SESSION_KEY);
            throw new ValidateCodeException("验证码已过期");
        }
        if (imageCodeInRequest == ImageCodeInSession.getCode()) {
            sessionStrategy.removeAttribute(request,ValidateCodeController.SESSION_KEY);
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
