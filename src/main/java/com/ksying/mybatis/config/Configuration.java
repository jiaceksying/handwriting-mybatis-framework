package com.ksying.mybatis.config;

import com.ksying.mybatis.executor.CachingExecutor;
import com.ksying.mybatis.executor.Executor;
import com.ksying.mybatis.executor.SimpleExecutor;
import com.ksying.mybatis.handler.PreparedStatementHandler;
import com.ksying.mybatis.handler.StatementHandler;
import lombok.Data;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="jiace.ksying@gmail.com">jiakai.zhang</a>
 * @version v1.0 , 2020/3/26 12:26
 */
@Data
public class Configuration {
    private DataSource dataSource;
    private Map<String, MappedStatement> mappedStatementMap = new HashMap<>();

    // 是否使用缓存
    private boolean userCache = true;

    public MappedStatement getMappedStatementById(String id) {
        return mappedStatementMap.get(id);
    }

    public void addMappedStatementMap(String statementId, MappedStatement mappedStatement) {
        this.mappedStatementMap.put(statementId, mappedStatement);
    }

    public Executor newExecutor() {
        Executor executor = null;
        // 此处具体逻辑参照源码，会根据具体类型创建不同的Executor。SimpleExecutor是默认的。
        executor = new SimpleExecutor();
        if (userCache) {
            executor = new CachingExecutor(executor);
        }
        return executor;
    }

    public StatementHandler newStatementHandler(String statementType) {
        if ("prepared".equals(statementType)) {
            return new PreparedStatementHandler();
        }
        return null;
    }
}
