package com.github.javahao.util;

import org.apache.commons.lang3.*;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * use for: 数组工具类
 * Created by javahao on 2016/12/22.
 * auth:JavaHao
 */
public class ArraysUtil {
    /**
     * 将object数组转化为string数组
     * @param objects 转换对象
     * @return 返回转换值
     */
    public static String[] objectToString(Object[] objects){
        if(objects.length<=0)return new String[1];
        String[] result = new String[objects.length];
        for(int i=0;i<objects.length;i++){
            result[i]=String.valueOf(objects[i]);
        }
        return result;
    }
    /**
     * 将string类型数组转换为long类型数组
     *
     * @param array 转换对象
     * @return 返回转换后的值
     */
    public static Long[] arrayToLongArray(String... array) {
        Long[] l = new Long[array.length];
        for (int i = 0; i < array.length; i++) {
            if (org.apache.commons.lang3.StringUtils.isNumeric(array[i])) {
                l[i] = Long.valueOf(array[i]);
            } else {
                l[i] = null;
            }
        }
        return l;
    }

    /**
     * 转化成长整型集合
     *
     * @param array 转换对象
     * @return 获取转换后的值
     */
    public static List<Long> arrayToLongList(String... array) {
        if (null == array || array.length <= 0)
            return new ArrayList<Long>();
        return Arrays.asList(arrayToLongArray(array));
    }

    /**
     * 将long集合类型用指定join串联起来
     *
     * @param items 连接集合
     * @param join 连接分割
     * @return 返回连接结果
     */
    public static String joinLongArray(List<Long> items, String join) {
        if (CollectionUtils.isEmpty(items))
            return "";
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < items.size(); i++) {
            sb.append(items.get(i));
            if (i != items.size() - 1)
                sb.append(join);
        }
        return sb.toString();
    }
    /**
     * 将long集合类型用指定join串联起来
     *
     * @param items 连接集合
     * @param join 连接分割
     * @return 返回连接结果
     */
    public static String joinStringArray(List<String> items, String join) {
        if (CollectionUtils.isEmpty(items))
            return "";
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < items.size(); i++) {
            sb.append(items.get(i));
            if (i != items.size() - 1)
                sb.append(join);
        }
        return sb.toString();
    }

    /**
     * 将long数组类型用指定join串联起来
     *
     * @param items 连接数组
     * @param join 连接分割
     * @return 返回连接结果
     */
    public static String joinLongArray(Long[] items, String join) {
        return joinLongArray(Arrays.asList(items), join);
    }

}
