package com.ksying.mybatis.executor;

import com.ksying.mybatis.config.Configuration;

import java.util.List;

/**
 * @author <a href="jiace.ksying@gmail.com">jiakai.zhang</a>
 * @version v1.0 , 2020/4/5 16:04
 */
public interface Executor {
    <T> List<T> query(String statementId, Configuration configuration, Object param);

}
