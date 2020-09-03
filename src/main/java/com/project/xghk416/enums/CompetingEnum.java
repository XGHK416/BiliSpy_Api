package com.project.xghk416.enums;

import com.project.xghk416.pojo.bili.mapper.BiliUploaderDao;
import com.project.xghk416.pojo.bili.mapper.BiliUploaderRankDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Component
public enum CompetingEnum implements CompetingEnumInterface {
//    粉丝变化
    FANS {
        @Autowired
        @Override
        public Map<String, Object> getResult(int[] mids,int limit) {
            Map<String,Object> resultMap = new HashMap<>();
            for (int item :
                    mids) {
                System.out.println(item);
                List<Map<String,Object>> result = biliUploaderDao.QueryFansChange(item, limit);
                resultMap.put(""+item,result);
            }
            return resultMap;
        }
    },
//    排名
    RANK {
        @Override
        public Map<String, Object> getResult(int[] mids,int limit) {
            Map<String,Object> resultMap = new HashMap<>();
            for (int item :
                    mids) {
                List<Map<String,Object>> result = biliUploaderRankDao.queryRank(item,limit);
                resultMap.put(""+item,result);
            }
            return resultMap;
        }
    },
//    视频数
    VIDEO {
        @Override
        public Map<String, Object> getResult(int[] mids,int limit) {
            Map<String,Object> resultMap = new HashMap<>();
            for (int item :
                    mids) {
                List<Map<String,Object>> result = biliUploaderDao.queryVideoCount(item,limit);
                resultMap.put(""+item,result);
            }
            return resultMap;
        }
    };
    BiliUploaderDao biliUploaderDao;
    BiliUploaderRankDao biliUploaderRankDao;
    public void getDao(BiliUploaderDao uploaderDao,BiliUploaderRankDao uploaderRankDao){
        this.biliUploaderDao = uploaderDao;
        this.biliUploaderRankDao = uploaderRankDao;

    }




}
