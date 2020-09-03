package com.project.xghk416.enums;

public enum RoleEnum {
    //这里是可以自己定义的，方便与前端交互即可
    USER("viewer","普通用户"),
    MANAGER("MANAGER","管理员"),
    SUPERMANAGER("SUPER_MANAGER","超级管理员"),
            ;
    private String name;
    private String value;

    RoleEnum(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
