package com.project.xghk416.project.viewer.list.service;

import com.project.xghk416.pojo.bili.BiliSectionPo;
import com.project.xghk416.pojo.bili.BiliVideoPo;
import com.project.xghk416.pojo.dto.UploaderListInfoDto;
import com.project.xghk416.pojo.dto.VideoListDto;

import java.util.List;

public interface ListService {
    List<UploaderListInfoDto> getUploaderList(int page, int pageSize, int selectId, String startDate, String endDate);

    List<VideoListDto> getVideoList(Integer page, Integer pageSize, Integer selectId, String startDate, String endDate);

    List<BiliSectionPo> getSection();


}
