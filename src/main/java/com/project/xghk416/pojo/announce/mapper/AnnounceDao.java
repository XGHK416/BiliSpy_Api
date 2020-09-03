package com.project.xghk416.pojo.announce.mapper;

import com.project.xghk416.pojo.announce.AnnouncePo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author XGHK416
 * @since 2020-03-31
 */
@Component
public interface AnnounceDao extends BaseMapper<AnnouncePo> {
    @Select("select * from announce Order By create_time Desc limit 1")
    AnnouncePo getCurrentAnnounce();
}
