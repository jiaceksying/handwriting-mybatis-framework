package com.ksying.mybatis.sqlnode;

import com.ksying.mybatis.sqlnode.iface.SqlNode;
import com.ksying.mybatis.sqlsource.DynamicContext;
import com.ksying.mybatis.util.OgnlUtils;
import lombok.AllArgsConstructor;

/**
 * 封装 if 动态标签的 MixedSqlNode
 *
 * @author <a href="jiace.ksying@gmail.com">jiakai.zhang</a>
 * @version v1.0 , 2020/3/26 18:09
 */
@AllArgsConstructor
public class IfSqlNode implements SqlNode {
    private String test;
    private MixedSqlNode mixedSqlNode;

    @Override
    public void apply(DynamicContext context) {
        if (OgnlUtils.evaluateBoolean(test, context.getBinds("_parameter"))) {
            mixedSqlNode.apply(context);
        }
    }
}
