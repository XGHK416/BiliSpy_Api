package com.project.xghk416.project.mo.manage.controller;

import com.project.xghk416.enums.ResultEnum;
import com.project.xghk416.enums.mo.ManagerOperationEnum;
import com.project.xghk416.enums.mo.TableEnum;
import com.project.xghk416.pojo.MoOperate;
import com.project.xghk416.pojo.dto.UserBaseInfoDto;
import com.project.xghk416.pojo.user.UserAccountPo;
import com.project.xghk416.pojo.punish.UserColdPo;
import com.project.xghk416.pojo.operate.UserLogOperaPo;
import com.project.xghk416.common.result.Result;
import com.project.xghk416.project.common.operate.service.impl.IMoLogService;
import com.project.xghk416.project.mo.manage.service.impl.IPunishService;
import com.project.xghk416.project.mo.manage.service.impl.IUserManageService;
import com.project.xghk416.project.mo.manage.service.impl.IIdentityAuthenticationService;
import com.project.xghk416.common.util.DateTimeUtil;
import com.project.xghk416.common.util.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Api(tags = "用户管理")
@RequestMapping(value = "/userManage")
public class UserManagerController {
    @Autowired
    IUserManageService userManageService;
    @Autowired
    IMoLogService moLogService;
    @Autowired
    IPunishService punishService;
    @Autowired
    IIdentityAuthenticationService identityAuthenticationService;


    @GetMapping(value = "/getCount")
    @ApiOperation(value = "获取角色总数")
    public Result getCount(){
        Map<String,Object> result = userManageService.getUserCount();
        return ResultUtil.success(result);
    }



    @PostMapping(value = "/addNewMo")
    @ApiOperation(value = "添加新的管理")
    public Result addNewMo(String operaMoId,String type){
        int num = userManageService.getCount(type)+1;
        String defaultNickName =type+num;
        String defaultPassword = "123456";
        if (userManageService.addNewMo(defaultNickName,defaultPassword,type)){
            MoOperate operate = new MoOperate(ManagerOperationEnum.ADD_MO, TableEnum.USER_ACCOUNT,operaMoId,defaultNickName,null,"UserAccount");
            moLogService.addOpera(operate);
            Map<String,Object> newAccount = new HashMap<>();
            newAccount.put("account",defaultNickName);
            newAccount.put("password",defaultPassword);
            return ResultUtil.success(newAccount);
        }else {
            return ResultUtil.error(ResultEnum.UNKNOWN_ERROR.getCode(),ResultEnum.UNKNOWN_ERROR.getMsg());
        }
    }

    @GetMapping(value = "/getUserList")
    @ApiOperation(value = "获得用户列表")
    public Result getUserList(int page,int pageSize,String type){

        List<UserAccountPo> result = userManageService.getUserList(page, pageSize, type);

        return ResultUtil.success(result);
    }

    @GetMapping(value = "/getUserInfo")
    @ApiOperation(value = "用户信息")
    public Result getUserInfo(String userId){
        UserBaseInfoDto result = userManageService.getUserAccount(userId);
        return ResultUtil.success(result);
    }
    @PostMapping(value = "/searchUser")
    @ApiOperation(value = "根据id和名称查询用户")
    public Result getUserByKey(String key,String type){
        List<UserAccountPo> userAccountPos = userManageService.getUserListByKey(key, type);
        return ResultUtil.success(userAccountPos);
    }

    @GetMapping(value = "/getMoOperate")
    @ApiOperation(value = "用户信息")
    public Result getMoOperate(String selectedId,String searchFrom,String date){
        String endDate = DateTimeUtil.returnOneDateLimit(date,1);
        List<Map<String,Object>> result= userManageService.getMoOperate(selectedId,searchFrom,date,endDate);
        return ResultUtil.success(result);
    }
    @GetMapping(value = "/getViewerOperate")
    @ApiOperation(value = "用户信息")
    public Result getViewerOperate(String viewerId,String searchFrom,String date){
        String endDate = DateTimeUtil.returnOneDateLimit(date,1);
        List<UserLogOperaPo> result = userManageService.getViewerOperate(viewerId,searchFrom,date,endDate);
        return ResultUtil.success(result);
    }

    @PostMapping(value = "/writtenOffUser")
    @ApiOperation(value = "封禁用户")
    public Result writtenOffUser(String createId,String writtenOffId,boolean isWrittenOf){
        // TODO: 2020/3/29 完善代码
        punishService.writtenOffUser(writtenOffId,isWrittenOf);
        MoOperate operate;
        if (isWrittenOf){
//            操作记录
            operate = new MoOperate(ManagerOperationEnum.WRITTEN_OFF_USER,TableEnum.USER_ACCOUNT,createId,"0","1","VARCHAR");
        }else {
            operate = new MoOperate(ManagerOperationEnum.WRITTEN_OFF_USER,TableEnum.USER_ACCOUNT,createId,"1","0","VARCHAR");
        }
        moLogService.addOpera(operate);
        return ResultUtil.success();
    }
    @PostMapping(value = "coldUser")
    @ApiOperation(value = "冻结用户")
    public Result coldUser(UserColdPo userColdPo){
        punishService.coldViewer(userColdPo);
        MoOperate operate = new MoOperate(ManagerOperationEnum.DE_COLD_VIEWER,TableEnum.USER_COLD,userColdPo.getColdUserId(),userColdPo.getColdUserId(),null,"UserColdPo");
        moLogService.addOpera(operate);
        return ResultUtil.success();
    }
    @DeleteMapping(value = "decoldUser")
    @ApiOperation(value = "解冻用户")
    public Result decoldUser(String coldId,String moId){
        punishService.decoldViewer(coldId);
        MoOperate operate = new MoOperate(ManagerOperationEnum.DE_COLD_VIEWER,TableEnum.USER_COLD,moId,null,coldId,"UserColdPo");
        moLogService.addOpera(operate);

        return ResultUtil.success();
    }
    @PostMapping(value = "confirmPassword")
    @ApiOperation("密码验证")
    public Result confirmPassword(String moId,String currentPassword){
        if (identityAuthenticationService.authenticatePassword(moId, currentPassword)){
            return ResultUtil.success();
        }else {
            return ResultUtil.error(ResultEnum.UNKNOWN_ERROR.getCode(),"密码错误");
        }
    }

}
