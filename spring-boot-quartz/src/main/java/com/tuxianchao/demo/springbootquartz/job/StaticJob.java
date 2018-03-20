package com.tuxianchao.demo.springbootquartz.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class StaticJob {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final static long SECOND = 1000;


    /**
     * fixedDelay: 固定延迟时间执行
     */
    @Scheduled(fixedDelay = 1 * SECOND)
    public void fixedDelayJob() {
        logger.info("{}\tfixedDelay", currentDateTime());
    }

    /**
     * fixedRate: 固定间隔时间执行
     */
    @Scheduled(fixedRate = 1 * SECOND)
    public void fixedRate() {
        logger.info("{}\tfixedRate", currentDateTime());
    }

    /**
     * cron: 通过 Cron 表达式控制执行
     */
    @Scheduled(cron = "*/10 * * * * *")
    public void cron() {
        logger.info("{}\tcron", currentDateTime());
    }

    public Date currentDateTime() {
        return new Date();
    }
}
