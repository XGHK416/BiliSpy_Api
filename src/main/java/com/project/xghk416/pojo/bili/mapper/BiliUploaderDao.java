package com.project.xghk416.pojo.bili.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.xghk416.pojo.bili.BiliUploaderPo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
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
public interface BiliUploaderDao extends BaseMapper<BiliUploaderPo> {

    @Select("SELECT\n" +
            "* \n" +
            "FROM\n" +
            "bili_uploader \n" +
            "WHERE\n" +
            "user_id IN (\n" +
            "SELECT\n" +
            "* \n" +
            "FROM\n" +
            "( SELECT uploader_id FROM bili_uploader_rank WHERE DATEDIFF( now( ), create_time ) = 1 AND section = #{tname} ORDER BY score DESC LIMIT 50 ) re \n" +
            ") \n" +
            "AND DATEDIFF( now( ), last_update ) = 1 \n" +
            "AND user_id not in (#{mid}) \n"+
            "GROUP BY\n" +
            "user_id")
    IPage<BiliUploaderPo> selectRecommend(Page<?> page,String tname,int mid);

    @Select("SELECT\n" +
            "count(*)\n" +
            "FROM\n" +
            "bili_uploader\n" +
            "INNER JOIN bili_uploader_rank ON bili_uploader.user_id = bili_uploader_rank.uploader_id\n" +
            "Where DATEDIFF(now(),bili_uploader.last_update) = 1\n" +
            "AND\n" +
            "bili_uploader_rank.section = #{tname}")
    int queryRecommendCount(String tname);

    @Select("SELECT * from bili_uploader WHERE user_id =#{mid}  ORDER BY last_update Desc LIMIT 1")
    BiliUploaderPo selectOne(int mid);

    @Select("SELECT sex as name,count(*) as value from bili_uploader WHERE last_update > DATE_SUB(curdate(),INTERVAL 1 DAY) AND last_update <DATE_SUB(curdate(),INTERVAL 0 DAY) GROUP BY bili_uploader.sex")
    @MapKey("name")
    List<Map<String,String>> CountUploaderSex();

    @Select("SELECT `level` from bili_uploader WHERE DATEDIFF(NOW(),last_update) = 1 GROUP BY `level`")
    @ResultType(String.class)
    List<String> QueryLevelList();

    @Select("SELECT level as name,count(*) as value from bili_uploader WHERE last_update > DATE_SUB(curdate(),INTERVAL 1 DAY) AND last_update <DATE_SUB(curdate(),INTERVAL 0 DAY) GROUP BY bili_uploader.level")
    @MapKey("name")
    List<Map<String,String>> CountUploaderLevel();

    @Select("SELECT nick_name,follower,last_update FROM bili_uploader WHERE last_update>#{startDate} AND last_update<=#{endDate} ORDER BY follower DESC LIMIT 0,20")
    @MapKey("nick_name")
    List<Map<String,String>> QueryTopFansUploader(String startDate,String endDate);

    @Select("SELECT\n" +
            "\tELT(\n" +
            "\t\tINTERVAL ( bili_uploader.follower, 0, 100, 1000, 10000, 100000 ),\n" +
            "\t\t'<100',\n" +
            "\t\t'100-1000',\n" +
            "\t\t'1000-10000',\n" +
            "\t\t'10000-100000',\n" +
            "\t\t'>100000' \n" +
            "\t) AS name,\n" +
            "\tcount( bili_uploader.follower ) AS \n" +
            "value\n" +
            "\t\n" +
            "FROM\n" +
            "\tbili_uploader \n" +
            "WHERE\n" +
            "\tlast_update > DATE_SUB( curdate( ), INTERVAL 1 DAY ) \n" +
            "\tAND last_update < DATE_SUB( curdate( ), INTERVAL 0 DAY ) \n" +
            "GROUP BY\n" +
            "name")
    @MapKey("name")
    List<Map<String,String>> QueryUploaderFansLevel();

