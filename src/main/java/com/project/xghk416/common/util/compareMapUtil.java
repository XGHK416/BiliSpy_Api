package com.project.xghk416.common.util;

import java.util.Comparator;
import java.util.Map;

public class compareMapUtil implements Comparator<String> {
    private Map<String,Integer> map;
    public compareMapUtil(Map<String, Integer> dynamicCount) {
        this.map = dynamicCount;
    }
    @Override
    public int compare(String obj1, String obj2) {
        return map.get(obj2).compareTo(map.get(obj1));
    }
}
