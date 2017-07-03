package com.github.javahao.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * usedfor：用户的信息获取、ThreadLocal的都可封装到此类
 * Created by javahao on 2017/6/23.
 * auth：JavaHao
 */
public abstract class Container {
    private static ContainerInterface c;

    public static void setContainer(ContainerInterface container){
        c=container;
    }

    /**
     * 获取当前用户的ID
     *
     * @return 返回用户ID
     */
    public static String getUserId(){
        return c.getUserId();
    }

    /**
     * 获取当前request对象
     * @return 返回当前请求的request对象
     */
    public static HttpServletRequest getRequest(){
        return c.getRequest();
    }
    /**
     * 获取当前response对象
     * @return 返回当前请求的response对象
     */
    public static HttpServletResponse getResponse(){
        return c.getResponse();
    }

    /**
     * 判断当前用户是否已经登录
     * @return 返回结果
     */
    public static boolean isLogin(){
        return c.isLogin();
    }
}
