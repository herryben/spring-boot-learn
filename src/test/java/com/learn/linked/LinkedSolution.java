package com.learn.linked;

import com.learn.utils.Utils;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

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
     * 1. 建一个优先级队列
     * 2. 把所有的head都加进去
     * 3. 每次从队列拿出最小值尾插法
     * 输入：lists = [[]]
     * 输出：[]
     * 解题思路：
     * 1. 新建优先级队列
     * 2. 每次获取头
     * @param lists
     * @return
     */
    public ListNode mergeKLists(ListNode[] lists) {
        ListNode dummy = new ListNode();
        ListNode cur = dummy;
        PriorityQueue<ListNode> queue = new PriorityQueue<>((o1, o2) -> o1.val - o2.val);
        for(ListNode head : lists) {
            if (head != null) {
                queue.offer(head);
            }
        }
        while (!queue.isEmpty()) {
            ListNode head = queue.poll();
            cur.next = head;
            if (head.next != null) {
                queue.offer(head.next);
            }
            cur = cur.next;
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
     * 解题思路：
     * 1. 归并排序中归并的部分
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
                // 等于就跳过
                cur.next = next.next;
            } else {
                // 不等于往前走
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
     * 25. K 个一组翻转链表
     * https://leetcode.cn/problems/reverse-nodes-in-k-group/
     * 给你链表的头节点 head ，每 k 个节点一组进行翻转，请你返回修改后的链表。
     * <p>
     * k 是一个正整数，它的值小于或等于链表的长度。如果节点总数不是 k 的整数倍，那么请将最后剩余的节点保持原有顺序。
     * <p>
     * 你不能只是单纯地改变节点内部的值，而是需要实际进行节点交换。
     * 解题思路：
     * 1. 从dummy开始
     * 2. for循环数够个数
     * 3. 闭区间反转
     * 4. 连上反转后的区间
     *
     * @param head
     * @param k
     * @return
     */
    public ListNode reverseKGroup(ListNode head, int k) {
        ListNode dummy = new ListNode();
        dummy.next = head;
        for (ListNode pre = dummy, end = head; end != null; end = pre.next) {
            // end开始就是区间的第一个，所以从1开始
            for (int i = 1; i < k && end != null; i++) {
                end = end.next;
            }
            // 不够就break
            if (end == null) {
                break;
            }
            pre = reverse(pre, pre.next, end, end.next);
        }
        return dummy.next;
    }

    /**
     * 闭区间反转
     *
     * @param pre   区间的前一个
     * @param start 区间开始
     * @param end   区间结束
     * @param after 区间的后一个
     * @return
     */
    ListNode reverse(ListNode pre, ListNode start, ListNode end, ListNode after) {
        ListNode dummy = new ListNode();
        for (ListNode cur = start, next = cur.next; cur != after/*这里是 after，因为只有after才可能等于null*/; cur = next, next = next == null ? null : next.next) {
            cur.next = dummy.next;
            dummy.next = cur;
        }
        // 头变成了尾，尾变成了头
        // 记录了区间的前一个和后一个，用前一个和后一个把反转的链表连上
        pre.next = end;
        start.next = after;
        dummy.next = null;
        // 返回尾
        return start;
    }

    @Test
    public void testReverseKGroup() {
        Assert.assertEquals(true, Utils.isLinkedListArrayEqual(reverseKGroup(Utils.buildLinkedList(new int[]{1,2,3,4,5}), 2), new int[]{2,1,4,3,5}));
        Assert.assertEquals(true, Utils.isLinkedListArrayEqual(reverseKGroup(Utils.buildLinkedList(new int[]{1,2,3,4,5}), 3), new int[]{3,2,1,4,5}));
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
     * 解题思路：
     * 1. 快慢指针找N节点靠前位置
     *  1.1 为了兼容1个节点的场景，新增dummy节点，人为构造2个节点
     * 2.从N节点靠前位置开始断开
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
        ListNode slow = dummy, fast = dummy;
        for (int i = 0; i < n; i++) {
            fast = fast.next;
        }
        while (fast.next != null) {
            fast = fast.next;
            slow = slow.next;
        }
        ListNode next = slow.next;
        slow.next = next.next;
        next.next = null;
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
     * 解题思路：
     * 1.要找找的是中间靠后的位置，所以不用pre节点记录
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
     * <p>
     * 请你将两个数相加，并以相同形式返回一个表示和的链表。
     * <p>
     * 你可以假设除了数字 0 之外，这两个数都不会以 0 开头。
     * 解题思路：
     * 1.直接模拟
     * 2.进位处理
     *
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
     * 解题思路：
     * 1. 用2个栈逆置列表
     * 2. 模拟加法
     * 3. 头插法
     * 4. 进位处理
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
            // 头插法
            tmp.next = dummy.next;
            dummy.next = tmp;
        }

        // 头插法进位处理
        if (carry != 0) {
            ListNode tmp = new ListNode(carry);
            tmp.next = dummy.next;
            dummy.next = tmp;
        }
        return dummy.next;
    }

    @Test
    public void testAddTwoNumbers2() {
        Assert.assertEquals(true, Utils.isLinkedListArrayEqual(addTwoNumbers2(Utils.buildLinkedList(new int[]{7,2,4,3}), Utils.buildLinkedList(new int[]{5,6,4})), new int[]{7,8,0,7}));
        Assert.assertEquals(true, Utils.isLinkedListArrayEqual(addTwoNumbers2(Utils.buildLinkedList(new int[]{2,4,3}), Utils.buildLinkedList(new int[]{5,6,4})), new int[]{8,0,7}));
        Assert.assertEquals(true, Utils.isLinkedListArrayEqual(addTwoNumbers2(Utils.buildLinkedList(new int[]{0}), Utils.buildLinkedList(new int[]{0})), new int[]{0}));
    }

    /**
     * 86. 分隔链表
     * https://leetcode.cn/problems/partition-list/description/
     * 给你一个链表的头节点 head 和一个特定值 x ，请你对链表进行分隔，使得所有 小于 x 的节点都出现在 大于或等于 x 的节点之前。
     *
     * 你应当 保留 两个分区中每个节点的初始相对位置。
     * 示例 1：
     *
     *
     * 输入：head = [1,4,3,2,5,2], x = 3
     * 输出：[1,2,2,4,3,5]
     * 示例 2：
     *
     * 输入：head = [2,1], x = 2
     * 输出：[1,2]
     * 解题思路：
     * 1. 左右双指针分别递增小于和大于的节点
     * 2. 左右接上
     * 3. 断开右边
     * @param head
     * @param x
     * @return
     */
    public ListNode partition(ListNode head, int x) {
        ListNode leftDummy = new ListNode();
        ListNode rightDummy = new ListNode();
        ListNode left = leftDummy;
        ListNode right = rightDummy;
        for(; head != null; head = head.next) {
            if (head.val < x) {
                left.next = head;
                left = left.next;
            } else {
                right.next = head;
                right = right.next;
            }
        }
        left.next = rightDummy.next;
        // right.next可能还在指向别的节点，不能忘记这一步
        right.next = null;
        return leftDummy.next;
    }

    @Test
    public void testPartition() {
        Assert.assertEquals(true, Utils.isLinkedListArrayEqual(partition(Utils.buildLinkedList(new int[]{1,4,3,2,5,2}), 3), new int[]{1,2,2,4,3,5}));
        Assert.assertEquals(true, Utils.isLinkedListArrayEqual(partition(Utils.buildLinkedList(new int[]{2,1}), 2), new int[]{1,2}));
    }

    /**
     * 24. 两两交换链表中的节点
     * https://leetcode.cn/problems/swap-nodes-in-pairs/
     * 给你一个链表，两两交换其中相邻的节点，并返回交换后链表的头节点。你必须在不修改节点内部的值的情况下完成本题（即，只能进行节点交换）。
     * 示例 1：
     * 输入：head = [1,2,3,4]
     * 输出：[2,1,4,3]
     * 示例 2：
     *
     * 输入：head = []
     * 输出：[]
     * 示例 3：
     *
     * 输入：head = [1]
     * 输出：[1]
     * 解题思路：
     * 1.从dummy节点开始用3个变量pre, cur, next标记4个节点pre, cur, next, next.next
     * 2.一共3步：①pre.next指向cur下个节点 ②cur.next指向next下个节点 ③next.next指回cur
     * 3.pre从dummy开始简化代码逻辑
     * @param head
     * @return
     */
    public ListNode swapPairs(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode dummy = new ListNode();
        dummy.next = head;
        for (ListNode pre = dummy, cur = pre.next, next = cur.next; next != null/*这里是next,因为next是最后一个需要操作的对象next.next=*/; pre = cur, cur = cur.next/*这里开始需要迭代cur，因为关系已经更新过了*/, next = cur != null ? cur.next : null) {
            pre.next = cur.next;
            cur.next = next.next;
            next.next = cur;
        }
        return dummy.next;
    }

    @Test
    public void testSwapPairs() {
        Assert.assertEquals(true, Utils.isLinkedListArrayEqual(swapPairs(Utils.buildLinkedList(new int[]{1, 2, 3, 4})), new int[]{2, 1, 4, 3}));
        Assert.assertEquals(true, Utils.isLinkedListArrayEqual(swapPairs(Utils.buildLinkedList(new int[]{})), new int[]{}));
        Assert.assertEquals(true, Utils.isLinkedListArrayEqual(swapPairs(Utils.buildLinkedList(new int[]{1})), new int[]{1}));
    }

    /**
     * 解题思路
     * 1. 用1，2，3，4法
     * 1.1.next = 3
     * 1.2.next = 4
     * 1.3.next = 2
     * 2. 用dummy做第一个节点简化代码
     *
     * @param head
     * @return
     */
    public ListNode swapPairs2(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode dummy = new ListNode();
        dummy.next = head;
        for (ListNode one = dummy, two = one.next, three = two.next, four = three.next;
             three != null;
             one = two, two = two.next, three = two != null ? two.next : null, four = three != null ? three.next : null) {
            one.next = three;
            two.next = four;
            three.next = two;
        }
        return dummy.next;
    }

    @Test
    public void testSwapPairs2() {
        Assert.assertEquals(true, Utils.isLinkedListArrayEqual(swapPairs2(Utils.buildLinkedList(new int[]{1, 2, 3, 4})), new int[]{2, 1, 4, 3}));
        Assert.assertEquals(true, Utils.isLinkedListArrayEqual(swapPairs2(Utils.buildLinkedList(new int[]{})), new int[]{}));
        Assert.assertEquals(true, Utils.isLinkedListArrayEqual(swapPairs2(Utils.buildLinkedList(new int[]{1})), new int[]{1}));
    }

    /**
     * https://leetcode.cn/problems/remove-duplicates-from-sorted-list-ii/description/
     * 82. 删除排序链表中的重复元素 II
     * 给定一个已排序的链表的头 head ， 删除原始链表中所有重复数字的节点，只留下不同的数字 。返回 已排序的链表 。
     * 解题思路：
     * 1. 2个循环遍历，需要一个pre记录前一个节点
     * 2. 重复了cur直接往前走，相当于跳过
     * 3. 不重复，就放到新的链表上
     * 4. 特殊边界值处理 如果所有元素都重复，此时dummy == pre，要把pre.next置为null
     *
     * @param head
     * @return
     */
    public ListNode deleteDuplicates2(ListNode head) {
        ListNode dummy = new ListNode(Integer.MIN_VALUE);
        dummy.next = head;
        ListNode pre = dummy, cur = dummy.next;
        while (cur != null) {
            boolean rep = false;
            while (cur.next != null && cur.val == cur.next.val) {
                cur = cur.next;
                rep = true;
            }
            if (rep) {
                // 重复cur再往前走一步
                cur = cur.next;
            } else {
                // 不重复2个都往前走
                pre.next = cur;
                pre = pre.next;
                cur = cur.next;
            }
        }

        // 特殊边界值：如果所有元素都重复，此时dummy == pre，要把pre.next置为null
        pre.next = null;
        return dummy.next;
    }

    @Test
    public void testDeleteDuplicates2() {
        Assert.assertEquals(true, Utils.isLinkedListArrayEqual(deleteDuplicates2(Utils.buildLinkedList(new int[]{1,2,3,3,4,4,5})), new int[]{1,2,5}));
        Assert.assertEquals(true, Utils.isLinkedListArrayEqual(deleteDuplicates2(Utils.buildLinkedList(new int[]{1,1,1,2,3})), new int[]{2,3}));
        Assert.assertEquals(true, Utils.isLinkedListArrayEqual(deleteDuplicates2(Utils.buildLinkedList(new int[]{1,1,1})), new int[]{}));
    }

    /**
     * 147. 对链表进行插入排序
     * 给定单个链表的头 head ，使用 插入排序 对链表进行排序，并返回 排序后链表的头 。
     * <p>
     * 插入排序 算法的步骤:
     * <p>
     * 插入排序是迭代的，每次只移动一个元素，直到所有元素可以形成一个有序地输出列表。
     * 每次迭代中，插入排序只从输入数据中移除一个待排序的元素，找到它在序列中适当的位置，并将其插入。
     * 重复直到所有输入数据插入完为止。
     * 下面是插入排序算法的一个图形示例。部分排序的列表(黑色)最初只包含列表中的第一个元素。每次迭代时，从输入数据中删除一个元素(红色)，并就地插入已排序的列表中。
     * 解题思路：
     * 1. 从dummy节点开始查找具体的位置
     * 1.1 dummy节点初始化最大/最小值
     * 2. findPos找到插入位置的前一个节点
     * 3. 穿针引线
     * 3.1 需要next节点记录下一个节点
     * 3.2 当前节点cur = next
     *
     * @param head
     * @return
     */
    public ListNode insertionSortList(ListNode head) {
        // 1.1 dummy节点初始化最大/最小值
        ListNode dummy = new ListNode(Integer.MIN_VALUE);
        for(ListNode cur = head; cur != null;){
            ListNode pos = findPos(dummy, cur.val);
            ListNode next = cur.next;
            cur.next = pos.next;
            pos.next = cur;
            cur = next;
        }
        return dummy.next;
    }

    public ListNode findPos(ListNode head, int val){
        ListNode pre = null;
        for(ListNode cur = head; cur != null && cur.val <= val; pre = cur, cur = cur.next) {

        }
        return pre;
    }

    @Test
    public void testInsertionSortList() {
        Assert.assertEquals(true, Utils.isLinkedListArrayEqual(insertionSortList(Utils.buildLinkedList(new int[]{4, 2, 1, 3})), new int[]{1, 2, 3, 4}));
        Assert.assertEquals(true, Utils.isLinkedListArrayEqual(insertionSortList(Utils.buildLinkedList(new int[]{-1, 5, 3, 4, 0})), new int[]{-1, 0, 3, 4, 5}));
    }

    /**
     * 148. 排序链表
     * https://leetcode.cn/problems/sort-list/
     * 给你链表的头结点 head ，请将其按 升序 排列并返回 排序后的链表 。
     * <p>
     * 输入：head = [4,2,1,3]
     * 输出：[1,2,3,4]
     * 示例 2：
     * 输入：head = [-1,5,3,4,0]
     * 输出：[-1,0,3,4,5]
     * 示例 3：
     * 输入：head = []
     * 输出：[]
     * 解题思路：
     * 1. 用dummy节点找到中间靠前节点
     * 2. 二路归并排序
     * 2.1 先找到中间节点，然后归并
     *
     * @param head
     * @return
     */
    public ListNode sortList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode dummy = new ListNode();
        dummy.next = head;
        ListNode fast = dummy, slow = dummy;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        ListNode before = head;
        ListNode after = slow.next;
        slow.next = null;

        before = sortList(before);
        after = sortList(after);

        dummy = new ListNode();
        ListNode cur = dummy;
        while (before != null && after != null) {
            if (before.val < after.val) {
                cur.next = before;
                cur = cur.next;
                before = before.next;
            } else {
                cur.next = after;
                cur = cur.next;
                after = after.next;
            }
        }

        if (before != null) {
            cur.next = before;
        }

        if (after != null) {
            cur.next = after;
        }
        return dummy.next;
    }

    @Test
    public void testSortList() {
        Assert.assertEquals(true, Utils.isLinkedListArrayEqual(sortList(Utils.buildLinkedList(new int[]{4, 2, 1, 3})), new int[]{1, 2, 3, 4}));
        Assert.assertEquals(true, Utils.isLinkedListArrayEqual(sortList(Utils.buildLinkedList(new int[]{-1, 5, 3, 4, 0})), new int[]{-1, 0, 3, 4, 5}));
    }

    public static ListNode reverse(ListNode head) {
        ListNode dummy = new ListNode();
        for (ListNode cur = head, next = cur.next;
             cur != null; cur = next, next = next != null ?
                next.next : null) {
            cur.next = dummy.next;
            dummy.next = cur;
        }
        return dummy.next;
    }

    /**
     * 143. 重排链表
     * https://leetcode.cn/problems/reorder-list/
     * 给定一个单链表 L 的头节点 head ，单链表 L 表示为：
     * <p>
     * L0 → L1 → … → Ln - 1 → Ln
     * 请将其重新排列后变为：
     * <p>
     * L0 → Ln → L1 → Ln - 1 → L2 → Ln - 2 → …
     * 不能只是单纯的改变节点内部的值，而是需要实际的进行节点交换。
     * 示例 1：
     * 输入：head = [1,2,3,4]
     * 输出：[1,4,2,3]
     * <p>
     * 示例 2：
     * 输入：head = [1,2,3,4,5]
     * 输出：[1,5,2,4,3]
     * 解题思路：
     * 1.查找中间位置节点
     * 1.1 要找的是中间节点靠前的位置，所以需要一个pre节点记录
     * 1.2 找中间节点标准格式：while(fast != null && fast.next != null)
     * 2.从中间靠前位置断开
     * 3.从中间靠后位置逆置
     * 4.新建dummy节点合并
     *
     * @param head
     */
    public ListNode reorderList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        // 1.1 要找的是中间节点靠前的位置，所以需要一个pre节点记录
        // 1.2 找中间节点标准格式：while(fast != null && fast.next != null)
        ListNode fast = head, slow = head, pre = slow;
        while (fast != null && fast.next != null) {
            pre = slow;
            slow = slow.next;
            fast = fast.next.next;
        }

        // 2.从中间靠前位置断开
        pre.next = null;
        ListNode afterHead = slow;
        // 3.从中间靠后位置逆置
        afterHead = reverse(afterHead);
        ListNode dummy = new ListNode();
        ListNode after = dummy;
        while (head != null || afterHead != null) {
            if (head != null) {
                after.next = head;
                head = head.next;
                after = after.next;
            }

            if (afterHead != null) {
                after.next = afterHead;
                afterHead = afterHead.next;
                after = after.next;
            }
        }

        return dummy.next;
    }

    @Test
    public void testReorderList() {
        Assert.assertEquals(true, Utils.isLinkedListArrayEqual(reorderList(Utils.buildLinkedList(new int[]{1, 2, 3, 4})), new int[]{1, 4, 2, 3}));
        Assert.assertEquals(true, Utils.isLinkedListArrayEqual(reorderList(Utils.buildLinkedList(new int[]{1, 2, 3, 4, 5})), new int[]{1, 5, 2, 4, 3}));
    }

    /**
     * 61. 旋转链表
     * https://leetcode.cn/problems/rotate-list/description/
     * 给你一个链表的头节点 head ，旋转链表，将链表每个节点向右移动 k 个位置。
     * 示例 1：
     * 输入：head = [1,2,3,4,5], k = 2
     * 输出：[4,5,1,2,3]
     * 示例 2：
     * 输入：head = [0,1,2], k = 4
     * 输出：[2,0,1]
     *
     * @param head
     * @param k
     * @return 解题思路：
     * 1. 算出链表的长度
     * 2. 计算出剩余的步数step = len - k % len
     * 3. 头尾相接
     * 4. 走过step，断开
     */
    public ListNode rotateRight(ListNode head, int k) {
        int len = 1;
        ListNode h = head;
        for (; h.next != null; h = h.next) {
            len++;
        }
        int step = len - k % len;
        h.next = head;
        for (int i = 0; i < step; i++) {
            h = h.next;
        }
        head = h.next;
        h.next = null;
        return head;
    }

    @Test
    public void testRotateRight() {
        Assert.assertEquals(true, Utils.isLinkedListArrayEqual(rotateRight(Utils.buildLinkedList(new int[]{1, 2, 3, 4, 5}), 2), new int[]{4, 5, 1, 2, 3}));
        Assert.assertEquals(true, Utils.isLinkedListArrayEqual(rotateRight(Utils.buildLinkedList(new int[]{0, 1, 2}), 4), new int[]{2, 0, 1}));
    }

    /**
     * 92. 反转链表 II
     * https://leetcode.cn/problems/reverse-linked-list-ii/description/
     * 给你单链表的头指针 head 和两个整数 left 和 right ，其中 left <= right 。请你反转从位置 left 到位置 right 的链表节点，返回 反转后的链表 。
     * 示例 1：
     * 输入：head = [1,2,3,4,5], left = 2, right = 4
     * 输出：[1,4,3,2,5]
     * 示例 2：
     * 输入：head = [5], left = 1, right = 1
     * 输出：[5]
     *
     * @param head
     * @param left
     * @param right 解题思路：
     *              1. dummy节点开始，for循环计数 left - 1步到达第left节点前一个
     *              2. for循环奇数剩下的right - left
     *              3. 头插法 + 穿针引线法
     *              3.1 穿针引线 3步
     *              ① cur.next = next.next;
     *              ② next.next = pre.next
     *              ③ pre.next = next
     * @return
     */
    public ListNode reverseBetween(ListNode head, int left, int right) {
        ListNode dummy = new ListNode();
        dummy.next = head;
        ListNode pre = dummy;
        for (int i = 0; i < left - 1; i++) {
            pre = pre.next;
        }

        ListNode cur = pre.next;
        for (int i = 0; i < right - left; i++) {
            ListNode next = cur.next;
            cur.next = next.next;
            next.next = pre.next;
            pre.next = next;
        }
        return dummy.next;
    }

    @Test
    public void testReverseBetween() {
        Assert.assertTrue(Utils.isLinkedListArrayEqual(reverseBetween(Utils.buildLinkedList(new int[]{1, 2, 3, 4, 5}), 2, 4), new int[]{1, 4, 3, 2, 5}));
        Assert.assertTrue(Utils.isLinkedListArrayEqual(reverseBetween(Utils.buildLinkedList(new int[]{5}), 1, 1), new int[]{5}));
    }

    /**
     * 138. 随机链表的复制
     * https://leetcode.cn/problems/copy-list-with-random-pointer/description/?envType=study-plan-v2&envId=top-100-liked
     * 给你一个长度为 n 的链表，每个节点包含一个额外增加的随机指针 random ，该指针可以指向链表中的任何节点或空节点。
     * <p>
     * 构造这个链表的 深拷贝。 深拷贝应该正好由 n 个 全新 节点组成，其中每个新节点的值都设为其对应的原节点的值。新节点的 next 指针和 random 指针也都应指向复制链表中的新节点，并使原链表和复制链表中的这些指针能够表示相同的链表状态。复制链表中的指针都不应指向原链表中的节点 。
     * <p>
     * 例如，如果原链表中有 X 和 Y 两个节点，其中 X.random --> Y 。那么在复制链表中对应的两个节点 x 和 y ，同样有 x.random --> y 。
     * <p>
     * 返回复制链表的头节点。
     * <p>
     * 用一个由 n 个节点组成的链表来表示输入/输出中的链表。每个节点用一个 [val, random_index] 表示：
     * <p>
     * val：一个表示 Node.val 的整数。
     * random_index：随机指针指向的节点索引（范围从 0 到 n-1）；如果不指向任何节点，则为  null 。
     * 你的代码 只 接受原链表的头节点 head 作为传入参数。
     * 示例 1：
     * 输入：head = [[7,null],[13,0],[11,4],[10,2],[1,0]]
     * 输出：[[7,null],[13,0],[11,4],[10,2],[1,0]]
     * 示例 2：
     * 输入：head = [[1,1],[2,1]]
     * 输出：[[1,1],[2,1]]
     * 示例 3：
     * 输入：head = [[3,null],[3,0],[3,null]]
     * 输出：[[3,null],[3,0],[3,null]]
     * 解题思路：
     * 1. 直接先把所有的都复制出来
     * 2. 构造新链表
     *
     * @param head
     * @return
     */
    public Node copyRandomList(Node head) {
        if (head == null) {
            return head;
        }
        Map<Node, Node> map = new HashMap<>();

        for (Node cur = head; cur != null; cur = cur.next) {
            map.put(cur, new Node(cur.val));
        }

        for (Node cur = head; cur != null; cur = cur.next) {
            map.get(cur).next = map.get(cur.next);
            map.get(cur).random = map.get(cur.random);
        }

        return map.get(head);
    }
}
