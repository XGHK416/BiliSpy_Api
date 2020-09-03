package com.project.xghk416.project.viewer.hot.controller;


import com.project.xghk416.common.result.Result;
import com.project.xghk416.enums.ResultEnum;
import com.project.xghk416.enums.UserOperationEnum;
import com.project.xghk416.pojo.bili.BiliDetectPo;
import com.project.xghk416.pojo.user.UserFavoritePo;
import com.project.xghk416.project.common.operate.service.impl.IOperationLogService;
import com.project.xghk416.project.viewer.favorite.service.impl.IUserFavoriteService;
import com.project.xghk416.project.viewer.hot.service.impl.IHotBiliService;
import com.project.xghk416.common.util.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

@Api(tags = "B站热门模块接口")
@RequestMapping(value = "/hotBili")
@RestController
public class HotBiliController {
    @Autowired
    IHotBiliService hotBiliService;
    @Autowired
    IUserFavoriteService favoriteService;
    @Autowired
    IOperationLogService operationLogService;

    @GetMapping(value = "/getRank")
    @ApiOperation(value = "获取排名列表")
    public Result getHotRank(int tid,int day){
        String baseUrl = "https://api.bilibili.com/x/web-interface/ranking?type=1&arc_type=0&jsonp=jsonp";
        baseUrl+="&rid="+tid;
        baseUrl+="&day="+day;
        Map<String,Object> result = hotBiliService.getRank(baseUrl);

        return ResultUtil.success(result.get("data"));
    }

    @GetMapping(value = "/getUploaderInfo")
    @ApiOperation(value = "获取up主信息")
    public Result getHotUploaderInfo(int mid,String userId){
        Map<String,Object> result = hotBiliService.getUploaderInfo(mid);
//        查看是否有收藏
        UserFavoritePo favoritePo = new UserFavoritePo();
        favoritePo.setUserId(userId);
        favoritePo.setFavoriteId(mid);
        favoritePo.setType("uploader");

        UserFavoritePo favoriteResult = favoriteService.checkIsFavorite(favoritePo);
        if (favoriteResult!=null){
            result.put("favoriteId",favoriteResult.getId());
        }
        else{
            result.put("favoriteId",-1);
        }


        return ResultUtil.success(result);
    }

    @GetMapping(value = "/getVideoInfo")
    @ApiOperation(value = "获取视频信息")
    public Result getHotVideoInfo(int aid,String userId){
        String baseUrl = "https://api.bilibili.com/x/web-interface/view?aid=" + aid;
        Map<String,Object> result = hotBiliService.getVideoInfo(baseUrl,aid);
        //        查看是否有收藏
        UserFavoritePo favoritePo = new UserFavoritePo();
        favoritePo.setUserId(userId);
        favoritePo.setFavoriteId(aid);
        favoritePo.setType("video");

        UserFavoritePo favoriteResult = favoriteService.checkIsFavorite(favoritePo);
        if (favoriteResult!=null){
            result.put("favoriteId",favoriteResult.getId());
        }
        else{
            result.put("favoriteId",-1);
        }
        return ResultUtil.success(result);
    }
    @ApiOperation("添加up主到侦测系统")
    @PostMapping("/addUploaderDetect")
    public Result addUploaderDetect(String userId,int mid){
        BiliDetectPo uploader = new BiliDetectPo(mid,userId);
        int flag = hotBiliService.addDetectObject(uploader);
        if (flag==0){
            return ResultUtil.error(ResultEnum.UNKNOWN_ERROR.getCode(),"插入失败");
        }
        else{
            operationLogService.addUserOperate(userId, UserOperationEnum.ADD_NEW_DETECT.getName(),"uploader#"+mid);
            return ResultUtil.success();
        }
    }
    @ApiOperation("添加视频主到侦测系统")
    @PostMapping("/addVideoDetect")
    public Result addVideoDetect(String userId,int aid){
        BiliDetectPo video = new BiliDetectPo(aid,userId,7);
        int flag = hotBiliService.addDetectObject(video);
        if (flag==0){
            return ResultUtil.error(ResultEnum.UNKNOWN_ERROR.getCode(),"插入失败");
        }
        else{
            operationLogService.addUserOperate(userId, UserOperationEnum.ADD_NEW_DETECT.getName(),"video#"+aid);
            return ResultUtil.success();
        }
    }
}