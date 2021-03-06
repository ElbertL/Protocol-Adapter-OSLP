/**
 * Copyright 2015 Smart Society Services B.V.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 */
package com.alliander.osgp.webdevicesimulator.application.config;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;

import com.alliander.osgp.webdevicesimulator.application.tasks.AutonomousDeviceRegister;

@Configuration
@EnableScheduling
@PropertySources({
    @PropertySource("classpath:web-device-simulator.properties"),
    @PropertySource(value = "file:${osgp/WebDeviceSimulator/config}", ignoreResourceNotFound = true),
})
public class AutonomousDeviceRegisterConfig {

    private static final String PROPERTY_NAME_AUTONOMOUS_TASKS_CRON_EXPRESSION = "autonomous.tasks.device.registration.cron.expression";
    private static final String PROPERTY_NAME_AUTONOMOUS_POOL_SIZE = "autonomous.task.device.registration.pool.size";
    private static final String PROPERTY_NAME_AUTONOMOUS_THREAD_NAME_PREFIX = "autonomous.task.device.registration.thread.name.prefix";

    @Resource
    private Environment environment;

    @Autowired
    private AutonomousDeviceRegister autonomousDeviceRegister;

    @Bean
    public CronTrigger autonomousDeviceRegisterTrigger() {
        final String cron = this.environment.getRequiredProperty(PROPERTY_NAME_AUTONOMOUS_TASKS_CRON_EXPRESSION);
        return new CronTrigger(cron);
    }

    @Bean(destroyMethod = "shutdown")
    public TaskScheduler deviceRegistrationTaskScheduler() {
        final ThreadPoolTaskScheduler deviceRegistrationTaskScheduler = new ThreadPoolTaskScheduler();
        deviceRegistrationTaskScheduler.setPoolSize(Integer.parseInt(this.environment
                .getRequiredProperty(PROPERTY_NAME_AUTONOMOUS_POOL_SIZE)));
        deviceRegistrationTaskScheduler.setThreadNamePrefix(this.environment
                .getRequiredProperty(PROPERTY_NAME_AUTONOMOUS_THREAD_NAME_PREFIX));
        deviceRegistrationTaskScheduler.setWaitForTasksToCompleteOnShutdown(false);
        deviceRegistrationTaskScheduler.setAwaitTerminationSeconds(10);
        deviceRegistrationTaskScheduler.initialize();
        deviceRegistrationTaskScheduler.schedule(this.autonomousDeviceRegister, this.autonomousDeviceRegisterTrigger());
        return deviceRegistrationTaskScheduler;
    }

}
