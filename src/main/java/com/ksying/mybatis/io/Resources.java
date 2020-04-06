package com.ksying.mybatis.io;

import java.io.InputStream;

/**
 * @author <a href="jiace.ksying@gmail.com">jiakai.zhang</a>
 * @version v1.0 , 2020/4/5 15:32
 */
public class Resources {
    public static InputStream getResourceAsStream(String local) {
        return Resources.class.getClassLoader().getResourceAsStream(local);
    }
}
