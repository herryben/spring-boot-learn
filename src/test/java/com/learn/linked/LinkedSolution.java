package com.learn.linked;

import com.learn.Utils.Utils;
import org.junit.Assert;
import org.junit.Test;

import javax.rmi.CORBA.Util;
import java.util.Deque;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Stack;

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

    /**
     * 21. 合并两个有序链表
     * https://leetcode.cn/problems/merge-two-sorted-lists/
     * 将两个升序链表合并为一个新的 升序 链表并返回。新链表是通过拼接给定的两个链表的所有节点组成的。
     * 示例 1：
     * 输入：l1 = [1,2,4], l2 = [1,3,4]
     * 输出：[1,1,2,3,4,4]
     * 示例 2：
     *
     * 输入：l1 = [], l2 = []
     * 输出：[]
     * 示例 3：
     *
     * 输入：l1 = [], l2 = [0]
     * 输出：[0]
     * @param list1
     * @param list2
     * @return
     */
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        if (list1 == null) {
            return list2;
        }
        if (list2 == null) {
            return list1;
        }
        ListNode dummy = new ListNode();
        ListNode tmp = dummy;
        while (list1 != null && list2 != null) {
            if (list1.val > list2.val) {
                tmp.next = list2;
                list2 = list2.next;
            } else {
                tmp.next = list1;
                list1 = list1.next;
            }
            tmp = tmp.next;
        }
        if (list1 != null) {
            tmp.next = list1;
        }
        if (list2 != null) {
            tmp.next = list2;
        }
        return dummy.next;
    }

    @Test
    public void testMergeTwoLists() {
        Assert.assertEquals(true, Utils.isLinkedListArrayEqual(
                mergeTwoLists(Utils.buildLinkedList(new int[]{1 ,2 ,4}), Utils.buildLinkedList(new int[]{1, 3, 4})),
                new int[] {1,1,2,3,4,4}));
        Assert.assertEquals(true, Utils.isLinkedListArrayEqual(
                mergeTwoLists(Utils.buildLinkedList(new int[]{}), Utils.buildLinkedList(new int[]{})),
                new int[] {}));
        Assert.assertEquals(true, Utils.isLinkedListArrayEqual(
                mergeTwoLists(Utils.buildLinkedList(new int[]{}), Utils.buildLinkedList(new int[]{0})),
                new int[] {0}));
    }

    /**
     * 83. 删除排序链表中的重复元素
     * https://leetcode.cn/problems/remove-duplicates-from-sorted-list/description/
     * 给定一个已排序的链表的头 head ， 删除所有重复的元素，使每个元素只出现一次 。返回 已排序的链表 。
     * 示例 1：
     * 输入：head = [1,1,2]
     * 输出：[1,2]
     * 示例 2：
     * 输入：head = [1,1,2,3,3]
     * 输出：[1,2,3]
     * @param head
     * @return
     */
    public ListNode deleteDuplicates(ListNode head) {
        if (head == null || head.next == null) {
            return null;
        }
        for(ListNode cur = head, next = head.next; next != null; next = next.next) {
            if (cur.val == next.val) {
                cur.next = next.next;
            } else {
                cur = next;
            }
        }
        return head;
    }

    @Test
    public void testDeleteDuplicates() {
        Assert.assertEquals(true, Utils.isLinkedListArrayEqual(deleteDuplicates(Utils.buildLinkedList(new int[]{1,1,2})), new int[]{1,2}));
        Assert.assertEquals(true, Utils.isLinkedListArrayEqual(deleteDuplicates(Utils.buildLinkedList(new int[]{1,1,2, 3, 3})), new int[]{1,2,3}));
    }

    /**
     * 206. 反转链表
     * https://leetcode.cn/problems/reverse-linked-list/
     * 给你单链表的头节点 head ，请你反转链表，并返回反转后的链表。
     * 示例 1：
     * 输入：head = [1,2,3,4,5]
     * 输出：[5,4,3,2,1]
     *
     * 示例 2：
     * 输入：head = [1,2]
     * 输出：[2,1]
     *
     * 示例 3：
     *
     * 输入：head = []
     * 输出：[]
     * @param head
     * @return
     */
    public ListNode reverseList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode dummy = new ListNode();
        for (ListNode cur = head, next = cur.next; cur != null; cur = next, next = next != null ? next.next : null) {
            cur.next = dummy.next;
            dummy.next = cur;
        }
        return dummy.next;
    }

    @Test
    public void testReverseList() {
        Assert.assertEquals(true, Utils.isLinkedListArrayEqual(reverseList(Utils.buildLinkedList(new int[]{1,2,3,4,5})), new int[]{5,4,3,2,1}));
        Assert.assertEquals(true, Utils.isLinkedListArrayEqual(reverseList(Utils.buildLinkedList(new int[]{1,2})), new int[]{2,1}));
        Assert.assertEquals(true, Utils.isLinkedListArrayEqual(reverseList(Utils.buildLinkedList(new int[]{})), new int[]{}));
    }

    /**
     * 19. 删除链表的倒数第 N 个结点
     * https://leetcode.cn/problems/remove-nth-node-from-end-of-list/
     * 示例 1：
     * 输入：head = [1,2,3,4,5], n = 2
     * 输出：[1,2,3,5]
     * 示例 2：
     *
     * 输入：head = [1], n = 1
     * 输出：[]
     * 示例 3：
     *
     * 输入：head = [1,2], n = 1
     * 输出：[1]
     * @param head
     * @param n
     * @return
     */
    public ListNode removeNthFromEnd(ListNode head, int n) {
        if (head == null) {
            return null;
        }
        ListNode dummy = new ListNode();
        dummy.next = head;
        ListNode p = dummy, q = dummy;
        for (int i = 0; i < n; i++) {
            q = q.next;
        }
        while (q.next != null) {
            q = q.next;
            p = p.next;
        }
        p.next = p.next.next;
        return dummy.next;
    }
    @Test
    public void testRemoveNthFrom() {
        Assert.assertEquals(true, Utils.isLinkedListArrayEqual(removeNthFromEnd(Utils.buildLinkedList(new int[]{1,2,3,4,5}), 2), new int[]{1, 2, 3, 5}));
        Assert.assertEquals(true, Utils.isLinkedListArrayEqual(removeNthFromEnd(Utils.buildLinkedList(new int[]{1}), 1), new int[]{}));
        Assert.assertEquals(true, Utils.isLinkedListArrayEqual(removeNthFromEnd(Utils.buildLinkedList(new int[]{1,2}), 1), new int[]{1}));
    }

    /**
     * 876. 链表的中间结点
     * https://leetcode.cn/problems/middle-of-the-linked-list/
     * 给你单链表的头结点 head ，请你找出并返回链表的中间结点。
     *
     * 如果有两个中间结点，则返回第二个中间结点。
     * 示例 1：
     * 输入：head = [1,2,3,4,5]
     * 输出：[3,4,5]
     * 解释：链表只有一个中间结点，值为 3 。
     * 示例 2：
     * 输入：head = [1,2,3,4,5,6]
     * 输出：[4,5,6]
     * 解释：该链表有两个中间结点，值分别为 3 和 4 ，返回第二个结点。
     * @param head
     * @return
     */
    public ListNode middleNode(ListNode head) {
        if (head == null) {
            return null;
        }
        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        return slow;
    }

    @Test
    public void testMiddleNode() {
        Assert.assertEquals(true, Utils.isLinkedListArrayEqual(middleNode(Utils.buildLinkedList(new int[]{1,2,3,4,5})), new int[]{3,4,5}));
        Assert.assertEquals(true, Utils.isLinkedListArrayEqual(middleNode(Utils.buildLinkedList(new int[]{1,2,3,4,5,6})), new int[]{4,5,6}));
    }

    /**
     * 160. 相交链表
     * https://leetcode.cn/problems/intersection-of-two-linked-lists/
     * 给你两个单链表的头节点 headA 和 headB ，请你找出并返回两个单链表相交的起始节点。如果两个链表不存在相交节点，返回 null 。
     *
     * 图示两个链表在节点 c1 开始相交：
     *
     *
     *
     * 题目数据 保证 整个链式结构中不存在环。
     *
     * 注意，函数返回结果后，链表必须 保持其原始结构 。
     *
     * 自定义评测：
     *
     * 评测系统 的输入如下（你设计的程序 不适用 此输入）：
     *
     * intersectVal - 相交的起始节点的值。如果不存在相交节点，这一值为 0
     * listA - 第一个链表
     * listB - 第二个链表
     * skipA - 在 listA 中（从头节点开始）跳到交叉节点的节点数
     * skipB - 在 listB 中（从头节点开始）跳到交叉节点的节点数
     * 评测系统将根据这些输入创建链式数据结构，并将两个头节点 headA 和 headB 传递给你的程序。如果程序能够正确返回相交节点，那么你的解决方案将被 视作正确答案 。
     *
     *
     *
     * 示例 1：
     *
     *
     *
     * 输入：intersectVal = 8, listA = [4,1,8,4,5], listB = [5,6,1,8,4,5], skipA = 2, skipB = 3
     * 输出：Intersected at '8'
     * 解释：相交节点的值为 8 （注意，如果两个链表相交则不能为 0）。
     * 从各自的表头开始算起，链表 A 为 [4,1,8,4,5]，链表 B 为 [5,6,1,8,4,5]。
     * 在 A 中，相交节点前有 2 个节点；在 B 中，相交节点前有 3 个节点。
     * — 请注意相交节点的值不为 1，因为在链表 A 和链表 B 之中值为 1 的节点 (A 中第二个节点和 B 中第三个节点) 是不同的节点。换句话说，它们在内存中指向两个不同的位置，而链表 A 和链表 B 中值为 8 的节点 (A 中第三个节点，B 中第四个节点) 在内存中指向相同的位置。
     *
     *
     * 示例 2：
     *
     *
     *
     * 输入：intersectVal = 2, listA = [1,9,1,2,4], listB = [3,2,4], skipA = 3, skipB = 1
     * 输出：Intersected at '2'
     * 解释：相交节点的值为 2 （注意，如果两个链表相交则不能为 0）。
     * 从各自的表头开始算起，链表 A 为 [1,9,1,2,4]，链表 B 为 [3,2,4]。
     * 在 A 中，相交节点前有 3 个节点；在 B 中，相交节点前有 1 个节点。
     * 示例 3：
     *
     *
     *
     * 输入：intersectVal = 0, listA = [2,6,4], listB = [1,5], skipA = 3, skipB = 2
     * 输出：null
     * 解释：从各自的表头开始算起，链表 A 为 [2,6,4]，链表 B 为 [1,5]。
     * 由于这两个链表不相交，所以 intersectVal 必须为 0，而 skipA 和 skipB 可以是任意值。
     * 这两个链表不相交，因此返回 null 。
     * 解题思路：
     * 所以，我们可以让p1遍历完链表A之后开始遍历链表B，让p2遍历完链表B之后开始遍历链表A，这样相当于「逻辑上」两条链表接在了一起。
     * 如果这样进行拼接，就可以让p1和p2同时进入公共部分，也就是同时到达相交节点c1：
     * @param headA
     * @param headB
     * @return
     */
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        ListNode p = headA, q = headB;
        while (p != q) {
            if (p == null) {
                p = headB;
            } else {
                p = p.next;
            }
            if (q == null) {
                q = headA;
            } else {
                q = q.next;
            }
        }
        return p;
    }

    /**
     * 没有构造相同节点 所以测试用例过不了
     */
    @Test
    public void testGetIntersectionNode() {
        Assert.assertEquals(8, getIntersectionNode(Utils.buildLinkedList(new int[]{4,1,8,4,5}), Utils.buildLinkedList(new int[]{5,6,1,8,4,5})).val);
        Assert.assertEquals(2, getIntersectionNode(Utils.buildLinkedList(new int[]{1,9,1,2,4}), Utils.buildLinkedList(new int[]{3,2,4})).val);
        Assert.assertEquals(null, getIntersectionNode(Utils.buildLinkedList(new int[]{2,6,4}), Utils.buildLinkedList(new int[]{1,5})));
    }

    /**
     * 2. 两数相加
     * https://leetcode.cn/problems/add-two-numbers/
     * 给你两个 非空 的链表，表示两个非负的整数。它们每位数字都是按照 逆序 的方式存储的，并且每个节点只能存储 一位 数字。
     *
     * 请你将两个数相加，并以相同形式返回一个表示和的链表。
     *
     * 你可以假设除了数字 0 之外，这两个数都不会以 0 开头。
     * @param l1
     * @param l2
     * @return
     */
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode();
        ListNode p = dummy;
        int carry = 0;
        for (ListNode pa = l1, pb = l2; pa != null || pb != null; pa = pa != null ? pa.next : null, pb = pb != null ? pb.next : null, p = p.next) {
            int a = pa != null ? pa.val : 0;
            int b = pb != null ? pb.val : 0;
            int val = (a + b + carry) % 10;
            carry = (a + b + carry) / 10;
            p.next = new ListNode(val);
        }
        if (carry != 0) {
            p.next = new ListNode(carry);
        }
        return dummy.next;
    }

    @Test
    public void testAddTwoNumbers() {
        Assert.assertEquals(true, Utils.isLinkedListArrayEqual(addTwoNumbers(Utils.buildLinkedList(new int[]{2,4,3}), Utils.buildLinkedList(new int[]{5,6,4})), new int[]{7,0,8}));
        Assert.assertEquals(true, Utils.isLinkedListArrayEqual(addTwoNumbers(Utils.buildLinkedList(new int[]{0}), Utils.buildLinkedList(new int[]{0})), new int[]{0}));
        Assert.assertEquals(true, Utils.isLinkedListArrayEqual(addTwoNumbers(Utils.buildLinkedList(new int[]{9,9,9,9,9,9,9}), Utils.buildLinkedList(new int[]{9,9,9,9})), new int[]{8,9,9,9,0,0,0,1}));
    }

    /**
     * 445. 两数相加 II
     * https://leetcode.cn/problems/add-two-numbers-ii/description/
     * 给你两个 非空 链表来代表两个非负整数。数字最高位位于链表开始位置。它们的每个节点只存储一位数字。将这两数相加会返回一个新的链表。
     *
     * 你可以假设除了数字 0 之外，这两个数字都不会以零开头。
     *
     *
     *
     * 示例1：
     *
     *
     *
     * 输入：l1 = [7,2,4,3], l2 = [5,6,4]
     * 输出：[7,8,0,7]
     * 示例2：
     *
     * 输入：l1 = [2,4,3], l2 = [5,6,4]
     * 输出：[8,0,7]
     * 示例3：
     *
     * 输入：l1 = [0], l2 = [0]
     * 输出：[0]
     * @param l1
     * @param l2
     * @return
     */
    public ListNode addTwoNumbers2(ListNode l1, ListNode l2) {
        if (l1 == null) {
            return l2;
        }
        if (l2 == null) {
            return l1;
        }
        int carry = 0;
        Deque<Integer> stack1 = new LinkedList<>();
        Deque<Integer> stack2 = new LinkedList<>();
        ListNode dummy = new ListNode();
        ListNode p = dummy;
        while (l1 != null) {
            stack1.push(l1.val);
            l1 = l1.next;
        }
        while (l2 != null) {
            stack2.push(l2.val);
            l2 = l2.next;
        }
        while (!stack1.isEmpty() || !stack2.isEmpty()) {
            int a = stack1.isEmpty() ? 0 : stack1.pop();
            int b = stack2.isEmpty() ? 0 : stack2.pop();
            int val = (a + b + carry) % 10;
            carry = (a + b + carry) / 10;
            ListNode tmp = new ListNode(val);
            tmp.next = p.next;
            p.next = tmp;
        }
        if (carry != 0) {
            ListNode tmp = new ListNode(carry);
            tmp.next = p.next;
            p.next = tmp;
        }
        return dummy.next;
    }

    @Test
    public void testAddTwoNumbers2() {
        Assert.assertEquals(true, Utils.isLinkedListArrayEqual(addTwoNumbers2(Utils.buildLinkedList(new int[]{7,2,4,3}), Utils.buildLinkedList(new int[]{5,6,4})), new int[]{7,8,0,7}));
        Assert.assertEquals(true, Utils.isLinkedListArrayEqual(addTwoNumbers2(Utils.buildLinkedList(new int[]{2,4,3}), Utils.buildLinkedList(new int[]{5,6,4})), new int[]{8,0,7}));
        Assert.assertEquals(true, Utils.isLinkedListArrayEqual(addTwoNumbers2(Utils.buildLinkedList(new int[]{0}), Utils.buildLinkedList(new int[]{0})), new int[]{0}));
    }
}
