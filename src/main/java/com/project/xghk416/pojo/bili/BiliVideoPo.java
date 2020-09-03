package com.project.xghk416.pojo.bili;

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
import org.springframework.stereotype.Component;

/**
 * <p>
 * 
 * </p>
 *
 * @author XGHK416
 * @since 2020-02-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("bili_video")
@Component
public class BiliVideoPo extends Model<BiliVideoPo> {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 视频id
     */
    private Integer videoId;

    /**
     * 视频bid
     */
    private Integer videoBvid;

    /**
     * 视频所属分类id
     */
    private Integer tid;

    /**
     * 视频所属分类名
     */
    private String tname;

    /**
     * 视频标题
     */
    private String videoTitle;

    /**
     * 视频封面
     */
    private String videoProfile;

    /**
     * 视频描述
     */
    private String videoDesc;

    /**
     * 观看数
     */
    private Integer videoView;

    /**
     * 收藏数
     */
    private Integer videoFavorite;

    /**
     * 投币数
     */
    private Integer coins;

    /**
     * 分享数
     */
    private Integer videoShare;

    /**
     * 点赞数
     */
    private Integer videoLike;

    /**
     * 评论数
     */
    private Integer reply;

    /**
     * 标签
     */
    private String dynamic;

    /**
     * 更新时间
     */
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private LocalDateTime lastUpdate;

    /**
     * 视频创建时间
     */
    private String createTime;

    /**
     * 视频作者
     */
    private String videoAuthor;

    /**
     * 作者mid
     */
    private Integer authorMid;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
