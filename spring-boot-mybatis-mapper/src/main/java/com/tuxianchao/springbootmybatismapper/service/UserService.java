package com.tuxianchao.springbootmybatismapper.service;

import com.github.pagehelper.PageInfo;
import com.tuxianchao.springbootmybatismapper.pojo.User;

public interface UserService {
    PageInfo findByPage(int pageNum, int pageSize);

    int insertUser(User user);
}
