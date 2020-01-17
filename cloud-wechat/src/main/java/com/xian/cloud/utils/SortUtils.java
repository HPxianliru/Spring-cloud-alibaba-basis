package com.xian.cloud.utils;

import org.apache.commons.lang3.StringUtils;

import java.net.URLEncoder;
import java.util.*;
import java.util.Map.Entry;

/**
 * <Description>
 *
 * @author xianliru@163.com
 * @version 1.0
 * @createDate 2020/01/08 14:47
 */

public class SortUtils {

    /**
     * @param param 参数
     * @param encode 编码
     * @param isLower 是否小写
     * @return
     */
    public static String formatUrlParam(Map<String, Object> param, String encode, boolean isLower) {
        String params = "";
        Map<String, Object> map = param;

        try {
            List<Map.Entry<String, Object>> itmes = new ArrayList<Map.Entry<String, Object>>(map.entrySet());

            //对所有传入的参数按照字段名从小到大排序
            //Collections.sort(items); 默认正序
            //可通过实现Comparator接口的compare方法来完成自定义排序
            Collections.sort(itmes, new Comparator<Map.Entry<String, Object>>() {
                @Override
                public int compare(Entry<String, Object> o1, Entry<String, Object> o2) {
                    // TODO Auto-generated method stub
                    return (o1.getKey().toString().compareTo(o2.getKey()));
                }
            });

            //构造URL 键值对的形式
            StringBuffer sb = new StringBuffer();
            for (Map.Entry<String, Object> item : itmes) {
                if (StringUtils.isNotBlank(item.getKey())) {
                    String key = item.getKey();
                    String val = String.valueOf(item.getValue());
                    val = URLEncoder.encode(val, encode);
                    if (isLower) {
                        sb.append(key.toLowerCase() + "=" + val);
                    } else {
                        sb.append(key + "=" + val);
                    }
                    sb.append("&");
                }
            }
            params = sb.toString();
            if (!params.isEmpty()) {
                params = params.substring(0, params.length() - 1);
            }
        } catch (Exception e) {
            return "";
        }
        return params;
    }

    public static void main(String[] args) {
        int a=3,b=3;
        for (int i = 0; i < 10; i++) {
            a <<= i;
            int c = a/3;
            System.out.println(i+" "+c+" " +a);
        }

    }
}
