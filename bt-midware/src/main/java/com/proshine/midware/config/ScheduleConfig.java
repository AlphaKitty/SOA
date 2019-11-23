package com.proshine.midware.config;

import com.proshine.midware.quartz.course.data.PullDataManagerImpl;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时任务配置类
 * ClassName: Schedule
 * Author: zyl
 * Date: 2019/10/18 21:31
 */
@Log4j2
@Component
@EnableScheduling
public class ScheduleConfig {

    @Autowired
    private PullDataManagerImpl pullDataManagerImpl;

    /**
     * 石油大学课表拉取定时任务 Oracle->Mysql
     */
    // TODO: 2019/10/18 zylTodo 测试每分钟执行一次 后面还要考虑定时任务的实现具体放在哪个模块
    // 测试每分钟执行一次
    // @Scheduled(cron = "0 * * * * ?")
    // 实际的每周一的凌晨一点执行
    @Scheduled(cron = "0 0 1 ? * MON")
    public void FetchDataFromOracle() {
        try {
            log.info("拉取课表数据源定时任务开始执行");
            pullDataManagerImpl.pullData();
        } catch (Exception e) {
            log.error("拉取课表数据源数据失败! 尝试第1次重试...");
            try {
                pullDataManagerImpl.pullData();
            } catch (Exception ex) {
                log.error("拉取课表数据源数据失败! 尝试第2次重试...");
                try {
                    pullDataManagerImpl.pullData();
                } catch (Exception exc) {
                    log.error("三次拉取全部失败! 打印堆栈如下:");
                    for (StackTraceElement element : exc.getStackTrace()) {
                        log.error(element.toString());
                    }
                }
            }
        }
    }

}
