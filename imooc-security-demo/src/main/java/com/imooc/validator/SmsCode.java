package com.imooc.validator;

import java.time.LocalDateTime;

public class SmsCode {
    String code;
    LocalDateTime ex;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getEx() {
        return ex;
    }

    public void setEx(LocalDateTime ex) {
        this.ex = ex;
    }

    public SmsCode(String code, LocalDateTime ex) {
        this.code = code;
        this.ex = ex;
    }
    public SmsCode(String code, int ex) {
        this.code = code;
        this.ex = LocalDateTime.now().plusSeconds(ex);
    }

    public boolean isExpire() {
        if (LocalDateTime.now().isAfter(ex))
            return true;
        return false;
    }
}
