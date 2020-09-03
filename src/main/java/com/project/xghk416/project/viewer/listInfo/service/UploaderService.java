package com.project.xghk416.project.viewer.listInfo.service;

import com.project.xghk416.pojo.bili.BiliUploaderPo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

public interface UploaderService {

    BiliUploaderPo getBaseInfo(int mid);

    String getBelongSection(int mid);

    int getDetectTime(int mid,String userId);

    Map<String,Object> getChange(int mid, int type);

    Map<String,Object> getCompetingData(int[] mids,String type,int limit);

    List<Map<String, Object>> getNickName(int[] mids);

    Map<String, Object> getListByKey(int page, int pageSize, String key);

    Map<String, Object> getRecommend(String sectionName, int page, int pageSize,int mid);
}
