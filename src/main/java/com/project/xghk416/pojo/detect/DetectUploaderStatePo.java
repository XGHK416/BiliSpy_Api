package com.project.xghk416.pojo.detect;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.ZoneOffset;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.xghk416.pojo.JobInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author XGHK416
 * @since 2020-04-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("detect_uploader_state")
public class DetectUploaderStatePo extends Model<DetectUploaderStatePo> {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String detectProfile;

    private String detectName;

    /**
     * 侦测id
     */
    private String detectId;

    private String createId;

    /**
     * 侦测up主id
     */
    private Integer detectUploaderId;

    /**
     * 已经侦测的数目
     */
    private Integer haveDetect;

    /**
     * 侦测视频的数目，即侦测该用户在时间内发布的前n个视频
     */
    private Integer detectTimes;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 起始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    /**
     * 表达式
     */
    private String cornExpression;

    /**
     * 是否结束
     */
    private Integer isClose;

    /**
     * 距上次插入视频任务
     * @return
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastUpdate;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    public DetectUploaderStatePo() {
    }

    public DetectUploaderStatePo(JobInfo jobInfo) {
        this.detectId = jobInfo.getGroup();
        this.detectProfile = jobInfo.getDetectObjectProfile();
        this.detectName = jobInfo.getDetectObject();
        this.detectUploaderId = Integer.valueOf(jobInfo.getDetectObjectId());
        this.createId = jobInfo.getUserId();
        this.detectTimes = Integer.valueOf(jobInfo.getDetectTime());
        this.createTime = LocalDateTime.now();
        this.startTime = LocalDateTime.ofEpochSecond(jobInfo.getStartTime()/1000,0, ZoneOffset.ofHours(8));
        this.endTime = LocalDateTime.ofEpochSecond(jobInfo.getEndTime()/1000,0, ZoneOffset.ofHours(8));
        this.cornExpression = jobInfo.getCornExpression();
        this.isClose = 0;
        this.lastUpdate = LocalDateTime.now();

    }
}
