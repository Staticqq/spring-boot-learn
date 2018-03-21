package com.tuxianchao.demo.springbootexception.exception;

/**
 * 自定义异常
 *
 * @author
 * @create 2018-03-21 14:00
 **/
public class CustomerException extends Exception {
    public CustomerException(String message) {
        super(message);
    }

}
