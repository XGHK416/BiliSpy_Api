package com.project.xghk416.pojo.token.mapper;

import com.project.xghk416.pojo.token.UserTokenPo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Component;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author XGHK416
 * @since 2020-02-08
 */
@Component
public interface UserTokenDao extends BaseMapper<UserTokenPo> {

    @Insert("REPLACE into user_token(user_id,token,create_time,expiry_date,isremenber)\n" +
            "values(#{userId},#{token},#{createTime},#{expiryDate},#{isremenber})")
    public int  insertToken(UserTokenPo userTokenPo);

}
