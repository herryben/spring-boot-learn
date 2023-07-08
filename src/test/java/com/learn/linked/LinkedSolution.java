package com.learn.linked;

import com.learn.Utils.Utils;
import org.junit.Assert;
import org.junit.Test;

import javax.rmi.CORBA.Util;
import java.util.PriorityQueue;

/**
 * @author herryhaorui@didiglobal.com
 * @desc
 * @date 2023/7/8 6:46 下午
 */
public class LinkedSolution {
    @Test
    public void testUtils() {
        ListNode head = Utils.buildLinkedList(new int[] {1, 2, 3, 4, 5, 6, 7});
        Utils.printLinkedList(head);
        Assert.assertTrue(Utils.isLinkedListArrayEqual(head, new int[] {1, 2, 3, 4, 5, 6 ,7}));
    }
    /**
     * 23. 合并 K 个升序链表
     * https://leetcode.cn/problems/merge-k-sorted-lists/
     * 给你一个链表数组，每个链表都已经按升序排列。
     *
     * 请你将所有链表合并到一个升序链表中，返回合并后的链表。
     *
     *
     *
     * 示例 1：
     *
     * 输入：lists = [[1,4,5],[1,3,4],[2,6]]
     * 输出：[1,1,2,3,4,4,5,6]
     * 解释：链表数组如下：
     * [
     *   1->4->5,
     *   1->3->4,
     *   2->6
     * ]
     * 将它们合并到一个有序链表中得到。
     * 1->1->2->3->4->4->5->6
     * 示例 2：
     *
     * 输入：lists = []
     * 输出：[]
     * 示例 3：
     *
     * 输入：lists = [[]]
     * 输出：[]
     * @param lists
     * @return
     */
    public ListNode mergeKLists(ListNode[] lists) {
        ListNode dummy = new ListNode();
        ListNode p = dummy;
        PriorityQueue<ListNode> queue = new PriorityQueue<>((o1, o2) -> o1.val - o2.val);
        for(ListNode head : lists) {
            if (head != null) {
                queue.offer(head);
            }
        }
        while (!queue.isEmpty()) {
            ListNode tmp = queue.poll();
            p.next = tmp;
            if (tmp.next != null) {
                queue.offer(tmp.next);
            }
            p = p.next;
        }
        return dummy.next;
    }

    @Test
    public void testMergeKLists() {
        Assert.assertEquals(true, Utils.isLinkedListArrayEqual(
                mergeKLists(new ListNode[] {Utils.buildLinkedList(new int[]{1, 4, 5}), Utils.buildLinkedList(new int[]{1, 3, 4}), Utils.buildLinkedList(new int[]{2, 6})}),
                new int[] {1,1,2,3,4,4,5,6}));
        Assert.assertEquals(true, Utils.isLinkedListArrayEqual(
                mergeKLists(new ListNode[] {}),
                new int[] {}));
    }
}
