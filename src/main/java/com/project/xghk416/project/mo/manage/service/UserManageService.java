package com.project.xghk416.project.mo.manage.service;

import com.project.xghk416.pojo.dto.UserBaseInfoDto;
import com.project.xghk416.pojo.user.UserAccountPo;
import com.project.xghk416.pojo.operate.UserLogOperaPo;

import java.util.List;
import java.util.Map;

public interface UserManageService {

    Map<String,Object> getUserCount();

    List<UserAccountPo> getUserList(int page, int pageSize, String type);

    boolean addNewMo(String defaultNickName,String defaultPassword,String role);

    int getCount(String type);

    UserBaseInfoDto getUserAccount(String userId);

    // TODO: 2020/3/29 整合管理员和用户的操作日志
    List<Map<String, Object>> getMoOperate(String moId, String searchFrom, String startDate, String endDate);

    List<UserLogOperaPo> getViewerOperate(String viewerId, String searchFrom,String startDate,String endDate);


    List<UserAccountPo> getUserListByKey(String key,String type);




}
