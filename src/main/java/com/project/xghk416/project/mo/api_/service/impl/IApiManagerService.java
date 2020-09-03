package com.project.xghk416.project.mo.api_.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.project.xghk416.pojo.api.mapper.ApiCollectionDao;
import com.project.xghk416.pojo.api.mapper.ApiParamsDao;
import com.project.xghk416.pojo.api.mapper.ApiReportDao;
import com.project.xghk416.pojo.api.ApiCollectionPo;
import com.project.xghk416.pojo.api.ApiParamsPo;
import com.project.xghk416.pojo.api.ApiReportPo;
import com.project.xghk416.project.mo.api_.service.ApiManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class IApiManagerService implements ApiManagerService {
    @Autowired
    ApiReportDao apiReportDao;
    @Autowired
    ApiCollectionDao apiCollectionDao;
    @Autowired
    ApiParamsDao apiParamsDao;

    @Override
    public int addReport(ApiReportPo apiReportPo) {
        String apiId = apiReportPo.getApiId();
        apiReportDao.insert(apiReportPo);
        ApiCollectionPo apiCollectionPo = new ApiCollectionPo();
        apiCollectionPo.setApiUseable(0);

        UpdateWrapper<ApiCollectionPo> wrapper = new UpdateWrapper<>();
        wrapper.eq("api_id",apiId);
        if (apiCollectionDao.update(apiCollectionPo,wrapper)>0){
            return apiReportPo.getId();
        }
        else return -1;
    }

    @Override
    public Map<String, Object> getApiList(String section) {
        Map<String ,Object> result = new HashMap<>();
        QueryWrapper<ApiCollectionPo> collectionPoQueryWrapper = new QueryWrapper<>();
        collectionPoQueryWrapper.select("*");
        collectionPoQueryWrapper.eq("api_section",section);
        List<ApiCollectionPo> list = apiCollectionDao.selectList(collectionPoQueryWrapper);

        int unusableCount = 0;
        for (ApiCollectionPo item:
             list) {
            if (item.getApiUseable()!=1){
                unusableCount++;
                System.out.println(unusableCount);
            }
        }
        result.put("list",list);
        result.put("unusableCount",unusableCount);
        return result;
    }

    @Override
    public List<ApiParamsPo> getApiParams(String apiId) {
        QueryWrapper<ApiParamsPo> apiParamsPoQueryWrapper = new QueryWrapper<>();
        apiParamsPoQueryWrapper.eq("api_id",apiId);
        return apiParamsDao.selectList(apiParamsPoQueryWrapper);
    }

    @Override
    public boolean fixApi(String moId, String apiId) {
        UpdateWrapper<ApiCollectionPo> collectionWrapper = new UpdateWrapper<>();
        UpdateWrapper<ApiReportPo> reportWrapper = new UpdateWrapper<>();

        ApiCollectionPo collectionPo= new ApiCollectionPo();
        collectionPo.setApiUseable(1);

        ApiReportPo reportPo= new ApiReportPo();
        reportPo.setFixedUser(moId);
        reportPo.setIsFix(1);

        collectionWrapper.eq("api_id",apiId);
        reportWrapper.eq("api_id",apiId);

        int cflag = apiCollectionDao.update(collectionPo,collectionWrapper);
        int rflag = apiReportDao.update(reportPo,reportWrapper);
        return cflag > 0 && rflag > 0;
    }

    @Override
    public ApiReportPo getReport(String apiId) {
        QueryWrapper<ApiReportPo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("api_id",apiId);
        queryWrapper.eq("is_fix",0);

        return apiReportDao.selectOne(queryWrapper);
    }
}
