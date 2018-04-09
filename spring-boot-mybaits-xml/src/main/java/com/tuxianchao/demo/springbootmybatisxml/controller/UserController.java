package com.tuxianchao.demo.springbootmybatisxml.controller;

import com.github.pagehelper.PageInfo;
import com.tuxianchao.demo.springbootmybatisxml.entity.User;
import com.tuxianchao.demo.springbootmybatisxml.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author
 * @create 2018-03-21 16:25
 **/
@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/users")
    @ResponseBody
    private PageInfo<User> users(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                 @RequestParam(value = "limit", defaultValue = "10") Integer limit) {
        return userService.findPage(page, limit);
    }

}
