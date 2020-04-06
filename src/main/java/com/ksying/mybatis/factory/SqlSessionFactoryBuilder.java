package com.ksying.mybatis.factory;

import com.ksying.mybatis.builder.XMLConfigBuilder;
import com.ksying.mybatis.config.Configuration;
import com.ksying.mybatis.util.DocumentUtil;
import org.dom4j.Document;

import java.io.InputStream;
import java.io.Reader;

/**
 * @author <a href="jiace.ksying@gmail.com">jiakai.zhang</a>
 * @version v1.0 , 2020/4/5 13:15
 */
public class SqlSessionFactoryBuilder {
    public SqlSessionFactory build(InputStream inputStream) {
        Document document = DocumentUtil.createDocument(inputStream);
        XMLConfigBuilder configBuilder = new XMLConfigBuilder();
        Configuration configuration = configBuilder.parseConfiguration(document.getRootElement());
        return build(configuration);
    }

    public SqlSessionFactory build(Reader reader) {
        Document document = DocumentUtil.createDocument(reader);
        XMLConfigBuilder configBuilder = new XMLConfigBuilder();
        Configuration configuration = configBuilder.parseConfiguration(document.getRootElement());
        return build(configuration);
    }

    public SqlSessionFactory build(Configuration configuration) {
        return new DefaultSqlSessionFactory(configuration);
    }
}
