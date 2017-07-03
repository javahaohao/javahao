package com.github.javahao.util;

/**
 * use for:计算类，避免出现空值造成异常
 * Created by javahao on 2017/1/14.
 * auth:JavaHao
 */
public class CalcUtil {
    /**
     * 两个整数参数乘积
     * @param one 乘数1
     * @param two 乘数2
     * @return 结果
     */
    public static int multiply(Integer one,Integer two){
        return isNull(one)*isNull(two);
    }

    /**
     * 两个整数相加
     * @param one 加数1
     * @param two 加数2
     * @return 结果
     */
    public static int plus(Integer one,Integer two){
        return isNull(one)+isNull(two);
    }

    /**
     * 两数相减
     * @param one 减数1
     * @param two 减数2
     * @return 结果
     */
    public static double sub(Double one,Double two){
        return isNull(one)-isNull(two);
    }

    /**
     * 相减
     * @param one 减数1
     * @param two 减数2
     * @return 结果
     */
    public static Integer sub(Integer one,Integer two){
        return isNull(one)-isNull(two);
    }

    /**
     * 判断是不是空
     * @param i 判断体
     * @return 结果
     */
    public static int isNull(Integer i){
        return null==i?0:i;
    }

    /**
     * 判断是不是空
     * @param d 判断体
     * @return 结果
     */
    public static double isNull(Double d){
        return null==d?0:d;
    }
}
