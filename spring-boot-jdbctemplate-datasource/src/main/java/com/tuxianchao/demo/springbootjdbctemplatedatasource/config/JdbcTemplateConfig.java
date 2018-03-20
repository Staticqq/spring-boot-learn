package com.tuxianchao.demo.springbootjdbctemplatedatasource.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * 两个数据源，所以配置两个jdcbtemplate
 */
@Configuration
public class JdbcTemplateConfig {
    /**
     * 数据源1 jdbctemplate
     *
     * @param dataSource
     * @return
     */
    @Bean
    @Qualifier("jdbcTemplateOne")
    public JdbcTemplate jdbcTemplateOne(
            @Qualifier("druidDataSourceOne") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    /**
     * 数据源2 jdbctemplate
     *
     * @param dataSource
     * @return
     */
    @Bean
    @Qualifier("jdbcTemplateTwo")
    public JdbcTemplate jdbcTemplateTwo(
            @Qualifier("druidDataSourceTwo") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
