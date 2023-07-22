package com.learn.sort;

import org.junit.Assert;
import org.junit.Test;

import java.util.PriorityQueue;

/**
 * @author herryhaorui@didiglobal.com
 * @desc
 * @date 2023/7/8 5:58 下午
 * 295. 数据流的中位数
 * https://leetcode.cn/problems/find-median-from-data-stream/description/
 * 中位数是有序整数列表中的中间值。如果列表的大小是偶数，则没有中间值，中位数是两个中间值的平均值。
 *
 * 例如 arr = [2,3,4] 的中位数是 3 。
 * 例如 arr = [2,3] 的中位数是 (2 + 3) / 2 = 2.5 。
 * 实现 MedianFinder 类:
 *
 * MedianFinder() 初始化 MedianFinder 对象。
 *
 * void addNum(int num) 将数据流中的整数 num 添加到数据结构中。
 *
 * double findMedian() 返回到目前为止所有元素的中位数。与实际答案相差 10-5 以内的答案将被接受。
 *
 * 示例 1：
 *
 * 输入
 * ["MedianFinder", "addNum", "addNum", "findMedian", "addNum", "findMedian"]
 * [[], [1], [2], [], [3], []]
 * 输出
 * [null, null, null, 1.5, null, 2.0]
 *
 * 解释
 * MedianFinder medianFinder = new MedianFinder();
 * medianFinder.addNum(1);    // arr = [1]
 * medianFinder.addNum(2);    // arr = [1, 2]
 * medianFinder.findMedian(); // 返回 1.5 ((1 + 2) / 2)
 * medianFinder.addNum(3);    // arr[1, 2, 3]
 * medianFinder.findMedian(); // return 2.0
 */
public class MedianFinder {
    // 数据分为上下2个部分
    // 上部分的最小值
    PriorityQueue<Integer> minQueue = new PriorityQueue<>();
    // 下部分的最大值
    PriorityQueue<Integer> maxQueue = new PriorityQueue<>((o1, o2) -> o2 - o1);
    public MedianFinder() {

    }

    public void addNum(int num) {
        if (minQueue.size() >= maxQueue.size()) {
            // 此时上部分的数量大于下部分
            minQueue.offer(num);
            maxQueue.offer(minQueue.poll());
        } else {
            maxQueue.offer(num);
            minQueue.offer(maxQueue.poll());
        }
    }
    // 保证2个堆的数量差小于等于1
    // 保证下部分的最大值小于等于上部分的最小值
    public double findMedian() {
        if (minQueue.size() > maxQueue.size()) {
            return minQueue.peek();
        } else if (minQueue.size() < maxQueue.size()) {
            return maxQueue.peek();
        }
        return (minQueue.peek() + maxQueue.peek()) / 2.0;
    }

    @Test
    public void testMedianFinder() {
        MedianFinder medianFinder = new MedianFinder();
        medianFinder.addNum(1);    // arr = [1]
        medianFinder.addNum(2);    // arr = [1, 2]
        Assert.assertEquals(1.5, medianFinder.findMedian(), 0); // 返回 1.5 ((1 + 2) / 2)
        medianFinder.addNum(3);    // arr[1, 2, 3]
        Assert.assertEquals(2.0, medianFinder.findMedian(), 0); // return 2.0
    }
}
