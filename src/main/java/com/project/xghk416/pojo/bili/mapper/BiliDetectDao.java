package com.project.xghk416.pojo.bili.mapper;

import com.project.xghk416.pojo.bili.BiliDetectPo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author XGHK416
 * @since 2020-02-17
 */
@Component
public interface BiliDetectDao extends BaseMapper<BiliDetectPo> {
    @Select("select count(*) from bili_detect WHERE DATEDIFF(NOW(),create_time)=0 AND detect_type = #{type}")
    int queryDetectDailyCount(int type);

    @Select("select count(*) from bili_detect Where detect_type = #{type} and useable = 1")
    int queryDetectAllCount(int type);

    @Select("select DATE_FORMAT(create_time,'%Y-%m-%d') as date,count(*) as count from bili_detect WHERE DATEDIFF(NOW(),create_time)<7 AND detect_type = #{type} \n" +
            "GROUP BY DATE_FORMAT(create_time,'%Y-%m-%d')")
    List<Map<String,Object>> queryEveryCount(int type);


    @Select("select count(*) from bili_detect where DATEDIFF(NOW(),update_time)<1 and detect_type = 1")
    int queryTodayVideoCount();
}
