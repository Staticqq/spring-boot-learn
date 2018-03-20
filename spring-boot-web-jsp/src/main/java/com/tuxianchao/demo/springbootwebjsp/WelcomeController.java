package com.tuxianchao.demo.springbootwebjsp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

/**
 * @author tuxianchao
 * @date 2018/1/24 0:25
 */
@Controller
public class WelcomeController {
    @Value("${application.message}")
    private String message;

    /**
     * Spring4.3中引进了｛@GetMapping、@PostMapping、@PutMapping、@DeleteMapping、@PatchMapping｝，来帮助简化常用的HTTP方法的映射，并更好地表达被注解方法的语义。
     * 以@GetMapping为例，Spring官方文档说：
     * <p>
     * GetMapping是一个组合注解，是@RequestMapping(method = RequestMethod.GET)的缩写。该注解将HTTP Get 映射到 特定的处理方法上。
     * Difference between @GetMapping & @RequestMapping:
     * GetMapping does not support  the consumes attribute of RequestMapping.
     */
    @GetMapping("/welcome")
    public String welcome(Model model) {
        model.addAttribute("time", new Date());
        model.addAttribute("message", this.message);
        //返回逻辑视图名
        return "welcome";
    }

    @RequestMapping("/foo")
    public String foo(Model model) {
        throw new RuntimeException("Foo");
    }

}
