package com.project.xghk416.project.common.login.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.project.xghk416.enums.IdentityTypeEnum;
import com.project.xghk416.enums.RoleEnum;
import com.project.xghk416.enums.UserStateEnum;
import com.project.xghk416.framework.shiro.pojo.UserDto;
import com.project.xghk416.pojo.user.mapper.UserAccountDao;
import com.project.xghk416.pojo.user.mapper.UserAuthsDao;
import com.project.xghk416.pojo.token.mapper.UserTokenDao;
import com.project.xghk416.pojo.user.UserAccountPo;
import com.project.xghk416.pojo.user.UserAuthsPo;
import com.project.xghk416.pojo.token.UserTokenPo;
import com.project.xghk416.common.util.MockTokenUtil;
import com.project.xghk416.project.common.login.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


@Service
public class ILoginService implements LoginService {
    @Autowired
    UserAccountDao userAccountDao;
    @Autowired
    UserAuthsDao userAuthsDao;
    @Autowired
    UserTokenDao userTokenDao;
    @Autowired
    HttpSession session;


    /**
     * 根据登录凭证获取用户id
     * @param identityId
     * @param type
     * @return
     */
    @Override
    public String getUserId(String identityId, String type) {
        QueryWrapper<UserAuthsPo> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("user_id");
        queryWrapper.eq("identity_id",identityId).eq("identity_type",type);

        UserAuthsPo userAuthsPo = userAuthsDao.selectOne(queryWrapper);
        if (userAuthsPo!=null){
            return userAuthsPo.getUserId();
        }else {
            return "";
        }
    }

    /**
     * 检查登录信息业务
     * @param identityId
     * @param credential
     * @param identityType
     * @return status,code,token
     */
    @Override
    public UserDto CheckLogin(String identityId, String credential, String identityType) {
        UserDto result = new UserDto();
        QueryWrapper<UserAuthsPo> queryAuthWrapper = new QueryWrapper<>();
        QueryWrapper<UserAccountPo> queryAccountWrapper = new QueryWrapper<>();

        queryAuthWrapper.eq("identity_id",identityId)
                    .eq("identity_type",identityType)
                    .eq("credential",credential);

        queryAccountWrapper.eq("user_id",identityId);

        UserAccountPo accountPo = userAccountDao.selectOne(queryAccountWrapper);

        if (accountPo!=null){
//            UserTokenPo userTokenPo = saveAndCreateToken(identityId,false);
//            result.setToken(userTokenPo);
            result.setUser_id(identityId);
            result.setStatus(UserStateEnum.OPERATE_SUCCESS.getMsg());
            result.setCode(UserStateEnum.OPERATE_SUCCESS.getCode());
            result.setIsCode(accountPo.getIsCold());
            result.setUsable(accountPo.getUsable());
            result.setRole(accountPo.getRole());
        }
        else {
            result.setStatus(UserStateEnum.USER_NOT_EXIST.getMsg());
            result.setCode(UserStateEnum.USER_NOT_EXIST.getCode());
        }
       return result;
    }

    /**
     * 注册业务
     * @param nickName
     * @param password
     * @return status,code,token
     */
    @Override
    public Map<String,Object> RegisterUser(String nickName,String password) {
        UserAccountPo currentAccountPo = new UserAccountPo();
        Map<String,Object> resultMap = new HashMap<>();
        currentAccountPo.setNickName(nickName);
        currentAccountPo.setRole(RoleEnum.USER.getName());

        userAccountDao.Register(currentAccountPo);
        currentAccountPo.setUserId(currentAccountPo.getRole().toLowerCase()+currentAccountPo.getId().toString());
//        更新userId
        userAccountDao.updateById(currentAccountPo);

//        用户注册途径登录
        UserAuthsPo userAuthsPo = new UserAuthsPo();
        userAuthsPo.setUserId(currentAccountPo.getUserId());
        userAuthsPo.setIdentityId(currentAccountPo.getUserId());
        userAuthsPo.setIdentityType(IdentityTypeEnum.ORIGIN.getName());
        userAuthsPo.setCredential(password);
        userAuthsPo.setLastLogin(LocalDateTime.now());
        userAuthsDao.insert(userAuthsPo);

        resultMap.put("token",saveAndCreateToken(userAuthsPo.getUserId(),false));
        resultMap.put("userId",userAuthsPo.getUserId());
        resultMap.put("status", UserStateEnum.OPERATE_SUCCESS.getMsg());
        resultMap.put("code", UserStateEnum.OPERATE_SUCCESS.getCode());


        return resultMap;
    }

    /**
     * 登出业务
     * 清除info和token
     * @return
     */
    @Override
    public boolean LogoutUser() {
        session.removeAttribute("TOKEN");
        return true;
    }

    /**
     * 保存并创建token
     * @param userId
     * @param isRemember
     * @return
     */
    @Override
    public UserTokenPo saveAndCreateToken(String userId, boolean isRemember){
        UserTokenPo userTokenPo = new UserTokenPo();
        userTokenPo.setUserId(userId);
        userTokenPo.setToken(MockTokenUtil.mockToken());
        userTokenPo.setCreateTime(LocalDateTime.now());
        if (isRemember){
            userTokenPo.setIsremenber(true);
            userTokenPo.setExpiryDate(LocalDateTime.now().plusDays(7));
        }
        else userTokenPo.setIsremenber(false);

        userTokenDao.insertToken(userTokenPo);
// TODO: 2020/3/18         还没整合Shiro，先用session获取token
        session.setAttribute("TOKEN",userTokenPo.getToken());
        return userTokenPo;
    }

    @Override
    public boolean changePassword(String newPassword, String userId) {
        return userAuthsDao.updatePassword(newPassword, userId) >= 1;
    }

    /**
     * 更新登录时间
     * @param userId
     * @return
     */
    @Override
    public int updateLoginTime(String userId) {
        UpdateWrapper<UserAccountPo> userAccountDaoUpdateWrapper = new UpdateWrapper<>();
        userAccountDaoUpdateWrapper.eq("user_id",userId);
        UserAccountPo userAccountPo = new UserAccountPo();
        userAccountPo.setLastLogin(LocalDateTime.now());

        return userAccountDao.update(userAccountPo,userAccountDaoUpdateWrapper);
    }
}
