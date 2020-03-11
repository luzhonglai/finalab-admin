package com.bytetcp.finalab.serve.course.domain;

import com.bytetcp.finalab.common.enums.TradeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MustAdd implements Serializable {

    private static final long serialVersionUID = -4308372721736643021L;

    private String instanceId;

    private String stockId;

    private Integer phaseNum;

    private Double price;

    private Integer quantity;

    private TradeType tradeType;

    private Long traderId;

    private String traderName;

}
