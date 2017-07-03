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
     * @param t 查询条件
     * @return 返回查询结果
     * @throws CRUDException
     */
    public T one(T t) throws CRUDException;

    /**
     * 按照条件查询数据
     * @param t
     * @return
     */
    public List<T> list(T t)throws CRUDException;

    /**
     * 按分页查询数据
     * @param t
     * @return
     * @throws CRUDException
     */
    public Page<T> page(T t) throws CRUDException;

    /**
     * 保存数据
     * @param t
     * @return
     */
    public int save(T t)throws CRUDException;

    /**
     * 修改数据
     * @param t
     * @return
     */
    public int update(T t)throws CRUDException;

    /**
     * 删除数据
     * @param t
     * @return
     */
    public int delete(T t)throws CRUDException;

    /**
     * 统计数据量
     * @param t
     * @return
     */
    public int count(T t)throws CRUDException;

}
