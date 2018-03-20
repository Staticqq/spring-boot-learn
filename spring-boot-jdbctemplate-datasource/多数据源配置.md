spring-boot使用druid连接池，配置多数据源
1.添加依赖

```
        <!--mysql连接驱动-->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <scope>runtime</scope>
        </dependency>
        <!-- 阿里巴巴Druid数据源,使用druid-spring-boot-starter配置 -->
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid-spring-boot-starter</artifactId>
            <version>1.1.9</version>
        </dependency>
```
2.数据源配置信息，参考https://github.com/alibaba/druid/tree/master/druid-spring-boot-starter

```
#数据源1&&数据源2
spring:
  datasource:
    username: root
    password: 123456
    driver-class-name: com.mysql.jdbc.Driver
    type: com.alibaba.druid.pool.DruidDataSource
    druid:
      datasource-one:
         url: jdbc:mysql://localhost:3306/spring-boot
      datasource-two:
        url: jdbc:mysql://localhost:3306/spring-boot-2

```
数据源配置信息

```
package com.tuxianchao.demo.springbootjdbctemplatedatasource.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

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

```

配置多个jdbcemplate

```
package com.tuxianchao.demo.springbootjdbctemplatedatasource.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

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

```