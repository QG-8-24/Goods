package com.com.example.goods.utils;


import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * @date 2020/6/14
 */
public class ListTransform {
    /**
     * 把List转化为逗号分隔的String
     *
     * @param list 待转换的List
     * @return 逗号分隔的String
     */
    public static String listToString(List<Integer> list) {
        if (list == null) {
            return null;
        }
        StringBuilder result = new StringBuilder();
        boolean first = true;
        //第一个前面不拼接","
        for (Integer i : list) {
            if (first) {
                first = false;
            } else {
                result.append(",");
            }
            result.append(i + "");
        }
        return result.toString();
    }

    /**
     * 把逗号分隔的String转化为List
     *
     * @param str 逗号分隔的字符串
     * @return 分隔好的List
     */
    public static List<Integer> stringToList(String str) {
        if (StringUtils.isBlank(str)) {
            return null;
        }
        String[] strs = str.split(",");
        List<Integer> list = new ArrayList<Integer>();
        for (String s : strs) {
            list.add(Integer.parseInt(s));
        }
        return list;
    }
}