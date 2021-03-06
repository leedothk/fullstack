package com.tips.batch.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.configuration.JobLocator;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import com.tips.batch.quartz.QuartzJob;

/**
 * The Class QuartzConfiguration.
 *
 * @author ashraf
 */
@Configuration
public class QuartzConfiguration
{
    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private JobLocator jobLocator;

    @Bean
    public JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor(JobRegistry jobRegistry)
    {
        JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor = new JobRegistryBeanPostProcessor();
        
        jobRegistryBeanPostProcessor.setJobRegistry(jobRegistry);
        
        return jobRegistryBeanPostProcessor;
    }
    
    @Bean
    public JobDetailFactoryBean jobDetailFactoryBean()
    {
        JobDetailFactoryBean jobfactory = new JobDetailFactoryBean();
        
        jobfactory.setJobClass(QuartzJob.class);
        
        Map<String, Object> map = new HashMap<String, Object>();
        
        map.put("jobName"    , "ETLJob");
        map.put("jobLauncher", jobLauncher);
        map.put("jobLocator" , jobLocator);
        
        jobfactory.setJobDataAsMap(map);
        jobfactory.setGroup("etl_group");
        jobfactory.setName("etl_job");
        
        return jobfactory;
    }

    // Job is scheduled after every 2 minute
    @Bean
    public CronTriggerFactoryBean cronTriggerFactoryBean()
    {
        CronTriggerFactoryBean ctFactory = new CronTriggerFactoryBean();
    
        ctFactory.setJobDetail(jobDetailFactoryBean().getObject());
        ctFactory.setStartDelay(3000);
        ctFactory.setName("cron_trigger");
        ctFactory.setGroup("cron_group");
      //ctFactory.setCronExpression("0 0/2 * 1/1 * ? *");
        ctFactory.setCronExpression("*/10 * * * * ?");
      
        return ctFactory;
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean()
    {
        SchedulerFactoryBean scheduler = new SchedulerFactoryBean();
        
        scheduler.setTriggers(cronTriggerFactoryBean().getObject());
        
        return scheduler;
    }
}