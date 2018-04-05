package com.github.javahao.base;

import com.github.javahao.exception.CRUDException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * usedfor：
 * Created by javahao on 2017/6/23.
 * auth：JavaHao
 */
public interface Service<T extends BaseBean> {

    /**
     * 查询一个符合条件的对象
     * @param id 查询条件id
     * @return 返回查询结果
     * @throws CRUDException
     */
    T one(Long id) throws CRUDException;

    /**
     * 查询一个符合条件的对象
     * @param t 查询条件
     * @return 返回查询结果
     * @throws CRUDException
     */
    T one(T t) throws CRUDException;

    /**
     * 按照条件查询数据
     * @param t 查询条件
     * @return 结果
     * @throws CRUDException 异常
     */
    List<T> list(T t)throws CRUDException;

    /**
     * 按分页查询数据
     * @param t 查询条件
     * @return 结果
     * @throws CRUDException 异常
     */
    Page<T> page(T t) throws CRUDException;

    /**
     * 保存数据
     * @param t 保存数据
     * @return 影响条数
     * @throws CRUDException 异常
     */
    int save(T t)throws CRUDException;

    /**
     * 修改数据
     * @param t 修改数据
     * @return 影响条数
     * @throws CRUDException 异常
     */
    int update(T t)throws CRUDException;

    /**
     * 删除数据
     * @param t 删除条件
     * @return 影响条数
     * @throws CRUDException 异常
     */
    int delete(T t)throws CRUDException;

    /**
     * 统计数据量
     * @param t 统计条件
     * @return 数量
     * @throws CRUDException 异常
     */
    int count(T t)throws CRUDException;

    /**
     * 批量修改操作
     * @param list 批处理数据
     * @return 影响条数
     * @throws CRUDException 异常
     */
    int updateBatch(List<T> list)throws CRUDException;

    /**
     * 批量修改操作
     * @param method 代理方法
     * @param list 批处理数据
     * @return 影响条数
     * @throws CRUDException 异常
     */
    int updateBatch(String method,List<T> list)throws CRUDException;

    /**
     * 批量保存操作
     * @param method 代理方法
     * @param list 批处理数据
     * @return 影响条数
     * @throws CRUDException 异常
     */
    int saveBatch(String method,List<T> list)throws CRUDException;

    /**
     * 批量保存操作
     * @param list 批处理数据
     * @return 影响条数
     * @throws CRUDException 异常
     */
    int saveBatch(List<T> list)throws CRUDException;
}
