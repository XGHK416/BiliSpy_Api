package com.project.xghk416.framework.shiro.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.project.xghk416.framework.shiro.service.UserService;
import com.project.xghk416.pojo.user.UserAccountPo;
import com.project.xghk416.pojo.user.mapper.UserAccountDao;
import com.project.xghk416.pojo.user.mapper.UserAuthsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.Query;
import java.util.HashSet;
import java.util.Set;

@Service
public class IUserService implements UserService {
    @Autowired
    UserAccountDao accountDao;
    @Autowired
    UserAuthsDao authsDao;

    @Override
    public Set<String> getRole(String role) {
        Set<String> permsSet = new HashSet<>();
        permsSet.add(role);
        return permsSet;
    }

    @Override
    public Set<String> getPermission(String role,int isCode,String usable) {
        Set<String> permsSet = new HashSet<>();
        switch (role){
            case "viewer":{
                System.out.println("give_power");
                permsSet.add("viewer:view");
                permsSet.add("viewer:info");
//                if (isCode!=1){
                permsSet.add("viewer:monitor");
//                }
                break;
            }
            case "monitor":{
            }
            case "manager":{
                permsSet.add("mo:view");
                permsSet.add("mo:manager");
                permsSet.add("mo:info");
                break;
            }
        }
        return permsSet;
    }

    @Override
    public UserAccountPo getAccount(String userId) {
        QueryWrapper<UserAccountPo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        return accountDao.selectOne(queryWrapper);
    }
}
