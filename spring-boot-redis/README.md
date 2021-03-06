Spring-boot集成redis

1.添加依赖

```
        <!--spring-boot-redis-->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-redis</artifactId>
		</dependency>
		 <!--序列化为json存储依赖json库文件-->
		<!-- https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-core -->
        		<dependency>
        			<groupId>com.fasterxml.jackson.core</groupId>
        			<artifactId>jackson-core</artifactId>
        			<version>2.9.4</version>
        		</dependency>
        		<!-- https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind -->
        		<dependency>
        			<groupId>com.fasterxml.jackson.core</groupId>
        			<artifactId>jackson-databind</artifactId>
        			<version>2.9.4</version>
        		</dependency>
        		<!-- https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-annotations -->
        		<dependency>
        			<groupId>com.fasterxml.jackson.core</groupId>
        			<artifactId>jackson-annotations</artifactId>
        			<version>2.9.4</version>
        		</dependency>
```

2.配置redis环境信息
```
spring:
  redis:
    host: 127.0.0.1
    port: 6379
    database: 0
    jedis:
      pool:
        max-active: 8
        min-idle: 0
        max-idle: 8
        max-wait: -1
    timeout: 1000000


```
3.配置redistemplate
```
package com.tuxianchao.demo.springbootredis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    /***
     * 配置一个默认的redistemplate，当然也可以在使用的时候注入后具体的来设置这些选项
     * @param redisConnectionFactory
     * @return
     */
    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        //设置nosql键值对序列化方式
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<Object>(Object.class));
        return redisTemplate;
    }


}


```
4.使用redis

```
package com.tuxianchao.demo.springbootredis;

import com.tuxianchao.demo.springbootredis.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {
    /**
     * 默认提供的字符串redistemplate
     */
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    /**
     * 配置的Redistemplate
     */
    @Autowired
    private RedisTemplate redisTemplate;


    /**
     * 存取数据到redis
     */
    @Test
    public void test01() {
        User user = new User("卡莉斯塔", "kalisita");
        User user2 = new User("阿莉斯塔", "alisita");
        User user3 = new User("利斯塔", "lisita");
        //存字符串
        stringRedisTemplate.opsForValue().set("stringUser", String.valueOf(user));
        //存对象
        redisTemplate.opsForValue().set("jsonUser", user2);
        System.out.println(stringRedisTemplate.opsForValue().get("stringUser"));
        System.out.println(redisTemplate.opsForValue().get("jsonUser"));
    }

    /**
     * 测试存取其他数据结构
     */
    @Test
    public void test2() {
        User user = new User("卡莉斯塔1", "kalisita");
        User user2 = new User("阿莉斯塔2", "alisita");
        User user3 = new User("利斯塔3", "lisita");
        //存为set
        redisTemplate.opsForSet().add("userSet", user, user2, user3);
        //取出set
        System.out.println("###############################################");
        System.out.println(redisTemplate.opsForSet().members("userSet"));

        //存list
        redisTemplate.opsForList().rightPushAll("userList", user, user2, user3);
        //取ist
        System.out.println("###############################################");
        System.out.println(redisTemplate.opsForList().rightPop("userList"));

        //存zSet
        redisTemplate.opsForZSet().add("userZSet", user, 1);
        redisTemplate.opsForZSet().add("userZSet", user2, 2);
        redisTemplate.opsForZSet().add("userZSet", user3, 3);
        //取zSet
        System.out.println("###############################################");
        System.out.println(redisTemplate.opsForZSet().range("userZSet", 0, -1));

        //存hash
        redisTemplate.opsForHash().put("userMap", "user", user);
        redisTemplate.opsForHash().put("userMap", "user2", user);
        redisTemplate.opsForHash().put("userMap", "user3", user);
        //取hash
        System.out.println("###############################################");
        Set userMapKeys = redisTemplate.opsForHash().keys("userMap");
        for (Object key : userMapKeys) {
            System.out.println(redisTemplate.opsForHash().get("userMap", key));
        }
    }

    /**
     * 测试发送命令
     */
    @Test
    public void test3() {
        //flushDb
        redisTemplate.getConnectionFactory().getConnection().flushDb();
        //遍历key
        Set<byte[]> keys = redisTemplate.getConnectionFactory().getConnection().keys("*".getBytes());
        for (byte[] b : keys) {
            System.out.println(new String(b));
        }
    }

}

```