package com.learn.stack;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class StackSolution {
    /**
     * TODO 739. 每日温度
     * https://leetcode.cn/problems/daily-temperatures/
     * 给定一个整数数组 temperatures ，表示每天的温度，返回一个数组 answer ，其中 answer[i] 是指对于第 i 天，下一个更高温度出现在几天后。
     * 如果气温在这之后都不会升高，请在该位置用 0 来代替。
     * 示例 1:
     *  输入: temperatures = [73,74,75,71,69,72,76,73]
     *  输出: [1,1,4,2,1,1,0,0]
     * 示例 2:
     *  输入: temperatures = [30,40,50,60]
     *  输出: [1,1,1,0]
     * 示例 3:
     *  输入: temperatures = [30,60,90]
     *  输出: [1,1,0]
     *
     * @param temperatures
     * @return
     */
    public int[] dailyTemperatures(int[] temperatures) {
        return temperatures;
    }

    @Test
    public void testDailyTemperatures() {
        Assert.assertEquals(true, Arrays.equals(new int[]{1, 1, 4, 2, 1, 1, 0, 0}, dailyTemperatures(new int[]{73, 74, 75, 71, 69, 72, 76, 73})));
        Assert.assertEquals(true, Arrays.equals(new int[]{1, 1, 1, 0}, dailyTemperatures(new int[]{30, 40, 50, 60})));
        Assert.assertEquals(true, Arrays.equals(new int[]{1, 1, 0}, dailyTemperatures(new int[]{30, 60, 90})));
    }

    /**
     * TODO 503. 下一个更大元素 II
     * https://leetcode.cn/problems/next-greater-element-ii/
     * 给定一个循环数组 nums （ nums[nums.length - 1] 的下一个元素是 nums[0] ），返回 nums 中每个元素的 下一个更大元素 。
     * 数字 x 的 下一个更大的元素 是按数组遍历顺序，这个数字之后的第一个比它更大的数，这意味着你应该循环地搜索它的下一个更大的数。如果不存在，则输出 -1 。
     * 示例 1:
     *  输入: nums = [1,2,1]
     *  输出: [2,-1,2]
     *  解释: 第一个 1 的下一个更大的数是 2；
     *  数字 2 找不到下一个更大的数；
     *  第二个 1 的下一个最大的数需要循环搜索，结果也是 2。
     * 示例 2:
     *  输入: nums = [1,2,3,4,3]
     *  输出: [2,3,4,-1,4]
     *
     * @param nums
     * @return
     */
    public int[] nextGreaterElements(int[] nums) {
        return nums;
    }

    @Test
    public void testNextGreaterElements() {
        Assert.assertEquals(true, Arrays.equals(new int[]{2, -1, 2}, nextGreaterElements(new int[]{1, 2, 1})));
        Assert.assertEquals(true, Arrays.equals(new int[]{2, 3, 4, -1, 4}, nextGreaterElements(new int[]{1, 2, 3, 4, 3})));
    }

    /**
     * TODO 496. 下一个更大元素 I
     * https://leetcode.cn/problems/next-greater-element-i/description/
     * nums1 中数字 x 的 下一个更大元素 是指 x 在 nums2 中对应位置 右侧 的 第一个 比 x 大的元素。
     * 给你两个 没有重复元素 的数组 nums1 和 nums2 ，下标从 0 开始计数，其中nums1 是 nums2 的子集。
     * 对于每个 0 <= i < nums1.length ，找出满足 nums1[i] == nums2[j] 的下标 j ，并且在 nums2 确定 nums2[j] 的 下一个更大元素 。
     * 如果不存在下一个更大元素，那么本次查询的答案是 -1 。
     * 返回一个长度为 nums1.length 的数组 ans 作为答案，满足 ans[i] 是如上所述的 下一个更大元素 。
     * 示例 1：
     *  输入：nums1 = [4,1,2], nums2 = [1,3,4,2].
     *  输出：[-1,3,-1]
     *  解释：nums1 中每个值的下一个更大元素如下所述：
     *  - 4 ，用加粗斜体标识，nums2 = [1,3,4,2]。不存在下一个更大元素，所以答案是 -1 。
     *  - 1 ，用加粗斜体标识，nums2 = [1,3,4,2]。下一个更大元素是 3 。
     *  - 2 ，用加粗斜体标识，nums2 = [1,3,4,2]。不存在下一个更大元素，所以答案是 -1 。
     * 示例 2：
     *  输入：nums1 = [2,4], nums2 = [1,2,3,4].
     *  输出：[3,-1]
     *  解释：nums1 中每个值的下一个更大元素如下所述：
     *  - 2 ，用加粗斜体标识，nums2 = [1,2,3,4]。下一个更大元素是 3 。
     *  - 4 ，用加粗斜体标识，nums2 = [1,2,3,4]。不存在下一个更大元素，所以答案是 -1 。
     *
     * @param nums1
     * @param nums2
     * @return
     */
    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        return nums1;
    }

    @Test
    public void testNextGreaterElement() {
        Assert.assertEquals(true, Arrays.equals(new int[]{-1, 3, -1}, nextGreaterElement(new int[]{4, 1, 2}, new int[]{1, 3, 4, 2})));
        Assert.assertEquals(true, Arrays.equals(new int[]{3, -1}, nextGreaterElement(new int[]{2, 4}, new int[]{1, 2, 3, 4})));
    }

    /**
     * TODO 316. 去除重复字母
     * https://leetcode.cn/problems/remove-duplicate-letters/description/
     * 给你一个字符串 s ，请你去除字符串中重复的字母，使得每个字母只出现一次。需保证 返回结果的字典序最小（要求不能打乱其他字符的相对位置）。
     * 示例 1：
     *  输入：s = "bcabc"
     *  输出："abc"
     * 示例 2：
     *  输入：s = "cbacdcbc"
     *  输出："acdb"
     *
     * @param s
     * @return
     */
    public String removeDuplicateLetters(String s) {
        return s;
    }

    @Test
    public void testRemoveDuplicateLetters() {
        Assert.assertEquals("abc", removeDuplicateLetters("bcabc"));
        Assert.assertEquals("acdb", removeDuplicateLetters("cbacdcbc"));
    }

    /**
     * TODO 918. 环形子数组的最大和
     * https://leetcode.cn/problems/maximum-sum-circular-subarray/description/
     * 给定一个长度为 n 的环形整数数组 nums ，返回 nums 的非空 子数组 的最大可能和 。
     * 环形数组 意味着数组的末端将会与开头相连呈环状。形式上， nums[i] 的下一个元素是 nums[(i + 1) % n] ， nums[i] 的前一个元素是 nums[(i - 1 + n) % n] 。
     * 子数组 最多只能包含固定缓冲区 nums 中的每个元素一次。形式上，对于子数组 nums[i], nums[i + 1], ..., nums[j] ，不存在 i <= k1, k2 <= j 其中 k1 % n == k2 % n 。
     * 示例 1：
     *  输入：nums = [1,-2,3,-2]
     *  输出：3
     *  解释：从子数组 [3] 得到最大和 3
     * 示例 2：
     *  输入：nums = [5,-3,5]
     *  输出：10
     *  解释：从子数组 [5,5] 得到最大和 5 + 5 = 10
     * 示例 3：
     *  输入：nums = [3,-2,2,-3]
     *  输出：3
     *  解释：从子数组 [3] 和 [3,-2,2] 都可以得到最大和 3
     *
     * @param nums
     * @return
     */
    public int maxSubarraySumCircular(int[] nums) {
        return 0;
    }

    @Test
    public void testMaxSubarraySumCircular() {
        Assert.assertEquals(3, maxSubarraySumCircular(new int[]{1, -2, 3, -2}));
        Assert.assertEquals(10, maxSubarraySumCircular(new int[]{5, -3, 5}));
        Assert.assertEquals(3, maxSubarraySumCircular(new int[]{3, -2, 2, -3}));
    }
}
