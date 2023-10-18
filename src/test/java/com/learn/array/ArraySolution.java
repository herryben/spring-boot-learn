package com.learn.array;

import org.junit.Assert;
import org.junit.Test;

public class ArraySolution {
    /**
     * 287. 寻找重复数
     * https://leetcode.cn/problems/find-the-duplicate-number/description/?envType=study-plan-v2&envId=top-100-liked
     * 给定一个包含 n + 1 个整数的数组 nums ，其数字都在 [1, n] 范围内（包括 1 和 n），可知至少存在一个重复的整数。
     * <p>
     * 假设 nums 只有 一个重复的整数 ，返回 这个重复的数 。
     * <p>
     * 你设计的解决方案必须 不修改 数组 nums 且只用常量级 O(1) 的额外空间。
     * <p>
     * 示例 1：
     * <p>
     * 输入：nums = [1,3,4,2,2]
     * 输出：2
     * 示例 2：
     * <p>
     * 输入：nums = [3,1,3,4,2]
     * 输出：3
     * 解题思路：
     * 1. 链表的变种
     * 1.1 例如[1,2,3,3] 1.next = num[num[1]] 最终到3肯定会死循环
     * 2. floyd（龟兔赛跑）判圈算法
     *
     * @param nums
     * @return
     */
    public int findDuplicate(int[] nums) {
        int slow = nums[0];
        int fast = nums[0];

        do {
            slow = nums[slow];
            fast = nums[nums[fast]];
            // 相遇时不一定在相遇点
        } while (slow != fast);

        int pt1 = slow;
        int pt2 = nums[0];
        while (pt1 != pt2) {
            pt1 = nums[pt1];
            pt2 = nums[pt2];
        }
        return pt2;
    }

    @Test
    public void testFindDuplicate() {
        Assert.assertEquals(9, findDuplicate(new int[]{2, 5, 9, 6, 9, 3, 8, 9, 7, 1}));
    }

    /**
     * TODO 128. 最长连续序列
     * https://leetcode.cn/problems/longest-consecutive-sequence/?envType=study-plan-v2&envId=top-100-liked
     * 给定一个未排序的整数数组 nums ，找出数字连续的最长序列（不要求序列元素在原数组中连续）的长度。
     * <p>
     * 请你设计并实现时间复杂度为 O(n) 的算法解决此问题。
     * <p>
     * 示例 1：
     * <p>
     * 输入：nums = [100,4,200,1,3,2]
     * 输出：4
     * 解释：最长数字连续序列是 [1, 2, 3, 4]。它的长度为 4。
     * 示例 2：
     * <p>
     * 输入：nums = [0,3,7,2,5,8,4,6,0,1]
     * 输出：9
     *
     * @param nums
     * @return
     */
    public int longestConsecutive(int[] nums) {
        return 0;
    }

    @Test
    public void testLongestConsecutive() {
        Assert.assertEquals(4, findDuplicate(new int[]{100, 4, 200, 1, 3, 2}));
        Assert.assertEquals(9, findDuplicate(new int[]{0, 3, 7, 2, 5, 8, 4, 6, 0, 1}));
    }

    /**
     * 53. 最大子数组和
     * https://leetcode.cn/problems/maximum-subarray/?envType=study-plan-v2&envId=top-100-liked
     * 给你一个整数数组 nums ，请你找出一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
     * <p>
     * 子数组 是数组中的一个连续部分。
     * 示例 1：
     * <p>
     * 输入：nums = [-2,1,-3,4,-1,2,1,-5,4]
     * 输出：6
     * 解释：连续子数组 [4,-1,2,1] 的和最大，为 6 。
     * 示例 2：
     * <p>
     * 输入：nums = [1]
     * 输出：1
     * 示例 3：
     * <p>
     * 输入：nums = [5,4,-1,7,8]
     * 输出：23
     * <p>
     * 解题思路：
     * 1. kadane算法标准流程
     *
     * @param nums
     * @return
     */
    public int maxSubArray(int[] nums) {
        int max = Integer.MIN_VALUE;
        int cur = 0;
        for (int num : nums) {
            cur = num + Math.max(cur, 0);
            max = Math.max(max, cur);
        }
        return max;
    }

    @Test
    public void testMaxSubArray() {
        Assert.assertEquals(6, maxSubArray(new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4}));
        Assert.assertEquals(1, maxSubArray(new int[]{1}));
        Assert.assertEquals(23, maxSubArray(new int[]{5, 4, -1, 7, 8}));
    }
}
