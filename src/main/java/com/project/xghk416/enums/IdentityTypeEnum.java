package com.project.xghk416.enums;

public enum IdentityTypeEnum {
    //这里是可以自己定义的，方便与前端交互即可
    ORIGIN("origin","ORIGIN"),
    BILIBILI("bili","BILIBILI"),
    PHONE("phone","PHONE"),
    EMAIL("email","EMAIL"),
            ;
    private String value;
    private String name;

    IdentityTypeEnum(String value, String name) {
        this.value = value;
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
