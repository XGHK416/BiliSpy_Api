package com.project.xghk416.project.mo.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.xghk416.enums.IdentityTypeEnum;
import com.project.xghk416.enums.UserStateEnum;
import com.project.xghk416.pojo.operate.mapper.UserLogOperaDao;
import com.project.xghk416.pojo.punish.mapper.UserColdDao;
import com.project.xghk416.pojo.dto.UserBaseInfoDto;
import com.project.xghk416.pojo.user.UserAccountPo;
import com.project.xghk416.pojo.user.UserAuthsPo;
import com.project.xghk416.pojo.operate.UserLogOperaPo;
import com.project.xghk416.pojo.operate.mapper.MoLogOperaDao;
import com.project.xghk416.pojo.user.mapper.UserAccountDao;
import com.project.xghk416.pojo.user.mapper.UserAuthsDao;
import com.project.xghk416.project.mo.manage.service.UserManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class IUserManageService implements UserManageService {
    @Autowired
    UserAccountDao accountDao;
    @Autowired
    UserAuthsDao authsDao;
    @Autowired
    MoLogOperaDao moLogOperaDao;
    @Autowired
    UserLogOperaDao userLogOperaDao;
    @Autowired
    UserColdDao userColdDao;


    /**
     * 获取用户列表
     * @param page
     * @param pageSize
     * @param type 用户类型
     * @return
     */
    @Override
    public List<UserAccountPo> getUserList(int page, int pageSize, String type) {
        Page<UserAccountPo> accountPage = new Page<>(page,pageSize);
        accountPage.addOrder(OrderItem.desc("last_login"));
        return accountDao.queryList(accountPage,type).getRecords();

    }

    /**
     * 添加新管理
     * @param defaultNickName
     * @param defaultPassword
     * @return
     */
    @Override
    public boolean addNewMo(String defaultNickName, String defaultPassword,String role) {
        UserAccountPo newMo = new UserAccountPo();
        UserAuthsPo newAuths = new UserAuthsPo();
        try{
            newAuths.setUserId(defaultNickName);
            newAuths.setIdentityId(defaultNickName);
            newAuths.setCredential(defaultPassword);
            newAuths.setIdentityType(IdentityTypeEnum.ORIGIN.getName());
            newAuths.setLastLogin(null);
            authsDao.insert(newAuths);

            newMo.setNickName(defaultNickName);
            newMo.setUserId(defaultNickName);
            newMo.setRole(role);
            newMo.setLastLogin(null);
            accountDao.insert(newMo);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }

    /**
     * 获取某一角色数量
     * @param type
     * @return
     */
    @Override
    public int getCount(String type) {
        return accountDao.queryRolesCount(type);
    }

    @Override
    public UserBaseInfoDto getUserAccount(String userId) {
        QueryWrapper<UserAccountPo> userAccountPoQueryWrapper = new QueryWrapper<>();
        userAccountPoQueryWrapper.eq("user_id",userId);
        UserAccountPo accountPo = accountDao.selectOne(userAccountPoQueryWrapper);

        QueryWrapper<UserAuthsPo> userAuthsPoQueryWrapper = new QueryWrapper<>();
        userAuthsPoQueryWrapper.eq("user_id",userId);
        List<UserAuthsPo> authsPoList = authsDao.selectList(userAuthsPoQueryWrapper);

        return new UserBaseInfoDto(accountPo, authsPoList, UserStateEnum.OPERATE_SUCCESS);

    }

    /**
     * 获取管理员操作
     * @param selectedId
     * @param searchFrom
     * @param startDate
     * @param endDate
     * @return
     */
    @Override
    public List<Map<String, Object>> getMoOperate(String selectedId, String searchFrom, String startDate, String endDate) {
        List<Map<String,Object>> reslut = new ArrayList<>();
        if (searchFrom.equals(selectedId)){
            reslut = moLogOperaDao.queryOperaListByMyself(selectedId,startDate,endDate);
        }else {
            reslut = moLogOperaDao.queryOperaListByOther(selectedId,startDate,endDate);
        }
        return reslut;
    }

    @Override
    public List<UserLogOperaPo> getViewerOperate(String viewerId, String searchFrom, String startDate, String endDate) {
        List<UserLogOperaPo> reslut = new ArrayList<>();
        if (searchFrom.equals(viewerId)){
            reslut = userLogOperaDao.queryUserOperateByMyself(viewerId,startDate,endDate);
        }else {
            reslut = userLogOperaDao.queryUserOperateByMo(viewerId,startDate,endDate);
        }
        return reslut;
    }


    @Override
    public Map<String, Object> getUserCount() {
        Map<String,Object> userCount = new HashMap<>();
        userCount.put("viewer",accountDao.queryRolesCount("viewer"));
        userCount.put("manager",accountDao.queryRolesCount("mo"));
        userCount.put("admin",accountDao.queryRolesCount("admin"));
        return userCount;
    }

    @Override
    public List<UserAccountPo> getUserListByKey(String key, String type) {

        return accountDao.queryUserByKey(key, type);
    }
}
