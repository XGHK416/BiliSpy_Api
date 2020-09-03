package com.project.xghk416.pojo.user;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author XGHK416
 * @since 2020-02-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("user_auths")
public class UserAuthsPo extends Model<UserAuthsPo> {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 用户名
     */
    private String identityId;

    /**
     * 登录类型
     */
    private String identityType;

    /**
     * 登录凭证
     */
    private String credential;

    /**
     * 最后登录时间
     */
    private LocalDateTime lastLogin;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
