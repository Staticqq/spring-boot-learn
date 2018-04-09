package com.tuxianchao.demo.springbootmybatisannotation.service;

import com.github.pagehelper.PageInfo;
import com.tuxianchao.demo.springbootmybatisannotation.entity.User;

public interface UserService {
    PageInfo<User> findPage(Integer page, Integer limit);

}
