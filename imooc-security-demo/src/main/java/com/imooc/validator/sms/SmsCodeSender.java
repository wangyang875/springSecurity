package com.imooc.validator.sms;

public interface SmsCodeSender {
    void send(String mobilePhone,String code);
}
