# spring-boot异常处理

spring-boot默认提供了一个/error的映射来处理异常,当发生异常的时候或者资源找不到的情况下就会转入异常视图


## 自定义统一异常处理

1.  对于http状态吗对应的错误（404，500等）在/template下建立/error文件夹，然后再建立404.html，500.html，spring-boot对于这些错误就会转到状态码页面

2. 对于普通的html视图的异常，可通过配置全局的异常处理来根据不同的异常处理异常。

3.  对于json接口的异常，需要返回json格式的异常信息

### 1.普通html视图异常处理

####1.创建全局的异常处理类
```
package com.tuxianchao.springbootexception.exception;

import com.tuxianchao.springbootexception.dto.CustomerException;
import com.tuxianchao.springbootexception.dto.ErrorInfo;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author tuxianchao
 * @date 2018/1/24 23:22
 *
 *  全局的异常处理类，使用ControllerAdvice注解来定义全局的异常处理类
 *  异常映射，使用ExceptionHandler注解来映射指定的异常。
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    /***
     *普通的错误页面处理，返回error.html视图
     * @param request 请求
     * @param e 异常对象
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest request, Exception e) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("url", request.getRequestURL());
        modelAndView.addObject("exception", e);
        return modelAndView;
    }
}

```


####2.建立异常的视图，和全局异常类配置的相对应
创建对应的视图，错误的信息等
####3.测试异常
```
 /**
     * 测试普通html视图异常
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/index")
    public String index() throws Exception {
        throw new Exception("系统发生错误了");
    }
```


### 2.json接口异常处理

接口异常的处理和普通html视图异常页面的处理类似，只需要在异常处理类中添加异常映射，然后使用ResponseBody返回json数据即可。

这里使用自定义异常。

####1.创建json数据的dto，创建自定义异常类，抛出异常

```
package com.tuxianchao.springbootexception.dto;

/**
 * @author tuxianchao
 * @date 2018/1/24 23:56
 * jsonc接口dto
 */
public class ErrorInfo<T> {
    public static final Integer ERROR = -1;
    public static final Integer OK = 0;
    /**
     * 状态
     */
    private Integer code;
    /**
     * 错误消息
     */
    private String message;
    /**
     * 请求的url
     */
    private String url;
    /**
     * 返回数据
     */
    private T data;
    //constuctor ,setter,getter
}

```
```
package com.tuxianchao.springbootexception.dto;

/**
 * @author tuxianchao
 * @date 2018/1/25 0:02
 * 自定义异常
 */
public class CustomerException extends Exception {
    public CustomerException(String message) {
        super(message);
    }
}
```


```
/**
     * 测试json接口异常
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/jsonError")
    @ResponseBody
    public String json() throws Exception {
        throw new CustomerException("json error");
    }

```



####2.在全局异常配置中配置

```
/**
     * json接口异常返回，使用responsebody注解来返回json格式信息
     *
     * @return
     */
    @ExceptionHandler(value = CustomerException.class)
    @ResponseBody
    public ErrorInfo<String> jsonErroeHandler(HttpServletRequest request, CustomerException e) {
        ErrorInfo<String> result = new ErrorInfo<>();
        result.setCode(ErrorInfo.OK);
        result.setData("error data");
        result.setMessage(e.getMessage());
        result.setUrl(request.getRequestURL().toString());
        return result;
    }
```
####
