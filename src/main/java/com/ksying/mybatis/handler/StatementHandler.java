package com.ksying.mybatis.handler;

import com.ksying.mybatis.config.MappedStatement;
import com.ksying.mybatis.sqlsource.BoundSql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

/**
 * @author <a href="jiace.ksying@gmail.com">jiakai.zhang</a>
 * @version v1.0 , 2020/4/5 16:47
 */
public interface StatementHandler {
    Statement prepare(Connection connection, String sql);

    void parameterize(Statement statement, BoundSql boundSql, Object param);

    ResultSet execute(Statement statement, String sql);

    List<Object> handleResultSet(MappedStatement mappedStatement, ResultSet resultSet);
}
