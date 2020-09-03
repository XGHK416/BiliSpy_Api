package com.project.xghk416.project.common.login.service;

import com.project.xghk416.framework.shiro.pojo.UserDto;
import com.project.xghk416.pojo.token.UserTokenPo;

import java.util.Map;

public interface LoginService {

    public String getUserId(String identityId,String type);

    public UserDto CheckLogin(String identityId, String credential, String type);

    public Map<String,Object> RegisterUser(String userId,String password);

    public boolean LogoutUser();

    public UserTokenPo saveAndCreateToken(String userId, boolean isRemember);

    boolean changePassword(String newPassword,String userId);

    int updateLoginTime(String userId);
}
