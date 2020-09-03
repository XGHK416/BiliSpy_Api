package com.project.xghk416.pojo.dto;

import com.project.xghk416.pojo.bili.BiliVideoPo;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class VideoListDto {
    BiliVideoPo video;
    String section;
    Long score;
    int rank;
    List<VideoListDto> list = new ArrayList<>();

    public VideoListDto(BiliVideoPo video, String section, Long score, int rank) {
        this.video = video;
        this.section = section;
        this.score = score;
        this.rank = rank;
    }

    public VideoListDto(BiliVideoPo video) {
        this.video = video;
    }

    public VideoListDto(List<BiliVideoPo> list) {
        for (BiliVideoPo item :
                list) {
            VideoListDto video = new VideoListDto(item);
            this.list.add(video);
        }

    }
}
