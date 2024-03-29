package com.imooc.web.controller;

import com.imooc.security.securityProperties.SecurityProperties;
import com.imooc.support.SimpleResponse;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class BrowserSecurityController {
    private Logger logger=LoggerFactory.getLogger(getClass());
    private RequestCache requestCache=new HttpSessionRequestCache();
    private RedirectStrategy redirectStrategy=new DefaultRedirectStrategy();
    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 当需要身份认证时跳转到这个请求
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/authentication/require")
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public SimpleResponse requireAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
        SavedRequest request1=requestCache.getRequest(request,response);
        if (request1!=null){
            String targetUrl=request1.getRedirectUrl();
            logger.info("引发跳转的请求是： "+targetUrl);
            if (StringUtils.endsWithIgnoreCase(targetUrl,".html")){
                redirectStrategy.sendRedirect(request,response,securityProperties.getBrowser().getLoginPage());
            }

        }
        return new SimpleResponse("访问的服务需要身份验证，请引导用户到登录页面");
    }
}
