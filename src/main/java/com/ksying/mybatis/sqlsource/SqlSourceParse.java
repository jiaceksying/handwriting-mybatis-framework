package com.ksying.mybatis.sqlsource;

import com.ksying.mybatis.sqlsource.iface.SqlSource;
import com.ksying.mybatis.util.GenericTokenParser;
import com.ksying.mybatis.util.ParameterMappingTokenHandler;

/**
 * @author <a href="jiace.ksying@gmail.com">jiakai.zhang</a>
 * @version v1.0 , 2020/4/5 15:52
 */
public class SqlSourceParse {
    public SqlSource parse(String sqlText) {
        ParameterMappingTokenHandler tokenHandler = new ParameterMappingTokenHandler();
        GenericTokenParser tokenParser = new GenericTokenParser("#{", "}", tokenHandler);
        String sql = tokenParser.parse(sqlText);

        return new StaticSqlSource(sql, tokenHandler.getParameterMappings());
    }
}
