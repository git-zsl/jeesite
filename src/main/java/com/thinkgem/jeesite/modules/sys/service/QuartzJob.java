package com.thinkgem.jeesite.modules.sys.service;


import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class QuartzJob implements Job{
        @Override
        public void execute(JobExecutionContext context) throws JobExecutionException {
                //定时处理自己的任务
                System.out.println("执行定时任务。。。。。。");
        }
    }
