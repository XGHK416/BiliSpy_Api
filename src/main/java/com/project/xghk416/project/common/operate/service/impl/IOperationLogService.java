package com.project.xghk416.project.common.operate.service.impl;

import com.project.xghk416.pojo.operate.mapper.UserLogOperaDao;
import com.project.xghk416.pojo.operate.UserLogOperaPo;
import com.project.xghk416.project.common.operate.service.OperationLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class IOperationLogService implements OperationLogService {

    @Autowired
    UserLogOperaDao userLogOperaDao;

    @Override
    public boolean addUserOperate(String userId, String operateType, String context) {
        UserLogOperaPo operaPo= new UserLogOperaPo(userId, LocalDateTime.now(),operateType,context);
        int flag = userLogOperaDao.insert(operaPo);
        return flag > 0;
    }

    @Override
    public List<UserLogOperaPo> getUserTodayOperate(String userId) {
        LocalDateTime today = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String end = today.plusDays(1).format(formatter);
        String start = today.plusDays(0).format(formatter);
        return userLogOperaDao.queryUserOperateByDate(userId,start,end);
    }
}
