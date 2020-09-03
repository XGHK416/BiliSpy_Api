package com.project.xghk416.project.viewer.job.service;

import com.project.xghk416.pojo.detect.DetectUploaderStatePo;
import com.project.xghk416.pojo.detect.DetectVideoResultPo;
import com.project.xghk416.pojo.detect.DetectVideoStatePo;

import java.util.List;

public interface DetectModelService {
    boolean addVideoJob(DetectVideoStatePo jobInfo);

    boolean addUploaderJob(DetectUploaderStatePo jobInfo);

    DetectVideoStatePo getVideoJob(String detectId);
    DetectUploaderStatePo getUploaderJob(String detectId);

    List<DetectVideoResultPo> getResult(String detectId);

    List<DetectUploaderStatePo> getUploaderDetectList(Integer page,Integer pageSize,String createId,int type,String detectName);

    List<DetectVideoStatePo> getVideoDetectList(Integer page,Integer pageSize,String createId,int type,String parentId);

    int getVideoDetectCount(String userId,int type,String parentId);

    int getUploaderDetectCount(String userId,int type,String detectName);

    boolean setVideoDetect(DetectVideoStatePo detectVideoStatePo);

    boolean setUploaderDetect(DetectUploaderStatePo detectUploaderStatePo,String userId);

    boolean addVideoResult(DetectVideoResultPo resultPo);
}
