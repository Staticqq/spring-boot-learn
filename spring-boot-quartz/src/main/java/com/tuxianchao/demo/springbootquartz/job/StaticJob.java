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
