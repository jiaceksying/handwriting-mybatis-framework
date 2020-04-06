package com.ksying.mybatis.handler;

import com.ksying.mybatis.config.MappedStatement;
import com.ksying.mybatis.sqlsource.BoundSql;
import com.ksying.mybatis.sqlsource.ParameterMapping;
import com.ksying.mybatis.util.SimpleTypeRegistry;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="jiace.ksying@gmail.com">jiakai.zhang</a>
 * @version v1.0 , 2020/4/5 16:59
 */
public class PreparedStatementHandler implements StatementHandler {
    @Override
    public Statement prepare(Connection connection, String sql) {
        try {
            return connection.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void parameterize(Statement statement, BoundSql boundSql, Object param) {
        try {
            PreparedStatement preparedStatement = (PreparedStatement) statement;
            Class<?> parameterTypeClass = param.getClass();
            if (SimpleTypeRegistry.isSimpleType(parameterTypeClass)) {
                preparedStatement.setObject(1, param);
            } else {
                List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
                for (int i = 0; i < parameterMappings.size(); i++) {
                    Field field = parameterTypeClass.getDeclaredField(parameterMappings.get(i).getName());
                    field.setAccessible(true);
                    Object value = field.get(param);
                    preparedStatement.setObject(i + 1, value);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ResultSet execute(Statement statement, String sql) {
        try {
            PreparedStatement preparedStatement = (PreparedStatement) statement;
            return preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Object> handleResultSet(MappedStatement mappedStatement, ResultSet rs) {
        try {
            List<Object> results = new ArrayList<>();
            Class<?> resultTypeClass = mappedStatement.getResultTypeClass();
            Object result;
            while (rs.next()) {
                result = resultTypeClass.newInstance();
                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    Field field = resultTypeClass.getDeclaredField(columnName);
                    field.setAccessible(true);
                    field.set(result, rs.getObject(columnName));
                }
                results.add(result);
            }
            return results;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
