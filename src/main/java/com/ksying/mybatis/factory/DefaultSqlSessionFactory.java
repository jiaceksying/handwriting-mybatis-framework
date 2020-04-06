package com.ksying.mybatis.factory;

import com.ksying.mybatis.config.Configuration;
import com.ksying.mybatis.sqlsession.DefaultSqlSession;
import com.ksying.mybatis.sqlsession.SqlSession;
import lombok.AllArgsConstructor;

/**
 * @author <a href="jiace.ksying@gmail.com">jiakai.zhang</a>
 * @version v1.0 , 2020/4/5 13:04
 */
@AllArgsConstructor
public class DefaultSqlSessionFactory implements SqlSessionFactory {
    private Configuration configuration;
    @Override
    public SqlSession openSession() {
        return new DefaultSqlSession(configuration);
    }
}
