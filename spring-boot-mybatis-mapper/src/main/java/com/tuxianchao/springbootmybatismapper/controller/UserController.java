package com.tuxianchao.springbootmybatismapper.controller;

import com.tuxianchao.springbootmybatismapper.pojo.User;
import com.tuxianchao.springbootmybatismapper.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author
 * @create 2018-04-09 11:23
 **/
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/getPage")
    public Object findPage(
            @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", defaultValue = "5") int pageSize) {
        return userService.findByPage(pageNum, pageSize);
    }

    @RequestMapping("/addUser")
    public int addUser(User user) {

        return userService.insertUser(user);
    }
}
