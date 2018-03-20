package com.tuxianchao.demo.springbootswagger2.controller;

import com.tuxianchao.demo.springbootswagger2.entity.User;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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

    /**
     * 使用ApiOperation和RequestMapping注解来描述swagger2生成的rest的文档
     *
     * @return
     */
    @ApiOperation(value = "获取用户列表", notes = "获取用户列表，返回list")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<User> getUsers() {

        return new ArrayList<User>(userMap.values());
    }

    @ApiOperation(value = "创建用户", notes = "根据User对象创建用户")
    @ApiImplicitParam(name = "user", value = "用户详细实体user", required = true, dataType = "User")
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String addUser(User user) {
        //post新增
        userMap.put(user.getId(), user);
        return "success";
    }

    @ApiOperation(value = "获取用户详细信息", notes = "根据url的id来获取用户详细信息")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public User getUser(@PathVariable Long id) {
        //get请求查询
        return userMap.get(id);
    }

    @ApiOperation(value = "更新用户详细信息", notes = "根据url的id来指定更新对象，并根据传过来的user信息来更新用户详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "user", value = "用户详细实体user", required = true, dataType = "User")
    })
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public String updateUser(@PathVariable Long id, User user) {
        //put请求修改
        User u = userMap.get(id);
        u.setName(user.getName());
        u.setAge(user.getAge());
        userMap.put(u.getId(), u);
        return "success";
    }

    @ApiOperation(value = "删除用户", notes = "根据url的id来指定删除对象")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String deleteUser(@PathVariable Long id) {
        //delete请求删除
        userMap.remove(id);
        return "success";
    }


}
