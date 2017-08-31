package com.github.javahao.base;

import com.github.javahao.exception.CRUDException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * usedfor：所有服务层的基类，封装了基本的CRUD
 * Created by javahao on 2017/6/19.
 * auth：JavaHao
 */
public abstract class BaseService<T extends BaseBean,M extends BaseMapper> implements com.github.javahao.base.Service<T> {
    protected final transient Log log = LogFactory.getLog(this.getClass());

    @Autowired
    protected M m;

    /**
     * 按照条件查询数据
     * @param t 查询条件
     * @return 返回查询结果
     */
    public List<T> list(T t) throws CRUDException {
        try {
            return m.list(t);
        } catch (Exception e) {
            log.error(e);
            throw new CRUDException(e);
        }
    }

    /**
     * 按照分页查询数据
     * @param t 查询条件
     * @return 返回分页结果
     * @throws CRUDException
     */
    public Page<T> page(T t) throws CRUDException {
        try{
            Page.setPageFromRequest();
            return new Page<T>(m.list(t));
        }catch(Exception e){
            log.error(e);
            throw new CRUDException(e);
        }
    }
    /**
     * 查询一个符合条件的对象
     * @param t 查询条件
     * @return 返回查询结果
     * @throws CRUDException
     */
    public T one(T t) throws CRUDException{
        try {
            List<T> results = m.list(t);
            if(!CollectionUtils.isEmpty(results))
                if(results.size()>1) {
                    throw new CRUDException(this.getClass().getPackage().getName()+
                            ".one() method allowed to found one element,but it's found "+results.size()+" elements!");
                }else
                    return results.get(0);
        } catch (Exception e) {
            log.error(e);
            throw new CRUDException(e);
        }
        return null;
    }

    /**
     * 保存数据
     * @param t 保存对象
     * @return  影响条数
     */
    public int save(T t) throws CRUDException {
        try {
            t.preSave();
            return m.save(t);
        } catch (Exception e) {
            log.error(e);
            throw new CRUDException(e);
        }
    }

    /**
     * 修改数据
     * @param t
     * @return
     */
    public int update(T t) throws CRUDException {
        try {
            t.preUpdate();
            return m.update(t);
        } catch (Exception e) {
            log.error(e);
            throw new CRUDException(e);
        }
    }

    /**
     * 删除数据
     * @param t
     * @return
     */
    public int delete(T t) throws CRUDException {
        try {
            return m.delete(t);
        } catch (Exception e) {
            log.error(e);
            throw new CRUDException(e);
        }
    }

    /**
     * 统计数据量
     * @param t
     * @return
     */
    public int count(T t) throws CRUDException {
        try {
            return m.count(t);
        } catch (Exception e) {
            log.error(e);
            throw new CRUDException(e);
        }
    }
}
