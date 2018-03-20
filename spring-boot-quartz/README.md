# spring-boot整合quzrtz
方式一：
    通过导入quartz依赖的方式，使用config来配置quartz，和在sping中整合quartz的思路一样。
    
方式二：
    通过使用`spring-boot-starter-quartz`来配置quartz，在spring-boot中显然更加合理
    
    
spring-boot中quartz使用主要是
    
    简单定时任务：
        通过简单的@Schedule注解就可以配置简单的静态任务，但是这种方式不够灵活，如果修改任务就要修改代码。
    可调度的任务:
        通过quartz来实现定时任务调度，任务存储到数据库中。
        
# 一,简单的定时任务
1.添加依赖 

```
        <!--quartz依赖-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-quartz</artifactId>
        </dependency>
```
    
    
2.使用@Schedulez注解配置定时任务
```
package com.tuxianchao.demo.springbootquartz.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 静态的定时任务，只需要简单的通过@Schedule注解配置即可完成
 * <p>
 * 在主方法上添加@EnableScheduling注解即可
 */
@Component
public class StaticJob {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final static long SECOND = 1000;


    /**
     * fixedDelay: 固定延迟时间执行
     */
    @Scheduled(fixedDelay = 1 * SECOND)
    public void fixedDelayJob() {
        logger.info("{}\t固定延迟时间执行", currentDateTime());
    }

    /**
     * fixedRate: 固定间隔时间执行
     */
    @Scheduled(fixedRate = 1 * SECOND)
    public void fixedRate() {
        logger.info("{}\t固定时间间隔执行", currentDateTime());
    }

    /**
     * cron: 通过 Cron 表达式控制执行
     */
    @Scheduled(cron = "*/10 * * * * *")
    public void cron() {
        logger.info("{}\tcron表达式定义任务", currentDateTime());
    }

    public Date currentDateTime() {
        return new Date();
    }
}

```
3.OK,就这样，在主类上添加@EnableScheduling就完成了定时任务的配置。
启动后控制台输出：
```
Scheduler class: 'org.quartz.core.QuartzScheduler' - running locally.
  NOT STARTED.
  Currently in standby mode.
  Number of jobs executed: 0
  Using thread pool 'org.quartz.simpl.SimpleThreadPool' - with 10 threads.
  Using job-store 'org.quartz.simpl.RAMJobStore' - which does not support persistence. and is not clustered.

2018-03-20 10:37:36.304  INFO 5148 --- [           main] org.quartz.impl.StdSchedulerFactory      : Quartz scheduler 'quartzScheduler' initialized from an externally provided properties instance.
2018-03-20 10:37:36.304  INFO 5148 --- [           main] org.quartz.impl.StdSchedulerFactory      : Quartz scheduler version: 2.3.0
2018-03-20 10:37:36.304  INFO 5148 --- [           main] org.quartz.core.QuartzScheduler          : JobFactory set to: org.springframework.boot.autoconfigure.quartz.AutowireCapableBeanJobFactory@7a1234bf
2018-03-20 10:37:36.711  INFO 5148 --- [           main] o.s.j.e.a.AnnotationMBeanExporter        : Registering beans for JMX exposure on startup
2018-03-20 10:37:36.724  INFO 5148 --- [           main] o.s.c.support.DefaultLifecycleProcessor  : Starting beans in phase 2147483647
2018-03-20 10:37:36.725  INFO 5148 --- [           main] o.s.s.quartz.SchedulerFactoryBean        : Starting Quartz Scheduler now
2018-03-20 10:37:36.726  INFO 5148 --- [           main] org.quartz.core.QuartzScheduler          : Scheduler quartzScheduler_$_NON_CLUSTERED started.
2018-03-20 10:37:36.737  INFO 5148 --- [           main] s.a.ScheduledAnnotationBeanPostProcessor : No TaskScheduler/ScheduledExecutorService bean found for scheduled processing
2018-03-20 10:37:36.758  INFO 5148 --- [pool-1-thread-1] c.t.demo.springbootquartz.job.StaticJob  : Tue Mar 20 10:37:36 CST 2018	固定时间间隔执行
2018-03-20 10:37:36.761  INFO 5148 --- [pool-1-thread-1] c.t.demo.springbootquartz.job.StaticJob  : Tue Mar 20 10:37:36 CST 2018	固定延迟时间执行
2018-03-20 10:37:36.762  INFO 5148 --- [           main] c.t.d.s.SpringBootQuartzApplication      : Started SpringBootQuartzApplication in 4.51 seconds (JVM running for 6.009)
2018-03-20 10:37:37.758  INFO 5148 --- [pool-1-thread-1] c.t.demo.springbootquartz.job.StaticJob  : Tue Mar 20 10:37:37 CST 2018	固定时间间隔执行
2018-03-20 10:37:37.763  INFO 5148 --- [pool-1-thread-1] c.t.demo.springbootquartz.job.StaticJob  : Tue Mar 20 10:37:37 CST 2018	固定延迟时间执行
2018-03-20 10:37:38.757  INFO 5148 --- [pool-1-thread-1] c.t.demo.springbootquartz.job.StaticJob  : Tue Mar 20 10:37:38 CST 2018	固定时间间隔执行
2018-03-20 10:37:38.763  INFO 5148 --- [pool-1-thread-1] c.t.demo.springbootquartz.job.StaticJob  : Tue Mar 20 10:37:38 CST 2018	固定延迟时间执行
2018-03-20 10:37:39.758  INFO 5148 --- [pool-1-thread-1] c.t.demo.springbootquartz.job.StaticJob  : Tue Mar 20 10:37:39 CST 2018	固定时间间隔执行
2018-03-20 10:37:39.764  INFO 5148 --- [pool-1-thread-1] c.t.demo.springbootquartz.job.StaticJob  : Tue Mar 20 10:37:39 CST 2018	固定延迟时间执行
2018-03-20 10:37:40.001  INFO 5148 --- [pool-1-thread-1] c.t.demo.springbootquartz.job.StaticJob  : Tue Mar 20 10:37:40 CST 2018	cron表达式定义
```
# 二,可调度的定时任务