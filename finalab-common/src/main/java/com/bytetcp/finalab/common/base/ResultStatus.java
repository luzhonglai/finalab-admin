package com.bytetcp.finalab.common.base;

public enum ResultStatus {
    NO_DATA(501, "无数据"),
    FAIL(500, "操作失败");
    int code;
    String msg;

    ResultStatus(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
