package com.project.xghk416.project.viewer.favorite.controller;


import com.project.xghk416.enums.ResultEnum;
import com.project.xghk416.pojo.user.UserFavoriteGroupPo;
import com.project.xghk416.pojo.user.UserFavoritePo;
import com.project.xghk416.common.result.Result;
import com.project.xghk416.project.viewer.favorite.service.impl.IUserFavoriteService;
import com.project.xghk416.common.util.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@Api(tags = "用户收藏")
@RequestMapping(value = "/favorite")
public class FavoriteController {
    @Autowired
    IUserFavoriteService userFavoriteService;

    @ApiOperation(value = "获取用户收藏组")
    @GetMapping(value = "/getGroup")
    public Result getUserGroup(String userId,@Nullable String type){
        List<UserFavoriteGroupPo> result = new ArrayList<>();
        if (type==null){
            List<UserFavoriteGroupPo> videoGroup= userFavoriteService.getFavoriteGroup(userId,"video");
            List<UserFavoriteGroupPo> uploaderGroup= userFavoriteService.getFavoriteGroup(userId,"uploader");
            result.addAll(uploaderGroup);
            result.addAll(videoGroup);
        }else {
            result = userFavoriteService.getFavoriteGroup(userId, type);

        }
        return ResultUtil.success(result);
    }

    @ApiOperation(value = "添加收藏组")
    @PostMapping(value = "/addGroup")
    public Result addGroup(@RequestBody  UserFavoriteGroupPo newGroup){
        Long today = LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
        String groupId = newGroup.getCreateId()+ today.toString();
        newGroup.setCreateTime(LocalDateTime.now());
        newGroup.setGroupId(groupId);
        boolean flag = userFavoriteService.addFavoriteGroup(newGroup);
        if (flag){
            return ResultUtil.success();
        }
        else return ResultUtil.error(ResultEnum.UNKNOWN_ERROR.getCode(),ResultEnum.UNKNOWN_ERROR.getMsg());

    }

    @ApiOperation(value = "删除收藏组")
    @DeleteMapping(value = "/cancelGroup")
    public Result cancleGroup(String groupId){
        boolean flag = userFavoriteService.cancelFavoriteGroup(groupId);
        if (flag){
            return ResultUtil.success();
        }
        else return ResultUtil.error(ResultEnum.UNKNOWN_ERROR.getCode(),ResultEnum.UNKNOWN_ERROR.getMsg());

    }

    @ApiOperation(value = "更新收藏组")
    @PutMapping(value = "/updateGroup")
    public Result cancleGroup(@RequestBody UserFavoriteGroupPo updateGroup){
        boolean flag = userFavoriteService.updateFavoriteGroup(updateGroup);
        if (flag){
            return ResultUtil.success();
        }
        else return ResultUtil.error(ResultEnum.UNKNOWN_ERROR.getCode(),ResultEnum.UNKNOWN_ERROR.getMsg());

    }


    /////////收藏-收藏组


    @ApiOperation(value = "添加收藏")
    @PostMapping(value = "/addFavorite")
    public Result addFavorite(@RequestBody UserFavoritePo favoritePo){
        int id = userFavoriteService.addFavorite(favoritePo);
        if (id>0){
            return ResultUtil.success(id);
        }
        else return ResultUtil.error(ResultEnum.UNKNOWN_ERROR.getCode(),ResultEnum.UNKNOWN_ERROR.getMsg());

    }

    @ApiOperation(value = "删除收藏")
    @GetMapping(value = "/cancelFavorite")
    public Result cancelFavorite(int id){
        boolean flag = userFavoriteService.cancelFavorite(id);
        if (flag){
            return ResultUtil.success();
        }
        else return ResultUtil.error(ResultEnum.UNKNOWN_ERROR.getCode(),ResultEnum.UNKNOWN_ERROR.getMsg());

    }

    @ApiOperation(value = "更新收藏")
    @PutMapping(value = "/updateFavorite")
    public Result updateFavorite(@RequestBody UserFavoritePo favoritePo){
        boolean flag = userFavoriteService.updateFavorite(favoritePo);
        if (flag){
            return ResultUtil.success();
        }
        else return ResultUtil.error(ResultEnum.UNKNOWN_ERROR.getCode(),ResultEnum.UNKNOWN_ERROR.getMsg());

    }
    @ApiOperation(value = "检查收藏")
    @PostMapping(value = "/checkFavorite")
    public Result checkIsFavorite(@RequestBody UserFavoritePo favoritePo){
        UserFavoritePo resultItem = userFavoriteService.checkIsFavorite(favoritePo);
        if (resultItem!=null){
            return ResultUtil.success(resultItem);
        }
        else return ResultUtil.success(null);

    }

    @ApiOperation(value = "查找收藏")
    @GetMapping(value = "/findFavorite")
    public Result findFavorite(String userId,String type,@Nullable String groupId){
        List<Map<String,Object>> result = new ArrayList<>();
        if (groupId==null){
            result = userFavoriteService.getFavoriteListByUser(userId,type);
        }else {
            result = userFavoriteService.getFavoriteListByGroup(groupId,type);
        }
        return ResultUtil.success(result);

    }


}
