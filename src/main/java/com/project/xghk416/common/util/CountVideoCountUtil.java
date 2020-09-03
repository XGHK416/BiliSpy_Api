package com.project.xghk416.common.util;

import java.util.*;

public class CountVideoCountUtil {
    public static Map<String, Integer> anaTags(List<String> tags) {
        Map<String, Integer> dynamicCount = new HashMap<>();
        for (String item :
                tags) {
            String[] result = item.split("#");
            for (String childItem :
                    result) {
                if (!"".equals(childItem) && childItem.length() < 20 && !" ".equals(childItem)) {
                    if (dynamicCount.containsKey(childItem)) {
                        int count = dynamicCount.get(childItem);
                        dynamicCount.put(childItem, count + 1);
                    } else {
                        dynamicCount.put(childItem, 1);
                    }
                }
            }
        }


        return dynamicCount;
    }
    public static List<Map<String, Object>> formatAll(Map<String, Integer> map){
        return format(map);
    }
    public static List<Map<String, Object>> formatLimit(Map<String, Integer> map){
        Map<String, Integer> treeMap = new TreeMap<>(new compareMapUtil(map));
        treeMap.putAll(map);
        return format(treeMap);

    }
    private static List<Map<String, Object>> format(Map<String, Integer> needFormat){
        List<Map<String, Object>> formatResult = new ArrayList<>();
        int limit = 0;
        for (Map.Entry<String, Integer> entry : needFormat.entrySet()) {
            if (limit <= 27) {
                Map<String, Object> temp = new HashMap<>();
                temp.put("name", entry.getKey());
                temp.put("value", entry.getValue());
                formatResult.add(temp);
                limit++;
            } else {
                break;
            }
        }
        return formatResult;
    }

}