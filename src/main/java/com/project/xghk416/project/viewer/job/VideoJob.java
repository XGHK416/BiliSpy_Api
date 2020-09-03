package com.project.xghk416.project.viewer.job;


import com.alibaba.fastjson.JSONObject;
import com.project.xghk416.enums.api.VideoApiEnum;
import com.project.xghk416.pojo.detect.DetectVideoResultPo;
import com.project.xghk416.project.viewer.job.service.impl.IDetectModelService;
import com.project.xghk416.project.viewer.job.service.BaseJob;
import com.project.xghk416.common.util.RequestTemplateUtil;
import com.project.xghk416.common.util.SpringUtil;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by haoxy on 2018/9/28.
 * E-mail:hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 */
@Component("videoJob")
    public class VideoJob implements BaseJob {

    private static Logger log = LoggerFactory.getLogger(VideoJob.class);

    public VideoJob() {

    }
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        String id = context.getJobDetail().getJobDataMap().get("id").toString();
        String group = context.getJobDetail().getJobDataMap().get("group").toString();
        ///////
        IDetectModelService modelService = (IDetectModelService) SpringUtil.getBean("IDetectModelService");
        String url = VideoApiEnum.VIDEO_INFO_BVID.getApiAddress()+"bvid="+id;
        JSONObject response = RequestTemplateUtil.requestGetForJson(url);
        JSONObject data = response.getJSONObject("data");
//        System.out.println(response.getJSONObject("data"));
//        JSONObject parseObject = JSONArray.parseObject(response.get("data").toString());
//        System.out.println(parseObject.toJSONString());
//
        DetectVideoResultPo resultPo = new DetectVideoResultPo(group,data);
        modelService.addVideoResult(resultPo);

    }
}

