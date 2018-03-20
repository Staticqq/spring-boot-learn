package com.tuxianchao.demo.springbootjdbctemplatedatasource.dao.impl;

import com.tuxianchao.demo.springbootjdbctemplatedatasource.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * @author tuxianchao
 * @date 2018/1/25 1:01
 */
@Repository
public class UserDaoImpl implements UserDao {
    @Autowired
    @Qualifier("jdbcTemplateOne")
    private JdbcTemplate jdbcTemplate;

    @Override
    public void create(Integer id, String name, Byte age) {
        jdbcTemplate.update("insert into TBL_USER(ID ,NAME, AGE) values(? ,?, ?)", id, name, age);
    }

    @Override
    public void deleteByName(String name) {
        jdbcTemplate.update("delete from TBL_USER where NAME = ?", name);
    }

    @Override
    public Integer getAllUsers() {
        return jdbcTemplate.queryForObject("select count(1) from TBL_USER", Integer.class);
    }

    @Override
    public void deleteAllUsers() {
        jdbcTemplate.update("delete from TBL_USER");
    }
}
