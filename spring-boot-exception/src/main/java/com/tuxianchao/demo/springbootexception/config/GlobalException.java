package com.tuxianchao.demo.springbootexception.config;

import com.tuxianchao.demo.springbootexception.dto.ErrorInfo;
import com.tuxianchao.demo.springbootexception.exception.CustomerException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常映射
 *
 * @author
 * @create 2018-03-21 14:38
 **/
@ControllerAdvice
public class GlobalException {
    /**
     * 默认的异常处理映射
     *
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest request, Exception e) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("url", request.getRequestURL());
        modelAndView.addObject("exception", e);
        return modelAndView;
    }

    /**
     * json接口异常映射，需要返回错误的json信息
     * 通过ExceptionHandler的属性来映射处理的异常
     * 通过ResponseBody注解来返回json格式数据
     *
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(value = CustomerException.class)
    @ResponseBody
    public ErrorInfo<Object> jsonErrorHandler(HttpServletRequest request, Exception e) {
        ErrorInfo errorInfo = new ErrorInfo();
        errorInfo.setCode(ErrorInfo.ERROR);
        errorInfo.setData("error data");
        errorInfo.setUrl(request.getRequestURL().toString());
        errorInfo.setMessage(e.getMessage());
        return errorInfo;
    }
}
