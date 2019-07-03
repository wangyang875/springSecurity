package com.imooc.security;

import com.imooc.security.securityProperties.SecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(SecurityProperties.class)//设置可以从配置文件中读取属性值
public class SecurityCoreConfig {
}
