package com.learn.stack;

import org.junit.Assert;
import org.junit.Test;

import java.util.Stack;

/**
 * @author herryhaorui@didiglobal.com
 * @desc
 * 用两个栈实现队列
 * https://leetcode.cn/problems/yong-liang-ge-zhan-shi-xian-dui-lie-lcof/solutions/103069/mian-shi-ti-09-yong-liang-ge-zhan-shi-xian-dui-l-3/
 * @date 2023/6/26 10:17 下午
 */
public class CQueue {
    private Stack<Integer> stack1;
    private Stack<Integer> stack2;
    public CQueue() {
        stack1 = new Stack<>();
        stack2 = new Stack<>();
    }

    public void appendTail(int value) {
        stack1.push(value);
    }

    public int deleteHead() {
        if (!stack2.isEmpty()) {
            return stack2.pop();
        } else {
            while (!stack1.isEmpty()) {
                stack2.push(stack1.pop());
            }
            return stack2.isEmpty() ? -1 : stack2.pop();
        }
    }

    @Test
    public void testCQueue() {
        CQueue cQueue1 = new CQueue();
        cQueue1.appendTail(3);
        Assert.assertEquals(3, cQueue1.deleteHead());
        Assert.assertEquals(-1, cQueue1.deleteHead());
        Assert.assertEquals(-1, cQueue1.deleteHead());

        CQueue cQueue2 = new CQueue();
        Assert.assertEquals(-1, cQueue2.deleteHead());
        cQueue2.appendTail(5);
        cQueue2.appendTail(2);
        Assert.assertEquals(5, cQueue2.deleteHead());
        Assert.assertEquals(2, cQueue2.deleteHead());
    }
}
