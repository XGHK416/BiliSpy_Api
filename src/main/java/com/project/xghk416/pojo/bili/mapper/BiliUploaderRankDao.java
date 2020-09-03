package com.project.xghk416.pojo.bili.mapper;

import com.project.xghk416.pojo.bili.BiliUploaderRankPo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
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
 * @since 2020-03-03
 */
@Component
public interface BiliUploaderRankDao extends BaseMapper<BiliUploaderRankPo> {
    @Select("select score as value,DATE_FORMAT(create_time,'%Y-%m-%d') as name from bili_uploader_rank WHERE uploader_id = #{mid} AND DATEDIFF(NOW(),create_time)<=#{limit}")
    List<Map<String,Object>> queryRank(int mid,int limit);

    @Select("select section from bili_uploader_rank WHERE uploader_id = #{mid} ORDER BY create_time DESC LIMIT 1")
    String queryUploaderSection(@Param("mid") int mid);

}
