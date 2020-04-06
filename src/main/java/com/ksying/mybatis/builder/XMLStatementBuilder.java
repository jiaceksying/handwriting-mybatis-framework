package com.ksying.mybatis.builder;

import com.ksying.mybatis.config.Configuration;
import com.ksying.mybatis.config.MappedStatement;
import com.ksying.mybatis.sqlsource.iface.SqlSource;
import com.ksying.mybatis.util.ReflectUtils;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Element;


/**
 * @author <a href="jiace.ksying@gmail.com">jiakai.zhang</a>
 * @version v1.0 , 2020/4/5 14:12
 */
@AllArgsConstructor
public class XMLStatementBuilder {
    private Configuration configuration;
    private String namespace;

//    public XMLStatementBuilder(Configuration configuration) {
//        this.configuration = configuration;
//    }

    public void parseStatementElement(Element statementElement) {
        String statementId = statementElement.attributeValue("id");
        if (StringUtils.isNotBlank(statementId)) {
            // mybatis statementId 的组成是 namespace.id
            statementId = namespace + "." + statementId;
            // 参数类型
            String paramType = statementElement.attributeValue("parameterType");
            Class<?> parameterTypeClass = ReflectUtils.resolveType(paramType);
            // 返回类型
            String resultType = statementElement.attributeValue("resultType");
            Class<?> resultTypeClass = ReflectUtils.resolveType(resultType);
            // statementType 指定创建的statement类型 prepareStatement statement ..
            String statementType = statementElement.attributeValue("statementType");
            statementType = statementType == null || "".equals(statementType) ? "prepared" : statementType;

            SqlSource sqlSource = createSqlSource(statementElement);

            MappedStatement mappedStatement = new MappedStatement(statementId, sqlSource, statementType, parameterTypeClass, resultTypeClass);

            configuration.addMappedStatementMap(statementId, mappedStatement);
        }
    }

    private SqlSource createSqlSource(Element element) {
        XMLScriptBuild scriptBuild = new XMLScriptBuild();
        SqlSource sqlSource = scriptBuild.parseSqlNode(element);
        return sqlSource;
    }
}
