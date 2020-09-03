package com.project.xghk416.project.mo.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.project.xghk416.pojo.user.mapper.UserAccountDao;
import com.project.xghk416.pojo.user.mapper.UserAuthsDao;
import com.project.xghk416.pojo.user.UserAuthsPo;
import com.project.xghk416.project.mo.manage.service.IdentityAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IIdentityAuthenticationService implements IdentityAuthenticationService {
    @Autowired
    UserAccountDao userAccountDao;
    @Autowired
    UserAuthsDao userAuthsDao;


    @Override
    public boolean authenticatePassword(String userId, String password) {
        QueryWrapper<UserAuthsPo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);

        List<UserAuthsPo> list = userAuthsDao.selectList(queryWrapper);
        if (list.size()>0){
            UserAuthsPo item = list.get(0);
            return password.equals(item.getCredential());
        }
        else return false;
    }
}
