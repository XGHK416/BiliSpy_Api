package com.project.xghk416.project.viewer.listInfo.controller;

import com.project.xghk416.common.result.Result;
import com.project.xghk416.common.util.CountVideoCountUtil;
import com.project.xghk416.common.util.ResultUtil;
import com.project.xghk416.project.viewer.listInfo.service.impl.IWorkAnaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "用户分系界面作品分析功能")
@RestController
@RequestMapping(value = "/work")
public class WorkAnalysisController {
    @Autowired
    IWorkAnaService iWorkAnaService;

    /**
     * 用于获取该up主排名前三的作品
     * @param mid
     * @return
     */
    @GetMapping(value = "/getTopThree")
    @ApiOperation(value = "获取排名前三的作品")
    public Result getTopThreeWorks(int mid){
        List<Map<String,Object>> result = iWorkAnaService.getTopThree(mid);


        return ResultUtil.success(result);
    }

    /**
     * 获取该up主全部作品的平均水平
     * @param mid
     * @return
     */
    @GetMapping(value = "/getAverage")
    @ApiOperation(value = "获取作品集平均水平")
    public Result getAverage(int mid){
        Map<String,Integer> result = new HashMap<>();
        result.put("view",iWorkAnaService.getWorkStatusAvg(mid,"video_view"));
        result.put("like",iWorkAnaService.getWorkStatusAvg(mid,"video_like"));
        result.put("coins",iWorkAnaService.getWorkStatusAvg(mid,"coins"));
        result.put("favorite",iWorkAnaService.getWorkStatusAvg(mid,"video_favorite"));

        return ResultUtil.success(result);
    }

    /**
     * 获取该up主常使用的tag
     * @param mid
     * @return
     */
    @GetMapping(value = "/getCommonlyTags")
    @ApiOperation(value = "获取up主常用的标签")
    public Result getCommonlyTags (int mid){
        List<String> tags = iWorkAnaService.getTags(mid);
        List<Map<String, Object>> result = CountVideoCountUtil.formatAll(CountVideoCountUtil.anaTags(tags));


        return ResultUtil.success(result);
    }

    /**
     * 获取该up主这几个月的作品数
     * @param mid
     * @return
     */
    @GetMapping(value = "/getMonthWorksNum")
    @ApiOperation(value = "获取up主每月作品数")
    public Result getMonthWorksNum (int mid){
        List<Map<String,Integer>> temp = iWorkAnaService.getMonthlyCount(mid);
        List<String> xAxis = new ArrayList<>();
        List<Integer> series = new ArrayList<>();
        Map<String,Object> result = new HashMap<>();
        for (Map<String,Integer> item:
        temp){
            xAxis.add(item.get("month").toString()+"月");
            series.add(item.get("count"));

            result.put("xAxis",xAxis);
            result.put("series",series);
        }

        return ResultUtil.success(result);
    }

    /**
     *获取近期的作品的状态
     * @param mid
     * @return
     */
    @GetMapping(value = "/getRecentWorksStatus")
    @ApiOperation(value = "获取近期的作品的状态")
    public Result getRecentWorksStatus(int mid){
        return ResultUtil.success(iWorkAnaService.getRecentWorkStatus(mid));
    }

    @GetMapping(value = "/predectNextCount")
    @ApiOperation(value = "预测下个月发布的视频数")
    public Result predictNextCount(int mid){
        Map<String,Object> predectResult = iWorkAnaService.predictNextMonth(mid);
        return ResultUtil.success(predectResult);
    }
    @GetMapping(value = "/predectNextStatus")
    @ApiOperation(value = "预测下个视频状态")
    public Result predictNextStatus(int mid,int tid){
        Map<String,Object> predectResult = iWorkAnaService.predictNextVideo(mid,tid);
        return ResultUtil.success(predectResult);
    }




}
