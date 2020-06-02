package com.bytetcp.finalab.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * @author chenchuan@autohome.com.cn
 * @create 2019-05-03-下午7:19
 * @description
 */
@Slf4j
public class ParamsUtil {
    public static double priceExact(double price, int length) {
        BigDecimal b = new BigDecimal(price);
        return b.setScale(length, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static Integer parseInt(Object value) {
        try {
            return null == value ? null : Integer.parseInt(value.toString());
        } catch (Exception ex) {
            log.error("parseInt error.[{}]", value, ex);
            return null;
        }
    }

    public static Double parseDouble(Object value) {
        try {
            return null == value ? null : Double.parseDouble(value.toString());
        } catch (Exception ex) {
            log.error("parseDouble error.[{}]", value, ex);
            return null;
        }
    }

    public static double quotedDecimalsValue(int length) {
        String value = "0.";
        for (int i = 0; i < length; i++) {
            value += "0";
        }
        return Double.parseDouble(value + "1");
    }

    public static double ifNullDoubleTo(Double num, Double defaultNum) {
        if (Objects.isNull(num)) {
            return new Double(defaultNum);
        }
        return num;
    }

    public static int ifNullIntegerTo(Integer num, Integer defaultNum) {
        if (Objects.isNull(num)) {
            return new Integer(defaultNum);
        }
        return num;
    }
}
