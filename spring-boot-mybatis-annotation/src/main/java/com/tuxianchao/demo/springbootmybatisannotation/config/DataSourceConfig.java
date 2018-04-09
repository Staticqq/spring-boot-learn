package com.tuxianchao.demo.springbootmybatisannotation.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * 配置数据源
 */
@Configuration
public class DataSourceConfig {
    /**
     * 数据源
     *
     * @return
     */
    @Bean
    @ConfigurationProperties("spring.datasource.druid.datasource-one")
    public DataSource dataSourceOne() {
        return DruidDataSourceBuilder.create().build();
    }


}
