package com.project.xghk416.project.mo.detect.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.xghk416.common.util.DateTimeUtil;
import com.project.xghk416.pojo.bili.mapper.BiliDetectDao;
import com.project.xghk416.pojo.bili.mapper.BiliUploaderDao;
import com.project.xghk416.pojo.bili.BiliDetectPo;
import com.project.xghk416.project.mo.detect.service.DetectManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class IDetectManagerService implements DetectManagerService {
    @Autowired
    BiliUploaderDao biliUploaderDao;
    @Autowired
    BiliDetectDao detectDao;

    @Override
    public Map<String, Object> scanUnusable(int page,int pageSize) {
        Map<String,Object> result  = new HashMap<>();
        Page<List<Map<String,Object>>> ipage = new Page<>(page,pageSize);

        result.put("result",biliUploaderDao.queryDetectUnusable(ipage, DateTimeUtil.returnDateStream(0)).getRecords());
        result.put("count",biliUploaderDao.queryDetectUnusableCount(DateTimeUtil.returnDateStream(0)));

        return result;
    }

    @Override
    public Map<String, Object> selectUploader(String key, int page, int pageSize) {
        Map<String,Object> result  = new HashMap<>();
        Page<List<Map<String,Object>>> ipage = new Page<>(page,pageSize);

        result.put("result",biliUploaderDao.queryDetectUploaderInfo(ipage,key).getRecords());
        result.put("count",biliUploaderDao.queryDetectUploaderCount(key));

        return result;
    }

    @Override
    public Map<String, Object> getCount(int type) {
        Map<String,Object> result = new HashMap<>();
        int daily = detectDao.queryDetectDailyCount(type);
        int all = detectDao.queryDetectAllCount(type);
        result.put("daily",daily);
        result.put("all",all);
        return result;
    }

    @Override
    public Map<String, Object> getDailyNum(int type) {
        String title;
        if (type == 1){
            title = "每日添加视频侦测变化";
        }else {
            title = "每日添加up主侦测变化";
        }
        List<Map<String,Object>> baseResult = detectDao.queryEveryCount(type);
        List<String> xAxis = new ArrayList<>();
        List<Integer> data = new ArrayList<>();
        for (Map<String, Object> item :
                baseResult) {
            xAxis.add(item.get("date").toString());
            data.add(Integer.parseInt(item.get("count").toString()));
        }

        Map<String,Object> result = new HashMap<>();
        result.put("title",title);
        result.put("x_axis",xAxis);
        result.put("data",data);


        return result;
    }

    @Override
    public boolean deleteDetect(String detectId) {
        UpdateWrapper<BiliDetectPo> biliDetectPoUpdateWrapper = new UpdateWrapper<>();
        biliDetectPoUpdateWrapper.eq("detect_id",detectId).eq("detect_type",0).set("useable",0);
        BiliDetectPo detectPo = new BiliDetectPo();
        detectPo.setUseable(0);



        return detectDao.update(detectPo,biliDetectPoUpdateWrapper)>0;
    }

    @Override
    public boolean deleteAllDetect() {
        biliUploaderDao.deleteAllDetectUnusable();
        return true;
    }
}
