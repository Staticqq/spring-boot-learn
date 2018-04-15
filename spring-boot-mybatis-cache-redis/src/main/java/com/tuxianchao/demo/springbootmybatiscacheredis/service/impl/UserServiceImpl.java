package com.tuxianchao.demo.springbootmybatiscacheredis.service.impl;

import com.tuxianchao.demo.springbootmybatiscacheredis.mapper.UserMapper;
import com.tuxianchao.demo.springbootmybatiscacheredis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author
 * @create 2018-04-15 18:18
 **/
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
  //  @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List getUser() {
        return userMapper.selectAll();
    }
}
