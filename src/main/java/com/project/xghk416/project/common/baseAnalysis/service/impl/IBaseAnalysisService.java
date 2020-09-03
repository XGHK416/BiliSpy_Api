package com.project.xghk416.project.common.baseAnalysis.service.impl;

import com.project.xghk416.common.util.CountVideoCountUtil;
import com.project.xghk416.common.util.RequestTemplateUtil;
import com.project.xghk416.common.util.compareMapUtil;
import com.project.xghk416.pojo.bili.BiliVideoPo;
import com.project.xghk416.pojo.bili.mapper.BiliDetectDao;
import com.project.xghk416.pojo.bili.mapper.BiliUploaderDao;
import com.project.xghk416.pojo.bili.mapper.BiliVideoDao;
import com.project.xghk416.project.common.baseAnalysis.service.BaseAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class IBaseAnalysisService implements BaseAnalysisService {

    @Autowired
    BiliUploaderDao biliUploaderDao;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    BiliVideoDao biliVideoDao;
    @Autowired
    BiliDetectDao biliDetectDao;

    /**
     * up主性别分布分析
     * @return
     */
    @Override
    public Map<String, Object> getGenderInfo() {
        try{
            String titleText = "B站up主性别分布";

            String[] strings = {"男","女","保密"};
            List<String> legendData = Arrays.asList(strings);

            List<Map<String,String>> query = biliUploaderDao.CountUploaderSex();

            Map<String,Object> result = new HashMap<>();
            result.put("title_text",titleText);
            result.put("legend_data",legendData);
            result.put("series_data",query);

            return result;
        }catch (Exception e){
            return null;
        }
    }

    /**
     * up主等级分布分析
     * @return
     */
    public Map<String, Object> getLevelInfo() {
        try{
            String titleText = "B站up主等级分布";

            List<String> legendData= biliUploaderDao.QueryLevelList();

            List<Map<String,String>> query = biliUploaderDao.CountUploaderLevel();

            Map<String,Object> result = new HashMap<>();
            result.put("title_text",titleText);
            result.put("legend_data",legendData);
            result.put("series_data",query);

            return result;
        }catch (Exception e){
            return null;
        }
    }

    /**
     * 前20粉丝数的up主
     * @return
     */
    @Override
    public Map<String, Object> getTop(String startDate,String endDate) {
        Map<String,Object> resultMap = new HashMap<>();
        String titleText = "粉丝数量前20的up主";
        List<String> xAxis = new ArrayList<>();
        List<String> seriesData = new ArrayList<>();
        try{
            List<Map<String,String>> query = biliUploaderDao.QueryTopFansUploader(startDate,endDate);
            for (Map<String, String> item: query
            ){
                xAxis.add(item.get("nick_name"));
                seriesData.add(item.get("follower"));
            }
            resultMap.put("title_text",titleText);
            resultMap.put("x_axis",xAxis);
            resultMap.put("series_data",seriesData);
            return resultMap;
        }catch (Exception e){
            return null;
        }
    }

    /**
     * up主粉丝数量级分析
     * @return
     */
    @Override
    public Map<String, Object> getLevel() {
        try{
            Map<String,Object> result = new HashMap<>();

            String titleText = "up主粉丝数量级分析";
            String[] strings= {"<100","100-1000","1000-10000","10000-100000",">100000"};
            List<String> legendData = Arrays.asList(strings);

            List<Map<String,String>> query = biliUploaderDao.QueryUploaderFansLevel();

            result.put("title_text",titleText);
            result.put("legend_data",legendData);
            result.put("series_data",query);

//            System.out.println(result);
            return result;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取topInfo
     * @return
     */
    @Override
    public Map<String,Object> getBaseInfo() {
        Map<String,Object> resultMap = new HashMap<>();
        int uploaderNum = biliDetectDao.queryDetectAllCount(0);
        int videoNum = biliDetectDao.queryDetectAllCount(1);
        int videoUpdateNum = biliDetectDao.queryTodayVideoCount();

        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startTime = LocalDateTime.parse("2020-01-29 12:00:00",df);
        Duration runTime  = Duration.between(startTime,LocalDateTime.now());


        resultMap.put("uploaderNum",uploaderNum);
        resultMap.put("videoNum",videoNum);
        resultMap.put("videoUpdateNum",videoUpdateNum);
        resultMap.put("runTimeDay",runTime.toDays());

        return resultMap;
    }

    /**
     * 获取video的分区的信息
     *
     * @return
     */
    @Override
    public Map<String, Object> getSection() {
        String titleText = "B站视频分区信息";
        Map<String, Object> resultMap = new HashMap<>();
        List<String> sectionList = new ArrayList<>();
        try {
            List<Map<String, Object>> tempResult = biliVideoDao.queryVideoSectionInfo();
            List<Map<String, Object>> sectionInfo = new ArrayList<>();
            int count = biliVideoDao.queryRecentCount();
            int tempCount = 0, limit = 0;
            for (Map<String, Object> item :
                    tempResult) {
                if (limit < 10) {
                    sectionList.add((String) item.get("name"));
                    sectionInfo.add(item);
                    tempCount += Integer.parseInt(item.get("value").toString());
                    limit++;
                } else {
                    Map<String, Object> otherSection = new HashMap<>();

                    otherSection.put("name", "其他");
                    otherSection.put("value", count - tempCount);

                    sectionList.add("其他");
                    sectionInfo.add(otherSection);
                    break;
                }
            }

            resultMap.put("title_text", titleText);
            resultMap.put("legend_data", sectionList);
            resultMap.put("series_data", sectionInfo);

            return resultMap;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 词云
     *
     * @return
     */
    @Override
    public Map<String, Object> getTagWorldCloud() {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            List<String> dynamicList = biliVideoDao.queryVideoDynamic();
            List<Map<String, Object>> result = CountVideoCountUtil.formatLimit(CountVideoCountUtil.anaTags(dynamicList));
//            for (String item :
//                    dynamicList) {
//                String[] result = item.split("#");
//                for (String childItem :
//                        result) {
//                    if (!"".equals(childItem) && childItem.length() < 20 && !" ".equals(childItem)) {
//                        if (dynamicCount.containsKey(childItem)) {
//                            int count = dynamicCount.get(childItem);
//                            dynamicCount.put(childItem, count + 1);
//                        } else {
//                            dynamicCount.put(childItem, 1);
//                        }
//                    }
//                }
//            }
//            Map<String, Integer> treeMap = new TreeMap<>(new compareMapUtil(dynamicCount));
//            List<Map<String, Object>> formatResult = new ArrayList<>();
//
//            treeMap.putAll(dynamicCount);
//            int limit = 0;
//            for (Map.Entry<String, Integer> entry : treeMap.entrySet()) {
//                if (limit <= 27) {
//                    Map<String, Object> temp = new HashMap<>();
//                    temp.put("name", entry.getKey());
//                    temp.put("value", entry.getValue());
//                    formatResult.add(temp);
//                    limit++;
//                } else {
//                    break;
//                }
//            }

            resultMap.put("cloud_data", result);

            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 数据库中关于视频爬取的信息
     *
     * @return
     */
    @Override
    public Map<String, Object> getSpider(int limit) {
        String titleText = "B站7日爬取视频及总视频数";
        Map<String, Object> resultMap = new HashMap<>();

        try {
            List<Map<String, Object>> queryDailyCount = biliVideoDao.queryEveryDayVideoCount(limit);

            List<String> xAxis = new ArrayList<>();
            List<Long> seriesTotal = new ArrayList<>();
            List<Long> seriesCurrent = new ArrayList<>();
            for (Map<String, Object> item :
                    queryDailyCount) {
                xAxis.add(item.get("name").toString());
                seriesTotal.add(biliVideoDao.queryAllVideoCount(item.get("name").toString()));
                seriesCurrent.add((Long) item.get("value"));
            }

            resultMap.put("title_text", titleText);
            resultMap.put("x_axis", xAxis);
            resultMap.put("series_total", seriesTotal);
            resultMap.put("series_current", seriesCurrent);

            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;

        }
    }

}
