package com.bytetcp.finalab.common.enums;

/**
 * 实例状态
 */
public enum InstanceStatus {
    START("START"),
    SUSPEND("SUSPEND"),
    RESUME("RESUME"),
    STOP("STOP"),
    LOOPSTART("LOOPSTART");


    private String name;

    InstanceStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static InstanceStatus getByValue(String name) {
        for (InstanceStatus status: values()) {
            if (status.getName().equals(name)) {
                return status;
            }
        }
        return null;
    }

}
