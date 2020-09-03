package com.project.xghk416.pojo.punish;

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
 * @since 2020-03-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("user_cold")
public class UserColdPo extends Model<UserColdPo> {

    private static final long serialVersionUID=1L;

    private String coldUserId;

    private LocalDateTime createTime;

    private String createMoId;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String coldReason;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
