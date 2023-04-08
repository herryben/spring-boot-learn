package com.learn.stack;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class StackSolution {
    /**
     * 739. 每日温度
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
        Deque<Integer> stack = new LinkedList<>();
        int[] ans = new int[temperatures.length];
        for (int i = temperatures.length - 1; i >= 0; i--) {
            while (!stack.isEmpty() && temperatures[i] >= temperatures[stack.peekFirst()]) {
                stack.pop();
            }
            ans[i] = stack.isEmpty() ? 0 : stack.peekFirst() - i;
            stack.push(i);
        }
        return ans;
    }

    @Test
    public void testDailyTemperatures() {
        Assert.assertEquals(true, Arrays.equals(new int[]{1, 1, 4, 2, 1, 1, 0, 0}, dailyTemperatures(new int[]{73, 74, 75, 71, 69, 72, 76, 73})));
        Assert.assertEquals(true, Arrays.equals(new int[]{1, 1, 1, 0}, dailyTemperatures(new int[]{30, 40, 50, 60})));
        Assert.assertEquals(true, Arrays.equals(new int[]{1, 1, 0}, dailyTemperatures(new int[]{30, 60, 90})));
    }

    /**
     * 503. 下一个更大元素 II
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
        int[] ans = new int[nums.length];
        Deque<Integer> stack = new LinkedList<>();
        for (int i = 2 * nums.length - 1; i >= 0; i--) {
            int idx = i % nums.length;
            // 相等的也需要弹出去
            while (!stack.isEmpty() && nums[idx] >= stack.peekFirst()) {
                stack.pop();
            }
            ans[idx] = stack.isEmpty() ? -1 : stack.peekFirst();
            stack.push(nums[idx]);
        }
        return ans;
    }

    @Test
    public void testNextGreaterElements() {
        Assert.assertEquals(true, Arrays.equals(new int[]{2, -1, 2}, nextGreaterElements(new int[]{1, 2, 1})));
        Assert.assertEquals(true, Arrays.equals(new int[]{2, 3, 4, -1, 4}, nextGreaterElements(new int[]{1, 2, 3, 4, 3})));
    }

    /**
     * 496. 下一个更大元素 I
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
        int[] ans = new int[nums1.length];
        Deque<Integer> stack = new LinkedList<>();
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = nums2.length - 1; i >= 0; i--) {
            while (!stack.isEmpty() && nums2[i] >= stack.peekFirst()) {
                stack.pop();
            }
            map.put(nums2[i], stack.isEmpty() ? -1 : stack.peekFirst());
            stack.push(nums2[i]);
        }
        for (int i = 0; i < nums1.length; i++) {
            ans[i] = map.get(nums1[i]);
        }
        return ans;
    }

    @Test
    public void testNextGreaterElement() {
        Assert.assertEquals(true, Arrays.equals(new int[]{-1, 3, -1}, nextGreaterElement(new int[]{4, 1, 2}, new int[]{1, 3, 4, 2})));
        Assert.assertEquals(true, Arrays.equals(new int[]{3, -1}, nextGreaterElement(new int[]{2, 4}, new int[]{1, 2, 3, 4})));
    }

    /**
     * 316. 去除重复字母
     * https://leetcode.cn/problems/remove-duplicate-letters/description/
     * 给你一个字符串 s ，请你去除字符串中重复的字母，使得每个字母只出现一次。需保证 返回结果的字典序最小（要求不能打乱其他字符的相对位置）。
     * 示例 1：
     * 输入：s = "bcabc"
     * 输出："abc"
     * 示例 2：
     * 输入：s = "cbacdcbc"
     * 输出："acdb"
     * 解题思路： 贪心 + 单调栈
     *
     * @param s
     * @return
     */
    public String removeDuplicateLetters(String s) {
        StringBuilder sb = new StringBuilder();
        // 字符的使用频率
        int[] freq = new int[26];
        // 字符是否出现在单调队列中
        boolean[] isVisited = new boolean[26];
        for (char ch : s.toCharArray()) {
            freq[ch - 'a']++;
        }
        for (char ch : s.toCharArray()) {
            // 当前字符没有在单调队列中出现过
            if (!isVisited[ch - 'a']) {
                // 栈非空且栈顶元素大于当前元素则弹出
                // sb[0]是栈底 sb[len]是栈顶 维护栈底到栈顶的单调递增
                while (sb.length() > 0 && ch < sb.charAt(sb.length() - 1)) {
                    // 这里面是对单调栈顶的元素更新 和当前元素ch没有一毛钱关系
                    // 字符不能重复所以出现频率不能是0
                    if (freq[sb.charAt(sb.length() - 1) - 'a'] > 0) {
                        // 以后可能还会用
                        isVisited[sb.charAt(sb.length() - 1) - 'a'] = false;
                        sb.deleteCharAt(sb.length() - 1);
                    } else {
                        break;
                    }
                }
                sb.append(ch);
                isVisited[ch - 'a'] = true;
                // 字符使用频率-1
            }
            freq[ch - 'a']--;
        }
        return sb.toString();
    }

    @Test
    public void testRemoveDuplicateLetters() {
//        Assert.assertEquals("abc", removeDuplicateLetters("bcabc"));
        Assert.assertEquals("acdb", removeDuplicateLetters("cbacdcbc"));
    }

    /**
     * 918. 环形子数组的最大和
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
     * 解题思路：Kadane算法 + 反向思考取最小
     * @param nums
     * @return
     */
    public int maxSubarraySumCircular(int[] nums) {
        if (nums.length == 1) {
            return nums[0];
        }
        int cur = 0, max = Integer.MIN_VALUE, min = Integer.MAX_VALUE, sum = 0;
        for (int i = 0; i < nums.length; i++) {
            // 计算总和
            sum += nums[i];
            // Kadane算法标准步骤
            cur = nums[i] + Math.max(cur, 0);
            max = Math.max(max, cur);
        }
        cur = 0;
        for (int i = 0; i < nums.length; i++) {
            cur = nums[i] + Math.min(cur, 0);
            min = Math.min(min, cur);
        }
        // 如果最大值是负数说明数组整体都是负数，此时答案就是整个数组的最大值，肯定在但区间内
        return max < 0 ? max : Math.max(sum - min, max);
    }

    @Test
    public void testMaxSubarraySumCircular() {
        Assert.assertEquals(-2, maxSubarraySumCircular(new int[]{-3, -2, -3}));
        Assert.assertEquals(3, maxSubarraySumCircular(new int[]{1, -2, 3, -2}));
        Assert.assertEquals(10, maxSubarraySumCircular(new int[]{5, -3, 5}));
        Assert.assertEquals(3, maxSubarraySumCircular(new int[]{3, -2, 2, -3}));
    }

    /**
     * 解题思路：数组翻倍 + 前缀和 + 单调队列取最小值
     *
     * @param nums
     * @return
     */
    public int maxSubarraySumCircularQueue(int[] nums) {
        int ans = nums[0], len = nums.length;
        // 前缀和定义：prefixSum[i] = sum(prefixSum[0..i-1])
        int[] prefixSum = new int[2 * len + 1];
        for (int i = 0; i < 2 * len; i++) {
            prefixSum[i + 1] = nums[i % len] + prefixSum[i];
        }
        Deque<Integer> queue = new LinkedList<>();
        queue.offer(0);
        // 这里迭代的是前缀和，所以索引是1..2*len
        for (int j = 1; j < prefixSum.length; j++) {
            // 这里要保证 j - peekFirst <= len
            // queue.peekFirst() < j - len
            if (j - queue.peekFirst() > len) {
                queue.pollFirst();
            }
            ans = Math.max(ans, prefixSum[j] - prefixSum[queue.peekFirst()]);
            // 现在的nums[j] 就是之后的nums[queue.peekFirst()]
            // 要维持队列单调递增
            while (!queue.isEmpty() && prefixSum[j] <= prefixSum[queue.peekLast()]) {
                queue.pollLast();
            }
            queue.offerLast(j);
        }
        return ans;
    }

    @Test
    public void testMaxSubarraySumCircularQueue() {
        Assert.assertEquals(-2, maxSubarraySumCircularQueue(new int[]{-3, -2, -3}));
        Assert.assertEquals(3, maxSubarraySumCircularQueue(new int[]{1, -2, 3, -2}));
        Assert.assertEquals(10, maxSubarraySumCircularQueue(new int[]{5, -3, 5}));
        Assert.assertEquals(3, maxSubarraySumCircularQueue(new int[]{3, -2, 2, -3}));
    }

    /**
     * 581. 最短无序连续子数组
     * https://leetcode.cn/problems/shortest-unsorted-continuous-subarray/
     * 给你一个整数数组 nums ，你需要找出一个 连续子数组 ，如果 对这个子数组进行升序排序，那么整个数组都会变为升序排序。
     * 请你找出符合题意的 最短 子数组，并输出它的长度。
     * 示例 1：
     * 输入：nums = [2,6,4,8,10,9,15]
     * 输出：5
     * 解释：你只需要对 [6, 4, 8, 10, 9] 进行升序排序，那么整个表都会变为升序排序。
     * 示例 2：
     * 输入：nums = [1,2,3,4]
     * 输出：0
     * 示例 3：
     * 输入：nums = [1]
     * 输出：0
     *
     * @param nums
     * @return
     */
    public int findUnsortedSubarray(int[] nums) {
        if (nums.length == 1) {
            return 0;
        }
        int left = -1, right = -1, min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
        for (int i = 0; i < nums.length; i++) {
            // 寻找第一个比最大值小的索引（从右到左）
            if (nums[i] < max) {
                // 这里就是第一个比最大值小的索引
                right = i;
            } else {
                // 更新最大值
                max = nums[i];
            }
        }
        for (int i = nums.length - 1; i >= 0; i--) {
            // 寻找第一个比最小值大的索引（从左到右）
            if (nums[i] > min) {
                left = i;
            } else {
                min = nums[i];
            }
        }
        return right != -1 ? right - left + 1 : 0;
    }

    @Test
    public void testFndUnsortedSubarray() {
        Assert.assertEquals(4, findUnsortedSubarray(new int[]{1, 3, 2, 2, 2}));
        Assert.assertEquals(0, findUnsortedSubarray(new int[]{1, 2, 3, 3, 3}));
        Assert.assertEquals(2, findUnsortedSubarray(new int[]{2, 1}));
        Assert.assertEquals(5, findUnsortedSubarray(new int[]{2, 6, 4, 8, 10, 9, 15}));
        Assert.assertEquals(0, findUnsortedSubarray(new int[]{1, 2, 3, 4}));
        Assert.assertEquals(0, findUnsortedSubarray(new int[]{1}));
    }
}
