package com.project.xghk416.pojo.operate.mapper;

import com.project.xghk416.pojo.operate.UserLogOperaPo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
public interface UserLogOperaDao extends BaseMapper<UserLogOperaPo> {
    @Select("select * from user_log_opera where create_user = #{userId} and create_time>=#{startTime} and create_time <= #{endTime} Order By create_time DESC Limit 5")
    List<UserLogOperaPo> queryUserOperateByDate(String userId,String startTime,String endTime);

    @Select("select * from user_log_opera where create_user = #{userId} and create_time>=#{startTime} and create_time <= #{endTime} Order By create_time DESC")
    List<UserLogOperaPo> queryUserOperateByMyself(String userId,String startTime,String endTime);

    @Select("select * from user_log_opera where create_user = #{userId} and create_time>=#{startTime} and create_time <= #{endTime} and operate_level!='5' Order By create_time DESC")
    List<UserLogOperaPo> queryUserOperateByMo(String userId,String startTime,String endTime);

}
