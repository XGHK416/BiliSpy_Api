package com.project.xghk416.pojo.bili;

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
 * @since 2020-03-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("bili_section")
public class BiliSectionPo extends Model<BiliSectionPo> {

    private static final long serialVersionUID=1L;

    @TableId(value = "tid", type = IdType.AUTO)
    private Integer tid;

    private String tname;

    /**
     *  父tid
     */
    private Integer parentId;


    @Override
    protected Serializable pkVal() {
        return this.tid;
    }

}
