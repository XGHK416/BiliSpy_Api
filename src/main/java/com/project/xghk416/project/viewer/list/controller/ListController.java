package com.project.xghk416.project.viewer.list.controller;

import com.project.xghk416.common.result.Result;
import com.project.xghk416.common.util.DateTimeUtil;
import com.project.xghk416.common.util.ResultUtil;
import com.project.xghk416.enums.ResultEnum;
import com.project.xghk416.pojo.bili.BiliSectionPo;
import com.project.xghk416.pojo.dto.UploaderListInfoDto;
import com.project.xghk416.pojo.dto.VideoListDto;
import com.project.xghk416.project.viewer.list.service.impl.IListService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "up主及其作品列表")
@RestController
@RequestMapping(value = "/list")
public class ListController {

    @Autowired
    IListService listService;

    @GetMapping(value = "/uploader")
    @ApiOperation(value = "获取uploader列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页数"),
            @ApiImplicitParam(name = "pageSize", value = "页大小"),
            @ApiImplicitParam(name = "selectId", value = "分区id"),
            @ApiImplicitParam(name = "date", value = "日期"),
    })
    public Result uploader(Integer page, Integer pageSize, Integer selectId, String date) {

        try {
            String endDate = DateTimeUtil.returnOneDateLimit(date,1);

            List<UploaderListInfoDto> uploaderList = listService.getUploaderList(page, pageSize,selectId,date, endDate);
            if (uploaderList != null) {
                return ResultUtil.success(uploaderList);
            } else {
                return ResultUtil.error(ResultEnum.UNKNOWN_ERROR.getCode(), ResultEnum.UNKNOWN_ERROR.getMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.error(ResultEnum.UNKNOWN_ERROR.getCode(), e.getMessage());
        }

    }

    @ApiOperation(value = "获取video列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页数"),
            @ApiImplicitParam(name = "pageSize", value = "页大小"),
            @ApiImplicitParam(name = "selectId", value = "分区id"),
            @ApiImplicitParam(name = "date", value = "日期"),
    })
    @GetMapping("/video")
    public Result video(Integer page, Integer pageSize,Integer selectId,String date){
        String startDate = DateTimeUtil.returnOneDateLimit(date,-1);
        try {
            List<VideoListDto> uploaderList = listService.getVideoList(page, pageSize,selectId,startDate,date);
            if (uploaderList != null) {
                return ResultUtil.success(uploaderList);
            } else {
                return ResultUtil.error(ResultEnum.UNKNOWN_ERROR.getCode(), ResultEnum.UNKNOWN_ERROR.getMsg());
            }
        } catch (Exception e) {
            return ResultUtil.error(ResultEnum.UNKNOWN_ERROR.getCode(), e.getMessage());
        }
    }

    @GetMapping(value = "/section")
    @ApiOperation(value = "获取分区")
    public Result getMainSection() {
        try {
            List<BiliSectionPo> sectionList = listService.getSection();
            if (sectionList != null) {
                return ResultUtil.success(sectionList);
            } else {
                return ResultUtil.error(ResultEnum.UNKNOWN_ERROR.getCode(), ResultEnum.UNKNOWN_ERROR.getMsg());
            }
        } catch (Exception e) {
            return ResultUtil.error(ResultEnum.UNKNOWN_ERROR.getCode(), e.getMessage());
        }

    }
}
