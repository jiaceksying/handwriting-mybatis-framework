package com.ksying.mybatis.executor;

import com.ksying.mybatis.config.Configuration;
import lombok.AllArgsConstructor;

import java.util.List;

/**
 * 处理二级缓存
 * @author <a href="jiace.ksying@gmail.com">jiakai.zhang</a>
 * @version v1.0 , 2020/4/5 16:09
 */
@AllArgsConstructor
public class CachingExecutor implements Executor {
    private Executor delegate;

    @Override
    public <T> List<T> query(String statementId, Configuration configuration, Object param) {
        //todo 如果开启二级缓存，从二级缓存中取数据

        // 未开启二级缓存，去一级缓存
        return delegate.query(statementId, configuration, param);
    }
}
