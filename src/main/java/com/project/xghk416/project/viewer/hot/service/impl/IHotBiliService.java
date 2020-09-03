package com.project.xghk416.project.viewer.hot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.project.xghk416.enums.api.UploaderApiEnum;
import com.project.xghk416.pojo.bili.BiliDetectPo;
import com.project.xghk416.pojo.bili.mapper.BiliDetectDao;
import com.project.xghk416.project.viewer.hot.service.HotBiliService;
import com.project.xghk416.common.util.RequestTemplateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class IHotBiliService implements HotBiliService {
    @Autowired
    BiliDetectDao biliDetectDao;


    @Override
    public Map<String, Object> getRank(String url) {
        return RequestTemplateUtil.requestGet(url);
    }

    @Override
    public Map<String, Object> getUploaderInfo(int mid) {
        String upstat = UploaderApiEnum.UPLOADER_UPSTAT_RANK.getApiAddress() +"mid="+mid;
        String stat = UploaderApiEnum.UPLOADER_STAT.getApiAddress()+"vmid="+mid;
        String info = UploaderApiEnum.UPLOADER_INFO.getApiAddress()+"mid="+mid;
        String notice = UploaderApiEnum.UPLOADER_NOTICE.getApiAddress()+"mid="+mid;
        Map<String,Object> upstatResult = RequestTemplateUtil.requestGet(upstat);
        Map<String,Object> statResult = RequestTemplateUtil.requestGet(stat);
        Map<String,Object> infoResult = RequestTemplateUtil.requestGet(info);
        Map<String,Object> noticeResult = RequestTemplateUtil.requestGet(notice);

        Map<String,Object> result = new HashMap<>();
        result.put("info",infoResult.get("data"));
        result.put("notice",noticeResult.get("data"));
        result.put("upstat",upstatResult.get("data"));
        result.put("stat",statResult.get("data"));

//        是否检测
        QueryWrapper<BiliDetectPo> biliDetectPoQueryWrapper = new QueryWrapper<>();
        biliDetectPoQueryWrapper.eq("detect_id",mid).eq("detect_type",0).select("detect_id");
        BiliDetectPo selectOne = biliDetectDao.selectOne(biliDetectPoQueryWrapper);
        if (selectOne!=null){
            result.put("isDetect",true);
        }else {
            result.put("isDetect",false);
        }

        return result;
    }

    @Override
    public Map<String, Object> getVideoInfo(String url,int aid) {
        Map<String,Object> result = RequestTemplateUtil.requestGet(url);
        //        是否检测
        QueryWrapper<BiliDetectPo> biliDetectPoQueryWrapper = new QueryWrapper<>();
        biliDetectPoQueryWrapper.eq("detect_id",aid).eq("detect_type",1).select("detect_id");
        BiliDetectPo selectOne = biliDetectDao.selectOne(biliDetectPoQueryWrapper);


        if (selectOne!=null){
            result.put("isDetect",true);
        }else {
            result.put("isDetect",false);
        }


        return result;
    }

    @Override
    public int addDetectObject(BiliDetectPo uploader) {
        return biliDetectDao.insert(uploader);
    }
}
