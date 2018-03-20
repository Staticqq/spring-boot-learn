package com.tuxianchao.demo.springbootjdbctemplatedatasource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 测试多数据源，注入两个jdbctemplate
 */
@RunWith(SpringRunner.class)
@SpringBootTest()
public class TestMultiDataSource {
    @Autowired
    @Qualifier("jdbcTemplateOne")
    private JdbcTemplate jdbcTemplateOne;
    @Autowired
    @Qualifier("jdbcTemplateTwo")
    private JdbcTemplate jdbcTemplateTwo;

    @Test
    public void test() {
        //测试插入到两个数据库
        jdbcTemplateOne.execute("insert into TBL_USER(NAME, AGE) values( 'www', '20')");
        jdbcTemplateTwo.execute("insert into TBL_USER(NAME, AGE) values( 'www', '20')");
    }
}
