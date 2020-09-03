package com.project.xghk416.project.viewer.listInfo.service;

import com.project.xghk416.pojo.bili.BiliVideoPo;
import com.project.xghk416.pojo.dto.VideoListDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface VideoService {
    LocalDateTime getLastPublish(int mid);

    int getPublishCount(int limit,int mid);

    Map<String,Object> analysisSection(int mid);

    Map<String,Object> analysisPublishTime(int mid);

    List<BiliVideoPo> getList(int i, int page, int pageSize);

    List<BiliVideoPo> getLatestInfo(int mid );

    BiliVideoPo getInfo(int aid);

    List<Map<String,Object>> getEvaluateByLimitDate(String startDate, String endDate, int aid);

    VideoListDto getRecommendList(String tag, String section);
}
