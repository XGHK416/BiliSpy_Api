package com.project.xghk416.project.common.space.controller;


import com.project.xghk416.pojo.operate.UserLogOperaPo;
import com.project.xghk416.common.result.Result;
import com.project.xghk416.project.common.operate.service.impl.IOperationLogService;
import com.project.xghk416.common.util.ResultUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/operate")
@Api(tags = "操作记录")
public class OperateLogController {
    @Autowired
    IOperationLogService operationLogService;
    @GetMapping("/user/getDaily")
    Result getUserLogOperate(String userId){
        List<UserLogOperaPo> userLogOperaPo = operationLogService.getUserTodayOperate(userId);
        return ResultUtil.success(userLogOperaPo);
    }
}
