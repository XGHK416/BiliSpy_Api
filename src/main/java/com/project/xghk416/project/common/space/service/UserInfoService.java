package com.project.xghk416.project.common.space.service;

import com.project.xghk416.pojo.dto.UserBaseInfoDto;
import com.project.xghk416.pojo.user.UserAccountPo;
import com.project.xghk416.pojo.user.UserAuthsPo;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface UserInfoService {


    UserBaseInfoDto GetBaseInfo(String token);

    UserBaseInfoDto SocialIdentification(UserAuthsPo userAuthsPo);

    Map<String,Object> bandBiliIdentification(String userId,String identityId,String credential);

    boolean alterUserInfo (String userId, UserAccountPo userAccountPo);

    boolean alterPassword(String userId, UserAuthsPo userAuthsPo);

    String alterProfile(String userId, MultipartFile profile);







}
