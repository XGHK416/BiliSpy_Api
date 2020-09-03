package com.project.xghk416.project.viewer.search.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.xghk416.common.util.RequestTemplateUtil;
import com.project.xghk416.pojo.bili.BiliUploaderPo;
import com.project.xghk416.pojo.bili.BiliVideoPo;
import com.project.xghk416.pojo.bili.mapper.BiliUploaderDao;
import com.project.xghk416.pojo.bili.mapper.BiliVideoDao;
import com.project.xghk416.project.viewer.search.service.SearchModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ISearchModelService implements SearchModelService {

    @Autowired
    BiliVideoDao biliVideoDao;
    @Autowired
    BiliUploaderDao biliUploaderDao;

    @Override
    public Map<String, Object> searchUploaderFromSystem(Page<BiliUploaderPo> page, String key,String order,String orderSort) {
        IPage<BiliUploaderPo> iPage = biliUploaderDao.QueryUploaderListByLike(page,key,order,orderSort);
        List<BiliUploaderPo> list = iPage.getRecords();

        Map<String,Object> result = new HashMap<>();
        result.put("list",list);
        result.put("count",list.size());

        return result;

    }

    @Override
    public Map<String, Object> searchVideoFromSystem(Page<BiliVideoPo> page, String key,String order) {

        IPage<BiliVideoPo> iPage = biliVideoDao.QueryVideoListByLike(page,key,order);
        List<BiliVideoPo> list = iPage.getRecords();

        Map<String,Object> result = new HashMap<>();
        result.put("list",list);
        result.put("count",list.size());

        return result;
    }

    @Override
    public Map<String, Object> searchUploaderFromBili(String url) {
        JSONArray object = RequestTemplateUtil.requestGetForJson(url).getJSONObject("data").getJSONArray("result");
        Map<String,Object> result = new HashMap<>();
        result.put("result",object);
        return result;
    }

    @Override
    public Map<String, Object> searchVideoFromBili(String url) {
        JSONArray object = RequestTemplateUtil.requestGetForJson(url).getJSONObject("data").getJSONArray("result");
        Map<String,Object> result = new HashMap<>();
        result.put("result",object);
        return result;
    }
}
