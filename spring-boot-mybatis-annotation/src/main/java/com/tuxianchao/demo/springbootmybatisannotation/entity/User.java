package com.tuxianchao.demo.springbootmybatisannotation.entity;

import java.io.Serializable;

/**
 * @author tuxianchao
 * @date 2018/1/30 23:15
 */
public class User implements Serializable {

    private static final long serialVersionUID = 1459159974353794657L;
    private Long id;
    private String name;
    private Byte age;


    public User() {
    }

    public User(Long id, String name, Byte age) {
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

    public Byte getAge() {
        return age;
    }

    public void setAge(Byte age) {
        this.age = age;
    }
}
