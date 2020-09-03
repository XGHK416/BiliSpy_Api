package com.project.xghk416.enums;

public enum  ResultEnum {
    //这里是可以自己定义的，方便与前端交互即可
    UNKNOWN_ERROR(-10000,"未知错误"),
    SUCCESS(20000,"成功啦娃哈哈！！！"),
    DATA_IS_NULL(30000,"数据为空"),
    UNUSABLE(10001,"账号已被封禁")
            ;
    private Integer code;
    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
