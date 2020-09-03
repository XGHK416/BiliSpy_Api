package com.project.xghk416.pojo.operate;

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
@TableName("user_log_opera")
public class UserLogOperaPo extends Model<UserLogOperaPo> {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 操作人
     */
    private String createUser;

    /**
     * 操作时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 操作类型
     */
    private String operateType;

    /**
     * 操作内容
     */
    private String context;

    private String operateLevel;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    public UserLogOperaPo() {
    }

    public UserLogOperaPo(String createUser, LocalDateTime createTime, String operateType, String context) {

        this.createUser = createUser;
        this.createTime = createTime;
        this.operateType = operateType;
        this.context = context;
    }
}
