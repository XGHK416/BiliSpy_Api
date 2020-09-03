package com.project.xghk416.pojo.detect.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.xghk416.pojo.detect.DetectVideoStatePo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author XGHK416
 * @since 2020-04-08
 */
@Component
public interface DetectVideoStateDao extends BaseMapper<DetectVideoStatePo> {

    @Select({"<script> " +
            "select * from detect_video_state where create_id = #{userId} " +
            "<if test='parentId!=null'> and parent_detect_id=#{parentId} </if>" +
            "</script>"})
    IPage<DetectVideoStatePo> queryDetectVideoList(Page<?> page, String userId, String parentId);

    @Select({"<script> " +
            "select * from detect_video_state where create_id = #{userId} and now() &lt; start_time " +
            "<if test='parentId!=null'>and parent_detect_id=#{parentId}</if>" +
            "</script>"})
    IPage<DetectVideoStatePo> queryDetectVideoListUnstart(Page<?> page, String userId, String parentId);

    @Select({"<script> " +
            "select * from detect_video_state where create_id = #{userId} and now() &gt; start_time AND now() &lt; end_time " +
            "<if test='parentId!=null'> and parent_detect_id=#{parentId} </if>" +
            "</script>"})
    IPage<DetectVideoStatePo> queryDetectVideoListSatrting(Page<?> page, String userId, String parentId);

    @Select({"<script> " +
            "select * from detect_video_state where create_id = #{userId} and now() &gt; end_time " +
            "<if test='parentId!=null'> and parent_detect_id=#{parentId} </if>" +
            "</script>"})
    IPage<DetectVideoStatePo> queryDetectVideoListFinish(Page<?> page, String userId, String parentId);

    ////////////////////////////
    @Select({"<script> " +
            "select count(*) from detect_video_state where create_id = #{userId} " +
            "<if test='parentId!=null'> and parent_detect_id=#{parentId}</if>" +
            "</script>"})
    int queryDetectVideoCount(String userId, String parentId);

    @Select({"<script> " +
            "select count(*) from detect_video_state where create_id = #{userId} and now() &lt; start_time " +
            "<if test='parentId!=null'>and parent_detect_id=#{parentId}</if>" +
            "</script>"})
    int queryDetectVideoCountUnstart(String userId, String parentId);

    @Select({"<script> " +
            "select count(*) from detect_video_state where create_id = #{userId} and now() &gt; start_time AND now() &lt; end_time " +
            "<if test='parentId!=null'> and parent_detect_id=#{parentId} </if>" +
            "</script>"})
    int queryDetectVideoCountSatrting(String userId, String parentId);

    @Select({"<script> " +
            "select count(*) from detect_video_state where create_id = #{userId} and now() &gt; end_time " +
            "<if test='parentId!=null'> and parent_detect_id=#{parentId} </if>" +
            "</script>"})
    int queryDetectVideoCountFinish(String userId, String parentId);


}
