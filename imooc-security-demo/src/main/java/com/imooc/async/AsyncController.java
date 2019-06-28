package com.imooc.async;

import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.Callable;

@RestController
public class AsyncController {
    @Autowired
    private MockQueue mockQueue;
    @Autowired
    private DeferredResultHolder deferredResultHolder;
    private Logger logger= LoggerFactory.getLogger(getClass());
    @RequestMapping("/order")
    public Callable<String> order() throws InterruptedException {
        logger.info("主线程开始。。");
        Callable<String> callable=new Callable<String>() {
            @Override
            public String call() throws Exception {
                logger.info("副线程开始");
                Thread.sleep(1000);
                logger.info("副线程返回");
                return "success";
            }
        };
//        Thread.sleep(1000);//模拟业务过程
        logger.info("主线程返回。。");
        return callable;
    }
    @RequestMapping("/order1")
    public DeferredResult<String> order1() throws InterruptedException {
        logger.info("主线程开始。。");
        String orderNumber = RandomStringUtils.randomNumeric(8);
        DeferredResult<String> deferredResult=new DeferredResult<>();
        mockQueue.setPlaceOrder(orderNumber);
        deferredResultHolder.getMap().put(orderNumber,deferredResult);

        logger.info("主线程返回。。");
        return deferredResult;
    }
}
