package com.tuxianchao.demo.springbootjdbctemplatedatasource.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * 配置两个数据源
 */
@Configuration
public class DataSourceConfig {
    /**
     * 数据源1
     *
     * @return
     */
    @Bean
    @ConfigurationProperties("spring.datasource.druid.datasource-one")
    @Qualifier("druidDataSourceOne")
    public DataSource dataSourceOne() {
        return DruidDataSourceBuilder.create().build();
    }

    /**
     * 数据源2
     *
     * @return
     */
    @Bean
    @ConfigurationProperties("spring.datasource.druid.datasource-two")
    @Qualifier("druidDataSourceTwo")
    public DataSource dataSourceTwo() {
        return DruidDataSourceBuilder.create().build();
    }
}
