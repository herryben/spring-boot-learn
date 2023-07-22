package com.learn.linked;

/**
 * @author herryhaorui@didiglobal.com
 * @desc
 * @date 2023/7/8 6:44 下午
 */
public class ListNode {
    public  int val;
    public ListNode next;
    public ListNode() {}
    public  ListNode(int val) { this.val = val; }
    public ListNode(int val, ListNode next) {
        this.val = val; this.next = next;
    }
}
