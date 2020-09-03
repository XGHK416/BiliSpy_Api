package com.project.xghk416.pojo.user.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.xghk416.pojo.user.UserAccountPo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author XGHK416
 * @since 2020-02-04
 */

@Component
public interface UserAccountDao extends BaseMapper<UserAccountPo> {

    @Insert("INSERT INTO user_account(nick_name,role) VALUES(#{nickName},#{role});")
    @Options(useGeneratedKeys = true,keyProperty = "id")
    void Register(UserAccountPo userAccountPo);

    @Select("select * from user_account Where role = #{role}")
    IPage<UserAccountPo> queryList(Page<?> page, String role);


    @Select("select count(*) from user_account where role=#{role}")
    int queryRolesCount(String role);

    @Select("select * from user_account where role = #{role} and (nick_name like '%${key}%' or user_id like '%${key}%')")
    List<UserAccountPo> queryUserByKey(String key,String role);

}
