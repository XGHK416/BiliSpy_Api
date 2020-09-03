package com.project.xghk416.project.common.operate.service;


import com.project.xghk416.pojo.operate.UserLogOperaPo;

import java.util.List;

public interface OperationLogService {
    public boolean addUserOperate(String userId,String operateType,String context);

    public List<UserLogOperaPo> getUserTodayOperate(String userId);

}

