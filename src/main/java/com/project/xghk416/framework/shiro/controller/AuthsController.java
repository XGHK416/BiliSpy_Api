package com.project.xghk416.framework.shiro.controller;


import com.project.xghk416.common.result.Result;
import com.project.xghk416.common.util.ResultUtil;
import com.project.xghk416.enums.ResultEnum;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/")
public class AuthsController {

    @GetMapping(value = "/auths")
    public Result auths(int code){
        System.out.println(code);
        if (code==10001){
            return ResultUtil.error(ResultEnum.UNUSABLE.getCode(),ResultEnum.UNUSABLE.getMsg());
        }
        else{
            return ResultUtil.success();
        }
    }
}
