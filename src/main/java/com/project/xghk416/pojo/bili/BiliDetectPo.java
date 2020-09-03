package com.project.xghk416.pojo.bili;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
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
@TableName("bili_detect")
@Component
public class BiliDetectPo extends Model<BiliDetectPo> {

    private static final long serialVersionUID=1L;

    /**
     * aid/mid
     */
    private Integer detectId;

    /**
     * 类型
     */
    private Integer detectType;

    /**
     * 已经侦测次数
     */
    private Integer haveDetect;

    /**
     * 最高侦测次数
     */
    private Integer maxDetect;

    private String createId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    private int useable;


    @Override
    protected Serializable pkVal() {
        return this.detectId;
    }

    public BiliDetectPo() {
    }

    /**
     * 创建新up主的侦测
     * @param mid
     */
    public BiliDetectPo(int mid,String userId) {
        this.detectId = mid;
        this.detectType = 0;
        this.haveDetect = 999;
        this.maxDetect = 7;
        this.createId = userId;
        this.createTime = LocalDateTime.now();
        this.updateTime = null;
        this.useable = 1;
    }

    /**
     * 创建新Video主的侦测
     * @param aid
     */
    public BiliDetectPo(int aid,String userId,int maxDetect) {
        this.detectId = aid;
        this.detectType = 1;
        this.haveDetect = 0;
        this.maxDetect = maxDetect;
        this.createId = userId;
        this.createTime = LocalDateTime.now();
        this.updateTime = null;
        this.useable = 1;
    }
}
