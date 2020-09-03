package com.project.xghk416.project.common.baseAnalysis.controller;


import com.project.xghk416.enums.ResultEnum;
import com.project.xghk416.common.result.Result;
import com.project.xghk416.project.common.baseAnalysis.service.impl.IBaseAnalysisService;
import com.project.xghk416.common.util.DateTimeUtil;
import com.project.xghk416.common.util.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@Api(tags = "基本信息展示主页")
@RequestMapping(value = "/baseAnalysis")
/**
 * 基础信息（不需要进行二次处理的数据）展示
 */

public class BaseAnalysisController {

    @Autowired
    public RestTemplate restTemplate;

    @Autowired
    public IBaseAnalysisService baseAnalysisService;


    /**
     * 返回爬取的基本信息
     * @return
     */
    @GetMapping(value = "/topInfo")
    @ApiOperation(value = "返回爬取的基本信息")
    public Result BaseCount(){
        Map result = baseAnalysisService.getBaseInfo();
        return ResultUtil.success(result);

    }

    /**
     * 返回前20粉丝量的up主
     * @return
     */
    @GetMapping(value = "/topFansLevelUploader")
    @ApiOperation(value = "top20粉丝数量排行")
    public Result TopFansCount(){
        String startDate = DateTimeUtil.returnDateStream(-1);
        String endDate = DateTimeUtil.returnDateStream(0);
        try {
            Map<String,Object> result = baseAnalysisService.getTop(startDate,endDate);
            return ResultUtil.success(result);
        } catch (Exception e){
            return ResultUtil.error(ResultEnum.UNKNOWN_ERROR.getCode(),e.getMessage());
        }
    }

    /**
     * 返回up主各类信息分析
      * @return
     */
    @GetMapping(value = "/countUploaderInfo")
    @ApiOperation(value = "返回用户各类信息分析")
    public Result UploaderBaseAnalysis() {
        Map<String,Object> result = new HashMap<>();
        try {
            Map<String,Object> genderResult = baseAnalysisService.getGenderInfo();
            Map<String,Object> levelResult = baseAnalysisService.getLevelInfo();
            Map<String,Object> fansLevelResult = baseAnalysisService.getLevel();
            result.put("fans_level",fansLevelResult);
            result.put("gender",genderResult);
            result.put("level",levelResult);

            return ResultUtil.success(result);
        }catch (Exception e){
            return ResultUtil.error(ResultEnum.UNKNOWN_ERROR.getCode(),ResultEnum.UNKNOWN_ERROR.getMsg());
        }
    }

    /**
     * 近七日爬取视频和总爬取
     * @return
     */
    @GetMapping(value = "/getSpiderVideoCount")
    @ApiOperation(value = "近七日爬取视频和总爬取")
    public Result VideoBaseAnalysis() {
        try {
            Map<String, Object> result = baseAnalysisService.getSpider(7);
            return ResultUtil.success(result);
        } catch (Exception e) {
            return ResultUtil.error(ResultEnum.UNKNOWN_ERROR.getCode(), ResultEnum.UNKNOWN_ERROR.getMsg());
        }
    }

    /**
     * 视频类别统计
     * @return
     */
    @GetMapping(value = "/getVideoSectionCount")
    @ApiOperation(value = "视频类别统计")
    public Result VideoSectionCount() {
        try {
            Map<String, Object> result = baseAnalysisService.getSection();
            return ResultUtil.success(result);
        } catch (Exception e) {
            return ResultUtil.error(ResultEnum.UNKNOWN_ERROR.getCode(), ResultEnum.UNKNOWN_ERROR.getMsg());
        }
    }

    /**
     * 视频词云
     * @return
     */
    @GetMapping(value = "/getWorldCloud")
    @ApiOperation(value = "视频词云")
    public Result VideoDynamicCount() {
        try {
            Map<String, Object> result = baseAnalysisService.getTagWorldCloud();
            return ResultUtil.success(result);
        } catch (Exception e) {
            return ResultUtil.error(ResultEnum.UNKNOWN_ERROR.getCode(), ResultEnum.UNKNOWN_ERROR.getMsg());
        }
    }
}
