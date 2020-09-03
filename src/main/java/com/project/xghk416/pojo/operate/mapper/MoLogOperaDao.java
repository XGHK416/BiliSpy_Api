package com.project.xghk416.pojo.operate.mapper;

import com.project.xghk416.pojo.operate.MoLogOperaPo;
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
 * @since 2020-03-29
 */
@Component
public interface MoLogOperaDao extends BaseMapper<MoLogOperaPo> {
    @Select("SELECT\n" +
            "mo_log_opera.create_user,\n" +
            "DATE_FORMAT(mo_log_opera.create_time,'%Y-%m-%d %h:%i %p') as create_time,\n" +
            "mo_log_opera.operate_type,\n" +
            "mo_log_opera.table_name,\n" +
            "mo_log_opera.table_Id,\n" +
            "mo_log_opera.serve_name,\n" +
            "mo_log_opera.log_id,\n" +
            "mo_log_content.table_key,\n" +
            "mo_log_content.table_value,\n" +
            "mo_log_content.current_value,\n" +
            "mo_log_content.value_type,\n" +
            "mo_log_opera.operate_level\n"+
            "FROM\n" +
            "mo_log_opera\n" +
            "INNER JOIN mo_log_content ON mo_log_opera.log_id = mo_log_content.log_id\n" +
            "\n" +
            "where mo_log_opera.create_user = #{moId} \n" +
            "and create_time>#{startTime} and create_time<#{endTime} \n"+
            "and mo_log_opera.operate_level!='5' \n"+
            "Order By create_time DESC")
    public List<Map<String,Object>> queryOperaListByOther(String moId,String startTime,String endTime);

    @Select("SELECT\n" +
            "mo_log_opera.create_user,\n" +
            "DATE_FORMAT(mo_log_opera.create_time,'%Y-%m-%d %h:%i %p') as create_time,\n" +
            "mo_log_opera.operate_type,\n" +
            "mo_log_opera.table_name,\n" +
            "mo_log_opera.table_Id,\n" +
            "mo_log_opera.serve_name,\n" +
            "mo_log_opera.log_id,\n" +
            "mo_log_content.table_key,\n" +
            "mo_log_content.table_value,\n" +
            "mo_log_content.current_value,\n" +
            "mo_log_content.value_type,\n" +
            "mo_log_opera.operate_level\n"+
            "FROM\n" +
            "mo_log_opera\n" +
            "INNER JOIN mo_log_content ON mo_log_opera.log_id = mo_log_content.log_id\n" +
            "\n" +
            "where mo_log_opera.create_user = #{moId} \n" +
            "and create_time>#{startTime} and create_time<#{endTime} \n"+
            "Order By create_time DESC")
    public List<Map<String,Object>> queryOperaListByMyself(String moId,String startTime,String endTime);

}
