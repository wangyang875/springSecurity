package com.imooc.security.securityProperties;

public class BrowserProperties {
    private String loginPage="/imooc-login.html";//如果不指定，那么默认是这个页面

    public String getLoginPage() {
        return loginPage;
    }

    public void setLoginPage(String loginPage) {
        this.loginPage = loginPage;
    }
}