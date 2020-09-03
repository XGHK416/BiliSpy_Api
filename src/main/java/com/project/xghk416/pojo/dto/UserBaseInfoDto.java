package com.project.xghk416.pojo.dto;

import com.project.xghk416.enums.IdentityTypeEnum;
import com.project.xghk416.enums.UserStateEnum;
import com.project.xghk416.pojo.user.UserAccountPo;
import com.project.xghk416.pojo.user.UserAuthsPo;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Data
public class UserBaseInfoDto {
    UserAccountPo base_info;
    Map<String,Object> auths;
    Integer state;
    String stateInfo;

    public void setAuths(List<UserAuthsPo> authsPoList){
        auths = new HashMap<>();
        for (UserAuthsPo item :
                authsPoList) {
            String identityType = IdentityTypeEnum.valueOf(item.getIdentityType()).getValue();

            if (identityType.equals(IdentityTypeEnum.BILIBILI.getValue())){
                auths.put(identityType,true);
            }else {
                auths.put(identityType,item.getIdentityId());
            }
        }
        auths.putIfAbsent(IdentityTypeEnum.BILIBILI.getValue(), false);
        auths.putIfAbsent(IdentityTypeEnum.PHONE.getValue(), "");
        auths.putIfAbsent(IdentityTypeEnum.EMAIL.getValue(), "");
    }

    public UserBaseInfoDto(){}

    public UserBaseInfoDto(UserAccountPo userAccountPo, List<UserAuthsPo> auths, UserStateEnum userStateEnum){
        setBase_info(userAccountPo);
        setAuths(auths);
        setState(userStateEnum.getCode());
        setStateInfo(userStateEnum.getMsg());
    }

    public UserBaseInfoDto(UserAccountPo userAccountPo, UserStateEnum userStateEnum){
        setBase_info(userAccountPo);
        setState(userStateEnum.getCode());
        setStateInfo(userStateEnum.getMsg());
    }

    public UserBaseInfoDto(UserStateEnum userStateEnum){
        setState(userStateEnum.getCode());
        setStateInfo(userStateEnum.getMsg());
    }

}
