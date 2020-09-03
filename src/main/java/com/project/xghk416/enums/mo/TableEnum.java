package com.project.xghk416.enums.mo;

public enum  TableEnum {

    BILI_DETECT(1,"bili_detect","detect_id"),
    MO_LOG_CONTENT(2,"mo_log_content","id"),
    MO_LOG_OPERA(3,"mo_log_opera","id"),
    USER_ACCOUNT(4,"user_account","id"),
    USER_AUTHS(5,"user_auths","id"),
    USER_COLD(6,"user_cold","id"),
    API_COLLECTION(7,"api_collection","id"),
    API_REPORT(8,"api_report","id"),
    ANNOUNCE(9,"announce","id");




    TableEnum(Integer tableId,String tableName, String tablePrimaryKey) {
        this.tableId = tableId;
        this.tableName = tableName;
        this.tablePrimaryKey = tablePrimaryKey;
    }
    private Integer tableId;
    private String tableName;
    private String tablePrimaryKey;
    public String getTableName(){
        return tableName;
    }
    public String getTablePrimaryKey(){
        return tablePrimaryKey;
    }
    public Integer getTableId(){
        return tableId;
    }

}
