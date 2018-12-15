package com.thinkgem.jeesite.modules.sys.utils;

import java.util.*;

public class MapSortByKeyUtils {
    /**
     * 使用 Map按key进行排序
     * @param k_v
     * @return
     */
    public static void sortMapByKey(HashMap<String, Object> k_v,List<Integer> muns){
        List<Map.Entry<String,Object>> list = new ArrayList<Map.Entry<String, Object>>(k_v.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Object>>() {

            @Override
            public int compare(Map.Entry<String, Object> o1,
                               Map.Entry<String, Object> o2) {
                // 升序排序
                return Integer.parseInt(o1.getKey())-Integer.parseInt(o2.getKey());
            }
        });

        for (Map.Entry<String, Object> entry : list) {
            System.out.println(entry.getKey()+"----"+entry.getValue());
            muns.add(Integer.parseInt(entry.getValue()+""));
        }
    }
}
