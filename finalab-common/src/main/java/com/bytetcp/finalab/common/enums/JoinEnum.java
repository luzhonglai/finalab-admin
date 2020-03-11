package com.bytetcp.finalab.common.enums;

public enum JoinEnum {
    /**
     * 允许新名单参与(1:允许；0：不允许)
     */
    ALLOW(1, "允许新名单参与"),
    NOT_ALLOW(0, "不允许新名单参与");

    int code;
    String desc;

    JoinEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
