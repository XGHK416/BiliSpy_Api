package com.project.xghk416.framework.shiro.service;


import com.project.xghk416.pojo.user.UserAccountPo;

import java.util.Set;

public interface UserService {
    Set<String> getRole(String role);

    Set<String> getPermission(String role,int isCode,String usable);

    UserAccountPo getAccount(String userId);



}
