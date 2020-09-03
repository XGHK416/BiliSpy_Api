package com.project.xghk416.pojo.user;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @since 2020-03-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("user_favorite_group")
public class UserFavoriteGroupPo extends Model<UserFavoriteGroupPo> {

    private static final long serialVersionUID=1L;

    private String groupId;

    private String groupName;

    /**
     * 视频、uploader
     */
    private String groupType;

    private String createId;

    private LocalDateTime createTime;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
