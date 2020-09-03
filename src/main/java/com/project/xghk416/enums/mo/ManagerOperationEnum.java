package com.project.xghk416.enums.mo;

public enum ManagerOperationEnum {

    ADD_MO("添加管理","4","增"),
    VIEW_VIEWER("查看用户","1","查"),
    VIEW_MANAGER("查看管理","1","查"),
    CHANGE_PASSWORD("修改密码","5","改"),
    WRITTEN_OFF_USER("封禁用户","4","改"),
    COLD_VIEWER("冻结用户","3","改"),
    DE_COLD_VIEWER("解冻用户","3","改"),
    CHANGE_ANNOUNCE("修改公告","2","改"),
    REPORT_API("回报api","2","增"),
    FIX_API("修改API","4","改"),
    TEST_API("测试API","1","查"),
    DELETE_DETECT_OBJECT("删除侦测对象","4","删"),
    DELETE_ALL_UNSABLE_DETECT_OBJECT("删除全部无用侦测对象","4","删");

    ManagerOperationEnum(String serviceName,String serviceLevel,String operateType) {
        this.serviceName = serviceName;
        this.serviceLevel = serviceLevel;
        this.operateType = operateType;
    }
    private String serviceName;
    private String serviceLevel;
    private String operateType;
    public String getServiceName(){
        return serviceName;
    }
    public String getServiceLevel(){
        return serviceLevel;
    }
    public String getOperateType(){
        return operateType;
    }
}
