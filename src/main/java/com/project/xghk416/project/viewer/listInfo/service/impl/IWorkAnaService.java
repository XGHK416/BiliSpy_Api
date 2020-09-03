package com.project.xghk416.project.viewer.listInfo.service.impl;

import com.project.xghk416.common.util.RequestTemplateUtil;
import com.project.xghk416.pojo.bili.BiliVideoPo;
import com.project.xghk416.pojo.bili.mapper.BiliVideoDao;
import com.project.xghk416.pojo.bili.mapper.BiliVideoRankDao;
import com.project.xghk416.project.viewer.listInfo.service.WorkAnaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class IWorkAnaService implements WorkAnaService {
    @Autowired
    BiliVideoRankDao biliVideoRankDao;
    @Autowired
    BiliVideoDao biliVideoDao;


    @Override
    public List<Map<String, Object>> getTopThree(int mid) {
        return biliVideoRankDao.getTopThreeRank(mid);
    }

    @Override
    public int getWorkStatusAvg(int mid, String type) {
        return biliVideoDao.queryAvgVideoStatus(mid, type);
    }

    @Override
    public List<String> getTags(int mid) {
        return biliVideoDao.queryTags(mid);
    }

    @Override
    public List<Map<String,Integer>> getMonthlyCount(int mid) {
        return biliVideoDao.queryMonthlyCount(mid);
    }

    @Override
    public Map<String,Object> getRecentWorkStatus(int mid) {
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("video",biliVideoDao.queryRecentWork(mid));
        resultMap.put("section",biliVideoDao.queryRecentWorkSection(mid));
        return resultMap;
    }

    /**
     * 预测这个月发布的视频数
     * @param mid
     * @return
     */
    @Override
    public Map<String, Object> predictNextMonth(int mid) {
        Map<String,Object> resultMap = new HashMap<>();
        String url = "http://39.106.228.42:8088/predict_count?mid="+mid;
        return RequestTemplateUtil.requestGetForJson(url);
    }

    @Override
    public Map<String, Object> predictNextVideo(int mid,int tid) {
        String url = "http://39.106.228.42:8088/predict_status?mid="+mid+"&tid="+tid;
        return RequestTemplateUtil.requestGetForJson(url);
    }
}
