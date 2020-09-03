package com.project.xghk416.project.common.baseAnalysis.service;


import java.util.Map;

public interface BaseAnalysisService {

    Map<String, Object> getGenderInfo();

    Map<String, Object> getLevelInfo();

    Map<String, Object> getTop(String startDate,String endDate);

    Map<String, Object> getLevel();

    Map<String,Object> getBaseInfo();

    Map<String, Object> getSection();

    Map<String, Object> getTagWorldCloud();

    Map<String, Object> getSpider(int limit);
}
