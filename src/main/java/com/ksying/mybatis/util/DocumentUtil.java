package com.ksying.mybatis.util;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.io.Reader;

/**
 * @author <a href="jiace.ksying@gmail.com">jiakai.zhang</a>
 * @version v1.0 , 2020/4/5 13:24
 */
public class DocumentUtil {
    public static Document createDocument(InputStream inputStream) {
        try {
            SAXReader saxReader = new SAXReader();
            return saxReader.read(inputStream);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Document createDocument(Reader reader) {
        try {
            SAXReader saxReader = new SAXReader();
            return saxReader.read(reader);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return null;
    }
}
