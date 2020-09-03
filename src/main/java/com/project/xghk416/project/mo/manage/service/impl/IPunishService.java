package com.project.xghk416.project.mo.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.project.xghk416.pojo.user.mapper.UserAccountDao;
import com.project.xghk416.pojo.punish.mapper.UserColdDao;
import com.project.xghk416.pojo.user.UserAccountPo;
import com.project.xghk416.pojo.punish.UserColdPo;
import com.project.xghk416.project.mo.manage.service.PunishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class IPunishService implements PunishService {
    @Autowired
    UserColdDao userColdDao;
    @Autowired
    UserAccountDao userAccountDao;
    @Autowired
    UserAccountDao accountDao;

    @Override
    public boolean coldViewer(UserColdPo userColdPo) {
        // TODO: 2020/4/2 封禁时间
        userColdPo.setCreateTime(LocalDateTime.now());
        userColdPo.setStartTime(LocalDateTime.now());
        userColdPo.setEndTime(LocalDateTime.now());
        int row = userColdDao.insert(userColdPo);
        if (row>0){
            UpdateWrapper<UserAccountPo> userAccountPoUpdateWrapper = new UpdateWrapper<>();
            userAccountPoUpdateWrapper.eq("user_id",userColdPo.getColdUserId());
            UserAccountPo userAccountPo = new UserAccountPo();
            userAccountPo.setIsCold(1);

            userAccountDao.update(userAccountPo,userAccountPoUpdateWrapper);
        }

        return row>0;
    }

    @Override
    public boolean decoldViewer(String viewerId) {
        QueryWrapper<UserColdPo> userColdWrapper = new QueryWrapper<>();
        userColdWrapper.eq("cold_user_id",viewerId);
        int row = userColdDao.delete(userColdWrapper);
        if(row>0){
            UpdateWrapper<UserAccountPo> userAccountPoUpdateWrapper = new UpdateWrapper<>();
            userAccountPoUpdateWrapper.eq("user_id",viewerId);
            UserAccountPo userAccountPo = new UserAccountPo();
            userAccountPo.setIsCold(0);

            userAccountDao.update(userAccountPo,userAccountPoUpdateWrapper);
        }
        return row>0;
    }

    @Override
    public boolean isColding(String viewerId) {
        return false;
    }

    @Override
    public boolean writtenOffUser(String writtenOffId, boolean isWritten) {
        UpdateWrapper<UserAccountPo> userAccountPoUpdateWrapper = new UpdateWrapper<>();
        userAccountPoUpdateWrapper.eq("user_id",writtenOffId);
        UserAccountPo userAccountPo = new UserAccountPo();
        if (isWritten){
            userAccountPo.setUsable("0");
        }else {
            userAccountPo.setUsable("1");
        }
        int row = accountDao.update(userAccountPo,userAccountPoUpdateWrapper);
        return row > 0;
    }
}