    @Select("SELECT\n" +
            "bili_uploader.id,\n" +
            "bili_uploader.user_id,\n" +
            "bili_uploader.nick_name,\n" +
            "bili_uploader.`profile`,\n" +
            "bili_uploader.sex,\n" +
            "bili_uploader.`level`,\n" +
            "bili_uploader.sign,\n" +
            "bili_uploader.silence,\n" +
            "bili_uploader.vip,\n" +
            "bili_uploader.birthday,\n" +
            "bili_uploader.video_count,\n" +
            "bili_uploader.follower,\n" +
            "bili_uploader.following,\n" +
            "bili_uploader.last_update\n" +
            "FROM\n" +
            "bili_uploader\n" +
            "LEFT JOIN bili_uploader_rank ON bili_uploader.user_id = bili_uploader_rank.uploader_id\n" +
            "WHERE\n" +
            "bili_uploader_rank.section IN (SELECT tname from bili_section WHERE bili_section.parent_id=#{selectId}) AND\n" +
            "bili_uploader.last_update > #{startDate} AND\n" +
            "bili_uploader.last_update < #{endDate}\n" +
            "GROUP BY user_id\n")
    IPage<BiliUploaderPo> QueryUploaderList(Page<?> page, int selectId, String startDate,String endDate);

    @Select("SELECT * FROM bili_uploader WHERE bili_uploader.last_update > #{startDate} AND bili_uploader.last_update < #{endDate} GROUP BY user_id")
    IPage<BiliUploaderPo> QueryUploaderDefaultList(Page<?> page, String startDate,String endDate);

    @Select("SELECT\n" +
            "*\n" +
            "FROM\n" +
            "(\n" +
            "SELECT\n" +
            "*\n" +
            "FROM\n" +
            "bili_uploader \n" +
            "WHERE\n" +
            "user_id IN ( SELECT bili_uploader.user_id FROM bili_uploader WHERE MATCH ( bili_uploader.nick_name ) AGAINST ( #{key} IN BOOLEAN MODE ) ) \n" +
            ") re \n" +
            "WHERE\n" +
            "NOT EXISTS ( SELECT * FROM bili_uploader AS b WHERE b.user_id = re.user_id AND re.last_update < b.last_update )\n"+
            "Order By re.${order} ${orderSort}")
    IPage<BiliUploaderPo> QueryUploaderListByLike(Page<?> page,String key,String order,String orderSort);
    @Select("SELECT\n" +
            "*\n" +
            "FROM\n" +
            "(\n" +
            "SELECT\n" +
            "*\n" +
            "FROM\n" +
            "bili_uploader \n" +
            "WHERE\n" +
            "user_id IN ( SELECT bili_uploader.user_id FROM bili_uploader WHERE MATCH ( bili_uploader.nick_name ) AGAINST ( #{key} IN BOOLEAN MODE ) ) \n" +
            ") re \n" +
            "WHERE\n" +
            "NOT EXISTS ( SELECT * FROM bili_uploader AS b WHERE b.user_id = re.user_id AND re.last_update < b.last_update )\n"+
            "Order By re.follower DESC")
    IPage<BiliUploaderPo> QueryUploaderListByLikeForCompeting(Page<?> page,String key);

    @Select({
            "<script>" +
                    "select user_id as id,nick_name as name from bili_uploader where user_id IN " +
                    "<foreach item = 'item' index = 'index' collection = 'mids' open='(' separator=',' close=')'>" +
                    "#{item}" +
                    "</foreach>"+
                    " AND DATEDIFF(NOW(),last_update)=1"+
                    "</script>"})
    List<Map<String,Object>> queryNickName(@Param("mids") int[] mids);

    @Select("select count(*) from bili_uploader where DateDiff(now(),last_update) = 1 And nick_name LIKE '%${key}%'")
    int QueryUploaderListCountByLike(@Param(value = "key") String key);

    @Select("select DATE_FORMAT(last_update,'%Y-%m-%d') as name ,follower as value from bili_uploader WHERE user_id = #{mid} AND DATEDIFF(NOW(),last_update)<=#{limit}")
//    查询粉丝根据时间
    List<Map<String,Object>> QueryFansChange(int mid ,int limit);


    @Select("select video_count as value,DATE_FORMAT(last_update,'%Y-%m-%d') as name from bili_uploader WHERE user_id = #{mid} AND DATEDIFF(NOW(),last_update)<=#{limit}")
    List<Map<String,Object>> queryVideoCount(int mid,int limit);

