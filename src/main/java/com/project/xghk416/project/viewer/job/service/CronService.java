package com.project.xghk416.project.viewer.job.service;

import com.project.xghk416.pojo.JobInfo;

public interface CronService {
    JobInfo addCronJob(JobInfo jobInfo) throws Exception;

    boolean jobPause(String jobClassName, String jobGroup) throws Exception;

    boolean jobResume(String jobClassName, String jobGroupName) throws Exception;

    boolean jobReschedule(String jobClassName, String jobGroupName, String cronExpression) throws Exception;

    boolean jobDelete(String jobClassName, String jobGroupName) throws Exception;

    BaseJob getClass(String classname) throws Exception;
}
