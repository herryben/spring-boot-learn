package com.learn.Utils;

import com.learn.linked.ListNode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.ListUtils;

import java.util.Arrays;
import java.util.List;

/**
 * @author herryhaorui@didiglobal.com
 * @desc
 * @date 2023/4/10 9:29 下午
 */
@Slf4j
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


    public static boolean isEqualArray(int[][] array1, int[][] array2) {
        if (array1 == array2) {
            return true;
        }
        if (array1 == null || array2 == null || array1.length != array2.length) {
            return false;
        }
        for (int i = 0; i < array1.length; i++) {
            if (!Arrays.equals(array1[i], array2[i])) {
                return false;
            }
        }
        return true;
    }

    public static boolean isLinkedListArrayEqual(ListNode head, int[] array) {
        for (int num: array) {
            if (head == null || num != head.val) {
                return false;
            }
            head = head.next;
        }
        return head == null;
    }

    public static ListNode buildLinkedList(int[] array) {
        ListNode dummy = new ListNode(-1);
        ListNode head = dummy;
        for (int num: array) {
            head.next = new ListNode(num);
            head = head.next;
        }
        return dummy.next;
    }

    public static void printLinkedList(ListNode head) {
        for(; head != null; head = head.next) {
            log.info(String.valueOf(head.val));
        }
    }
}
