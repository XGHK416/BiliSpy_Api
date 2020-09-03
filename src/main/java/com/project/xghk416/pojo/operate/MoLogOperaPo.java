package com.project.xghk416.pojo.operate;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
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
@TableName("mo_log_opera")
public class MoLogOperaPo extends Model<MoLogOperaPo> {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     *  操作人
     */
    private String createUser;

    /**
     *  操作时间
     */
    private LocalDateTime createTime;

    /**
     *  操作类型
     */
    private String operateType;

    /**
     * 操作表
     */
    private String tableName;

    /**
     * 操作表id
     */
    @TableField("table_Id")
    private Integer tableId;

    /**
     * 业务名
     */
    private String serveName;

    /**
     * 对应日志id
     */
    private String logId;

    /**
     * 业务等级
     */
    private String operateLevel;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    public MoLogOperaPo(String createUser, LocalDateTime createTime, String operateType, String tableName, Integer tableId, String serveName, String logId, String operateLevel) {
        this.createUser = createUser;
        this.createTime = createTime;
        this.operateType = operateType;
        this.tableName = tableName;
        this.tableId = tableId;
        this.serveName = serveName;
        this.logId = logId;
        this.operateLevel = operateLevel;
    }
}
