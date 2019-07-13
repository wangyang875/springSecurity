package com.imooc.web.controller;

import com.imooc.validator.ImageCode;
import com.imooc.validator.SmsCode;
import com.imooc.validator.sms.DefaultSmsCodeSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
public class ValidateCodeController {
    public static final String SESSION_IMAGE_KEY = "SESSIONKEY_IMAGE_CODE";
    public static final String SESSION_MS_KEY = "SESSIONKEY_MS_CODE";
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();//session的工具类，用来操作session
    @Autowired
    DefaultSmsCodeSender defaultSmsCodeSender;

    @GetMapping("/code/image")
    public void createCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ImageCode imageCode = CreateImageCode(request);
        //拿到当前请求的session，并把验证码存到session里面去
        sessionStrategy.setAttribute(new ServletWebRequest(request), SESSION_IMAGE_KEY, imageCode);
        //把图片写入到响应的输出流里面
        ImageIO.write(imageCode.getImage(), "JPEG", response.getOutputStream());
    }

    @GetMapping("/code/sms")
    public void createSmsCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        SmsCode smsCode = createmsCode();
        //拿到当前请求的session，并把验证码存到session里面去
        sessionStrategy.setAttribute(new ServletWebRequest(request), SESSION_MS_KEY, smsCode);
        //模拟进行发送短信验证码的操作
        String mobile = request.getParameter("mobile");
        defaultSmsCodeSender.send(mobile, smsCode.getCode());
    }

    private SmsCode createmsCode() {
        Random random = new Random();
        String sRand = "";
        for (int i = 0; i < 4; i++) {
            String rand = String.valueOf(random.nextInt(10));
            sRand += rand;
        }
        return new SmsCode(sRand, 80);
    }

    private ImageCode CreateImageCode(HttpServletRequest request) {
        int width = 67;
        int height = 23;
        Random random = new Random();
        BufferedImage bufferedImage = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);

        // 步骤二 图片绘制背景颜色 ---通过绘图对象
        Graphics g = bufferedImage.getGraphics();// 得到画图对象 --- 画笔
        // 绘制任何图形之前 都必须指定一个颜色
        g.setColor(getRandColor(200, 250));
        g.fillRect(0, 0, width, height);
        g.drawRect(0, 0, width - 1, height - 1);
        g.setColor(getRandColor(160, 200));
        for (int i = 0; i < 155; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int x1 = random.nextInt(12);
            int y1 = random.nextInt(12);
            g.drawLine(x, y, x + x1, y + y1);
        }
        String sRand = "";
        for (int i = 0; i < 4; i++) {
            String rand = String.valueOf(random.nextInt(10));
            sRand += rand;
            g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
            g.drawString(rand, 13 * i + 6, 16);
        }
        g.dispose();

        return new ImageCode(bufferedImage, sRand, 60);
    }

    private Color getRandColor(int fc, int bc) {
        // 取其随机颜色
        Random random = new Random();
        if (fc > 255) {
            fc = 255;
        }
        if (bc > 255) {
            bc = 255;
        }
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }
}
