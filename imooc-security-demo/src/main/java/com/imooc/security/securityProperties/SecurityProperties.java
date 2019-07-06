package com.imooc.security.securityProperties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "imooc.security")//从配置文件中读取特定的值作为browserProperties的loginPage属性值
public class SecurityProperties {
    //用这种方法读取配置文件中的配置项到类对象里面时，需要再加一个配置类SecurityCoreConfig，用来设置可以从配置文件中读取配置项
    //而且下面类的对象的get()\set()方法名称要与配置文件中设置的key保持一致,这样才能保证能读取进去
    private BrowserProperties browserProperties=new BrowserProperties();

    public BrowserProperties getBrowser() {
        return browserProperties;
    }

    public void setBrowser(BrowserProperties browserProperties) {
        this.browserProperties = browserProperties;
    }
}
