package com.project.xghk416.pojo.user.mapper;

import com.project.xghk416.pojo.user.UserFavoritePo;
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
 * @since 2020-03-19
 */
@Component
public interface UserFavoriteDao extends BaseMapper<UserFavoritePo> {
    @Select("SELECT * from (\n" +
            "SELECT\n" +
            "\tbili_uploader.user_id AS id,\n" +
            "\tbili_uploader.nick_name AS NAME,\n" +
            "\tbili_uploader.`profile` AS avater,\n" +
            "\tbili_uploader.last_update,\n" +
            "\tuser_favorite.type AS type,\n" +
            "\tuser_favorite.create_time AS create_time,\n" +
            "\tuser_favorite.id AS favorite_id \n" +
            "FROM\n" +
            "\tuser_favorite\n" +
            "\tLEFT JOIN bili_uploader ON user_favorite.favorite_id = bili_uploader.user_id \n" +
            "WHERE\n" +
            "\tuser_favorite.user_id = #{userId} and user_favorite.type = #{type}\n" +
            ") re\n" +
            "WHERE not EXISTS (select * from bili_uploader as b WHERE b.user_id = re.id and b.last_update<re.last_update)")
    public List<Map<String,Object>>queryUploaderFavoriteListByUserId(String userId,String type);

    @Select("SELECT * from (\n" +
            "SELECT\n" +
            "\tbili_uploader.user_id AS id,\n" +
            "\tbili_uploader.nick_name AS NAME,\n" +
            "\tbili_uploader.`profile` AS avater,\n" +
            "\tbili_uploader.last_update,\n" +
            "\tuser_favorite.type AS type,\n" +
            "\tuser_favorite.create_time AS create_time,\n" +
            "\tuser_favorite.id AS favorite_id \n" +
            "FROM\n" +
            "\tuser_favorite\n" +
            "\tLEFT JOIN bili_uploader ON user_favorite.favorite_id = bili_uploader.user_id \n" +
            "WHERE\n" +
            "\tuser_favorite.group_id = #{groupId}\n" +
            ") re\n" +
            "WHERE not EXISTS (select * from bili_uploader as b WHERE b.user_id = re.id and b.last_update<re.last_update)")
    public List<Map<String,Object>> queryUploaderFavoriteListByGroupId(String groupId);

    @Select("SELECT\n" +
            "* \n" +
            "FROM\n" +
            "(\n" +
            "SELECT\n" +
            "bili_video.video_id AS id,\n" +
            "bili_video.video_profile AS avater,\n" +
            "bili_video.video_title AS NAME,\n" +
            "bili_video.last_update,\n" +
            "user_favorite.create_time AS create_time,\n" +
            "user_favorite.type AS type,\n" +
            "user_favorite.id AS favorite_id \n" +
            "FROM\n" +
            "bili_video\n" +
            "INNER JOIN user_favorite ON bili_video.video_id = user_favorite.favorite_id \n" +
            "WHERE\n" +
            "user_favorite.user_id = #{userId} \n" +
            "AND user_favorite.type = #{type} \n" +
            ") re\n" +
            "WHERE not EXISTS(select * from bili_video as b where b.video_id = re.id and b.last_update<re.last_update)")
    public List<Map<String,Object>> queryVideoFavoriteListByUserId(String userId,String type);

    @Select("SELECT\n" +
            "* \n" +
            "FROM\n" +
            "(\n" +
            "SELECT\n" +
            "bili_video.video_id AS id,\n" +
            "bili_video.video_profile AS avater,\n" +
            "bili_video.video_title AS NAME,\n" +
            "bili_video.last_update,\n" +
            "user_favorite.create_time AS create_time,\n" +
            "user_favorite.type AS type,\n" +
            "user_favorite.id AS favorite_id \n" +
            "FROM\n" +
            "bili_video\n" +
            "INNER JOIN user_favorite ON bili_video.video_id = user_favorite.favorite_id \n" +
            "WHERE\n" +
            "user_favorite.group_id=#{groupId} \n" +
            ") re\n" +
            "WHERE not EXISTS(select * from bili_video as b where b.video_id = re.id and b.last_update<re.last_update)")
    public List<Map<String,Object>> queryVideoFavoriteListByGroupId(String groupId);

}
