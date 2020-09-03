package com.project.xghk416.project.viewer.search.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.xghk416.pojo.bili.BiliUploaderPo;
import com.project.xghk416.pojo.bili.BiliVideoPo;

import java.util.Map;

public interface SearchModelService {
    public Map<String,Object> searchUploaderFromSystem(Page<BiliUploaderPo> page,String key,String order,String orderSort);

    public Map<String,Object> searchVideoFromSystem(Page<BiliVideoPo> page,String key,String order);

    public Map<String,Object> searchUploaderFromBili(String url);

    public Map<String,Object> searchVideoFromBili(String url);
}