    @Select("SELECT\n" +
            "bili_uploader.user_id,\n" +
            "count(bili_uploader.user_id) as count,\n" +
            "bili_uploader.nick_name,\n" +
            "bili_uploader.`level`,\n" +
            "bili_uploader.video_count,\n"+
            "bili_uploader.follower,\n" +
            "bili_uploader.following,\n" +
            "bili_detect.create_id,\n"+
            "DATE_FORMAT(bili_detect.create_time,'%Y-%m-%d') as create_time\n" +
            "FROM\n" +
            "bili_uploader\n" +
            "INNER JOIN bili_detect ON bili_detect.detect_id = bili_uploader.user_id\n" +
            "WHERE\n" +
            "MATCH(bili_uploader.nick_name) Against(#{key})\n" +
            "GROUP BY bili_uploader.user_id")
    IPage<Map<String,Object>> queryDetectUploaderInfo(Page<?> page,String key);

    @Select("select count(*) from(\n" +
            "SELECT\n" +
            "bili_uploader.user_id\n" +
            "FROM\n" +
            "bili_uploader\n" +
            "INNER JOIN bili_detect ON bili_detect.detect_id = bili_uploader.user_id\n" +
            "WHERE\n" +
            "MATCH(bili_uploader.nick_name) Against('明日')\n" +
            "GROUP BY bili_uploader.user_id) re")
    int queryDetectUploaderCount(String key);

    @Select("SELECT\n" +
            "bili_uploader.user_id,\n" +
            "Count(bili_uploader.user_id) AS count,\n" +
            "bili_uploader.nick_name,\n" +
            "bili_uploader.`level`,\n" +
            "bili_uploader.follower,\n" +
            "bili_uploader.following,\n" +
            "bili_detect.create_id,\n"+
            "DATE_FORMAT(bili_detect.create_time,'%Y-%m-%d') as create_time,\n" +
            "bili_uploader.video_count\n" +
            "FROM\n" +
            "bili_uploader\n" +
            "INNER JOIN bili_detect ON bili_detect.detect_id = bili_uploader.user_id\n" +
            "WHERE\n" +
            "bili_uploader.last_update >#{today} AND\n" +
            "bili_uploader.video_count=0 AND\n" +
            "bili_uploader.`level` IN('Level0','Level1','Level2') AND\n" +
            "bili_uploader.follower<10000 AND\n" +
            "bili_detect.useable=1 \n"+
            "GROUP BY\n" +
            "bili_uploader.user_id")
    IPage<Map<String,Object>> queryDetectUnusable(Page<?> page,String today);

    @Select("SELECT\n" +
            "count( * ) \n" +
            "FROM\n" +
            "(\n" +
            "SELECT\n" +
            "bili_uploader.user_id \n" +
            "FROM\n" +
            "bili_uploader\n" +
            "INNER JOIN bili_detect ON bili_detect.detect_id = bili_uploader.user_id \n" +
            "WHERE\n" +
            "bili_uploader.last_update >#{today}\n" +
            "AND bili_uploader.video_count = 0 \n" +
            "AND bili_uploader.`level` IN ( 'Level0', 'Level1', 'Level2' ) \n" +
            "AND bili_uploader.follower < 10000 \n" +
            "AND bili_detect.useable = 1 \n" +
            "GROUP BY\n" +
            "bili_uploader.user_id \n" +
            ") re")
    int queryDetectUnusableCount(String today);

    @Select("UPDATE bili_detect \n" +
            "SET bili_detect.useable = 0 \n" +
            "WHERE\n" +
            "bili_detect.detect_id IN (\n" +
            "SELECT\n" +
            "re.user_id \n" +
            "FROM\n" +
            "(\n" +
            "SELECT\n" +
            "bili_uploader.user_id \n" +
            "FROM\n" +
            "bili_uploader\n" +
            "INNER JOIN bili_detect ON bili_detect.detect_id = bili_uploader.user_id \n" +
            "WHERE\n" +
            "bili_uploader.last_update >'2020-5-28'\n" +
            "And bili_uploader.video_count = 0 \n" +
            "AND bili_uploader.`level` IN ( 'Level0', 'Level1', 'Level2' ) \n" +
            "AND bili_uploader.follower < 10000 \n" +
            "AND bili_detect.useable = 1 \n" +
            "GROUP BY\n" +
            "bili_uploader.user_id \n" +
            ") re \n" +
            ") \n" +
            "AND detect_type = 0")
    int deleteAllDetectUnusable();





}
