package com.project.xghk416.enums;

public enum UserStateEnum {

    //这里是可以自己定义的，方便与前端交互即可
    OPERATE_SUCCESS(2000,"操作成功"),
    LOGIN_SUCCESS(2001,"登录成功"),
    REGISTER_SUCCESS(2002,"注册成功"),
    ALTER_SUCCESS(2003,"修改成功"),
    OPERATE_FAILED(-1000,"操作失败"),
    WRONG_PASSWORD(-1002,"密码错误"),
    CANT_SAME(-1003,"不能与原值相同"),
    USER_NOT_EXIST(-1010,"用户不存在"),
            ;
    private Integer code;
    private String msg;

    UserStateEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public Integer getCode() {
        return code;
    }
}
