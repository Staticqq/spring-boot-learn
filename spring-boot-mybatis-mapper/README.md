# spring-boot集成mybatis，使用通用mapper

spring-boot集成mybatis其实非常简单，这里选择了一个github上的开源项目
，可以逆向生成实体类，mapper接口和mapper映射文件，同时集成了通用mapper，
对于简单的单表操作，已经有了，比较方便。

> 通用mapper:https://github.com/abel533/Mapper/wiki/1.3-spring-boot
> 逆向工程：https://github.com/abel533/Mapper/wiki/4.1.mappergenerator


# 集成具体步骤
## 1.添加依赖。
pom.xml
```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.tuxianchao</groupId>
    <artifactId>spring-boot-mybatis-mapper</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <packaging>jar</packaging>

    <name>spring-boot-mybatis-mapper</name>
    <description>spring-boot集成通用mapper，逆向工程生成mapper</description>

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.0.1.RELEASE</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>

    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
        <java.version>1.8</java.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-jdbc</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-logging</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>


        <!--mybatis-spring-boot-starter-->
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
            <version>1.3.2</version>
        </dependency>
        <!--通用mapper-->
        <dependency>
            <groupId>tk.mybatis</groupId>
            <artifactId>mapper-spring-boot-starter</artifactId>
            <version>2.0.0</version>
        </dependency>

        <!--pagehelper分页插件-->
        <dependency>
            <groupId>com.github.pagehelper</groupId>
            <artifactId>pagehelper-spring-boot-starter</artifactId>
            <version>1.2.3</version>
        </dependency>

        <!--工逆向工程依赖-->
        <dependency>
            <groupId>org.mybatis.generator</groupId>
            <artifactId>mybatis-generator-core</artifactId>
            <version>1.3.2</version>
            <scope>compile</scope>
            <optional>true</optional>
        </dependency>

        <!--mysql驱动-->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <scope>runtime</scope>
        </dependency>
        <!--druid数据源-->
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid-spring-boot-starter</artifactId>
            <version>1.1.9</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <resources>
        <resources>
            <resource>
                <directory>src/main/java</directory>
                <includes>
                    <include>**/*.xml</include>
                </includes>
            </resource>
            <!--<resource>-->
                <!--<directory>src/main/resources</directory>-->
                <!--<includes>-->
                    <!--<include>**/*.xml</include>-->
                    <!--<include>**/*.properties</include>-->
                    <!--<include>**/*.yml</include>-->
                <!--</includes>-->
            <!--</resource>-->
        </resources>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
            <plugin>
                <artifactId>maven-compiler-plugin</artifactId>
                <configuration>
                    <source>1.8</source>
                    <target>1.8</target>
                </configuration>
            </plugin>
            <plugin>
                <!--逆向工程插件-->
                <groupId>org.mybatis.generator</groupId>
                <artifactId>mybatis-generator-maven-plugin</artifactId>
                <version>1.3.6</version>
                <configuration>
                    <!--逆向工程的配置文件-->
                    <configurationFile>
                        ${basedir}/src/main/resources/generator/generatorConfig.xml
                    </configurationFile>
                    <overwrite>true</overwrite>
                    <verbose>true</verbose>
                </configuration>
                <dependencies>
                    <dependency>
                        <groupId>mysql</groupId>
                        <artifactId>mysql-connector-java</artifactId>
                        <version>5.1.29</version>
                    </dependency>
                    <dependency>
                        <groupId>tk.mybatis</groupId>
                        <artifactId>mapper</artifactId>
                        <version>4.0.0</version>
                    </dependency>
                </dependencies>
            </plugin>
        </plugins>
    </build>


</project>


```

