package com.project.xghk416.project.viewer.hot.service;

import com.project.xghk416.pojo.bili.BiliDetectPo;

import java.util.Map;

public interface HotBiliService {
    Map<String, Object> getRank(String url);

    Map<String,Object> getUploaderInfo(int mid);

    Map<String,Object> getVideoInfo(String url,int aid);

    int addDetectObject(BiliDetectPo uploader);
}
