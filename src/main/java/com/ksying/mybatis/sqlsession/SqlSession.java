package com.ksying.mybatis.sqlsession;

import java.util.List;

/**
 * 提供CRUD功能
 *
 * @author <a href="jiace.ksying@gmail.com">jiakai.zhang</a>
 * @version v1.0 , 2020/4/5 12:31
 */
public interface SqlSession {
    /**
     * 查询单个数据
     * @param statementId
     * @param paramObject
     * @param <T>
     * @return
     */
    <T> T selectOne(String statementId, Object paramObject);

    /**
     * 查询集合数据
     * @param statementId
     * @param paramObject
     * @param <T>
     * @return
     */
    <T> List<T> selectList(String statementId, Object paramObject);
}
