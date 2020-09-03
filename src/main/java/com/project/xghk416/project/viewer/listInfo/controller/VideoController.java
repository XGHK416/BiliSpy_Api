package com.project.xghk416.project.viewer.listInfo.controller;

import com.project.xghk416.common.result.Result;
import com.project.xghk416.common.util.ResultUtil;
import com.project.xghk416.enums.ResultEnum;
import com.project.xghk416.pojo.bili.BiliVideoPo;
import com.project.xghk416.pojo.dto.VideoListDto;
import com.project.xghk416.project.viewer.listInfo.service.impl.IVideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Api(tags = "video详情")
@RestController
@RequestMapping("/info/video/")
public class VideoController {

    @Autowired
    IVideoService videoService;

    @GetMapping("/getInfo")
    @ApiOperation(value = "获取video基本信息")
    @ApiImplicitParam(name = "aid",value = "aid")
    public Result getInfo(int aid){
        try{
            BiliVideoPo videoPo = videoService.getInfo(aid);
            if (videoPo != null){
                return ResultUtil.success(videoPo);
            }else {
                return ResultUtil.error(ResultEnum.UNKNOWN_ERROR.getCode(), ResultEnum.UNKNOWN_ERROR.getMsg());
            }
        }catch (Exception e) {
            return ResultUtil.error(ResultEnum.UNKNOWN_ERROR.getCode(), e.getMessage());
        }

    }

    @GetMapping("/getEvaluateInfo")
    @ApiOperation(value = "获取video评价信息")
    @ApiImplicitParam(name = "aid",value = "aid")
    public Result getEvaluateInfo(int aid){
        DateTimeFormatter dtf= DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime today = LocalDateTime.now();
        String endDate = today.plusDays(-1).format(dtf);
        String startDate = today.plusDays(-7).format(dtf);
        try{
            List<Map<String,Object>> result = videoService.getEvaluateByLimitDate(startDate,endDate,aid);
            if (result != null){
                return ResultUtil.success(result);
            }else {
                return ResultUtil.error(ResultEnum.UNKNOWN_ERROR.getCode(), ResultEnum.UNKNOWN_ERROR.getMsg());
            }
        }catch (Exception e) {
            return ResultUtil.error(ResultEnum.UNKNOWN_ERROR.getCode(), e.getMessage());
        }

    }

    @GetMapping("/getRecommend")
    @ApiOperation(value = "获取video基本信息")
    @ApiImplicitParam(name = "aid",value = "aid")
    public Result getRecommend(int aid){
        BiliVideoPo videoInfo = videoService.getInfo(aid);
        String tag = videoInfo.getDynamic();
        String section = videoInfo.getTname();
        String[] tagList = tag.split("#");

        String flag="";
        for (String item :
                tagList) {
            if (item.length()!=0){
                flag = item;
                break;
            }
        }
        try{
            VideoListDto result = videoService.getRecommendList(flag,section);
            if (result != null){
                return ResultUtil.success(result.getList());
            }else {
                return ResultUtil.error(ResultEnum.UNKNOWN_ERROR.getCode(), ResultEnum.UNKNOWN_ERROR.getMsg());
            }
        }catch (Exception e) {
            return ResultUtil.error(ResultEnum.UNKNOWN_ERROR.getCode(), e.getMessage());
        }

    }

}
