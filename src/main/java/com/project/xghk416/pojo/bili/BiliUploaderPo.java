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
@TableName("bili_uploader")
@Component
public class BiliUploaderPo extends Model<BiliUploaderPo> {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * B用户id
     */
    private Integer userId;

    /**
     * B用户昵称
     */
    private String nickName;

    /**
     * B用户头像
     */
    private String profile;

    /**
     * B用户性别
     */
    private String sex;

    /**
     *  B用户等级
     */
    private String level;

    /**
     * 个人签名
     */
    private String sign;

    /**
     * 是否被封禁
     */
    private Integer silence;

    /**
     * 用户vip等级
     */
    private String vip;

    /**
     * 生日
     */
    private String birthday;

    /**
     * 视频数
     */
    private Integer videoCount;

    /**
     *  被关注数
     */
    private Integer follower;

    /**
     * 关注数
     */
    private Integer following;

    /**
     * 最后更新日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime lastUpdate;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
