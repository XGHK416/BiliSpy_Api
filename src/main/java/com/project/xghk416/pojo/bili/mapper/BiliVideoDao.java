package com.project.xghk416.pojo.bili.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.xghk416.pojo.bili.BiliUploaderPo;
import com.project.xghk416.pojo.bili.BiliVideoPo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
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
public interface BiliVideoDao extends BaseMapper<BiliVideoPo> {
    @Select("SELECT tname as name,count(*) as value from bili_video WHERE last_update > DATE_SUB(curdate(),INTERVAL 1 DAY) AND last_update <DATE_SUB(curdate(),INTERVAL 0 DAY)  GROUP BY tid ORDER BY value desc")
    @MapKey("name")
    List<Map<String,Object>> queryVideoSectionInfo();

    @Select("SELECT count(*) from bili_video WHERE last_update > DATE_SUB(curdate(),INTERVAL 1 DAY) AND last_update <DATE_SUB(curdate(),INTERVAL 0 DAY) ")
    @ResultType(Integer.class)
    Integer queryRecentCount();

    @Select("SELECT  DATE_FORMAT(create_time,'%Y-%m-%d') as name,count(*) as value FROM bili_detect WHERE detect_type=1 AND create_time > DATE_SUB(curdate(),INTERVAL #{limit} DAY) AND create_time <DATE_SUB(curdate(),INTERVAL 1 DAY)  GROUP BY name")
    @MapKey("name")
    List<Map<String,Object>> queryEveryDayVideoCount(@Param("limit") int limit);

    @Select("SELECT count(*) FROM bili_detect WHERE detect_type = 1  And DATE_FORMAT(create_time,'%Y,%m,%d') <= #{date}")
    Long queryAllVideoCount(@Param("date") String date);

    @Select("SELECT dynamic FROM bili_video WHERE last_update > DATE_SUB(curdate(),INTERVAL 7 DAY) AND last_update <DATE_SUB(curdate(),INTERVAL 0 DAY) GROUP BY video_id")
    @ResultType(String.class)
    List<String> queryVideoDynamic();

    @Select("select tname as name,count(*) as value from (SELECT tname from bili_video WHERE author_mid = #{mid} GROUP BY video_id) as result GROUP BY tname ORDER By value Desc")
    @MapKey("name")
    //用户视频的分区分析
    List<Map<String, Object>> queryUploaderVideoSection(@Param("mid") int mid);

    @Select("SELECT count(*) from (select video_id from bili_video WHERE author_mid = #{mid} AND DATEDIFF(NOW(),create_time)<=#{limit} GROUP BY video_id) as result")
    @ResultType(Integer.class)
//    近期发布视频数
    Integer queryPublishCount(int mid,int limit);

    @Select("select create_time from bili_video WHERE author_mid = #{mid} GROUP BY video_id ORDER BY create_time DESC limit 1")
    @ResultType(LocalDateTime.class)
    LocalDateTime queryLastPublish(int mid);

    @Select("select create_time from bili_video WHERE author_mid = #{mid} GROUP BY video_id")
    @ResultType(LocalDateTime.class)
    //一个up主他的视频的发布时间统计
    List<LocalDateTime> queryPublishDate(int mid);

    // TODO: 2020/3/13 dao合并
    @Select("SELECT * from (select * from bili_video WHERE author_mid = #{mid} ORDER BY last_update DESC limit 100000) as result GROUP BY video_id")
    IPage<BiliVideoPo> QueryVideoList(Page<?> page,int mid);

    @Select("SELECT\n" +
            "\tbili_video.*\n" +
            "FROM\n" +
            "\tbili_video\n" +
            "\tLEFT JOIN bili_video_rank ON bili_video.video_id = bili_video_rank.video_id \n" +
            "WHERE\n" +
            "\tbili_video.last_update>=#{startDate} And\tbili_video.last_update<#{endDate}\n" +
            "\tAND bili_video_rank.section IN ( SELECT tname FROM bili_section WHERE bili_section.parent_id = #{selectId} )\n" +
            "GROUP BY bili_video.id\n")
    IPage<BiliVideoPo> queryVideoList(Page<?> page, int selectId, String startDate,String endDate);

    @Select("SELECT\n" +
            "bili_video.* \n" +
            "FROM\n" +
            "bili_video\n" +
            "Where bili_video.last_update >= #{startDate} \n" +
            "AND bili_video.last_update < #{endDate} \n" +
            "GROUP BY\n" +
            "bili_video.id ")
    IPage<BiliVideoPo> queryVideoDefaultList(Page<?> page, String startDate,String endDate);

    @Select("select * from bili_video WHERE last_update=(SELECT last_update from bili_video WHERE video_id = #{aid} ORDER BY last_update DESC LIMIT 1) and video_id = #{aid}")
    BiliVideoPo queryLatestInfo(int aid);

//    @Select("select last_update,video_view,video_favorite,coins,video_like from bili_video WHERE last_update>= #{startDate} and last_update<= #{endDate} AND video_id = #{aid}  GROUP BY last_update")
    @Select("select last_update,video_view,video_favorite,coins,video_like from bili_video WHERE video_id = #{aid} ")
    @MapKey("last_update")
    // TODO: 2020/5/27 数据库语句完善 1
    List<Map<String,Object>> queryEstimateLimitDate(String startDate,String endDate,@Param("aid") int aid);

