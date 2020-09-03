package com.project.xghk416.project.mo.detect.controller;


import com.project.xghk416.enums.ResultEnum;
import com.project.xghk416.enums.mo.ManagerOperationEnum;
import com.project.xghk416.enums.mo.TableEnum;
import com.project.xghk416.pojo.MoOperate;
import com.project.xghk416.common.result.Result;
import com.project.xghk416.project.common.operate.service.impl.IMoLogService;
import com.project.xghk416.project.mo.detect.service.DetectManagerService;
import com.project.xghk416.common.util.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/detectManager")
@Api(tags = "侦测管理")
public class DetectManageController {

    @Autowired
    DetectManagerService detectManagerService;
    @Autowired
    IMoLogService moLogService;

    @GetMapping(value = "/scanUnusable")
    @ApiOperation(value = "扫描无用账号")
    public Result scanUnusable(int page,int pageSize){
        return ResultUtil.success(detectManagerService.scanUnusable(page, pageSize));
    }
    @GetMapping(value = "/selectUploader")
    @ApiOperation(value = "模糊查找")
    public Result selectUploader(String key,int page,int pageSize){
        try {
            return ResultUtil.success(detectManagerService.selectUploader(key,page, pageSize));
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.error(ResultEnum.UNKNOWN_ERROR.getCode(), e.getMessage());
        }

    }


    @PostMapping(value = "/deleteDetectObject")
    @ApiOperation(value = "禁止侦测对象")
    public Result deleteDetectObject(String moId,String unusableId){
        detectManagerService.deleteDetect(unusableId);

        MoOperate operate = new MoOperate(ManagerOperationEnum.DELETE_DETECT_OBJECT, TableEnum.BILI_DETECT,moId,null,"BiliDetectPo","BiliDetectPo");
        moLogService.addOpera(operate);
        return ResultUtil.success();
    }

    @PostMapping(value = "/deleteAllUnusableObject")
    @ApiOperation(value = "禁止全部侦测对象")
    public Result deleteAllUnusableObject(String moId){
        detectManagerService.deleteAllDetect();

        MoOperate operate = new MoOperate(ManagerOperationEnum.DELETE_ALL_UNSABLE_DETECT_OBJECT, TableEnum.BILI_DETECT,moId,null,"BiliDetectPo","BiliDetectPo");
        moLogService.addOpera(operate);
        return ResultUtil.success();
    }


    @GetMapping(value = "/getDetectInfo")
    @ApiOperation(value = "侦测信息")
    public Result deleteDetectObject(String key){
        Map<String,Object> result = new HashMap<>();
        int flag = 0;
        if ("video".equals(key)){
            flag = 1;
        }
        result.put("num",detectManagerService.getCount(flag));
        result.put("bar",detectManagerService.getDailyNum(flag));



        return ResultUtil.success(result);
    }



}
