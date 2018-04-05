package com.github.javahao.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * usedfor：用户空间，可以存储一些当前用户的信息和当前request、response、session
 * Created by javahao on 2017/7/27.
 * auth：JavaHao
 */
public class UserSpace {
    private static ThreadLocal<HttpServletRequest> request = new ThreadLocal<HttpServletRequest>();
    private static ThreadLocal<HttpServletResponse> response = new ThreadLocal<HttpServletResponse>();
    private static ThreadLocal<HttpSession> session = new ThreadLocal<HttpSession>();
    private static ThreadLocal<Token> token = new ThreadLocal<Token>();;

    /**
     * 获取当前request对象
     * @return request
     */
    public static HttpServletRequest getRequest() {
        return request.get();
    }

    /**
     * 设置当前request对象
     * @param request request参数
     */
    public static void setRequest(HttpServletRequest request) {
        UserSpace.request.set(request);
    }

    /**
     * 获取当前response对象
     * @return 返回response
     */
    public static HttpServletResponse getResponse() {
        return response.get();
    }

    /**
     * 设置当前response对象
     * @param response 设置response
     */
    public static void setResponse(HttpServletResponse response) {
        UserSpace.response.set(response);
    }

    /**
     * 获取当前session对象
     * @return session值
     */
    public static HttpSession getSession() {
        return session.get();
    }

    /**
     * 设置当前session对象
     * @param session 设置session
     */
    public static void setSession(HttpSession session) {
        UserSpace.session.set(session);
    }

    /**
     * 判断当前用户是否登录
     * @return 是否登录标识
     */
    public static boolean isLogin(){
        Token t = token.get();
        if(t!=null)
            return t.isLogin();
        return false;
    }

    /**
     * 获取操作人的ID
     * @return id值
     */
    public static Long getId(){
        Token t = token.get();
        if(t!=null)
            return t.getId();
        return null;
    }

    /**
     * 设置当前token
     * @param token 参数
     */
    public static void setToken(Token token) {
        UserSpace.token.set(token);
    }
}
