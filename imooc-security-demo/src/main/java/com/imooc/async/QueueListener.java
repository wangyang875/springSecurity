package com.imooc.async;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class QueueListener implements ApplicationListener<ContextRefreshedEvent> {//整个spring容器初始化完毕额时间
    @Autowired
    private MockQueue mockQueue;
    @Autowired
    DeferredResultHolder deferredResultHolder;
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        //监听过程是初始化之后就会无限循环，就要开启一个线程
        new Thread(()->{
            while (true) {
                //当订单完成的字段不为空的时候
                if (StringUtils.isNotBlank(mockQueue.getCompleteOrder())) {
                    String orderNumber = mockQueue.getCompleteOrder();
                    logger.info("返回订单处理结果"+orderNumber);
                    deferredResultHolder.getMap().get(orderNumber).setResult("订单处理成功！！");//setResult是返回到前台的消息
                    mockQueue.setCompleteOrder(null);
                } else {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        ).start();

    }
}
