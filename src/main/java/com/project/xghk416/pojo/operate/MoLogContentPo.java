package com.project.xghk416.pojo.operate;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
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
@TableName("mo_log_content")
public class MoLogContentPo extends Model<MoLogContentPo> {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * log的id
     */
    private String logId;

    /**
     * 表主键
     */
    private String tableKey;

    /**
     * 修改表的旧值
     */
    private String tableValue;

    /**
     * 表当前值
     */
    private String currentValue;

    private String valueType;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    public MoLogContentPo(String logId, String tableKey, String tableValue, String currentValue, String valueType) {
        this.logId = logId;
        this.tableKey = tableKey;
        this.tableValue = tableValue;
        this.currentValue = currentValue;
        this.valueType = valueType;
    }
}
