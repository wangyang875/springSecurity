package com.imooc.validator;

import com.imooc.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sound.midi.Soundbank;
import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Valid;

public class MyConstraintValidator implements ConstraintValidator<MyConstraint,Object> {
    /**
     * 在用于校验的类里面也可以使用@autowire注解来注入容器里面的其他bean
     */
    @Autowired
    HelloService helloService;
    @Override
    public void initialize(MyConstraint myConstraint) {
        System.out.println("MyValidator init...");
    }

    /**
     * 校验的逻辑，把要校验的对象传进来
     * @param o
     * @param constraintValidatorContext
     * @return
     */
    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        helloService.greeting("tom");
        System.out.println(o);
        return false;
    }
}
