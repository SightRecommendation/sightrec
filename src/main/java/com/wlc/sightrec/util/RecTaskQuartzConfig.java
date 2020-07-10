package com.wlc.sightrec.util;

import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RecTaskQuartzConfig {

    private static final String REC_TASK_IDENTITY = "RecTaskQuartz";

    @Bean(name = "recQuartzDetail")
    public JobDetail recQuartzDetail(){
        return JobBuilder.newJob(RecTask.class).withIdentity(REC_TASK_IDENTITY).storeDurably().build();
    }

    @Bean(name = "recQuartzTrigger")
    public Trigger recQuartzTrigger(){
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
        		.withIntervalInSeconds(300)  //10s执行一次 for debug
                //.withIntervalInHours(1)  //一个小时执行一次
                .repeatForever();
        return TriggerBuilder.newTrigger().forJob(recQuartzDetail())
                .withIdentity(REC_TASK_IDENTITY)
                .withSchedule(scheduleBuilder)
                .build();
    }
}
