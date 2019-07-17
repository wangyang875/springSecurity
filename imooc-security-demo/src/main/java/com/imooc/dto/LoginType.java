package com.imooc.dto;

/**
 * 用来设置登录验证成功后是返回json信息还是直接跳转到请求的url(默认是跳转到原来请求的url)
 */
public enum LoginType {
    REDIRECT,
    JSON
}
