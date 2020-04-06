package com.ksying.mybatis.util;

/**
 * @author <a href="jiace.ksying@gmail.com">jiakai.zhang</a>
 * @version v1.0 , 2020/4/5 14:19
 */
public class ReflectUtils {
    public static Class<?> resolveType(String paramType) {
        try {
            return Class.forName(paramType);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
