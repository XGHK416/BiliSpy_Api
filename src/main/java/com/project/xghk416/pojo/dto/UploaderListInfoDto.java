package com.project.xghk416.pojo.dto;

import com.project.xghk416.pojo.bili.BiliUploaderPo;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UploaderListInfoDto {


    /**
     * mid : 111
     * nick_name:
     * gender : 男
     * level : level1
     * follower : 11
     * followering : 1
     * video_num : 111
     */

    private int mid;
    private String nick_name;
    private String gender;
    private String level;
    private String profile;
    private int follower;
    private int following;
    private int video_num;

    private List<UploaderListInfoDto> list = new ArrayList<>();

    public UploaderListInfoDto(BiliUploaderPo data){
        this.nick_name = data.getNickName();
        this.mid = data.getUserId();
        this.gender = data.getSex();
        this.level = data.getLevel();
        this.profile = data.getProfile();
        this.follower = data.getFollower();
        this.following = data.getFollowing();
        this.video_num = data.getVideoCount();




    }
    public UploaderListInfoDto(List<BiliUploaderPo> data){
        for (BiliUploaderPo item :
                data) {
            UploaderListInfoDto listItem = new UploaderListInfoDto(item);
            this.list.add(listItem);
        }
    }
}
