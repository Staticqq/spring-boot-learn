package com.tuxianchao.demo.springbootrestfulurldemo.controller;

import com.tuxianchao.demo.springbootrestfulurldemo.entity.User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author tuxianchao
 * @date 2018/1/23 1:01
 * <p>
 * RESTFUL的url
 * /users   GET 查询用户列表
 * /users   POST    创建用户
 * /users/id    GET 根据id查询
 * /users/id    PUT 根据id更新
 * /users/id    DELETE 根据id删除
 * <p>
 * <p>
 * 注解解释
 * @RestController spring4之后加入的注解，使用controller返回json时需要在目标方法添加responsebody注解，
 * RestController=Controller+ResponseBody
 */
@RestController
@RequestMapping("/users")
public class UserController {

    /**
     * 线程安全的map，模拟数据库
     */
    static Map<Long, User> userMap = new ConcurrentHashMap<>();

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<User> getUsers() {

        return new ArrayList<User>(userMap.values());
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String addUser(User user) {
        //post新增
        userMap.put(user.getId(), user);
        return "success";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public User getUser(@PathVariable Long id) {
        //get请求查询
        return userMap.get(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public String updateUser(@PathVariable Long id, User user) {
        //put请求修改
        User u = userMap.get(id);
        u.setName(user.getName());
        u.setAge(user.getAge());
        userMap.put(u.getId(), u);
        return "success";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String deleteUser(@PathVariable Long id) {
        //delete请求删除
        userMap.remove(id);
        return "success";
    }
}
