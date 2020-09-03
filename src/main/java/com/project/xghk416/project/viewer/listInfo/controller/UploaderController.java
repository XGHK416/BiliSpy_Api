package com.project.xghk416.project.viewer.listInfo.controller;


import com.project.xghk416.common.result.Result;
import com.project.xghk416.common.util.ResultUtil;
import com.project.xghk416.enums.ResultEnum;
import com.project.xghk416.pojo.bili.BiliUploaderPo;
import com.project.xghk416.pojo.bili.BiliVideoPo;
import com.project.xghk416.pojo.dto.UploaderInfoDto;
import com.project.xghk416.project.viewer.listInfo.service.impl.IUploaderService;
import com.project.xghk416.project.viewer.listInfo.service.impl.IVideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "up主详情")
@RestController
@RequestMapping("/info/uploader/")
public class UploaderController {

    @Autowired
    IUploaderService uploaderService;
    @Autowired
    IVideoService videoService;

    @GetMapping(value = "/base")
    @ApiOperation(value = "获取up主界面头部信息")
    @ApiImplicitParam(name = "mid", value = "up主mid")
    public Result getBasicInfo(Integer mid,String userId) {
        try {
            BiliUploaderPo baseInfo = uploaderService.getBaseInfo(mid);
            if (baseInfo==null){
                return ResultUtil.success(-1);
            }
            String belongSection = uploaderService.getBelongSection(mid);
            LocalDateTime lastPublish = videoService.getLastPublish(mid);
            int detectTime = uploaderService.getDetectTime(mid,userId);
            return ResultUtil.success(new UploaderInfoDto(baseInfo, belongSection, lastPublish, detectTime));


        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.error(ResultEnum.UNKNOWN_ERROR.getCode(), e.getMessage());
        }
    }

    @GetMapping(value = "/workInfo")
    @ApiOperation(value = "获取up主界面视频数据方面信息")
    @ApiImplicitParam(name = "mid", value = "up主mid")
    public Result getVideoAna(Integer mid) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            int wdayCount = videoService.getPublishCount(mid, 7);
            int mdayCount = videoService.getPublishCount(mid, 30);

            Map<String, Object> section = videoService.analysisSection(mid);

            Map<String, Object> publish = videoService.analysisPublishTime(mid);

            resultMap.put("wday_count", wdayCount);
            resultMap.put("mday_count", mdayCount);

            resultMap.put("section", section);
            resultMap.put("publish", publish);

            return ResultUtil.success(resultMap);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.error(ResultEnum.UNKNOWN_ERROR.getCode(), e.getMessage());
        }
    }

    @GetMapping(value = "/fans")
    @ApiOperation(value = "获取up主界面粉丝数据方面信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mid",value ="up主mid"),
            @ApiImplicitParam(name = "type",value ="时间跨度")
    })
    public Result fansChange(int mid,int type){
        Map<String,Object> resultMap = new HashMap<>();
        try {
            Map<String,Object> fans = uploaderService.getChange(mid, type);
            resultMap.put("type",type);
            resultMap.put("fans",fans);
            return ResultUtil.success(resultMap);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.error(ResultEnum.UNKNOWN_ERROR.getCode(), e.getMessage());
        }

    }

    @GetMapping(value = "/recentWork")
    @ApiOperation(value = "获取up主界面近期视频发布方面信息")
    @ApiImplicitParams ({
            @ApiImplicitParam(name = "mid",value ="up主mid"),
            @ApiImplicitParam(name = "page", value = "页数"),
            @ApiImplicitParam(name = "pageSize", value = "页大小")
    })
    public Result getRecentVideo(int mid,int page,int pageSize){
        Map<String,Object> resultMap = new HashMap<>();
        try {
            List<BiliVideoPo> videoPos = videoService.getList(mid,page,pageSize);
            resultMap.put("list",videoPos);
            return ResultUtil.success(resultMap);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.error(ResultEnum.UNKNOWN_ERROR.getCode(), e.getMessage());
        }

    }
    @GetMapping(value = "/competeWaveInfo")
    @ApiOperation(value = "获取up主界面竞品具体核心数据")
    public Result getCompetingOnesData(int[] mids, String type, int limit){
//        int[] midList = new int[10];
//        for(int i=0;i<mids.length;i++){
//            midList[i] = Integer.parseInt(mids[i]);
//        }
        Map<String,Object> resultMap = new HashMap<>();
        try {
            resultMap.put("list",uploaderService.getCompetingData(mids, type,limit));
            resultMap.put("legend",uploaderService.getNickName(mids));
            return ResultUtil.success(resultMap);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.error(ResultEnum.UNKNOWN_ERROR.getCode(), e.getMessage());
        }

    }

    @GetMapping(value = "/competeBaseInfo")
    @ApiOperation(value = "获取up主界面竞品产品的大致核心数据")
    @ApiImplicitParam(name = "mid", value = "mid")
    public Result getCompetingData(int mid){
        try {
//            获取基本数据
            BiliUploaderPo baseInfo = uploaderService.getBaseInfo(mid);
            Map<String, Object> fansInfo = uploaderService.getChange(mid,7);
            List<BiliVideoPo> latestVideo = videoService.getLatestInfo(mid);

            Map<String,Object> resultMap = new HashMap<>();

            resultMap.put("uploader",baseInfo);
            resultMap.put("fans",fansInfo);
            resultMap.put("latestVideo",latestVideo);

            return ResultUtil.success(resultMap);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.error(ResultEnum.UNKNOWN_ERROR.getCode(), e.getMessage());
        }

    }

    @GetMapping(value = "/competeList")
    @ApiOperation(value = "获取up主界面竞品产品")
    @ApiImplicitParams ({
            @ApiImplicitParam(name = "key",value ="模糊查询的关键字"),
            @ApiImplicitParam(name = "page", value = "页数"),
            @ApiImplicitParam(name = "pageSize", value = "页大小"),
    })
    public Result getCompetingUploader(String key,int page,int pageSize){
        try {
            return ResultUtil.success(uploaderService.getListByKey(page, pageSize, key));
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.error(ResultEnum.UNKNOWN_ERROR.getCode(), e.getMessage());
        }

    }

    @GetMapping(value = "recommendCompeteList")
    @ApiOperation(value = "推荐up主界面竞品产品")
    @ApiImplicitParams ({
            @ApiImplicitParam(name = "mid",value ="mid"),
            @ApiImplicitParam(name = "page", value = "页数"),
            @ApiImplicitParam(name = "pageSize", value = "页大小"),
    })
    public Result getRecommendUploader(int mid,int page,int pageSize){
        try {
            String section = uploaderService.getBelongSection(mid);
            return ResultUtil.success(uploaderService.getRecommend(section,page,pageSize,mid));
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.error(ResultEnum.UNKNOWN_ERROR.getCode(), e.getMessage());
        }

    }

}
