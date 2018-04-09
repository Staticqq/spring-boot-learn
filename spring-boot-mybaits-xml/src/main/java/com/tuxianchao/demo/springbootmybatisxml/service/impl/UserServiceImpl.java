package com.tuxianchao.demo.springbootmybatisxml.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tuxianchao.demo.springbootmybatisxml.entity.User;
import com.tuxianchao.demo.springbootmybatisxml.mapper.UserMapper;
import com.tuxianchao.demo.springbootmybatisxml.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author
 * @create 2018-03-21 16:19
 **/
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public PageInfo<User> findPage(Integer page, Integer limit) {

        PageHelper.startPage(page, limit);
        List<User> users = userMapper.selectAll();
        return new PageInfo<>(users);
    }
}
