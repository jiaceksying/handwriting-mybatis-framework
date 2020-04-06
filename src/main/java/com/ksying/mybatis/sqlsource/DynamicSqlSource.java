package com.ksying.mybatis.sqlsource;

import com.ksying.mybatis.sqlnode.iface.SqlNode;
import com.ksying.mybatis.sqlsource.iface.SqlSource;
import com.ksying.mybatis.util.GenericTokenParser;
import com.ksying.mybatis.util.ParameterMappingTokenHandler;
import lombok.AllArgsConstructor;

/**
 * @author <a href="jiace.ksying@gmail.com">jiakai.zhang</a>
 * @version v1.0 , 2020/3/26 18:35
 */
@AllArgsConstructor
public class DynamicSqlSource implements SqlSource {
    /**
     * 因为要在处理sqlSource的时候去处理sqlNode，所以在此保存
     */
    private SqlNode rootSqlNode;

    /**
     * 处理包含 ${} 的sql，只要有${}， 不管包含不包含#{}，都在此处处理
     * @param paramObject
     * @return
     */
    @Override
    public BoundSql getBoundSql(Object paramObject) {
        DynamicContext context = new DynamicContext(paramObject);
        // 将所有的sqlNode拼接为一条未处理 #{} 和 ${} 的 sql 语句
        rootSqlNode.apply(context);
        SqlSourceParse sqlSourceParse = new SqlSourceParse();
        SqlSource sqlSource = sqlSourceParse.parse(context.getSql());
        return sqlSource.getBoundSql(paramObject);
    }
}
