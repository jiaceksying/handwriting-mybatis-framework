package com.ksying.mybatis.builder;

import com.ksying.mybatis.sqlnode.IfSqlNode;
import com.ksying.mybatis.sqlnode.MixedSqlNode;
import com.ksying.mybatis.sqlnode.StaticTextSqlNode;
import com.ksying.mybatis.sqlnode.TextSqlNode;
import com.ksying.mybatis.sqlnode.iface.SqlNode;
import com.ksying.mybatis.sqlsource.DynamicSqlSource;
import com.ksying.mybatis.sqlsource.RawSqlSource;
import com.ksying.mybatis.sqlsource.iface.SqlSource;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="jiace.ksying@gmail.com">jiakai.zhang</a>
 * @version v1.0 , 2020/4/5 14:25
 */
public class XMLScriptBuild {
    private Boolean isDynamic;
    public SqlSource parseSqlNode(Element element) {
        MixedSqlNode rootSqlNode = parseDynamicTags(element);
        SqlSource sqlSource;
        if (isDynamic) {
            sqlSource = new DynamicSqlSource(rootSqlNode);
        } else {
            sqlSource = new RawSqlSource(rootSqlNode);
        }
        return sqlSource;
    }

    private MixedSqlNode parseDynamicTags(Element element) {
        List<SqlNode> sqlNodes = new ArrayList<>();
        // 为了获取文本信息，使用 node， 由 行数判断
        int nodeCount = element.nodeCount();
        for (int i = 0; i < nodeCount; i++) {
            Node node = element.node(i);
            if (node instanceof Text) {
                // 文本信息
                String sqlText = node.getText();
                if (sqlText == null || "".equals(sqlText)) {
                    continue;
                }
                TextSqlNode textSqlNode = new TextSqlNode(sqlText);
                if (textSqlNode.isDynamic(sqlText)) {
                    sqlNodes.add(textSqlNode);
                    isDynamic = true;
                } else {
                    sqlNodes.add(new StaticTextSqlNode(sqlText));
                }
            } else if (node instanceof Element) {
                // 动态标签
                Element ifElement = (Element) node;
                // 动态标签名称 if where...
                String name = node.getName();
                if ("if".equals(name)) {
                    // if 标签条件语句
                    String test = ifElement.attributeValue("test");
                    // if 标签下的mixedSqlNode
                    MixedSqlNode mixedSqlNode = parseDynamicTags(ifElement);
                    IfSqlNode ifSqlNode = new IfSqlNode(test, mixedSqlNode);
                    sqlNodes.add(ifSqlNode);
                }
                // todo where foreach 等标签

            }
        }
        return new MixedSqlNode(sqlNodes);

    }

}
