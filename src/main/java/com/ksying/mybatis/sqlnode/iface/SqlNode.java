package com.ksying.mybatis.sqlnode.iface;

import com.ksying.mybatis.sqlsource.DynamicContext;

/**
 * @author <a href="jiace.ksying@gmail.com">jiakai.zhang</a>
 * @version v1.0 , 2020/3/26 17:38
 */
public interface SqlNode {
    public void apply(DynamicContext context);
}
