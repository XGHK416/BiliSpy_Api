package com.project.xghk416.project.viewer.favorite.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.project.xghk416.pojo.user.mapper.UserFavoriteDao;
import com.project.xghk416.pojo.user.mapper.UserFavoriteGroupDao;
import com.project.xghk416.pojo.user.UserFavoriteGroupPo;
import com.project.xghk416.pojo.user.UserFavoritePo;
import com.project.xghk416.project.viewer.favorite.service.UserFavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class IUserFavoriteService implements UserFavoriteService {
    @Autowired
    UserFavoriteGroupDao groupDao;
    @Autowired
    UserFavoriteDao favoriteDao;

//    收藏组获取
    @Override
    public List<UserFavoriteGroupPo> getFavoriteGroup(String userId, String type) {
        QueryWrapper<UserFavoriteGroupPo> groupWrapper = new QueryWrapper<>();
        groupWrapper.eq("create_id",userId).eq("group_type",type);
        return groupDao.selectList(groupWrapper);
    }
//收藏组添加
    @Override
    public boolean addFavoriteGroup(UserFavoriteGroupPo addGroup) {

        return groupDao.insert(addGroup) >= 1;
    }

    @Override
    public boolean updateFavoriteGroup(UserFavoriteGroupPo updateGroup) {
        UpdateWrapper<UserFavoriteGroupPo> userFavoriteGroupPoUpdateWrapper = new UpdateWrapper<>();
        userFavoriteGroupPoUpdateWrapper.eq("group_id",updateGroup.getGroupId());

        return groupDao.update(updateGroup,userFavoriteGroupPoUpdateWrapper)>0;
    }

    //收藏组删除 也删除里面的收藏
    @Override
    public boolean cancelFavoriteGroup(String groupId) {
        Map<String,Object> group = new HashMap<>();
        group.put("group_id",groupId);
        boolean groupFlag = groupDao.deleteByMap(group)>0;
        if (groupFlag){
            Map<String,Object> favorite = new HashMap<>();
            favorite.put("group_id",groupId);
            favoriteDao.deleteByMap(favorite);
            return true;
        }else {
            return false;
        }
    }


//    获取收藏列 通过userid
    @Override
    public List<Map<String, Object>> getFavoriteListByUser(String userId, String type) {
        List<Map<String,Object>> result;
       if (type.equals("video")){
            result = favoriteDao.queryVideoFavoriteListByUserId(userId, type);
       }else {
           result = favoriteDao.queryUploaderFavoriteListByUserId(userId, type);
       }
       return result;
    }
    //    获取收藏列 通过group
    @Override
    public List<Map<String, Object>> getFavoriteListByGroup(String groupId,String type) {
        List<Map<String,Object>> result;
        if (type.equals("video")){
            result = favoriteDao.queryVideoFavoriteListByGroupId(groupId);
        }else {
            result = favoriteDao.queryUploaderFavoriteListByGroupId(groupId);
        }
        return result;

    }
//    添加收藏
    @Override
    public int addFavorite(UserFavoritePo userFavoritePo) {
        boolean flag = favoriteDao.insert(userFavoritePo)>0;
        if (flag){
            return userFavoritePo.getId();
        }else {return -1;}
    }

    //    更新收藏信息
    @Override
    public boolean updateFavorite(UserFavoritePo userFavoritePo) {
        favoriteDao.updateById(userFavoritePo);
        return favoriteDao.updateById(userFavoritePo)>=1;
    }
    //收藏删除
    @Override
    public boolean cancelFavorite(int id) {
        return favoriteDao.deleteById(id)>0;
    }
//    检查是否收藏
    @Override
    public UserFavoritePo checkIsFavorite(UserFavoritePo favoritePo) {
        QueryWrapper<UserFavoritePo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",favoritePo.getUserId())
                .eq("favorite_id",favoritePo.getFavoriteId())
                .eq("type",favoritePo.getType());
        return favoriteDao.selectOne(queryWrapper);
    }
}
