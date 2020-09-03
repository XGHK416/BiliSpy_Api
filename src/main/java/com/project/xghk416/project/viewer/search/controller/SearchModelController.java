package com.project.xghk416.project.viewer.search.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.xghk416.common.result.Result;
import com.project.xghk416.common.util.ResultUtil;
import com.project.xghk416.enums.api.UploaderApiEnum;
import com.project.xghk416.enums.api.VideoApiEnum;
import com.project.xghk416.pojo.bili.BiliUploaderPo;
import com.project.xghk416.pojo.bili.BiliVideoPo;
import com.project.xghk416.project.viewer.search.service.impl.ISearchModelService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("/search")
@RestController
@Api("搜索模块的api")
public class SearchModelController {

    @Autowired
    ISearchModelService searchModelService;

    @GetMapping(value = "/uploader")
    public Result searchUploader(String key,int page,int pageSize,String searchType,String order,String orderSort){
        Map<String,Object> result;
        String orderForBili = "";
        int orderSortForBili = 0;


        if ("system".equals(searchType)){
            Page<BiliUploaderPo> pageSetting = new Page<>(page,pageSize);

            result = searchModelService.searchUploaderFromSystem(pageSetting,key,order,orderSort);
        }else {

            if ("follower".equals(order)){
                orderForBili = "fans";
            }
            if ("ASC".equals(orderSort)){
                orderSortForBili = 1;
            }

            String url = UploaderApiEnum.UPLOADER_SEARCH.getApiAddress()+"page="+page+"&keyword="+key+"&order_sort="+orderSortForBili+"&order="+orderForBili;
            result = searchModelService.searchUploaderFromBili(url);
        }


        return ResultUtil.success(result);

    }

    @GetMapping(value = "/video")
    public Result searchVideo(String key,int page,int pageSize,String searchType,String order){
        Map<String,Object> result;
        String orderForBili = "";

        if ("system".equals(searchType)){
            Page<BiliVideoPo> pageSetting = new Page<>(page,pageSize);

            result = searchModelService.searchVideoFromSystem(pageSetting,key,order);
        }else {

            if ("video_view".equals(order)){
                orderForBili = "click";
            }else if ("create_time".equals(order)){
                orderForBili = "pubdate";
            }else if ("video_favorite".equals(order)){
                orderForBili = "stow";
            }

            String url = VideoApiEnum.VIDEO_LIST.getApiAddress()+"page="+page+"&keyword="+key+"&order="+orderForBili;
            result = searchModelService.searchUploaderFromBili(url);
        }


        return ResultUtil.success(result);
    }
}
