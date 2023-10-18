package com.learn.stack;

import org.junit.Assert;
import org.junit.Test;

import java.util.Deque;
import java.util.LinkedList;

/**
 * 155. 最小栈
 * https://leetcode.cn/problems/min-stack/description/?envType=study-plan-v2&envId=top-100-liked
 * 设计一个支持 push ，pop ，top 操作，并能在常数时间内检索到最小元素的栈。
 * <p>
 * 实现 MinStack 类:
 * <p>
 * MinStack() 初始化堆栈对象。
 * void push(int val) 将元素val推入堆栈。
 * void pop() 删除堆栈顶部的元素。
 * int top() 获取堆栈顶部的元素。
 * int getMin() 获取堆栈中的最小元素。
 * <p>
 * <p>
 * 示例 1:
 * <p>
 * 输入：
 * ["MinStack","push","push","push","getMin","pop","top","getMin"]
 * [[],[-2],[0],[-3],[],[],[],[]]
 * <p>
 * 输出：
 * [null,null,null,null,-3,null,0,-2]
 * <p>
 * 解释：
 * MinStack minStack = new MinStack();
 * minStack.push(-2);
 * minStack.push(0);
 * minStack.push(-3);
 * minStack.getMin();   --> 返回 -3.
 * minStack.pop();
 * minStack.top();      --> 返回 0.
 * minStack.getMin();   --> 返回 -2.
 * 解题思路:
 * 1. 辅助栈，同时更新最小值
 * 2. 额外新增一个字段记录最小值
 */
public class MinStack {
    Deque<Integer> stack;
    Deque<Integer> helperStack;

    public MinStack() {
        stack = new LinkedList<>();
        helperStack = new LinkedList<>();
        helperStack.push(Integer.MAX_VALUE);
    }

    public void push(int val) {
        stack.push(val);
        helperStack.push(Math.min(val, helperStack.peek()));
    }

    public void pop() {
        stack.pop();
        helperStack.pop();
    }

    public int top() {
        return stack.peek();
    }

    public int getMin() {
        return helperStack.peek();
    }

    @Test
    public void testMinStack() {
        MinStack minStack = new MinStack();
        minStack.push(-2);
        minStack.push(0);
        minStack.push(-3);
        Assert.assertEquals(-3, minStack.getMin());
        minStack.pop();
        Assert.assertEquals(0, minStack.top());
        Assert.assertEquals(-2, minStack.getMin());
    }
}
