package com.project.xghk416.project.viewer.favorite.service;

import com.project.xghk416.pojo.user.UserFavoriteGroupPo;
import com.project.xghk416.pojo.user.UserFavoritePo;

import java.util.List;
import java.util.Map;

public interface UserFavoriteService {
    public List<UserFavoriteGroupPo> getFavoriteGroup(String userId, String type);

    public boolean addFavoriteGroup(UserFavoriteGroupPo addGroup);

    public boolean updateFavoriteGroup(UserFavoriteGroupPo updateGroup);

    public boolean cancelFavoriteGroup(String groupId);

    public List<Map<String, Object>> getFavoriteListByUser(String userId, String type);

    public List<Map<String, Object>> getFavoriteListByGroup(String groupId,String type);

    public boolean cancelFavorite(int id);

    public boolean updateFavorite(UserFavoritePo userFavoritePo);

    public int addFavorite(UserFavoritePo userFavoritePo);

    public UserFavoritePo checkIsFavorite (UserFavoritePo favoritePo);

}
