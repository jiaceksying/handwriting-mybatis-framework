package com.ksying.mybatis.executor;

import com.ksying.mybatis.config.Configuration;
import com.ksying.mybatis.config.MappedStatement;
import com.ksying.mybatis.sqlsource.BoundSql;
import com.ksying.mybatis.sqlsource.iface.SqlSource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="jiace.ksying@gmail.com">jiakai.zhang</a>
 * @version v1.0 , 2020/4/5 16:29
 */
public abstract class BaseExecutor implements Executor{
    private Map<String, List<Object>> oneLevelCache = new HashMap<>();

    @Override
    public <T> List<T> query(String statementId, Configuration configuration, Object param) {
        MappedStatement mappedStatement = configuration.getMappedStatementById(statementId);
        SqlSource sqlSource = mappedStatement.getSqlSource();
        BoundSql boundSql = sqlSource.getBoundSql(param);
        List<Object> list = oneLevelCache.get(boundSql.getSql());
        if (list != null && list.size() > 0) {
            return (List<T>) list;
        }
        list = queryFromDataBase(mappedStatement, configuration, boundSql, param);
        oneLevelCache.put(boundSql.getSql(), list);
        return (List<T>) list;
    }

    public abstract List<Object> queryFromDataBase(MappedStatement mappedStatement, Configuration configuration, BoundSql boundSql, Object param);
}
