package com.github.javahao.base;

import com.github.javahao.annotation.MyBatisMapper;

import java.util.List;

/**
 * usedfor：DAO层的映射接口，封装了基本的接口方法
 * Created by javahao on 2017/6/19.
 * auth：JavaHao
 */
@MyBatisMapper
public interface BaseMapper<T extends BaseBean> {
    /**
     * 按照条件查询数据
     * @param t
     * @return
     */
    public List<T> list(T t);

    /**
     * 保存数据
     * @param t
     * @return
     */
    public int save(T t);

    /**
     * 修改数据
     * @param t
     * @return
     */
    public int update(T t);

    /**
     * 删除数据
     * @param t
     * @return
     */
    public int delete(T t);

    /**
     * 统计数据量
     * @param t
     * @return
     */
    public int count(T t);
}
