package com.project.xghk416.project.common.space.controller;


import com.project.xghk416.enums.ResultEnum;
import com.project.xghk416.pojo.dto.UserBaseInfoDto;
import com.project.xghk416.pojo.user.UserAccountPo;
import com.project.xghk416.pojo.user.UserAuthsPo;
import com.project.xghk416.common.result.Result;
import com.project.xghk416.project.common.space.service.impl.IUserInfoService;
import com.project.xghk416.common.util.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Api(tags = "用户个人界面api")
@RequestMapping(value = "/space")
public class PersonalSpaceController {
    @Autowired
    IUserInfoService IUserInfoService;


    @ApiOperation(value = "用户绑定")
    @ApiImplicitParam(name = "userAuthsPo",value = "用户绑定",paramType = "body",required = true)
    @PostMapping(value = "/bandIdentity")
    public Result bandIdentity(@RequestBody UserAuthsPo userAuthsPo){
        UserBaseInfoDto resultDto = IUserInfoService.SocialIdentification(userAuthsPo);
        if (resultDto.getState()>0){
            return ResultUtil.success();
        }else {
            return ResultUtil.error(resultDto.getState(),resultDto.getStateInfo());
        }
    }

    // TODO: 2020/2/12 绑定B站
    @ApiOperation(value = "用户绑定B站账号")
    @ApiImplicitParam(name = "userAuthsPo",value = "用户B站绑定账号",paramType = "body",required = true)
    @PostMapping(value = "/bandBili")
    public Result bandBili(@RequestBody UserAuthsPo userAuthsPo){
        return ResultUtil.success(userAuthsPo);
    }

    @ApiOperation(value = "获取用户基本信息")
    @ApiImplicitParam(name = "userId",value = "用户id",dataType = "String",required = true,example = "user1")
    @GetMapping(value = "/getBaseInfo")
    public Result baseUserInfo(String userId){
        try {
            UserBaseInfoDto resultDto = IUserInfoService.GetBaseInfo(userId);
            if (resultDto.getState()>0){
                return ResultUtil.success(resultDto);
            }
            else return ResultUtil.error(resultDto.getState(),resultDto.getStateInfo());
        } catch (Exception e){
            return ResultUtil.error(ResultEnum.UNKNOWN_ERROR.getCode(),e.getMessage());
        }

    }

    @ApiOperation(value = "更改账户信息")
    @ApiImplicitParam(name = "userAccountPo",value = "更新个人信息",paramType = "body")
    @PutMapping(value = "/updateInfo")
    public Result changeBaseInfo(@RequestBody UserAccountPo userAccountPo){
        boolean success = IUserInfoService.alterUserInfo(userAccountPo.getUserId(),userAccountPo);
        if (success){
            return ResultUtil.success();
        }
        else {
            return ResultUtil.error(ResultEnum.UNKNOWN_ERROR.getCode(),ResultEnum.UNKNOWN_ERROR.getMsg());
        }
    }

//    @ApiOperation(value = "更改密码")
//    @ApiImplicitParam(name = "newPassword",value = "新的密码",dataType = "String",required = true,example = "123445")
//    @PutMapping(value = "/changePassword")
//    public Result changePassword(String userId,String newPassword){
//        UserAuthsPo newUserAuthsPo = new UserAuthsPo();
//        newUserAuthsPo.setCredential(newPassword);
//        boolean success = userInfoServiceImpl.alterPassword(userId,newUserAuthsPo);
//        if (success){
//            return ResultUtil.success();
//        }
//        else {
//            return ResultUtil.error(ResultEnum.UNKNOWN_ERROR.getCode(),ResultEnum.UNKNOWN_ERROR.getMsg());
//        }
//    }

    @ApiOperation(value = "更改头像")
    @ApiImplicitParam(name = "url",value = "头像文件",required = true)
    @PutMapping(value = "/updateProfile")
    public Result updateProfile(String userId, MultipartFile avatar){
        String newUrl = IUserInfoService.alterProfile(userId,avatar);
        return ResultUtil.success(newUrl);
    }
}
