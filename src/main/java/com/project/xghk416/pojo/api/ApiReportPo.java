package com.project.xghk416.pojo.api;

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
 * @since 2020-03-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("api_report")
public class ApiReportPo extends Model<ApiReportPo> {

    private static final long serialVersionUID=1L;

    private String apiId;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String reportReason;

    private LocalDateTime createTime;

    private String createUser;

    private Integer isFix;

    private String fixedUser;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
