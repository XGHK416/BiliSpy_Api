package com.project.xghk416.project.viewer.job.service.impl;

import com.project.xghk416.pojo.JobInfo;
import com.project.xghk416.project.viewer.job.service.BaseJob;
import com.project.xghk416.project.viewer.job.service.CronService;
import com.project.xghk416.common.util.SpringUtil;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service("ICronService")
public class ICronService implements CronService {

    //加入Qulifier注解，通过名称注入bean
    @Autowired
    @Qualifier("Scheduler")
    private Scheduler scheduler;

    @Override
    public JobInfo addCronJob(JobInfo jobInfo) throws Exception {
        //        数据处理
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        List<String> duringDate = Arrays.asList(jobInfo.getDuringDate().split(","));
        long currentSecond = new Date().getTime();
        Date start = format.parse(duringDate.get(0));
        Date end = format.parse(duringDate.get(1));
        long during = end.getTime()-start.getTime();
        if (currentSecond>start.getTime()){
//            如果开始时间是今天的话
            //开始时间定在15分钟后
            start =new Date(currentSecond+60*15);
            end = new Date(start.getTime()+during);
        }


        // 启动调度器
        scheduler.start();
        String jobGroup = jobInfo.getUserId()+ start.getTime()+jobInfo.getDetectObjectId();
        System.out.println("jobGroup:"+jobGroup);
        jobInfo.setGroup(jobGroup);
        jobInfo.setStartTime(start.getTime());
        jobInfo.setEndTime(end.getTime());

        //构建job信息
        JobDetail jobDetail = JobBuilder.newJob(getClass(jobInfo.getJobType()).getClass()).
                withIdentity(jobInfo.getJobType(), jobGroup)
                .build();

//        传递参数
        jobDetail.getJobDataMap().put("id",jobInfo.getDetectObjectId());
        jobDetail.getJobDataMap().put("group",jobGroup);

        //表达式调度构建器(即任务执行的时间)
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(jobInfo.getCornExpression());

        //按新的cronExpression表达式构建一个新的trigger
        CronTrigger trigger = TriggerBuilder.newTrigger().
                withIdentity(jobInfo.getJobType(), jobGroup)
                .startAt(start)
                .endAt(end)
                .withSchedule(scheduleBuilder)
                .build();

        try {
            scheduler.scheduleJob(jobDetail, trigger);

        } catch (SchedulerException e) {
            System.out.println("创建定时任务失败" + e);
            throw new Exception("创建定时任务失败");
        }
        return jobInfo;
    }

    @Override
    public boolean jobPause(String jobClassName, String jobGroup) throws Exception {
        scheduler.pauseJob(JobKey.jobKey(jobClassName, jobGroup));
        return true;
    }

    @Override
    public boolean jobResume(String jobClassName, String jobGroupName) throws Exception {
        scheduler.resumeJob(JobKey.jobKey(jobClassName, jobGroupName));
        return true;
    }

    @Override
    public boolean jobReschedule(String jobClassName, String jobGroupName, String cronExpression) throws Exception {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(jobClassName, jobGroupName);
            // 表达式调度构建器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);

            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

            // 按新的cronExpression表达式重新构建trigger
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

            // 按新的trigger重新设置job执行
            scheduler.rescheduleJob(triggerKey, trigger);
        } catch (SchedulerException e) {
            System.out.println("更新定时任务失败" + e);
            throw new Exception("更新定时任务失败");
        }
        return true;
    }

    @Override
    public boolean jobDelete(String jobClassName, String jobGroupName) throws Exception {
        scheduler.pauseTrigger(TriggerKey.triggerKey(jobClassName, jobGroupName));
        scheduler.unscheduleJob(TriggerKey.triggerKey(jobClassName, jobGroupName));
        scheduler.deleteJob(JobKey.jobKey(jobClassName, jobGroupName));
        return true;
    }

    @Override
    public BaseJob getClass(String classname) throws Exception {
        BaseJob baseJob = (BaseJob) SpringUtil.getBean(classname);
        return baseJob;
    }
}
