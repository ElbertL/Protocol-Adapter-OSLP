package com.alliander.osgp.webdevicesimulator.application.config;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;

import com.alliander.osgp.webdevicesimulator.application.tasks.LightSwitchingOn;

@Configuration
@EnableScheduling
@PropertySource("file:${osp//webDeviceSimulator/config}")
public class LightSwitchingOnConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(LightSwitchingOnConfig.class);

    private static final String PROPERTY_NAME_AUTONOMOUS_TASKS_LIGHTSWITCHING_ON_CRON_EXPRESSION = "autonomous.tasks.lightswitching.on.cron.expression";
    private static final String PROPERTY_NAME_AUTONOMOUS_LIGHTSWITCHING_ON_POOL_SIZE = "autonomous.tasks.lightswitching.on.pool.size";
    private static final String PROPERTY_NAME_AUTONOMOUS_LIGHTSWITCHING_ON_THREAD_NAME_PREFIX = "autonomous.tasks.lightswitching.on.thread.name.prefix";

    @Resource
    private Environment environment;

    @Autowired
    private LightSwitchingOn lightSwitchingOn;

    @Bean
    public CronTrigger lightSwitchingOffTrigger() {
        final String cron = this.environment
                .getRequiredProperty(PROPERTY_NAME_AUTONOMOUS_TASKS_LIGHTSWITCHING_ON_CRON_EXPRESSION);
        return new CronTrigger(cron);
    }

    @Bean(destroyMethod = "shutdown")
    public TaskScheduler lightSwitchingOnTaskScheduler() {
        final ThreadPoolTaskScheduler lightSwitchingOnTaskScheduler = new ThreadPoolTaskScheduler();
        lightSwitchingOnTaskScheduler.setPoolSize(Integer.parseInt(this.environment
                .getRequiredProperty(PROPERTY_NAME_AUTONOMOUS_LIGHTSWITCHING_ON_POOL_SIZE)));
        lightSwitchingOnTaskScheduler.setThreadNamePrefix(this.environment
                .getRequiredProperty(PROPERTY_NAME_AUTONOMOUS_LIGHTSWITCHING_ON_THREAD_NAME_PREFIX));
        lightSwitchingOnTaskScheduler.setWaitForTasksToCompleteOnShutdown(false);
        lightSwitchingOnTaskScheduler.setAwaitTerminationSeconds(10);
        lightSwitchingOnTaskScheduler.initialize();
        lightSwitchingOnTaskScheduler.schedule(this.lightSwitchingOn, this.lightSwitchingOffTrigger());
        return lightSwitchingOnTaskScheduler;
    }

}