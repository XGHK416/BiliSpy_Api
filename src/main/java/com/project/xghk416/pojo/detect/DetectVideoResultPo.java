package com.project.xghk416.pojo.detect;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author XGHK416
 * @since 2020-04-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("detect_video_result")
public class DetectVideoResultPo extends Model<DetectVideoResultPo> {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 侦测视频id
     */
    private String videoDetectId;

    /**
     * 视频bid
     */
    private String videoBid;

    /**
     * 视频名称
     */
    private String videoName;

    private Integer videoView;

    private Integer videoLike;

    private Integer videoCoins;

    private Integer videoFavorite;

    private Integer videoShare;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    public DetectVideoResultPo() {
    }

    public DetectVideoResultPo(String groupName, JSONObject parseObject) {
        this.videoDetectId = groupName;
        this.videoBid = parseObject.getString("bvid");
        this.videoName = parseObject.getString("title");
        this.createTime = LocalDateTime.now();
        JSONObject stat = parseObject.getJSONObject("stat");
        this.videoView = Integer.valueOf(stat.getString("view"));
        this.videoLike = Integer.valueOf(stat.getString("like"));
        this.videoCoins = Integer.valueOf(stat.getString("coin"));
        this.videoFavorite = Integer.valueOf(stat.getString("favorite"));
        this.videoShare = Integer.valueOf(stat.getString("share"));
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
