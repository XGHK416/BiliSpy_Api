package com.project.xghk416.project.viewer.listInfo.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.xghk416.pojo.bili.BiliVideoPo;
import com.project.xghk416.pojo.bili.mapper.BiliVideoDao;
import com.project.xghk416.pojo.bili.mapper.BiliVideoRankDao;
import com.project.xghk416.pojo.dto.VideoListDto;
import com.project.xghk416.project.viewer.listInfo.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class IVideoService implements VideoService {
    @Autowired
    BiliVideoDao biliVideoDao;

    /**
     * 获取最后发布视频的时间
     * @param mid
     * @return
     */
    @Override
    public LocalDateTime getLastPublish(int mid) {
        return biliVideoDao.queryLastPublish(mid);
    }

    /**
     * 获取发布视频数
     * @param limit
     * @return
     */
    @Override
    public int getPublishCount(int mid,int limit) {
        return biliVideoDao.queryPublishCount(mid,limit);
    }

    /**
     * 分析视频分区
     * @param mid
     * @return
     */
    @Override
    public Map<String, Object> analysisSection(int mid) {
        try{
            Map<String,Object> result = new HashMap<>();
            String title_text = "单up主视频分布";
            List<Map<String, Object>> series_data = biliVideoDao.queryUploaderVideoSection(mid);
            List<String> legend_data = new ArrayList<>();
            for (Map<String, Object> item:
                    series_data){
                item.putIfAbsent("name", "未分区");
                legend_data.add(item.get("name").toString());
            }
            result.put("title_text",title_text);
            result.put("legend_data",legend_data);
            result.put("series_data",series_data);
            return result;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 分析发布时间段
     * @param mid
     * @return
     */
    @Override
    public Map<String, Object> analysisPublishTime(int mid) {
        try{
            List<LocalDateTime> publishDateList = biliVideoDao.queryPublishDate(mid);
            List<Map<String,Object>> seriesData = new ArrayList<>();
            String titleText = "视频发布时间段分析";
            List<Integer> legendData = new ArrayList<>();

            Map<String,Object> result = new HashMap<>();

            Map<Integer,Integer> itemMap = new HashMap<>();
            for (LocalDateTime item :
                    publishDateList) {
                itemMap.merge(item.getHour(), 1, Integer::sum);
            }
            for (Map.Entry<Integer,Integer> integerItem:
                    itemMap.entrySet()) {
                legendData.add(integerItem.getKey());
                Map<String,Object> temp = new HashMap<>();
                temp.put("name",integerItem.getKey());
                temp.put("value",integerItem.getValue());
                seriesData.add(temp);
            }

            result.put("title_text",titleText);
            result.put("legend_data",legendData);
            result.put("series_data",seriesData);

            return result;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取视频列表
     *
     * @param mid
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public List<BiliVideoPo> getList(int mid, int page, int pageSize) {
        try{
            Page<BiliVideoPo> videoPage = new Page<>(page,pageSize);
            videoPage.addOrder(OrderItem.desc("create_time"));
            IPage<BiliVideoPo> uploaderIpage = biliVideoDao.QueryVideoList(videoPage,mid);
            return uploaderIpage.getRecords();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<BiliVideoPo> getLatestInfo(int mid) {
        try{
            return biliVideoDao.queryLatestVideo(mid);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 获取单个视频的信息
     *
     * @param aid
     * @return
     */
    @Override
    public BiliVideoPo getInfo(int aid) {
        try{
            return biliVideoDao.queryLatestInfo(aid);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Map<String, Object>> getEvaluateByLimitDate(String startDate,String endDate,int aid) {
        try{
            return biliVideoDao.queryEstimateLimitDate(startDate, endDate, aid);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 推荐列表
     * @param tag
     * @param section
     * @return
     */
    @Override
    public VideoListDto getRecommendList(String tag, String section) {
        try{
            List<BiliVideoPo> biliVideoPoList = biliVideoDao.queryRecommentList(tag,section);
            return new VideoListDto(biliVideoPoList);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }
}
