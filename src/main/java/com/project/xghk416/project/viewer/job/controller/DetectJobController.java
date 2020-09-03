package com.project.xghk416.project.viewer.job.controller;


import com.alibaba.fastjson.JSONObject;
import com.project.xghk416.enums.ResultEnum;
import com.project.xghk416.enums.api.UploaderApiEnum;
import com.project.xghk416.enums.api.VideoApiEnum;
import com.project.xghk416.pojo.JobInfo;
import com.project.xghk416.pojo.detect.DetectUploaderStatePo;
import com.project.xghk416.pojo.detect.DetectVideoResultPo;
import com.project.xghk416.pojo.detect.DetectVideoStatePo;
import com.project.xghk416.common.result.Result;
import com.project.xghk416.project.viewer.job.service.impl.ICronService;
import com.project.xghk416.project.viewer.job.service.impl.IDetectModelService;
import com.project.xghk416.common.util.RequestTemplateUtil;
import com.project.xghk416.common.util.ResultUtil;
import com.project.xghk416.common.util.DateUnit;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Api(tags = "侦测任务")
@RequestMapping("/detectJob")
public class DetectJobController {
    @Autowired
    ICronService cronService;
    @Autowired
    IDetectModelService modelService;

    private static Logger log = LoggerFactory.getLogger(DetectJobController.class);

    @GetMapping(value = "/getUploader")
    @ApiOperation(value = "根据mid获取up主对象")
    public Result getUploader(String params){
        String url = UploaderApiEnum.UPLOADER_INFO.getApiAddress();
        JSONObject result = RequestTemplateUtil.requestGetForJson(url+params);
        return ResultUtil.success(result.get("data"));
    }

    @PostMapping(value = "/getVideoDetectInfo")
    @ApiOperation(value = "获取视频基本信息")
    public Result getVideoDetectInfo(String params){
        String url = VideoApiEnum.VIDEO_INFO_BVID.getApiAddress();
        Map<String,Object> result = RequestTemplateUtil.requestGet(url+params);
        return ResultUtil.success(result.get("data"));
    }

    @PostMapping(value = "/getUploaderList")
    @ApiOperation(value = "搜索用户")
    public Result getUploaderDetectInfo(String params){
        String url = UploaderApiEnum.UPLOADER_SEARCH.getApiAddress();
        JSONObject result = RequestTemplateUtil.requestGetForJson(url+params);
        return ResultUtil.success(result.getJSONObject("data"));
    }

    /**
     * 添加任务
     *
     * @param jobInfo
     * @throws Exception
     */
    @PostMapping(value = "/addjob")
    @ApiOperation(value = "添加工作")
    public Result addjob(JobInfo jobInfo) throws Exception {
//        System.out.println(jobInfo);
        if ("".equals(jobInfo.getDetectObject()) || "".equals(jobInfo.getCornExpression()) || "".equals(jobInfo.getJobType())) {
            return ResultUtil.error(ResultEnum.UNKNOWN_ERROR.getCode(),ResultEnum.UNKNOWN_ERROR.getMsg());
        }
        jobInfo = cronService.addCronJob(jobInfo);
        if ("videoJob".equals(jobInfo.getJobType())){
            DetectVideoStatePo detectVideoStatePo= new DetectVideoStatePo(jobInfo);
            modelService.addVideoJob(detectVideoStatePo);
        }else{
            DetectUploaderStatePo detectUploaderStatePo = new DetectUploaderStatePo(jobInfo);
            modelService.addUploaderJob(detectUploaderStatePo);
        }

        return ResultUtil.success();
    }


    /**
     * 暂停任务
     *
     * @param jobClassName
     * @param jobGroupName
     * @throws Exception
     */
    @PostMapping(value = "/pausejob")
    public void pausejob(@RequestParam(value = "jobClassName") String jobClassName, @RequestParam(value = "jobGroupName") String jobGroupName) throws Exception {
        cronService.jobPause(jobClassName, jobGroupName);
    }

    /**
     * 恢复任务
     *
     * @param jobClassName
     * @param jobGroupName
     * @throws Exception
     */
    @PostMapping(value = "/resumejob")
    public void resumejob(@RequestParam(value = "jobClassName") String jobClassName, @RequestParam(value = "jobGroupName") String jobGroupName) throws Exception {
        cronService.jobResume(jobClassName, jobGroupName);
    }

    /**
     * 更新任务
     *
     * @param jobClassName
     * @param jobGroupName
     * @param cronExpression
     * @throws Exception
     */
    @PostMapping(value = "/reschedulejob")
    public void rescheduleJob(@RequestParam(value = "jobClassName") String jobClassName,
                              @RequestParam(value = "jobGroupName") String jobGroupName,
                              @RequestParam(value = "cronExpression") String cronExpression) throws Exception {
        cronService.jobReschedule(jobClassName, jobGroupName, cronExpression);
    }


    /**
     * 删除任务
     * 删除操作前应该暂停该任务的触发器，并且停止该任务的执行
     *
     * @param jobClassName
     * @param jobGroupName
     * @throws Exception
     */
    @PostMapping(value = "/deletejob")
    @ApiOperation("删除job")
    public Result deletejob(@RequestParam(value = "jobClassName") String jobClassName, @RequestParam(value = "jobGroupName") String jobGroupName) throws Exception {
        cronService.jobDelete(jobClassName, jobGroupName);
        return ResultUtil.success();
    }


    /**
     * 查询任务
     *
     * @return
     */
    @GetMapping(value = "/queryJob")
    public Result queryJob(Integer page,Integer pageSize,String userId) {
        // TODO: 2020/4/6   查询

        return ResultUtil.success();
    }

    /**
     *
     * @param page
     * @param pageSize
     * @param userId
     * @param type 0全部 1未开始 2以开始 3以结束
     * @return
     */
    @GetMapping(value = "getVideoJogList")
    @ApiOperation("获取用户侦测视频任务一览")
    public Result getVideoJobList(Integer page,Integer pageSize,String userId,int type,String parentId){
        Map<String,Object> result = new HashMap<>();
        List<DetectVideoStatePo> detectList = modelService.getVideoDetectList(page, pageSize, userId,type,parentId);
        int count = modelService.getVideoDetectCount(userId, type,parentId);
        result.put("list",detectList);
        result.put("count",count);
        return ResultUtil.success(result);
    }
    /**
     *
     * @param page
     * @param pageSize
     * @param userId
     * @param type 0全部 1未开始 2以开始 3以结束
     * @return
     */
    @GetMapping(value = "getUploaderJobList")
    @ApiOperation("获取用户侦测视频任务一览")
    public Result getUploaderJobList(Integer page,Integer pageSize,String userId,int type,String searchName){
        Map<String,Object> result = new HashMap<>();
        List<DetectUploaderStatePo> detectList = detectList = modelService.getUploaderDetectList(page, pageSize, userId,type,searchName);
        int count = modelService.getUploaderDetectCount(userId, type,searchName);

        result.put("list",detectList);
        result.put("count",count);
        return ResultUtil.success(result);
    }


    /**
     * 获取单个视频任务信息
     * @param detectId
     * @return
     */
    @GetMapping(value = "getVideoJogInfo")
    @ApiOperation("获取单个侦测视频任务一览")
    public Result getVideoJobInfo(String detectId){
        return ResultUtil.success(modelService.getVideoJob(detectId));
    }

    @GetMapping(value = "getResultList")
    @ApiOperation("获取结果集")
    public Result getResult(String detectId){
        List<DetectVideoResultPo> resultPos = modelService.getResult(detectId);
        return ResultUtil.success(resultPos);
    }


}
