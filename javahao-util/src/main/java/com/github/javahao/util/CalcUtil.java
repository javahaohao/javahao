package com.github.javahao.util;

/**
 * use for:计算类，避免出现空值造成异常
 * Created by javahao on 2017/1/14.
 * auth:JavaHao
 */
public class CalcUtil {
    /**
     * 两个整数参数乘积
     * @param one
     * @param two
     * @return
     */
    public static int multiply(Integer one,Integer two){
        return isNull(one)*isNull(two);
    }

    /**
     * 两个整数相加
     * @param one
     * @param two
     * @return
     */
    public static int plus(Integer one,Integer two){
        return isNull(one)+isNull(two);
    }

    /**
     * 两数相减
     * @param one
     * @param two
     * @return
     */
    public static double sub(Double one,Double two){
        return isNull(one)-isNull(two);
    }
    public static Integer sub(Integer one,Integer two){
        return isNull(one)-isNull(two);
    }

    public static int isNull(Integer i){
        return null==i?0:i;
    }
    public static double isNull(Double d){
        return null==d?0:d;
    }
}
