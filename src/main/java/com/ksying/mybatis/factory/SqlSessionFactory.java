package com.ksying.mybatis.factory;

import com.ksying.mybatis.sqlsession.SqlSession;

/**
 * @author <a href="jiace.ksying@gmail.com">jiakai.zhang</a>
 * @version v1.0 , 2020/4/5 13:03
 */
public interface SqlSessionFactory {
    /**
     * 创建 sqlsession
     * @return
     */
    SqlSession openSession();
}
