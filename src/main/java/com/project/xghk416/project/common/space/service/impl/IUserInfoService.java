package com.project.xghk416.project.common.space.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.project.xghk416.enums.IdentityTypeEnum;
import com.project.xghk416.enums.UserStateEnum;
import com.project.xghk416.pojo.user.mapper.UserAccountDao;
import com.project.xghk416.pojo.user.mapper.UserAuthsDao;
import com.project.xghk416.pojo.user.UserAccountPo;
import com.project.xghk416.pojo.user.UserAuthsPo;
import com.project.xghk416.pojo.dto.UserBaseInfoDto;
import com.project.xghk416.common.util.AliyunOssUtils;
import com.project.xghk416.common.util.DeleteFileUtil;
import com.project.xghk416.project.common.space.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import java.io.File;
import java.io.FileOutputStream;

@Service
public class IUserInfoService implements UserInfoService {
    @Autowired
    HttpSession session;
    @Autowired
    UserAccountDao userAccountDao;
    @Autowired
    UserAuthsDao userAuthsDao;
    @Autowired
    private AliyunOssUtils aliyunOssUtils;


    /**
     * 获取个人信息业务
     *
     * @param userId
     * @return status, code, data-->UserAccountPo
     */
    @Override
    public UserBaseInfoDto GetBaseInfo(String userId) {
        try {
            QueryWrapper<UserAccountPo> userAccountPoQueryWrapper = new QueryWrapper<>();
            userAccountPoQueryWrapper.eq("user_id",userId);
//            UserAccountPo userAccountPo = userAccountDao.selectOne(userId.replaceAll("[a-zA-Z]", ""));
            UserAccountPo userAccountPo = userAccountDao.selectOne(userAccountPoQueryWrapper);
            QueryWrapper<UserAuthsPo> userAuthsPoQueryWrapper = new QueryWrapper<>();
            userAuthsPoQueryWrapper.eq("user_id", userId);
            List<UserAuthsPo> authsPoList = userAuthsDao.selectList(userAuthsPoQueryWrapper);
            return new UserBaseInfoDto(userAccountPo, authsPoList, UserStateEnum.OPERATE_SUCCESS);
        } catch (Exception e) {
            return new UserBaseInfoDto(UserStateEnum.OPERATE_FAILED);
        }
    }

    /**
     * 邮箱手机认证
     *
     * @param newAuths
     * @return
     */
    @Override
    public UserBaseInfoDto SocialIdentification(UserAuthsPo newAuths) {
        QueryWrapper<UserAuthsPo> authsWrapper = new QueryWrapper<>();
        QueryWrapper<UserAuthsPo> currentAuthsWrapper = new QueryWrapper<>();

        try {
            authsWrapper.eq("user_id", newAuths.getUserId())
                    .eq("identity_type", newAuths.getIdentityType());
            UserAuthsPo hasAuths = userAuthsDao.selectOne(authsWrapper);

            currentAuthsWrapper.eq("user_id", newAuths.getUserId())
                    .eq("identity_type", IdentityTypeEnum.ORIGIN.getName());
            UserAuthsPo currentAuths = userAuthsDao.selectOne(currentAuthsWrapper);
// TODO: 2020/2/13 这只是做了一个人的，之后要对每个授权进行全局查重
            if (hasAuths == null) {
//            还没有授权
                newAuths.setLastLogin(LocalDateTime.now());
                newAuths.setCredential(currentAuths.getCredential());
                userAuthsDao.insert(newAuths);
                return new UserBaseInfoDto(UserStateEnum.OPERATE_SUCCESS);
            } else {
//            已存在授权
                if (hasAuths.getIdentityId().equals(newAuths.getIdentityId())) {
//                授权相同
                    return new UserBaseInfoDto(UserStateEnum.CANT_SAME);
                } else {
//                修改授权
                    newAuths.setLastLogin(LocalDateTime.now());
                    newAuths.setId(hasAuths.getId());
                    newAuths.setCredential(currentAuths.getCredential());
                    userAuthsDao.updateById(newAuths);
                    return new UserBaseInfoDto(UserStateEnum.ALTER_SUCCESS);
                }
            }
        } catch (Exception e) {
            return new UserBaseInfoDto(UserStateEnum.OPERATE_FAILED);
        }

    }

    @Override
    public Map<String, Object> bandBiliIdentification(String userId, String identityId, String credential) {
        return null;
    }

    @Override
    public boolean alterUserInfo(String userId, UserAccountPo userAccountPo) {
        UpdateWrapper<UserAccountPo> userAccountPoUpdateWrapper = new UpdateWrapper<>();
        userAccountPoUpdateWrapper.eq("user_id", userId);
        int rows = userAccountDao.update(userAccountPo, userAccountPoUpdateWrapper);
        return rows > 0;
    }

    @Override
    public boolean alterPassword(String userId, UserAuthsPo userAuthsPo) {
        // TODO: 2020/2/13 密码进一步验证
        UpdateWrapper<UserAuthsPo> userAuthsPoUpdateWrapper = new UpdateWrapper<>();
        userAuthsPoUpdateWrapper.eq("user_id", userId);
        int rows = userAuthsDao.update(userAuthsPo, userAuthsPoUpdateWrapper);
        return rows > 0;
    }

    @Override
    public String alterProfile(String userId, MultipartFile profile) {
        String filename = profile.getOriginalFilename();
        UpdateWrapper<UserAccountPo> userAccountPoUpdateWrapper = new UpdateWrapper<>();

        String uploadUrl = "";
        try {
            assert filename != null;
            if (!"".equals(filename.trim())) {
                File newFile = new File(filename);
                FileOutputStream os = new FileOutputStream(newFile);
                os.write(profile.getBytes());
                os.close();
                profile.transferTo(newFile);
                // 上传到OSS
                uploadUrl = aliyunOssUtils.upLoad(newFile, userId);
//                System.err.println(uploadUrl);
                // 删除上传的本地文件
                File file1 = new File("");
                String s = file1.getAbsolutePath();
                DeleteFileUtil.delete(s + "\\" + filename);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (!uploadUrl.equals("")) {
            userAccountPoUpdateWrapper.eq("user_id", userId);
            UserAccountPo profileUpdatePo = new UserAccountPo();
            profileUpdatePo.setProfile(uploadUrl);
            userAccountDao.update(profileUpdatePo, userAccountPoUpdateWrapper);
        }
        return uploadUrl;
    }

}
