package com.tuxianchao.demo.springbootmybatiscacheredis.controller;

import com.tuxianchao.demo.springbootmybatiscacheredis.mapper.UserMapper;
import com.tuxianchao.demo.springbootmybatiscacheredis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author
 * @create 2018-04-15 17:50
 **/
@RestController
public class UserController {
   
    @Autowired
    private UserService userService;

    @GetMapping("/getUser")
    public Object getUser() {
        return userService.getUser();
    }
}
