package com.project.xghk416.project.viewer.list.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.xghk416.pojo.bili.BiliSectionPo;
import com.project.xghk416.pojo.bili.BiliUploaderPo;
import com.project.xghk416.pojo.bili.BiliVideoPo;
import com.project.xghk416.pojo.bili.mapper.*;
import com.project.xghk416.pojo.dto.UploaderListInfoDto;
import com.project.xghk416.pojo.dto.VideoListDto;
import com.project.xghk416.project.viewer.list.service.ListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IListService implements ListService {

    @Autowired
    BiliUploaderDao biliUploaderDao;
//    @Autowired
//    BiliOfficialDao biliOfficialDao;
//    @Autowired
//    BiliUvCountDao biliUvCountDao;
    @Autowired
    BiliVideoDao biliVideoDao;
//    @Autowired
//    BiliUploaderRankDao biliUploaderRankDao;
    @Autowired
    BiliSectionDao biliSectionDao;

    /**
     * 获取up主列表
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public List<UploaderListInfoDto> getUploaderList(int page, int pageSize, int selectId, String startDate, String endDate) {
        try{
            Page<BiliUploaderPo> uploaderPage = new Page<>(page,pageSize);
            uploaderPage.addOrder(OrderItem.desc("bili_uploader.follower"));
            IPage<BiliUploaderPo> uploaderIpage;

            if (selectId!=0){
                uploaderIpage = biliUploaderDao.QueryUploaderList(uploaderPage,selectId,startDate,endDate);
            }
            else {
                uploaderIpage = biliUploaderDao.QueryUploaderDefaultList(uploaderPage,startDate,endDate);
            }
            UploaderListInfoDto uploaderListInfoDto= new UploaderListInfoDto(uploaderIpage.getRecords());
            return uploaderListInfoDto.getList();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 获取视频列表
     * @param page
     * @param pageSize
     * @param selectId
     * @param
     * @return
     */
    // TODO: 2020/3/13 合并
    @Override
    public List<VideoListDto> getVideoList(Integer page, Integer pageSize, Integer selectId,String startDate,String endDate) {
        try{
            Page<BiliVideoPo> videoPage = new Page<>(page,pageSize);
            videoPage.addOrder(OrderItem.desc("bili_video.video_view"));
            IPage<BiliVideoPo> videoIpage;
            if (selectId!=0){
                videoIpage = biliVideoDao.queryVideoList(videoPage,selectId, startDate,endDate);
            }
            else videoIpage = biliVideoDao.queryVideoDefaultList(videoPage, startDate,endDate);
            List<BiliVideoPo>list =  videoIpage.getRecords();
            return new VideoListDto(list).getList();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<BiliSectionPo> getSection() {
        QueryWrapper<BiliSectionPo> sectionPoQueryWrapper = new QueryWrapper<>();
        sectionPoQueryWrapper.select("*");
        sectionPoQueryWrapper.isNull("parent_id");
        biliSectionDao.selectList(sectionPoQueryWrapper);
        return biliSectionDao.selectList(sectionPoQueryWrapper);
    }


}