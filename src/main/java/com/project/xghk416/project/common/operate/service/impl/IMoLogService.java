package com.project.xghk416.project.common.operate.service.impl;

import com.project.xghk416.pojo.MoOperate;
import com.project.xghk416.pojo.operate.mapper.MoLogContentDao;
import com.project.xghk416.pojo.operate.mapper.MoLogOperaDao;
import com.project.xghk416.pojo.operate.MoLogContentPo;
import com.project.xghk416.pojo.operate.MoLogOperaPo;
import com.project.xghk416.project.common.operate.service.MoLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class IMoLogService implements MoLogService {
    @Autowired
    MoLogOperaDao operaDao;
    @Autowired
    MoLogContentDao contentDao;


    @Override
    public boolean addOpera(MoOperate operateLog) {
        try {
            LocalDateTime nowTime = LocalDateTime.now();
            String logId = "log" + nowTime.toEpochSecond(ZoneOffset.of("+8"));
            MoLogContentPo content = new MoLogContentPo(logId, operateLog.getTable().getTablePrimaryKey(), operateLog.getOldValue(), operateLog.getNewValue(), operateLog.getType());
            MoLogOperaPo opera = new MoLogOperaPo(operateLog.getUserId(), nowTime, operateLog.getOperation().getOperateType(), operateLog.getTable().getTableName(), operateLog.getTable().getTableId(), operateLog.getOperation().getServiceName(), logId, operateLog.getOperation().getServiceLevel());

            operaDao.insert(opera);
            contentDao.insert(content);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }

}
