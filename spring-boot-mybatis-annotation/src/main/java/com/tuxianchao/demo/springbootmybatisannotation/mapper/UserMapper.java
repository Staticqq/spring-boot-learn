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
