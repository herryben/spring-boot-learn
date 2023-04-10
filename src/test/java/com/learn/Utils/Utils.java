package com.learn.Utils;

import org.apache.commons.collections.ListUtils;

import java.util.List;

/**
 * @author herryhaorui@didiglobal.com
 * @desc
 * @date 2023/4/10 9:29 下午
 */
public class Utils {
    public static boolean isEqualList(List<List> list1, List<List> list2) {
        if (list1 == list2) {
            return true;
        }
        if (list1 == null || list2 == null || list1.size() != list2.size()) {
            return false;
        }
        for (int i = 0; i < list1.size(); i++) {
            if (!ListUtils.isEqualList(list1.get(i), list2.get(i))) {
                return false;
            }
        }
        return true;
    }
}
