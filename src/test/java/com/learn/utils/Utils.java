package com.learn.utils;

import com.learn.linked.ListNode;
import com.learn.tree.TreeNode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang.ArrayUtils;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

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

    /**
     * 使用层级遍历构造一棵树
     * @param array
     * @return
     */
    public static TreeNode buildBinaryTree(Integer[] array) {
        if (array.length == 0) {
            return null;
        }
        int cur = 0;
        TreeNode root = new TreeNode(array[cur++]);
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while(!queue.isEmpty()) {
            TreeNode tRoot = queue.poll();
            if (tRoot != null) {
                if (cur < array.length) {
                    if (array[cur] == null) {
                        queue.offer(null);
                        cur++;
                    } else {
                        tRoot.left = new TreeNode(array[cur++]);
                        queue.offer(tRoot.left);
                    }
                }
                if (cur < array.length) {
                    if (array[cur] == null) {
                        queue.offer(null);
                        cur++;
                    } else {
                        tRoot.right = new TreeNode(array[cur++]);
                        queue.offer(tRoot.right);
                    }
                }
            }
        }
        return root;
    }

    /**
     * 层级遍历打印一颗数
     * @param root
     */
    public static void printBinaryTree(TreeNode root) {
        if (root == null) {
            return;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            if (node == null) {
                continue;
            }
            log.info(String.valueOf(node.val));
            if (node.left != null) {
                queue.offer(node.left);
            }
            if (node.right != null) {
                queue.offer(node.right);
            }
        }
    }

    /**
     * 层级遍历比较
     * @param root
     * @param array
     * @return
     */
    public static boolean isBinaryTreeArrayEqual(TreeNode root, Integer[] array) {
        if (root == null && ArrayUtils.isEmpty(array)) {
            return true;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        int cur = 0;
        while(!queue.isEmpty()) {
            TreeNode node = queue.poll();
            if (cur < array.length) {
                if (node != null) {
                    if (node.val != array[cur++]) {
                        return false;
                    }
                    queue.offer(node.left);
                    queue.offer(node.right);
                } else {
                    if (null != array[cur++]) {
                        return false;
                    }
                }
            }
        }
        return cur == array.length;
    }
}
