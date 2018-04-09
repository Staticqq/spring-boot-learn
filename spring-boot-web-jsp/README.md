# spring-boot使用jsp视图

简单几步就可以在spring-boot中使用jsp

1.添加依赖

```
<!--使用jsp视图配置以下三个依赖-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-tomcat</artifactId>
            <!--<scope>provided</scope>-->
        </dependency>
        <dependency>
            <groupId>org.apache.tomcat.embed</groupId>
            <artifactId>tomcat-embed-jasper</artifactId>
            <!--<scope>provided</scope>-->
        </dependency>
        <dependency>
            <groupId>javax.servlet</groupId>
            <artifactId>jstl</artifactId>
        </dependency>
```

2.配置视图解析，类似于配置springmvc的视图解析器

在application.properties中：
```
#jsp视图配置，类似于配置springmvc的视图解析器
spring.mvc.view.prefix=/WEB-INF/jsp/
spring.mvc.view.suffix=.jsp
application.message=Hello tuxianchao
```

3.建立好视图资源的路径

在controller中返回逻辑视图名即可

```
   @GetMapping("/welcome")
    public String welcome(Model model) {
        model.addAttribute("time", new Date());
        model.addAttribute("message", this.message);
        //返回逻辑视图名
        return "welcome";
    }
```