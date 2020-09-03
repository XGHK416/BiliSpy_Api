package com.project.xghk416.project.mo.detect.service;

import java.util.List;
import java.util.Map;

public interface DetectManagerService {
    Map<String,Object> scanUnusable(int page,int pageSize);

   Map<String,Object> selectUploader(String key,int page,int pageSize);

    Map<String,Object> getCount(int type);

    Map<String, Object> getDailyNum(int type);

    boolean deleteDetect(String detectId);

    public boolean deleteAllDetect();


}
