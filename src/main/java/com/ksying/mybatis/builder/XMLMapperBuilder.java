package com.ksying.mybatis.builder;

import com.ksying.mybatis.config.Configuration;
import com.ksying.mybatis.config.MappedStatement;
import lombok.AllArgsConstructor;
import org.dom4j.Element;

import java.util.List;

/**
 * @author <a href="jiace.ksying@gmail.com">jiakai.zhang</a>
 * @version v1.0 , 2020/4/5 13:41
 */
public class XMLMapperBuilder {
    private Configuration configuration;
    private String namespace;

    public XMLMapperBuilder(Configuration configuration) {
        this.configuration = configuration;
    }

    public void parseMapper(Element rootElement) {
        namespace = rootElement.attributeValue("namespace");
        // 处理select标签
        List<Element> selectList = rootElement.elements("select");
        selectList.forEach(
            statementElement -> {
                XMLStatementBuilder statementBuilder = new XMLStatementBuilder(configuration, namespace);
                statementBuilder.parseStatementElement(statementElement);
            }
        );
    }
}
