package com.com.example.goods.utils;


import com.alibaba.fastjson.JSON;


/**
 * @author MQ
 */
public class JsonUtils {

    public static <T> T string2Object(String jsonString, Class<T> clazz) {
        T t = null;
        try {
            t = JSON.parseObject(jsonString, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

    public static String object2String(Object o) {
        String string = null;
        try {
            string = JSON.toJSONString(o);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return string;
    }
}


