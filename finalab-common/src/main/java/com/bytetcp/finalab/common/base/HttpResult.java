package com.bytetcp.finalab.common.base;

import lombok.Data;

@Data
public class HttpResult {

    private Integer status;

    private String msg;

    private Integer code;


}
