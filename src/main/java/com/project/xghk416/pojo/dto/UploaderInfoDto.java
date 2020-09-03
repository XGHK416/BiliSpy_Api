package com.project.xghk416.pojo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.xghk416.pojo.bili.BiliUploaderPo;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UploaderInfoDto {
    String profile;
    String nick_name;
    int mid;
    String vip;
    String belong_section;
    String idiograph;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone="GMT+8")
    LocalDateTime last_publish;
    int detect_time;

    public UploaderInfoDto(){};

    public UploaderInfoDto(BiliUploaderPo uploader,String belongSection,LocalDateTime dateTime,int detectTime){
        nick_name = uploader.getNickName();
        profile = uploader.getProfile();
        mid = uploader.getUserId();
        vip = uploader.getVip();
        idiograph = uploader.getSign();

        belong_section = belongSection;

        last_publish = dateTime;

        detect_time = detectTime;
    }

}
