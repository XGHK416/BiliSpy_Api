package com.project.xghk416.project.mo.api_.controller;

import com.project.xghk416.enums.ResultEnum;
import com.project.xghk416.enums.api.UploaderApiEnum;
import com.project.xghk416.enums.api.VideoApiEnum;
import com.project.xghk416.enums.mo.ManagerOperationEnum;
import com.project.xghk416.enums.mo.TableEnum;
import com.project.xghk416.pojo.MoOperate;
import com.project.xghk416.pojo.api.ApiParamsPo;
import com.project.xghk416.pojo.api.ApiReportPo;
import com.project.xghk416.common.result.Result;
import com.project.xghk416.project.mo.api_.service.impl.IApiManagerService;
import com.project.xghk416.project.common.operate.service.impl.IMoLogService;
import com.project.xghk416.common.util.RequestTemplateUtil;
import com.project.xghk416.common.util.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Api(tags = "api管理")
@RequestMapping(value = "/apiMo")
public class ApiManagerController {
    @Autowired
    IMoLogService moLogService;
    @Autowired
    IApiManagerService apiManagerService;


    @PostMapping("getApiTest")
    @ApiOperation(value = "api测试")
    public Result getApiTest(String apiId, String apiBaseUrl, String params,String moId) {
        String baseUrl;
        char type = apiId.charAt(0);
        MoOperate moOperate = new MoOperate(ManagerOperationEnum.TEST_API, TableEnum.API_COLLECTION,moId,null,null,null);
        moLogService.addOpera(moOperate);
        if ('v' == type) {
           baseUrl = VideoApiEnum.getAddressById(apiId);
        }
        else {
            baseUrl = UploaderApiEnum.getAddressById(apiId);
        }
        if (baseUrl != null && baseUrl.equals(apiBaseUrl)) {
            //做测试
            Map<String,Object> result = RequestTemplateUtil.requestGet(baseUrl+params);
            return ResultUtil.success(result);
        }else {
            return ResultUtil.error(ResultEnum.UNKNOWN_ERROR.getCode(),"与数据库不符");
        }

    }

    @PostMapping("reportApi")
    @ApiOperation(value = "汇报api错误")
    public Result reportApi(ApiReportPo apiReportPo) {
        apiReportPo.setCreateTime(LocalDateTime.now());
        if (apiManagerService.addReport(apiReportPo)>0){
            MoOperate operate = new MoOperate(ManagerOperationEnum.REPORT_API,TableEnum.API_REPORT,apiReportPo.getCreateUser(),"ApiReportPo",null,"ApiReportPo");
            moLogService.addOpera(operate);
            return ResultUtil.success();
        }else {
            return ResultUtil.error(ResultEnum.UNKNOWN_ERROR.getCode(),ResultEnum.UNKNOWN_ERROR.getMsg());
        }
    }

    @GetMapping("getApiList")
    @ApiOperation(value = "获取api列表")
    public Result getApiList() {
        Map<String,Object> videoApis = apiManagerService.getApiList("video");
        Map<String,Object> uploaderApis = apiManagerService.getApiList("uploader");

        Map<String,Object> result = new HashMap<>();
        result.put("video",videoApis);
        result.put("uploader",uploaderApis);

        return ResultUtil.success(result);
    }

    @GetMapping("getApiParam")
    @ApiOperation(value = "获取api参数列表")
    public Result getApiParam(String apiId) {
        List<ApiParamsPo> params = apiManagerService.getApiParams(apiId);
        return ResultUtil.success(params);
    }

    @GetMapping("getApiReportInfo")
    @ApiOperation(value = "获取api汇报信息")
    public Result getApiReportInfo(String apiId) {
        ApiReportPo report= apiManagerService.getReport(apiId);
        return ResultUtil.success(report);
    }

    @PostMapping("getApiRecover")
    @ApiOperation(value = "解封api")
    public Result getApiRecover(String apiId,String moId) {
        if (apiManagerService.fixApi(moId,apiId)){
            MoOperate operate = new MoOperate(ManagerOperationEnum.FIX_API,TableEnum.API_REPORT,moId,null,"ApiReportPo","ApiReportPo");
            moLogService.addOpera(operate);
            return ResultUtil.success();
        }else {
           return ResultUtil.error(ResultEnum.UNKNOWN_ERROR.getCode(),ResultEnum.UNKNOWN_ERROR.getMsg());
        }
    }


}
