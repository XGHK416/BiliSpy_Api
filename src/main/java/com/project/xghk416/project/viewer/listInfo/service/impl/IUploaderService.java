package com.project.xghk416.project.viewer.listInfo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.xghk416.pojo.bili.BiliUploaderPo;
import com.project.xghk416.pojo.bili.mapper.BiliUploaderDao;
import com.project.xghk416.pojo.bili.mapper.BiliUploaderRankDao;
import com.project.xghk416.pojo.detect.DetectUploaderStatePo;
import com.project.xghk416.pojo.detect.mapper.DetectUploaderStateDao;
import com.project.xghk416.project.viewer.listInfo.service.UploaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.Query;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class IUploaderService implements UploaderService {
    @Autowired
    BiliUploaderDao biliUploaderDao;
    @Autowired
    BiliUploaderRankDao biliUploaderRankDao;
    @Autowired
    DetectUploaderStateDao detectUploaderStateDao;

    /**
     * 获取up主基本信息
     * @return
     */
    @Override
    public BiliUploaderPo getBaseInfo(int mid) {
        try {
//            QueryWrapper<BiliUploaderPo> uploaderWrapper = new QueryWrapper<>();
//            uploaderWrapper.eq("user_id",mid).eq("DATEDIFF(NOW(),last_update)",1);
            return biliUploaderDao.selectOne(mid);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 分析up主所属区
     * @param mid
     * @return
     */
    @Override
    public String getBelongSection(int mid) {
        // TODO: 2020/3/1 改进算法
        try{
            //            int videoCount=0;
//            for (Map<String, Object> item :
//                    resultSection) {
//                videoCount+=Integer.valueOf(item.get("value").toString());
//            }
            return biliUploaderRankDao.queryUploaderSection(mid);
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public int getDetectTime(int mid,String userId) {

        return detectUploaderStateDao.haveDetect(mid,userId);
    }

    @Override
    public Map<String, Object> getChange(int mid, int type) {
        try{
            Map<String,Object> result = new HashMap<>();
            String titleText = "粉丝变化量";
            List<String> xAxis = new ArrayList<>();
            List<Map<String,Object>> fans = biliUploaderDao.QueryFansChange(mid, type);
            for (Map<String, Object> item :
                    fans) {
                item.put("name",item.get("name").toString().split(" ")[0]);
                xAxis.add(item.get("name").toString());
            }
            result.put("title_text",titleText);
            result.put("x_axis",xAxis);
            result.put("series_data",fans);
            return result;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 获取竞品数据
     * @param mids
     * @param type 需要的数据列
     * @return
     */
    @Override
    public Map<String, Object> getCompetingData(int[] mids, String type, int limit) {
        try{
            Map<String,Object> resultMap = new HashMap<>();
            for (int item :
                    mids) {
                switch (type){
                    case "FANS":{
                        resultMap.put(item+"",biliUploaderDao.QueryFansChange(item, limit));
                        break;
                    }
                    case ("RANK"):{
                        resultMap.put(item+"",biliUploaderRankDao.queryRank(item,limit));
                        break;
                    }
                    case ("VIDEO"): {
                        resultMap.put(item+"",biliUploaderDao.queryVideoCount(item,limit));
                        break;
                    }
                    default:break;
                }
            }
            return resultMap;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取名称
     * @param mids
     * @return
     */
    @Override
    public List<Map<String, Object>> getNickName(int[] mids) {
        return biliUploaderDao.queryNickName(mids);
    }

    /**
     * 按照关键字以粉丝数量进行排序查找参照对象
     * @param page
     * @param pageSize
     * @param key
     * @return
     */
    @Override
    public Map<String, Object> getListByKey(int page, int pageSize, String key) {
        Map<String,Object> result = new HashMap<>();
        try {
            Page<BiliUploaderPo> uploaderPage = new Page<>(page, pageSize);
//            int count = biliUploaderDao.QueryUploaderListCountByLike(key);
            IPage<BiliUploaderPo> uploaderIpage = biliUploaderDao.QueryUploaderListByLikeForCompeting(uploaderPage,key);

            result.put("list",uploaderIpage.getRecords());
            return result;

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 自动推荐按照粉丝数量和section进行查找参照对象
     * @param sectionName
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public Map<String, Object> getRecommend(String sectionName, int page, int pageSize,int mid) {
        Map<String,Object> result = new HashMap<>();
        try {
//            result.put("count",biliUploaderDao.queryRecommendCount(sectionName));
            result.put("count",50);
            Page<BiliUploaderPo> recommendPage = new Page<>(page,pageSize);
            recommendPage.addOrder(OrderItem.desc("bili_uploader.follower"));
            IPage<BiliUploaderPo> recommendIpage = biliUploaderDao.selectRecommend(recommendPage,sectionName,mid);
            result.put("list",recommendIpage.getRecords());
            return result;
        }catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }

    }


}
