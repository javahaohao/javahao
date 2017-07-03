package com.github.javahao.util;

import cn.org.rapid_framework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * usedfor：容器接口
 * Created by javahao on 2017/6/25.
 * auth：JavaHao
 */
public interface ContainerInterface {
    /**
     * 获取当前登录用户的ID
     * @return 返回结果
     */
    public String getUserId();
    /**
     * 获取当前request对象
     * @return 返回当前请求的request对象
     */
    public HttpServletRequest getRequest();
    /**
     * 获取请求响应
     * @return 响应
     */
    public HttpServletResponse getResponse();

    /**
     * 判断是否已经登录
     * @return 返回结果
     */
    boolean isLogin();
}

