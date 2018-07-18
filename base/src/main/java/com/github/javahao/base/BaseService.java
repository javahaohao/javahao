package com.github.javahao.base;

import com.github.javahao.exception.CRUDException;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.ParameterizedType;
import java.util.Collections;
import java.util.List;

/**
 * usedfor：所有服务层的基类，封装了基本的CRUD
 * Created by javahao on 2017/6/19.
 * auth：JavaHao
 */
public abstract class BaseService<T extends BaseBean,M extends BaseMapper> implements com.github.javahao.base.Service<T> {
    protected final Logger LOG = LoggerFactory.getLogger(this.getClass());
    /**
     * 默认flushstatements大小
     */
    protected static Long BATCH_LIMIT=1000L;

    @Autowired
    protected M m;

    @Autowired
    protected SqlSessionFactory sqlSessionFactory;

    /**
     * 为了批量处理定义mapperClz
     */
    protected Class<?> mapperClz;
    /**
     * 为了获取entity实例
     */
    protected Class<?> beanClz;
    /**
     * 按照条件查询数据
     * @param t 查询条件
     * @return 返回查询结果
     * @throws CRUDException 异常
     */
    public List<T> list(T t) throws CRUDException {
        try {
            List<T> list = m.list(t);
            if(CollectionUtils.isEmpty(list))
                return Collections.emptyList();
            return list;
        } catch (Exception e) {
            LOG.error("按照条件查询数据失败",e);
            throw new CRUDException(e);
        }
    }

    /**
     * 按照分页查询数据
     * @param t 查询条件
     * @return 返回分页结果
     * @throws CRUDException 异常
     */
    public Page<T> page(T t) throws CRUDException {
        try{
            Page.setPageFromRequest();
            return new Page<T>(m.list(t));
        }catch(Exception e){
            LOG.error("按照分页查询数据失败",e);
            throw new CRUDException(e);
        }
    }

    /**
     * 查询一个符合条件的对象
     *
     * @param id 查询条件id
     * @return 返回查询结果
     * @throws CRUDException
     */
    @Override
    public T one(Long id) throws CRUDException {
        T t = null;
        try {
            t = (T) getEntityTypeClz().newInstance();
            t.setId(id);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return one(t);
    }

    /**
     * 查询一个符合条件的对象
     * @param t 查询条件
     * @return 返回查询结果
     * @throws CRUDException 异常
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
            LOG.error("查询一个符合条件的对象失败",e);
            throw new CRUDException(e);
        }
        return null;
    }

    /**
     * 保存数据
     * @param t 保存对象
     * @return  影响条数
     * @throws CRUDException 异常
     */
    public int save(T t) throws CRUDException {
        try {
            t.preSave();
            return m.save(t);
        } catch (Exception e) {
            LOG.error("保存数据失败",e);
            throw new CRUDException(e);
        }
    }

    /**
     * 修改数据
     * @param t 更新数据
     * @return 影响条数
     * @throws CRUDException 异常
     */
    public int update(T t) throws CRUDException {
        try {
            t.preUpdate();
            return m.update(t);
        } catch (Exception e) {
            LOG.error("修改数据失败",e);
            throw new CRUDException(e);
        }
    }

    /**
     * 删除数据
     * @param t 删除条件
     * @return 影响条数
     * @throws CRUDException 异常
     */
    public int delete(T t) throws CRUDException {
        try {
            return m.delete(t);
        } catch (Exception e) {
            LOG.error("删除数据失败",e);
            throw new CRUDException(e);
        }
    }

    /**
     * 统计数据量
     * @param t 统计条件
     * @return 数量
     * @throws CRUDException 异常
     */
    public int count(T t) throws CRUDException {
        try {
            return m.count(t);
        } catch (Exception e) {
            LOG.error("统计数据量失败",e);
            throw new CRUDException(e);
        }
    }

    /**
     * 批量修改操作
     *
     * @param list 批处理数据
     * @return 影响条数
     * @throws CRUDException 异常
     */
    public int updateBatch(List<T> list) throws CRUDException {
        return updateBatch(String.format("%s.%s", getMapperTypeClz().getName(),"update"),list);
    }

    /**
     * 批量保存操作
     *
     * @param list 批处理数据
     * @return 影响条数
     * @throws CRUDException 异常
     */
    public int saveBatch(List<T> list) throws CRUDException {
        return saveBatch(String.format("%s.%s", getMapperTypeClz().getName(),"save"),list);
    }

    /**
     * 获取entity的clz
     * @return clz
     */
    private Class<?> getEntityTypeClz(){
        if(ObjectUtils.isEmpty(beanClz)){
            beanClz = (Class <?>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        }
        return beanClz;
    }
    /**
     * 获取mapper的clz
     * @return clz
     */
    private Class<?> getMapperTypeClz(){
        if(ObjectUtils.isEmpty(mapperClz)){
            mapperClz = (Class <?>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        }
        return mapperClz;
    }

    /**
     * 批量修改操作
     *
     * @param excuMethod 代理方法
     * @param list   批处理数据
     * @return 影响条数
     * @throws CRUDException 异常
     */
    public int updateBatch(String excuMethod, List<T> list) throws CRUDException {
        SqlSession batchSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
        int i=0;
        try {
            for(T t:list){
                t.preSave();
                i++;
                batchSession.update(excuMethod,t);
                if ((i + 1) % BATCH_LIMIT == 0){
                    batchSession.flushStatements();
                    LOG.debug(String.format("%s batch %s data sizes",excuMethod,i));
                }
            }
            batchSession.flushStatements();
            batchSession.commit();
            batchSession.clearCache();
        } catch (Exception e) {
            LOG.error("批量修改操作失败",e);
            batchSession.rollback();
            throw new CRUDException(e);
        }finally {
            batchSession.close();
        }
        return i;
    }

    /**
     * 批量保存操作
     * @param excuMethod 代理方法
     * @param list   批处理数据
     * @return 影响条数
     * @throws CRUDException 异常
     */
    public int saveBatch(String excuMethod, List<T> list) throws CRUDException {
        SqlSession batchSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
        int i=0;
        try {
            for(T t:list){
                t.preSave();
                i++;
                batchSession.insert(excuMethod,t);
                if ((i + 1) % BATCH_LIMIT == 0){
                    batchSession.flushStatements();
                    LOG.debug(String.format("%s batch %s data sizes",excuMethod,i));
                }
            }
            batchSession.flushStatements();
            batchSession.commit();
            batchSession.clearCache();
        } catch (Exception e) {
            LOG.error("批量保存操作失败",e);
            batchSession.rollback();
            throw new CRUDException(e);
        }finally {
            batchSession.close();
        }
        return i;
    }
}
