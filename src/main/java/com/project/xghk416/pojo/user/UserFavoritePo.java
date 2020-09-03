package com.project.xghk416.pojo.user;

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

/**
 * <p>
 * 
 * </p>
 *
 * @author XGHK416
 * @since 2020-03-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("user_favorite")
public class UserFavoritePo extends Model<UserFavoritePo> {

    private static final long serialVersionUID=1L;

    private String userId;

    private Integer favoriteId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    private String groupId;

    private String type;

    /**
     * 备注
     */
    private String remark;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
