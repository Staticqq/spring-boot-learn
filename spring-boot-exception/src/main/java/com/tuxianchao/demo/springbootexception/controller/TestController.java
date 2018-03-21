package com.tuxianchao.demo.springbootexception.controller;

import com.tuxianchao.demo.springbootexception.exception.CustomerException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 测试异常
 * 发生异常的时候，spring-boot会提供一个默认的异常映射/error
 * 但是，这显然是不够友好的。需要考虑到其他的情况
 * 例如：
 * 错误页面的定制。
 * 更加细粒度的错误页面映射
 * json接口的异常返回处理
 *
 * @author
 * @create 2018-03-21 14:14
 **/
@Controller
public class TestController {

    /**
     * 测试普通视图异常
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/index")
    public String index() throws Exception {
        throw new Exception("系统发生错误了");
    }

    /**
     * 测试json接口异常
     *
     * @return
     * @throws CustomerException
     */
    @RequestMapping("/jsonerror")
    public String json() throws CustomerException {
        throw new CustomerException("json接口返回异常");
    }


}
