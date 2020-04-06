package com.ksying.mybatis.executor;

import com.ksying.mybatis.config.Configuration;
import com.ksying.mybatis.config.MappedStatement;
import com.ksying.mybatis.handler.StatementHandler;
import com.ksying.mybatis.sqlsource.BoundSql;
import com.ksying.mybatis.sqlsource.iface.SqlSource;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="jiace.ksying@gmail.com">jiakai.zhang</a>
 * @version v1.0 , 2020/4/5 16:07
 */
public class SimpleExecutor extends BaseExecutor {
    @Override
    public List<Object> queryFromDataBase(MappedStatement mappedStatement, Configuration configuration, BoundSql boundSql, Object param) {
        List<Object> results = new ArrayList<>();
        // 获取连接
        Connection connection = getConnection(configuration.getDataSource());
        // 获取sql
        String sql = boundSql.getSql();

        StatementHandler statementHandler = configuration.newStatementHandler(mappedStatement.getStatementType());

        Statement statement = statementHandler.prepare(connection, sql);

        statementHandler.parameterize(statement, boundSql, param);

        ResultSet rs = statementHandler.execute(statement, sql);

        results = statementHandler.handleResultSet(mappedStatement, rs);

        return results;
    }

    private Connection getConnection(DataSource dataSource) {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
