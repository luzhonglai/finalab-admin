package com.bytetcp.finalab.common.enums;

public enum  ServerStatus {
    SUCCESS("0","成功");

    String code;
    String desc;

    ServerStatus(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
