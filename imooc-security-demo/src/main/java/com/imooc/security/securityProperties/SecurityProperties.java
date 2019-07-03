package com.imooc.security.securityProperties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "imooc.security")//从配置文件中读取特定的值作为browserProperties的loginPage属性值
public class SecurityProperties {
    private BrowserProperties browserProperties=new BrowserProperties();

    public BrowserProperties getBrowserProperties() {
        return browserProperties;
    }

    public void setBrowserProperties(BrowserProperties browserProperties) {
        this.browserProperties = browserProperties;
    }
}
