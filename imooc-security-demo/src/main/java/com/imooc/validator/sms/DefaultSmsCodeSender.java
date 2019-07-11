package com.imooc.validator.sms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class DefaultSmsCodeSender implements SmsCodeSender{
    private Logger logger=LoggerFactory.getLogger(getClass());
    @Override
    public void send(String mobilePhone, String code) {
        logger.info("向手机"+mobilePhone+"发送"+code);
    }
}
