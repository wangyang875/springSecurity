package com.imooc.validator;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

public class ImageCode {
    private BufferedImage image;
    private String code;
    private LocalDateTime ex;
    public ImageCode(BufferedImage image, String code, int exInt) {
        this.image = image;
        this.code = code;
        this.ex = LocalDateTime.now().plusSeconds(exInt);
    }
    public ImageCode(BufferedImage image, String code, LocalDateTime ex) {
        this.image = image;
        this.code = code;
        this.ex = ex;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

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
    public boolean isExpire(){
        if (this.ex.isBefore(LocalDateTime.now())){
            return true;
        }
        return false;
    }
}
