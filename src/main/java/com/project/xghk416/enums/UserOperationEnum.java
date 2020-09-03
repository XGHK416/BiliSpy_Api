package com.project.xghk416.enums;

public enum UserOperationEnum {

    LOGIN("登录"),
    CHANGE_PASSWORD("修改密码"),
    FAVORITE_VIDEO("收藏视频"),
    FAVORITE_UPLOADER("收藏up主"),
    DEFAVORITE_VIDEO("取消收藏视频"),
    DEFAVORITE_UPLOADER("取消收藏up主"),
    ADD_VIDEO_MONITOR("添加视频监控"),
    ADD_UPLOADER_MONITOR("添加用户监控"),
    ADD_NEW_DETECT("添加新的up主侦测"),
    WATCH_VIDEO("查看用户"),
    WATCH_UPLOADER("查看视频");

    UserOperationEnum(String name) {
        this.name = name;
    }
    private String name;
    public String getName(){
        return name;
    }
}
