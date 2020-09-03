package com.project.xghk416.pojo;

import com.project.xghk416.enums.mo.ManagerOperationEnum;
import com.project.xghk416.enums.mo.TableEnum;
import lombok.Data;

@Data
public class MoOperate {
    private ManagerOperationEnum operation;
    private TableEnum table;
    private String userId;
    private String newValue;
    private String oldValue;
    /**
     * 修改数据类型
     */
    private String type;

    public MoOperate(ManagerOperationEnum operation, TableEnum table, String userId, String newValue, String oldValue, String type) {
        this.operation = operation;
        this.table = table;
        this.userId = userId;
        this.newValue = newValue;
        this.oldValue = oldValue;
        this.type = type;
    }
}
