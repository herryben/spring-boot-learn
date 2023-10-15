package com.learn.heap;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class HeapSolution {
    /**
     * 347. 前 K 个高频元素
     * 给你一个整数数组 nums 和一个整数 k ，请你返回其中出现频率前 k 高的元素。你可以按 任意顺序 返回答案。
     * 示例 1:
     * <p>
     * 输入: nums = [1,1,1,2,2,3], k = 2
     * 输出: [1,2]
     * 示例 2:
     * <p>
     * 输入: nums = [1], k = 1
     * 输出: [1]
     * 解题思路：
     * 1. 使用字典，统计每个元素出现的次数，元素为键，元素出现的次数为值
     * 2. 使用小顶堆取topK个元素
     * TODO 可以尝试桶排序法
     *
     * @param nums
     * @param k
     * @return
     */
    public int[] topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }

        PriorityQueue<Integer> heap = new PriorityQueue<>((o1, o2) -> map.get(o1) - map.get(o2));
        for (int num : map.keySet()) {
            heap.offer(num);
            if (heap.size() > k) {
                heap.poll();
            }
        }
        int[] ret = new int[k];
        for (int i = k - 1; i >= 0; i--) {
            ret[i] = heap.poll();
        }
        return ret;
    }

    @Test
    public void testTopKFrequent() {
        Assert.assertEquals(true, Arrays.equals(new int[]{1, 2}, topKFrequent(new int[]{1, 1, 1, 2, 2, 3}, 2)));
        Assert.assertEquals(true, Arrays.equals(new int[]{1}, topKFrequent(new int[]{1}, 1)));
    }
}
