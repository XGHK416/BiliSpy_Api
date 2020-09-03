package com.project.xghk416.project.mo.api_.service;

import com.project.xghk416.pojo.api.ApiParamsPo;
import com.project.xghk416.pojo.api.ApiReportPo;

import java.util.List;
import java.util.Map;

public interface ApiManagerService {
    int addReport(ApiReportPo apiReportPo);

    Map<String, Object> getApiList(String type);

    List<ApiParamsPo> getApiParams(String apiId);

    boolean fixApi(String moId, String apiId);

    ApiReportPo getReport(String reportId);
}
