package com.ksying.mybatis.builder;

import com.ksying.mybatis.config.Configuration;
import com.ksying.mybatis.config.MappedStatement;
import com.ksying.mybatis.io.Resources;
import com.ksying.mybatis.util.DocumentUtil;
import org.apache.commons.dbcp.BasicDataSource;
import org.dom4j.Document;
import org.dom4j.Element;

import java.io.InputStream;
import java.util.List;
import java.util.Properties;

/**
 * @author <a href="jiace.ksying@gmail.com">jiakai.zhang</a>
 * @version v1.0 , 2020/4/5 13:32
 */
public class XMLConfigBuilder {
    private Configuration configuration;

    public XMLConfigBuilder(    ) {
        this.configuration = new Configuration();
    }

    public Configuration parseConfiguration(Element rootElement) {
        // 解析 environment 标签，获取数据源信息，并封装到configuration中
        Element environments = rootElement.element("environments");
        parseEnvironments(environments);
        // 解析 mappers 标签，获取 mapper.xml 路径，进一步解析mapper.xml
        Element mappers = rootElement.element("mappers");
        parseMappers(mappers);
        return configuration;
    }

    private void parseMappers(Element mappers) {
        List<Element> mapperList = mappers.elements();
        mapperList.forEach(
                mapper -> {
                    // 获取各个mapper标签的路径，并解析出各个 mapper.xml 的 document 对象
                    String resource = mapper.attributeValue("resource");
                    InputStream inputStream = Resources.getResourceAsStream(resource);
                    Document document = DocumentUtil.createDocument(inputStream);
                    XMLMapperBuilder MapperBuilder = new XMLMapperBuilder(configuration);
                    MapperBuilder.parseMapper(document.getRootElement());
                }
        );

    }

    private void parseEnvironments(Element environments) {
        String def = environments.attributeValue("default");
        List<Element> elementList = environments.elements();
        elementList
                .forEach(
                        element -> {
                            if (def.equals(element.attributeValue("id"))) {
                                parseDataSource(element);
                            }
                        }
                );
    }

    private void parseDataSource(Element element) {
        Element dataSourceElement = element.element("dataSource");
        String type = dataSourceElement.attributeValue("type");
        if ("DBCP".equals(type)) {
            BasicDataSource dataSource = new BasicDataSource();
            Properties properties = parseProperty(dataSourceElement);
            dataSource.setDriverClassName(properties.getProperty("driver"));
            dataSource.setUrl(properties.getProperty("url"));
            dataSource.setUsername(properties.getProperty("username"));
            dataSource.setPassword(properties.getProperty("password"));
            configuration.setDataSource(dataSource);
        }
    }

    private Properties parseProperty(Element dataSourceElement) {
        Properties properties = new Properties();
        List<Element> propElements = dataSourceElement.elements("property");
        propElements
                .forEach(
                        element -> {
                            String name = element.attributeValue("name");
                            String value = element.attributeValue("value");
                            properties.put(name, value);
                        }
                );
        return properties;
    }

}
