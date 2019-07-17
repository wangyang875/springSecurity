package com.imooc.security.securityProperties;

import com.imooc.dto.LoginType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

public class BrowserProperties {
    private String loginPage = "/imooc-login.html";//如果不指定，那么默认是这个页面
    private LoginType loginType = LoginType.JSON;

    public String getLoginPage() {
        return loginPage;
    }

    public void setLoginPage(String loginPage) {
        this.loginPage = loginPage;
    }

    public LoginType getLoginType() {
        return loginType;
    }

    public void setLoginType(LoginType loginType) {
        this.loginType = loginType;
    }
}
