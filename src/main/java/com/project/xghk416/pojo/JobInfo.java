package com.project.xghk416.pojo;


import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class JobInfo {
    private String userId;
//    侦测的类型：up主/视频
    private String jobType;
//    corn表达式
    private String cornExpression;
//    侦测的对象
    private String detectObject;
    private String detectObjectId;
    private String detectObjectProfile;
//    侦测次数
    private String detectTime;
    private String duringDate;
//  作者名称
    private Integer authsId;
    private String auths;
    private String authsProfile;

//    侦测id
    private String group;
//    开始时间
    private long startTime;
    private long endTime;
}

