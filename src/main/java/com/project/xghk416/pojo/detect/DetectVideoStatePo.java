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
@TableName("detect_video_state")
public class DetectVideoStatePo extends Model<DetectVideoStatePo> {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String detectProfile;

    private String detectName;

    private String detectId;

    private String createId;

    /**
     * 对应的up主侦测id
     */
    private String parentDetectId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    private String cornExpression;

    private Integer isClose;

    private Integer authsId;

    private String auths;

    private String authsProfile;



    /**
     * 侦测视频id
     */
    private String detectVideoId;

    public DetectVideoStatePo() {
    }

    public DetectVideoStatePo(JobInfo jobInfo) {
        this.startTime = LocalDateTime.ofEpochSecond(jobInfo.getStartTime()/1000,0, ZoneOffset.ofHours(8));
        this.endTime = LocalDateTime.ofEpochSecond(jobInfo.getEndTime()/1000,0,ZoneOffset.ofHours(8));
        this.createTime = LocalDateTime.now();
        this.detectVideoId = jobInfo.getDetectObjectId();
        this.detectProfile = jobInfo.getDetectObjectProfile();
        this.detectName = jobInfo.getDetectObject();
        this.detectId = jobInfo.getGroup();
        this.createId = jobInfo.getUserId();
        this.cornExpression = jobInfo.getCornExpression();
        this.auths = jobInfo.getAuths();
        this.authsId = jobInfo.getAuthsId();
        this.authsProfile = jobInfo.getAuthsProfile();

        this.isClose = 0;
    }

    public DetectVideoStatePo(JobInfo jobInfo,String parent) {
        this(jobInfo);
        this.parentDetectId = parent;
    }


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
