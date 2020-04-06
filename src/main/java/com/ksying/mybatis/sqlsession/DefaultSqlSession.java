package com.ksying.mybatis.sqlsession;

import com.ksying.mybatis.config.Configuration;
import com.ksying.mybatis.executor.Executor;
import lombok.AllArgsConstructor;

import java.util.List;

/**
 * @author <a href="jiace.ksying@gmail.com">jiakai.zhang</a>
 * @version v1.0 , 2020/4/5 12:44
 */
@AllArgsConstructor
public class DefaultSqlSession implements SqlSession {
    private Configuration configuration;

    @Override
    public <T> T selectOne(String statementId, Object paramObject) {
        List<Object> list = this.selectList(statementId, paramObject);
        if (list != null && list.size() == 1) {
            return (T) list.get(0);
        }
        return null;
    }

    @Override
    public <T> List<T> selectList(String statementId, Object paramObject) {
        Executor executor = configuration.newExecutor();
        // 是否使用二级缓存

        // 是否使用一级缓存

        // 使用批处理执行方式/简单执行方式/可重用执行方式

        return executor.query(statementId, configuration, paramObject);
    }
}
