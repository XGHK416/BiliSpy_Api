package com.project.xghk416.pojo.bili;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
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
@TableName("bili_uv_count")
@Component
public class BiliUvCountPo extends Model<BiliUvCountPo> {

    private static final long serialVersionUID=1L;

    @TableId(value = "tid", type = IdType.AUTO)
    private Integer tid;

    private Integer count;

    private String name;

    private Integer userId;

    private LocalDateTime updateTime;


    @Override
    protected Serializable pkVal() {
        return this.tid;
    }

}
