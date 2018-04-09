# spring boot中使用mybatis，完全注解

这种方式呢，需要手动编写实体类和mapper接口，然后使用mybatis的注解来配置sql和结果集等。



1.配置druid数据源,分页插件

```
spring:
  datasource:
    username: root
    password: 123456
    driver-class-name: com.mysql.jdbc.Driver
    type: com.alibaba.druid.pool.DruidDataSource
    druid:
      datasource-one:
        url: jdbc:mysql://localhost:3306/spring-boot
pagehelper:
  helper-dialect: mysql
  reasonable: true
  support-methods-arguments: true
server:
  port: 8888
```

2.编写对应的实体类和mapper接口

实体类
```
public class User implements Serializable {

    private static final long serialVersionUID = 1459159974353794657L;
    private Long id;
    private String name;
    private Byte age;

    //setter,getter
}

```
mapper接口

```
package com.tuxianchao.demo.springbootmybatisannotation.mapper;

import com.tuxianchao.demo.springbootmybatisannotation.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author tuxianchao
 * @date 2018/1/30 23:19
 */
@Mapper
public interface UserMapper {
    /**
     * 查询所有，使用pagehelper来做分页
     *
     * @return
     */
    @Results({
            @Result(column = "name", property = "name"),
            @Result(column = "age", property = "age"),
            @Result(column = "id", property = "id")
    })
    @Select("SELECT id,name,age FROM tbl_user")
    List<User> selectAll();

    /**
     * 查
     *
     * @param name
     * @return
     */
    @Select("SELECT id,name,age FROM tbl_user WHERE name=#{name}")
    User findByName(@Param("name") String name);

    /**
     *
     * @param user
     * @return
     */
    @Insert("INSERT INTO tbl_user (name,age) VALUES(#{name},#{age})")
    int insert(User user);

    /**
     * @param user 改
     */
    @Update("UPDATE tbl_user SET name=#{name},age=#{age} WHERE id=#{id}")
    void update(User user);

    /**
     * @param id 删
     */
    @Delete("DELETE FROM tbl_user WHERE id=#{id}")
    void delete(Long id);


}

```

3.OK



