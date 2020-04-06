package com.ksying.mybatis.sqlsource;

import com.ksying.mybatis.sqlnode.iface.SqlNode;
import com.ksying.mybatis.sqlsource.iface.SqlSource;
import com.ksying.mybatis.util.GenericTokenParser;
import com.ksying.mybatis.util.ParameterMappingTokenHandler;

/**
 * 只包含#{}只需要处理一次替换工作，在构造方法中进行
 *
 * @author <a href="jiace.ksying@gmail.com">jiakai.zhang</a>
 * @version v1.0 , 2020/3/26 18:35
 */
public class RawSqlSource implements SqlSource {
    private SqlSource sqlSource;

    public RawSqlSource(SqlNode rootSqlNode) {
        DynamicContext context = new DynamicContext(null);
        // 由于只包含#{}，在预编译的时候就进行 ？ 的替换
        rootSqlNode.apply(context);

        SqlSourceParse sqlSourceParse = new SqlSourceParse();
        sqlSourceParse.parse(context.getSql());



    }

    @Override
    public BoundSql getBoundSql(Object paramObject)  {
        return sqlSource.getBoundSql(paramObject);
    }
}
