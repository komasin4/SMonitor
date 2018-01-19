package com.example.demo.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

@Configuration
@EnableScheduling
@ComponentScan({"com.example.demo.service"})
public class SchedulerConfiguration implements SchedulingConfigurer{

	@Override
	public void configureTasks(ScheduledTaskRegistrar arg0) {
		// TODO Auto-generated method stub
		
	}

}
