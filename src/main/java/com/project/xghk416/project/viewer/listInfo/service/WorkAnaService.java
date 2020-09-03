package com.project.xghk416.project.viewer.listInfo.service;

import com.project.xghk416.pojo.bili.BiliVideoPo;

import java.util.List;
import java.util.Map;

public interface WorkAnaService {
    List<Map<String,Object>> getTopThree(int mid);

    int getWorkStatusAvg(int mid,String type);

    List<String> getTags(int mid);

    List<Map<String,Integer>> getMonthlyCount(int mid);

    Map<String,Object> getRecentWorkStatus(int mid);

    Map<String,Object> predictNextMonth(int mid);

    Map<String,Object> predictNextVideo(int mid,int tid);



}
