package com.tuxianchao.demo.springbootswagger2.entity;

import java.io.Serializable;

/**
 * @author tuxianchao
 * @date 2018/1/23 1:00
 */
public class User implements Serializable {

    private static final long serialVersionUID = -8385683540034764105L;
    private Long id;
    private String name;
    private Integer age;

    public User() {
    }

    public User(Long id, String name, Integer age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
