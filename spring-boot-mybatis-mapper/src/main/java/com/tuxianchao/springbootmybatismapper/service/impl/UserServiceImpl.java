package com.tuxianchao.springbootmybatismapper.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tuxianchao.springbootmybatismapper.mapper.UserMapper;
import com.tuxianchao.springbootmybatismapper.pojo.User;
import com.tuxianchao.springbootmybatismapper.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author
 * @create 2018-04-09 11:18
 **/
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    /**
     * Cacheable:
     * CachePut:
     * CacheEvict:
     * CacheConfig:
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public PageInfo findByPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        PageInfo pageInfo = new PageInfo(userMapper.selectAll());
        return pageInfo;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)

    public int insertUser(User user) {
        int result = userMapper.insert(user);
        //手动抛出一个异常，测试事务是否生效
        //throw new NullPointerException();
        return result;
    }
}
