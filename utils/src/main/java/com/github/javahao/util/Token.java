package com.github.javahao.util;

/**
 * usedfor：当前用户登录的一些凭证存储空间
 * Created by javahao on 2017/8/2.
 * auth：JavaHao
 */
public interface Token {
    /**
     * 获取当前操作用户的主键
     * @return 主键值
     */
    Long getId();

    /**
     * 是否登录判断
     * @return 登录标识结果
     */
    boolean isLogin();
}
