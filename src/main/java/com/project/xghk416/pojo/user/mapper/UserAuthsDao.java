package com.project.xghk416.pojo.user.mapper;

import com.project.xghk416.pojo.user.UserAuthsPo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author XGHK416
 * @since 2020-02-04
 */
@Component
public interface UserAuthsDao extends BaseMapper<UserAuthsPo> {

    @Select("select ")
    public Map<String,Object> queryOnesAuths(@Param("user_id") String userId);

    @Update("update user_auths set credential = #{newPassword} where user_id = #{userId}")
    int updatePassword(String newPassword,String userId);

}
