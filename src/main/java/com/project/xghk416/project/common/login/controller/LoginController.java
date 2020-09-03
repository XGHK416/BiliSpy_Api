package com.project.xghk416.project.common.login.controller;


import com.project.xghk416.enums.ResultEnum;
import com.project.xghk416.enums.UserOperationEnum;
import com.project.xghk416.framework.shiro.pojo.UserDto;
import com.project.xghk416.pojo.user.UserAuthsPo;
import com.project.xghk416.common.result.Result;
import com.project.xghk416.project.common.login.service.impl.ILoginService;
import com.project.xghk416.project.common.operate.service.impl.IOperationLogService;
import com.project.xghk416.common.util.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;


@Api(value = "登录接口",tags = "登陆接口")
@RestController
public class LoginController {

    @Autowired
    ILoginService ILoginService;
    @Autowired
    IOperationLogService operationLogService;

    /**
     * 登录验证
     * @return
     */
    // TODO: 2020/4/2 登录时判断用户是否解冻
    @ApiOperation(value = "用户登录验证")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "identityId",value = "用户账户名",required = true,dataType = "String",example = "user1"),
            @ApiImplicitParam(name = "credential",value = "登录凭证(密码)",required = true,dataType = "String",example = "1234567"),
            @ApiImplicitParam(name = "identityType",value = "登录类型",required = true,dataType = "String",example = "ORIGIN|BILI|PHONE|EMAIL")

    })
    @PostMapping(value = "/login")
    public Result login(@RequestBody UserAuthsPo userAuthsPo, HttpServletResponse response){
        /** shiro认证 */
        try {
            String userId = ILoginService.getUserId(userAuthsPo.getIdentityId(),userAuthsPo.getIdentityType());
            if (userId.length()>0){
                UsernamePasswordToken upToken = new UsernamePasswordToken(userId,userAuthsPo.getCredential());

                Subject subject = SecurityUtils.getSubject();
                //获取sessionId
                String sid = (String) subject.getSession().getId();
                System.out.println("sid:"+sid);

                System.out.println("开始登陆。。。");
                subject.login(upToken);
                System.out.println("登陆完成。。。");

                UserDto userDto = (UserDto)subject.getPrincipal();
                if ("1".equals(userDto.getUsable())){
                    userDto.setSessionId(sid);
                    operationLogService.addUserOperate(userId, UserOperationEnum.LOGIN.getName(),"登录上线");
                    ILoginService.updateLoginTime(userId);
                    return ResultUtil.success(userDto);
                }else{
                    response.sendRedirect("auths?code=10001");
                    return null;
                }


            }else {
                return ResultUtil.error(104,"用户不存在");
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResultUtil.error(103,"登陆失败");
        }

    }

    /**
     * 注册
     * @param nickname
     * @param password
     * @return
     */
    @ApiOperation(value = "用户注册")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "nickname",value = "用户注册昵称",required = true,dataType = "String",example = "XGHK416"),
            @ApiImplicitParam(name = "password",value = "用户注册密码",required = true,dataType = "String",example = "1234567")
    })
    @PostMapping(value = "/register")
    public Result register(String nickname,String password){
        Map<String,Object> resultMap = ILoginService.RegisterUser(nickname,password);
        if ((Integer)resultMap.get("code")>0){
            String userId = resultMap.get("userId").toString();
            ILoginService.updateLoginTime(userId);
            return ResultUtil.success(resultMap.get("token"));
        }
        else {
            return ResultUtil.error((Integer) resultMap.get("code"),resultMap.get("status").toString());
        }
    }

    /**
     * 登出
     * @return
     */
    @ApiOperation(value = "用户登出")
    @PostMapping(value = "/logout")
    public Result logout(){
        if (ILoginService.LogoutUser()){
            return ResultUtil.success();
        }else return ResultUtil.error(ResultEnum.UNKNOWN_ERROR.getCode(),ResultEnum.UNKNOWN_ERROR.getMsg());
    }

    @ApiOperation(value = "更改密码")
    @PostMapping(value = "/changePassword")
    public Result changePassword(@RequestBody UserAuthsPo userAuthsPo){
        String password = userAuthsPo.getCredential();
        String userId = userAuthsPo.getUserId();
        if (ILoginService.changePassword(password, userId)){
            operationLogService.addUserOperate(userId, UserOperationEnum.CHANGE_PASSWORD.getName(),"修改密码");
            return ResultUtil.success();
        }else {
            return ResultUtil.error(ResultEnum.UNKNOWN_ERROR.getCode(),ResultEnum.UNKNOWN_ERROR.getMsg());
        }
    }
}