## 2.逆向工程的配置：
```
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE generatorConfiguration
        PUBLIC "-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN"
        "http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd">

<generatorConfiguration>
    <context id="MysqlContext" targetRuntime="MyBatis3Simple" defaultModelType="flat">
        <property name="beginningDelimiter" value="`"/>
        <property name="endingDelimiter" value="`"/>

        <!--配置是否使用通用 Mapper 自带的注释扩展，默认 true-->
        <!--<property name="useMapperCommentGenerator" value="false"/>-->

        <!--配置通用 Mapper 插件，可以生成带注解的实体类
         这里最关键的参数就是 mappers，配置后生成的 Mapper 接口都会自动继承上改接口，
        -->
        <plugin type="tk.mybatis.mapper.generator.MapperPlugin">
            <!--<property name="mappers" value="tk.mybatis.mapper.common.Mapper,tk.mybatis.mapper.hsqldb.HsqldbMapper"/>-->
            <property name="mappers" value="tk.mybatis.mapper.common.Mapper"/>
            <!--caseSensitive 是否区分大小写，默认值 false。如果数据库区分大小写，这里就需要配置为 true，这样当表名为 USER 时，会生成 @Table(name = "USER") 注解，否则使用小写 user 时会找不到表。-->
            <property name="caseSensitive" value="true"/>
            <!--forceAnnotation 是否强制生成注解，默认 false，如果设置为 true，不管数据库名和字段名是否一致，都会生成注解（包含 @Table 和 @Column）。-->
            <property name="forceAnnotation" value="true"/>
            <!--beginningDelimiter 和 endingDelimiter 开始和结束分隔符，对于有关键字的情况下适用。-->
            <property name="beginningDelimiter" value="`"/>
            <property name="endingDelimiter" value="`"/>
            <!--useMapperCommentGenerator 是否使用通用 Mapper 提供的注释工具，默认 true 使用，这样在生成代码时会包含字段的注释（目前只有 mysql 和 oracle 支持），设置 false 后会用默认的，或者你可以配置自己的注释插件。
            -->
            <property name="useMapperCommentGenerator" value="true"/>
        </plugin>

        <jdbcConnection driverClass="com.mysql.jdbc.Driver"
                        connectionURL="jdbc:mysql://localhost:3306/spring-boot"
                        userId="root"
                        password="123456">
        </jdbcConnection>

        <!-- 对于生成的pojo所在包 -->
        <javaModelGenerator targetPackage="com.tuxianchao.springbootmybatismapper.pojo" targetProject="src/main/java"/>

        <!-- 对于生成的mapper所在目录 -->
        <sqlMapGenerator targetPackage="com.tuxianchao.springbootmybatismapper.mapper" targetProject="src/main/java"/>

        <!-- 配置mapper对应的java映射 -->
        <javaClientGenerator targetPackage="com.tuxianchao.springbootmybatismapper.mapper" targetProject="src/main/java"
                             type="XMLMAPPER"/>

        <!--和数据库对应的配置-->
        <table tableName="tbl_user" domainObjectName="User">
            <generatedKey column="id" sqlStatement="JDBC"/>
        </table>

    </context>
</generatorConfiguration>
```

## 3.执行逆向工程，生成实体类和mapper接口，mapper文件
我选择maven插件的方式，也可以手写一个工具来生成。

## 4.配置mybatis
具体的配置包含
```
    #数据源配置
    spring:
      datasource:
        url: jdbc:mysql://localhost:3306/spring-boot
        password: 123456
        username: root
        driver-class-name: com.mysql.jdbc.Driver
        druid:
          min-idle: 1
          initial-size: 10
          max-active: 20
          test-on-borrow: true
    #mybaits配置
    mybatis:
      mapper-locations: classpath:com/tuxianchao/springbootmybatismapper/mapper/*.xml
    #通用mapper配置
    mapper:
      identity: MYSQL
      mappers: tk.mybatis.mapper.common.Mapper
      not-empty: true
    #分页插件配置
    pagehelper:
      helper-dialect: mysql
      reasonable: true
      support-methods-arguments: true
      params: count=countSql
```
1.数据源
2.mybatis的mapper位置的配置
3.通用mapper的配置
4.分页插件的配置
## 5.然后在Application上加上MapperScan的配置就好了
```
@SpringBootApplication
@MapperScan(
        basePackages = "com.tuxianchao.springbootmybatismapper.mapper")
public class SpringBootMybatisMapperApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootMybatisMapperApplication.class, args);
    }
}
```
## 使用注意点
1.MapperScan使用的是tk.mybatis.spring.annotation.MapperScan
2.配置mapperlocation的地址
3.如果mapper文件放在和接口包里，可能存在maven资源拷贝的问题，加上资源拷贝插件


## 5. 事务的配置

事务的配置采用全注解的方式，其实spring-boot在启动的时候，就会根据你pom里面依赖的，比如
使用的spring-boot-statrer-jdbc,那么就会自动配置一个相应的平台事务管理器。
所以，可以简单的使用spring-boot的注解来完成事务的配置

1.在Main类启用事务注解
```
/**
 * 启用日志注解，等同于xml配置方式的 <tx:annotation-driven />
 * 开启mybbatis的mapper扫描
 */
@SpringBootApplication
@EnableTransactionManagement
@MapperScan(
        basePackages = "com.tuxianchao.springbootmybatismapper.mapper")
public class SpringBootMybatisMapperApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootMybatisMapperApplication.class, args);
    }
}
```
2. 在需要事务的地方，使用@Transactional注解配置事务

```
   @Transactional(propagation = Propagation.REQUIRED)
    public int insertUser(User user) {
        int result = userMapper.insert(user);
        //手动抛出一个异常，测试事务是否生效
        throw new NullPointerException();
        //return result;
    }
```

3.ok
