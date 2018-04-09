#spring-boot集成swagger


##seagger2
> 随着互联网技术的发展，现在的网站架构基本都由原来的后端渲染，变成了：前端渲染、先后端分离的形态，而且前端技术和后端技术在各自的道路上越走越远。 
前端和后端的唯一联系，变成了API接口；API文档变成了前后端开发人员联系的纽带，变得越来越重要，swagger就是一款让你更好的书写API文档的框架

参考[http://blog.csdn.net/i6448038/article/details/77622977](http://blog.csdn.net/i6448038/article/details/77622977)


总的说来，swagger用来构建restful API的文档，在前后端分离的时代，前端和后端交流的纽带。同时还可以做一些接口测试等。


## springboot集成swagger

###1. 添加swagger依赖

```
 <!--添加swagger依赖，用于构建restful的文档-->
        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-swagger2</artifactId>
            <version>2.2.2</version>
        </dependency>
        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-swagger-ui</artifactId>
            <version>2.2.2</version>
        </dependency>
```
### 2.配置swagger
在应用启动的Application.java同级目录创建配置类Swagger2Config
```
package com.tuxianchao.demo.springbootswagger2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author tuxianchao
 * @date 2018/1/24 0:57
 * 配置swagger2
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.tuxianchao.demo.springbootswagger2.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     *
     * 配置好的信息最后出现在生成的api文档上
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Spring Boot中使用Swagger2构建RESTful APIs")
                .description("wagger2，它可以轻松的整合到Spring Boot中，并与Spring MVC程序配合组织出强大RESTful API文档" +
                        "。它既可以减少我们创建文档的工作量，同时说明内容又整合入实现代码中，让维护文档和修改代码整合为一体" +
                        "，可以让我们在修改代码逻辑的同时方便的修改文档说明。另外Swagger2也提供了强大的页面测试功能来调试每个" +
                        "RESTful API")
                .termsOfServiceUrl("http://baidu.com/")
                .contact("tuxianchao")
                .version("1.0")
                .build();
    }

}

```
### 3.配置文档内容，也就是api文档
使用@ApiOperation和@ApiImplicitParam，@ApiImplicitParams注解来配置文档生成


例如
```
  @ApiOperation(value = "获取用户列表", notes = "获取用户列表，返回list")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<User> getUsers() {

        return new ArrayList<User>(userMap.values());
    }
```

```
@ApiOperation(value = "创建用户", notes = "根据User对象创建用户")
    @ApiImplicitParam(name = "user", value = "用户详细实体user", required = true, dataType = "User")
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String addUser(User user) {
        //post新增
        userMap.put(user.getId(), user);
        return "success";
    }
```

```
 @ApiOperation(value = "更新用户详细信息", notes = "根据url的id来指定更新对象，并根据传过来的user信息来更新用户详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "user", value = "用户详细实体user", required = true, dataType = "User")
    })
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
```

访问http://localhost:8080/swagger-ui.html查看