    @Select("SELECT * from bili_video where tname = #{section} and dynamic LIKE '%${tag}%' GROUP BY video_id LIMIT 4 ")
    List<BiliVideoPo> queryRecommentList(String tag,String section);

    @Select("SELECT * from bili_video where  video_id = ( SELECT video_id from bili_video WHERE author_mid = #{mid} ORDER BY video_id DESC LIMIT 0,1) ORDER BY last_update")
//    最新发布的视频
    List<BiliVideoPo> queryLatestVideo(int mid);

//    计算作品的某一状态的平均值
    @Select("SELECT\n" +
            "\tAVG( ${type} ) \n" +
            "FROM\n" +
            "\t( SELECT * FROM bili_video WHERE video_id IN ( SELECT video_id FROM bili_video WHERE bili_video.author_mid = #{mid} GROUP BY video_id ) ) AS v \n" +
            "WHERE\n" +
            "\tNOT EXISTS ( SELECT * FROM bili_video AS b WHERE v.video_id = b.video_id AND v.last_update < b.last_update )")
    int queryAvgVideoStatus(int mid,String type);

    @Select("SELECT\n" +
            "\tdynamic\n" +
            "FROM\n" +
            "\t( SELECT * FROM bili_video WHERE video_id IN ( SELECT video_id FROM bili_video WHERE bili_video.author_mid = #{mid} GROUP BY video_id ) ) AS v \n" +
            "WHERE\n" +
            "\tNOT EXISTS ( SELECT * FROM bili_video AS b WHERE v.video_id = b.video_id AND v.last_update < b.last_update )")
    List<String> queryTags(int mid);

    @Select("SELECT\n" +
            "\tcount( * ) count,\n" +
            "\tMONTH ( create_time ) month \n" +
            "FROM\n" +
            "\t( SELECT * FROM bili_video WHERE video_id IN ( SELECT video_id FROM bili_video WHERE bili_video.author_mid = #{mid} GROUP BY video_id ) ) AS v \n" +
            "WHERE\n" +
            "\tNOT EXISTS ( SELECT * FROM bili_video AS b WHERE v.video_id = b.video_id AND v.last_update < b.last_update ) \n" +
            "GROUP BY\n" +
            "\tMONTH ( create_time ) \n" +
            "ORDER BY\n" +
            "\tMONTH ( create_time ) ASC;")
    List<Map<String,Integer>> queryMonthlyCount(int mid);

    @Select("SELECT\n" +
            "\t* \n" +
            "FROM\n" +
            "\t(\n" +
            "\tSELECT\n" +
            "\t\t*\n" +
            "\tFROM\n" +
            "\t\tbili_video\n" +
            "\tWHERE\n" +
            "\t\tbili_video.video_id IN ( SELECT sc.video_id FROM ( SELECT video_id FROM bili_video_rank WHERE uploader_id = #{mid} GROUP BY video_id ORDER BY rank DESC LIMIT 5 ) AS sc ) \n" +
            "\tORDER BY\n" +
            "\t\tbili_video.last_update DESC \n" +
            "\t) r \n" +
            "WHERE\n" +
            "\tNOT EXISTS ( SELECT * FROM bili_video AS b WHERE r.video_id = b.video_id AND r.last_update < b.last_update )\n" +
            "ORDER BY r.create_time ASC")
    List<BiliVideoPo> queryRecentWork(int mid);

    @Select("SELECT\n" +
            "\ttid,tname \n" +
            "FROM\n" +
            "\t(\n" +
            "\tSELECT\n" +
            "\t\t* \n" +
            "\tFROM\n" +
            "\t\tbili_video \n" +
            "\tWHERE\n" +
            "\t\tbili_video.video_id IN (\n" +
            "\t\tSELECT\n" +
            "\t\t\tsc.video_id \n" +
            "\t\tFROM\n" +
            "\t\t\t( SELECT video_id FROM bili_video_rank WHERE uploader_id = #{mid} GROUP BY video_id ORDER BY rank DESC ) AS sc \n" +
            "\t\t) \n" +
            "\tORDER BY\n" +
            "\t\tbili_video.last_update DESC \n" +
            "\t) r \n" +
            "WHERE\n" +
            "\tNOT EXISTS ( SELECT * FROM bili_video AS b WHERE r.video_id = b.video_id AND r.last_update < b.last_update ) \n" +
            "GROUP BY\n" +
            "\tr.tid\n" +
            "ORDER BY\n" +
            "\tr.create_time ASC")
    List<Map<String,Object>> queryRecentWorkSection(int mid);

    @Select("SELECT * FROM bili_video WHERE MATCH ( `dynamic`, `video_title` ) AGAINST ( #{key} IN BOOLEAN MODE ) GROUP BY video_id Order By ${order} DESC")
    IPage<BiliVideoPo> QueryVideoListByLike(Page<?> page, String key,String order);


}
