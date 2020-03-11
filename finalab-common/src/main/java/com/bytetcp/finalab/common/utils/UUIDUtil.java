package com.bytetcp.finalab.common.utils;

import org.apache.poi.ss.formula.functions.Index;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class UUIDUtil {
    public static String getId() {

        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static String getOrderNo() {

        String orderNo = "";
        String trandNo = String.valueOf((Math.random() * 9 + 1) * 1000000);
        String sdf = new SimpleDateFormat("yyyyMMddHHMMSS").format(new Date());
        orderNo = trandNo.toString().substring(0, 4);
        orderNo = orderNo + sdf;
        return orderNo;
    }

    public static Long getRandom() {

        String sdf = new SimpleDateFormat("yyyyMMddHHMMSS").format(new Date());
        Random random = new Random();
        int ends = random.nextInt(9);
        String nums = sdf + String.format("%02d", ends);
        nums = nums.substring(3);
        Long a = Long.parseLong(nums);
       return a;
    }

    public static Long getRandom(int length) {
        String sdf = new SimpleDateFormat("yyyyMMddHHMMSS").format(new Date());
        Random random = new Random();
        int ends = random.nextInt(9);
        String nums = sdf + String.format("%02d", ends);
        nums = nums.substring(length);
        Long a = Long.parseLong(nums);
        return a;
    }




    //length表示生成字符串的长度
    public static String getRandomString(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    public static String getAccountIdByUUId() {
        int machineId = 1;//最大支持1-9个集群机器部署
        int hashCodeV = UUID.randomUUID().toString().hashCode();
        if(hashCodeV < 0) {//有可能是负数
            hashCodeV = - hashCodeV;
        }
        return machineId + String.format("%015d", hashCodeV);
    }

}
