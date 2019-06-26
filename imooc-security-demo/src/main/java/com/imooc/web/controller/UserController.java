package com.imooc.web.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.imooc.dto.User;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.jws.soap.SOAPBinding;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {
    @GetMapping("/user")
    @JsonView(User.UserSimpleView.class)
    public List<User> getUser() {
        List<User> list=new ArrayList<>();
        list.add(new User());
        list.add(new User());
        list.add(new User());
        return list;
    }
    @GetMapping("/user/{id}")
    @JsonView(User.UserDetailView.class)
    public User getUserInfo(@PathVariable String id){
        User user=new User();
        user.setUsername("aa"+id);
        return user;
    }

    /**
     * @RequestBody注解负责将请求中的json数据绑定到user中
     * @valid是对请求的数据的校验，一般配合User类中的注解如@notnull、@notBlank等，这种情况下检验一旦不通过就会直接请求失败，
     * 不会进入到方法题里面去
     * 加入BindingResult之后就算校验不通过也会进入方法体里面去,相应的错误信息也会放到BindingResult这个对象里面
     * @param user
     * @return
     */
    @PostMapping("/user")
    public User create(@Valid @RequestBody User user, BindingResult errors){
        if (errors.hasErrors()){
            errors.getAllErrors().stream().forEach(error-> System.out.println(error.getDefaultMessage()));
        }
        System.out.println(user.getId());
        System.out.println(user.getUsername());
        System.out.println(user.getPassword());
        user.setId("1");
        System.out.println(user.getBirthday());
        return user;
    }
}
