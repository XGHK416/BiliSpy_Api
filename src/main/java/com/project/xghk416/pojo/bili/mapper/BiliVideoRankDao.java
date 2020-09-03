package com.project.xghk416.pojo.bili.mapper;

import com.project.xghk416.pojo.bili.BiliVideoRankPo;
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
 * @since 2020-03-13
 */
@Component
public interface BiliVideoRankDao extends BaseMapper<BiliVideoRankPo> {

    @Select("SELECT\n" +
            "\t* \n" +
            "FROM\n" +
            "\t(\n" +
            "\tSELECT\n" +
            "\t\tbili_video.*,bili_video_rank.rank\n" +
            "\tFROM\n" +
            "\t\tbili_video \n" +
            "\tINNER JOIN bili_video_rank ON bili_video.video_id = bili_video_rank.video_id\n" +
            "\tWHERE\n" +
            "\t\tbili_video.video_id IN ( SELECT sc.video_id FROM ( SELECT video_id FROM bili_video_rank WHERE uploader_id = #{mid} GROUP BY video_id ORDER BY rank DESC LIMIT 3 ) AS sc ) \n" +
            "\tORDER BY\n" +
            "\t\tbili_video.last_update DESC \n" +
            "\t) r \n" +
            "WHERE\n" +
            "\tNOT EXISTS ( SELECT * FROM bili_video AS b WHERE r.video_id = b.video_id AND r.last_update < b.last_update ) AND\n" +
            "\tNOT EXISTS ( SELECT * FROM bili_video_rank AS c WHERE r.video_id = c.video_id AND r.rank < c.rank )\n" +
            "ORDER BY r.rank DESC")
    List<Map<String,Object>> getTopThreeRank(int mid);
}
