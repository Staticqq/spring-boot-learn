package com.tuxianchao.demo.springbootjdbctemplatedatasource;

import com.tuxianchao.demo.springbootjdbctemplatedatasource.dao.UserDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 测试单数据源情况下，jdbctemplate的使用
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootJdbctemplateDatasourceApplicationTests {

    @Autowired
    private UserDao userDao;

    @Test
    public void contextLoads() {

    }

    @Test
    public void test1() {
        userDao.create(null, "test", (byte) 20);

    }

}
