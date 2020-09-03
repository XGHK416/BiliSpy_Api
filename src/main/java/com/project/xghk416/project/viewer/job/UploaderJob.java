package com.project.xghk416.project.viewer.job;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.project.xghk416.enums.api.UploaderApiEnum;
import com.project.xghk416.pojo.JobInfo;
import com.project.xghk416.pojo.detect.DetectUploaderStatePo;
import com.project.xghk416.pojo.detect.DetectVideoStatePo;
import com.project.xghk416.project.viewer.job.service.impl.ICronService;
import com.project.xghk416.project.viewer.job.service.impl.IDetectModelService;
import com.project.xghk416.project.viewer.job.service.BaseJob;
import com.project.xghk416.common.util.RequestTemplateUtil;
import com.project.xghk416.common.util.SpringUtil;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * Created by haoxy on 2018/9/28.
 * E-mail:hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 */
@Component("uploaderJob")
public class UploaderJob implements BaseJob {

    private static Logger log = LoggerFactory.getLogger(UploaderJob.class);

    public UploaderJob() {

    }
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
//        IJobAndTriggerService iJobAndTriggerService = (IJobAndTriggerService) SpringUtil.getBean("IJobAndTriggerServiceImpl");
//        PageInfo<JobAndTrigger> jobAndTriggerDetails = iJobAndTriggerService.getJobAndTriggerDetails(1, 10);
//        System.out.println(jobAndTriggerDetails.getTotal());
//        log.info("Hello Job执行时间: " + new Date());
        String id = context.getJobDetail().getJobDataMap().get("id").toString();
        String group = context.getJobDetail().getJobDataMap().get("group").toString();
        System.out.println("job-params:"+id+","+group);
        IDetectModelService modelService = (IDetectModelService) SpringUtil.getBean("IDetectModelService");
        DetectUploaderStatePo uploaderStatePo = modelService.getUploaderJob(group);
        int have = uploaderStatePo.getHaveDetect();
        int max = uploaderStatePo.getDetectTimes();
        System.out.println("have&max"+have+","+max);

        if (have<max){
            long lastUpdate = uploaderStatePo.getLastUpdate().toEpochSecond(ZoneOffset.of("+8"));
            System.out.println("lastUpdate:"+lastUpdate);
            String url = UploaderApiEnum.UPLOADER_VIDEO_PUBLISH.getApiAddress()+"mid="+id;
            System.out.println("url:"+url);
            JSONArray result = RequestTemplateUtil.requestGetForJson(url).getJSONObject("data").getJSONObject("list").getJSONArray("vlist");
            long temp = lastUpdate;
            for (int i = 0; i < result.size(); i++) {

                if (have>=max){break;}

                JSONObject item = result.getJSONObject(i);
                long createTime = item.getLong("created");
//                System.out.println("videoCreate:"+createTime);
//                有新的视频发布了
                if (temp<createTime){
                    //获取最新的视频更新日期
                    if (lastUpdate<createTime){
                        lastUpdate = createTime;
                        System.out.println("the newest publishTime:"+lastUpdate);
                    }//之后都是介于最新视频与上次更新之间的新视频

                    //添加新的视频检测
                    System.out.println("create new VideoDetect.....");
                    JobInfo videoJob = new JobInfo();
                    videoJob.setUserId(uploaderStatePo.getCreateId());
                    videoJob.setJobType("videoJob");
                    videoJob.setCornExpression(uploaderStatePo.getCornExpression());
                    videoJob.setDetectObject(item.getString("title"));
                    videoJob.setDetectObjectId(item.getString("bvid"));
                    videoJob.setDetectObjectProfile(item.getString("pic"));

                    videoJob.setDuringDate(
                            LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))+
                            ","+
                            LocalDateTime.now().plusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                    videoJob.setAuths(item.getString("author"));
                    videoJob.setAuthsId(item.getInteger("mid"));
                    videoJob.setAuthsProfile(" ");

                    ICronService cronService = (ICronService) SpringUtil.getBean("ICronService");
                    try {
                        videoJob = cronService.addCronJob(videoJob);
                        DetectVideoStatePo detectVideoStatePo = new DetectVideoStatePo(videoJob,uploaderStatePo.getDetectId());
                        modelService.addVideoJob(detectVideoStatePo);
                        System.out.println("create new VideoDetect done");
                        have = have+1;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }
            }
            if (lastUpdate!=uploaderStatePo.getLastUpdate().toEpochSecond(ZoneOffset.of("+8"))){
                System.out.println("updateState....");
                //更新up主检测状态
                DetectUploaderStatePo newState = new DetectUploaderStatePo();
                newState.setLastUpdate(LocalDateTime.ofEpochSecond(lastUpdate,0,ZoneOffset.ofHours(8)));
                newState.setHaveDetect(have);
                System.out.println("newState:" + newState);
                modelService.setUploaderDetect(newState,group);
            }

        }else {
            System.out.println("侦测次数已满");
        }


    }
}

