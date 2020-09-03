package com.project.xghk416.pojo.detect.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.xghk416.pojo.detect.DetectUploaderStatePo;
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
public interface DetectUploaderStateDao extends BaseMapper<DetectUploaderStatePo> {
    @Select({"<script>" +
            "select * from detect_uploader_state where create_id = #{userId} " +
            "<if test='detectName!=null'>and detect_name like '%${detectName}%'</if>" +
            "</script>"})
    IPage<DetectUploaderStatePo> queryDetectUploaderList(Page<?> page, String userId, String detectName);

    @Select({"<script>" + "select * from detect_uploader_state where create_id = #{userId} and now() &lt; start_time " +
            "<if test='detectName!=null'>and detect_name like '%${detectName}%'</if>" +
            "</script>"})
    IPage<DetectUploaderStatePo> queryDetectUploaderListUnstart(Page<?> page, String userId, String detectName);

    @Select({"<script>" + "select * from detect_uploader_state where create_id = #{userId} and now() &gt; start_time AND now() &lt; end_time " +
            "<if test='detectName!=null'>and detect_name like '%${detectName}%'</if>" +
            "</script>"})
    IPage<DetectUploaderStatePo> queryDetectUploaderListSatrting(Page<?> page, String userId, String detectName);

    @Select({"<script>" + "select * from detect_uploader_state where create_id = #{userId} and now() &gt; end_time " +
            "<if test='detectName!=null'>and detect_name like '%${detectName}%'</if>" +
            "</script>"})
    IPage<DetectUploaderStatePo> queryDetectUploaderListFinish(Page<?> page, String userId, String detectName);

    ////////////////////////////
    @Select({"<script>" + "select count(*) from detect_uploader_state where create_id = #{userId} " +
            "<if test='detectName!=null'>and detect_name like '%${detectName}%'</if>" +
            "</script>"})
    int queryDetectUploaderCount(String userId, String detectName);

    @Select({"<script>" + "select count(*) from detect_uploader_state where create_id = #{userId} and now() &lt; start_time " +
            "<if test='detectName!=null'>and detect_name like '%${detectName}%'</if>" +
            "</script>"})
    int queryDetectUploaderCountUnstart(String userId, String detectName);

    @Select({"<script>" + "select count(*) from detect_uploader_state where create_id = #{userId} and now() &gt; start_time AND now() &lt; end_time " +
            "<if test='detectName!=null'>and detect_name like '%${detectName}%'</if>" +
            "</script>"})
    int queryDetectUploaderCountSatrting(String userId, String detectName);

    @Select({"<script>" + "select count(*) from detect_uploader_state where create_id = #{userId} and now() &gt; end_time " +
            "<if test='detectName!=null'>and detect_name like '%${detectName}%'</if>" +
            "</script>"})
    int queryDetectUploaderCountFinish(String userId, String detectName);

    @Select("SELECT count(*) from detect_uploader_state where create_id = #{userId} AND detect_uploader_id = #{mid}")
    int haveDetect(int mid,String userId);

}
