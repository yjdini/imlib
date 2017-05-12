package com.ini.data.utils;

import static org.hibernate.criterion.Example.create;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;


import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Somnus`L on 2017/5/4.
 */
public class BaseDao {
    @Autowired
    private SessionFactory _sessionFactory;

    private Session getSession() {
        return _sessionFactory.getCurrentSession();
    }

    //保存相应的Bean
    public Integer save(Object entity) {
        Integer id = (Integer) getSession().save(entity);
        return id;
    }

    //保存相应的Bean
    public void saveOrUpdate(Object entity) {
        getSession().saveOrUpdate(entity);
    }

    //更新相应的bean
    public void update(Object entity) {
        getSession().update(entity);
    }

    //删除相应的bean
    public void delete(Object bean) {
        getSession().delete(bean);
    }

    //根据相应的bean类型和主键ID，删除相应的bean
    public void delete(Class<?> cls, Integer id) {
        Object object = (Object) getSession().get(cls, id);
        getSession().delete(object);
    }

    //根据主键ID，获取bean对象
    public Object findById(Class<?> cls, Integer id) {
        return getSession().get(cls, id);
    }

    //获取指定bean对应的表的记录总数
    public int getTotalCount(Class<?> cls) {
        return ((BigInteger) getSession()
                .createSQLQuery("select count(*) from " + cls.getClass().getSimpleName())
                .list().get(0)).intValue();
    }

    //返回给定SQL获取的数值，如最大ID、总记录数等，sql应该是类似select count(1)..., select max(id)...等
    public int getNumBySQL(String sql){
        int returnNum = 0;
        List resultList = getSession().createSQLQuery(sql).list();
        if (resultList != null && resultList.size() > 0) {
            Object obj = resultList.get(0);
            if (obj instanceof Integer)
                returnNum = ((Integer) obj).intValue();
            else if (obj instanceof BigInteger)
                returnNum = ((BigInteger) obj).intValue();
            else if (obj instanceof Long)
                returnNum = ((Long) obj).intValue();
            else if (obj instanceof BigDecimal)
                returnNum = ((BigDecimal) obj).intValue();
            else if (obj instanceof Double)
                returnNum = ((Double) obj).intValue();
            else if (obj instanceof Float)
                returnNum = ((Float) obj).intValue();
        }
        return returnNum;
    }

    /**
     * 根据SQL获取数值，如总金额等，sql应该类似select sum(amount)...等
     * @param sql
     * @return
     */
    public BigDecimal getAmountBySQL(String sql){
        BigDecimal amount = null;
        Object obj = getSession().createSQLQuery(sql).list().get(0);
        if (obj instanceof Integer)
            amount = new BigDecimal(((Integer) obj).intValue());
        else if (obj instanceof BigInteger)
            amount = new BigDecimal((BigInteger)obj);
        else if (obj instanceof Long)
            amount = new BigDecimal(((Long)obj).longValue());
        else if (obj instanceof BigDecimal)
            amount = (BigDecimal)obj;
        else if (obj instanceof Double)
            amount = new BigDecimal(((Double)obj).doubleValue());
        return amount;
    }


    /**
     * 根据SQL语句，获取查询列表
     * @param sql
     * @param param
     * @return
     */
    public List<?> queryBySQL(String sql, String[] param, Class entity) {
        List<?> list = null;
        SQLQuery query = getSession().createSQLQuery(sql);
        if (entity != null)
            query.addEntity(entity);
        if (param != null) {
            for (int i = 0; i < param.length; i++) {
                query.setString(i, param[i]);
            }
        }
        list = query.list();
        return list;
    }

    /**
     * 根据指定的Bean的匹配条件，返回相应表的记录集
     * @param obj
     * @return
     */
    public List<?> findByExample(Object obj) {
        return (List<?>) getSession()
                .createCriteria(obj.getClass())
                .add(create(obj)).list();
    }

    /**
     * 根据指定的Bean的匹配条件，返回相应的第一条记录
     * @param obj
     * @return
     */
    public Object findFirstByExample(Object obj){
        Object result = null;
        List resultList = this.findByExample(obj);
        if (resultList != null && resultList.size() > 0){
            result = resultList.get(0);
        }
        return result;
    }


    /**
     * 根据指定的sql进行查询，并返回查询结果
     * @param sql, JAVABEAN
     * @return 查询结果
     * @throws Exception
     */
    public List<?> findBySQL(String sql) throws Exception {
        return getSession().createSQLQuery(sql).list();
    }

    /**
     * 根据指定的sql进行查询，并返回查询结果
     * @param sql, JAVABEAN
     * @return 查询结果
     * @throws Exception
     */
    public List<?> findBySQL(String sql, Class entity) throws Exception {
        return getSession().createSQLQuery(sql).addEntity(entity).list();
    }

    /**
     * 根据SQL获取分页查询结果
     * @param sql
     * @param searchVO
     * @return
     */
    public List<?> pageQueryBySQL(String sql, BaseSearchVO searchVO, Class entity) throws Exception{
        List<?> list = new ArrayList<Object>();
        String currentPage = searchVO.getCurrentPage();
        if (currentPage == null || "null".equals(currentPage) || "".equals(currentPage)) {
            currentPage = "0";
        }
        int pageSize = searchVO.getPageSize(); //被调用的地方，可以另行设置每页行数
        int startNum = new Integer(currentPage).intValue() * pageSize;

        Query query = getSession().createSQLQuery(sql).addEntity(entity).setFirstResult(startNum).setMaxResults(pageSize);
        return query.list();
    }

    /**
     * 根据SQL获取分页查询结果,直接获取array内的变量是普通list[],而非对象{}
     * @param sql
     * @param searchVO
     * @return
     */
    public List<?> pageQueryBySQL(String sql, BaseSearchVO searchVO) throws Exception{
        String currentPage = searchVO.getCurrentPage();
        if (currentPage == null || "null".equals(currentPage) || "".equals(currentPage)) {
            currentPage = "0";
        }
        int pageSize = searchVO.getPageSize(); //被调用的地方，可以另行设置每页行数
        int startNum = new Integer(currentPage).intValue() * pageSize;

        Query query = getSession().createSQLQuery(sql).setFirstResult(startNum).setMaxResults(pageSize);
        return query.list();
    }




}
