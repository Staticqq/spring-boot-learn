#  使用redis作为mybatis的二级缓存

使用redis作为mybatis的二级缓存

在mybatis的缓存配置中可以指定缓存的实现，所以只需要实现一个org.apache.ibatis.cache.Cache,
然后作为mybatis的二级缓存实现即可，对于数据的读写操作转移到redis

1.配置好redis
2.实现缓存接口
MybatisRedisCache：
```
package com.tuxianchao.demo.springbootmybatiscacheredis.cache;

import org.apache.ibatis.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;

import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * mybatis redis缓存
 *
 * @author
 * @create 2018-04-15 17:57
 **/
public class MybatisRedisCache implements Cache {
    private static final Logger logger = LoggerFactory.getLogger(MybatisRedisCache.class);

    // 读写锁
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock(true);

    private RedisTemplate<String, Object> redisTemplate = SpringContextHolder.getBean("redisTemplate");

    private String id;

    public MybatisRedisCache(final String id) {
        if (id == null) {
            throw new IllegalArgumentException("Cache instances require an ID");
        }
        logger.info("Redis Cache id " + id);
        this.id = id;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void putObject(Object key, Object value) {
        if (value != null) {
            // 向Redis中添加数据，有效时间是2天
            redisTemplate.opsForValue().set(key.toString(), value, 2, TimeUnit.DAYS);
        }
    }

    @Override
    public Object getObject(Object key) {
        try {
            if (key != null) {
                Object obj = redisTemplate.opsForValue().get(key.toString());
                return obj;
            }
        } catch (Exception e) {
            logger.error("redis ");
        }
        return null;
    }

    @Override
    public Object removeObject(Object key) {
        try {
            if (key != null) {
                redisTemplate.delete(key.toString());
            }
        } catch (Exception e) {
        }
        return null;
    }

    @Override
    public void clear() {
        logger.debug("清空缓存");
        try {
            Set<String> keys = redisTemplate.keys("*:" + this.id + "*");
            if (!CollectionUtils.isEmpty(keys)) {
                redisTemplate.delete(keys);
            }
        } catch (Exception e) {
        }
    }

    @Override
    public int getSize() {
        Long size = (Long) redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.dbSize();
            }
        });
        return size.intValue();
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return this.readWriteLock;
    }

}

```
SpringContextHolder：
```
package com.tuxianchao.demo.springbootmybatiscacheredis.cache;


import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 以静态变量保存Spring ApplicationContext,
 * 可在任何代码任何地方任何时候中取出ApplicaitonContext.
 *
 * @author
 * @create 2018-04-15 18:10
 **/

@Component
public class SpringContextHolder implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    /**
     * 实现ApplicationContextAware接口的context注入函数, 将其存入静态变量.
     */
    public void setApplicationContext(ApplicationContext applicationContext) {
        SpringContextHolder.applicationContext = applicationContext; // NOSONAR
    }

    /**
     * 获取ApplicationContext.
     */
    public static ApplicationContext getApplicationContext() {
        checkApplicationContext();
        return applicationContext;
    }

    /**
     * 从ApplicationContext中取得Bean, 自动转型为所赋值对象的类型.
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) {
        checkApplicationContext();
        return (T) applicationContext.getBean(name);
    }

    /**
     * 从ApplicationContext中取得Bean, 自动转型为所赋值对象的类型.
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(Class<T> clazz) {
        checkApplicationContext();
        return (T) applicationContext.getBeansOfType(clazz);
    }


    public static void cleanApplicationContext() {
        applicationContext = null;
    }

    private static void checkApplicationContext() {
        if (applicationContext == null) {
            throw new IllegalStateException("applicaitonContext未注入");
        }
    }
}

```

3.配置使用mybatis二级缓存

```
1.替换缓存实现类
 <cache type="com.tuxianchao.demo.springbootmybatiscacheredis.cache.MybatisRedisCache">
        <property name="eviction" value="LRU" />
        <property name="flushInterval" value="6000000" />
        <property name="size" value="1024" />
        <property name="readOnly" value="false" />
    </cache>
    
 2.修改mapper接口（通用mapper的话加上注解CacheNamespaceRef）
 @CacheNamespaceRef(UserMapper.class)
 public interface UserMapper extends Mapper<User> {
 }
 
```