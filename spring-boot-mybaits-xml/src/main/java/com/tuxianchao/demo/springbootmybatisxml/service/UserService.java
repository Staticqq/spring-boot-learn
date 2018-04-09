package com.tuxianchao.demo.springbootmybatisxml.service;

import com.github.pagehelper.PageInfo;
import com.tuxianchao.demo.springbootmybatisxml.entity.User;

public interface UserService {
    PageInfo<User> findPage(Integer page, Integer limit);

}
