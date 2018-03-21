package com.tuxianchao.demo.springbootexception.dto;

/**
 * @author tuxianchao
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

    public ErrorInfo() {
    }

    public ErrorInfo(Integer code, String message, String url, T data) {
        this.code = code;
        this.message = message;
        this.url = url;
        this.data = data;
    }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
