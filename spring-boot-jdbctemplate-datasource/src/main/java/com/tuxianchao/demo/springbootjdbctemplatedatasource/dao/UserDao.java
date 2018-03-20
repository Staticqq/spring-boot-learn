package com.tuxianchao.demo.springbootjdbctemplatedatasource.dao;

/**
 * @author tuxianchao
 * @date 2018/1/25 1:01
 */
public interface UserDao {
    /**
     * 新增一个用户
     *
     * @param name
     * @param age
     */
    void create(Integer id, String name, Byte age);

    /**
     * 根据name删除一个用户
     *
     * @param name
     */
    void deleteByName(String name);

    /**
     * 获取用户总量
     */
    Integer getAllUsers();

    /**
     * 删除所有用户
     */
    void deleteAllUsers();
}
