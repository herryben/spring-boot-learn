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
     * 解题思路：
     * 1.单调栈
     *  1.1 维持从栈底到栈顶元素单调递增
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
                        // 字符以后没有了，所以不弹出
                        break;
                    }
                }
                sb.append(ch);
                isVisited[ch - 'a'] = true;
            }
            // 字符使用频率-1
            freq[ch - 'a']--;
        }
        return sb.toString();
    }

    @Test
    public void testRemoveDuplicateLetters() {
        Assert.assertEquals("abc", removeDuplicateLetters("bcabc"));
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
            // 本质上是动归 cur是当前累积增量，如果累积的增量小于0，累积增量就是上一个状态
            cur = nums[i] + Math.max(cur, 0);
            max = Math.max(max, cur);
        }
        cur = 0;
        for (int i = 0; i < nums.length; i++) {
            // 当前累计和是否中断
            cur = nums[i] + Math.min(cur, 0);
            min = Math.min(min, cur);
        }
        // 如果最大值是负数说明数组整体都是负数，此时答案就是整个数组的最大值，肯定在但区间内
        return max < 0 ? max : Math.max(sum - min, max);
    }

    @Test
    public void testMaxSubArraySumCircular() {
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
     * 解题思路：
     * 1. 找到最大、最小值
     * 2. 找到从左到右第一个比最大值小的数
     * 3. 找到从右到左第一个比最小值大的数
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

    /**
     * 42. 接雨水
     * https://leetcode.cn/problems/trapping-rain-water/description/
     * 给定 n 个非负整数表示每个宽度为 1 的柱子的高度图，计算按此排列的柱子，下雨之后能接多少雨水。
     * 示例 1：
     *  输入：height = [0,1,0,2,1,0,1,3,2,1,2,1]
     *  输出：6
     *  解释：上面是由数组 [0,1,0,2,1,0,1,3,2,1,2,1] 表示的高度图，在这种情况下，可以接 6 个单位的雨水（蓝色部分表示雨水）。
     * 示例 2：
     *  输入：height = [4,2,0,3,2,5]
     *  输出：9
     * 解题思路：同时维护左右边最大值 + 双指针向中间缩小
     * TODO 可以实现下单调栈法
     * @param height
     * @return
     */
    public int trap(int[] height) {
        int ans = 0;
        int leftMax = height[0], rightMax = height[height.length - 1], left = 0, right = height.length - 1;
        // 双指针向中心走
        while (left < right) {
            leftMax = Math.max(leftMax, height[left]);
            rightMax = Math.max(rightMax, height[right]);
            // 小的那个索引向前走
            // 如果 height[left]<height[right]，则必有leftMax<rightMax，此时height[left]是低洼处，容量是左右两边低的那个
            // 算完了再往前走，找下一个低洼处
            if (leftMax < rightMax) {
                ans += leftMax - height[left];
                left++;
            } else {
                ans += rightMax - height[right];
                right--;
            }
        }
        return ans;
    }

    @Test
    public void testTrap() {
        Assert.assertEquals(6, trap(new int[]{0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1}));
        Assert.assertEquals(9, trap(new int[]{4, 2, 0, 3, 2, 5}));
    }

    public int trapDp(int[] height) {
        int res = 0;
        int[] leftMax = new int[height.length];
        int[] rightMax = new int[height.length];
        leftMax[0] = height[0];
        for (int i = 1; i < height.length; i++) {
            leftMax[i] = Math.max(height[i], leftMax[i - 1]);
        }
        rightMax[height.length - 1] = height[height.length - 1];
        for (int i = height.length - 2; i >= 0; i--) {
            rightMax[i] = Math.max(height[i], rightMax[i + 1]);
        }
        for (int i = 0; i < height.length; i++) {
            int tmp = Math.min(leftMax[i], rightMax[i]);
            if (tmp > height[i]) {
                res += tmp - height[i];
            }
        }
        return res;
    }

    @Test
    public void testTrapDp() {
        Assert.assertEquals(6, trapDp(new int[]{0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1}));
        Assert.assertEquals(9, trapDp(new int[]{4, 2, 0, 3, 2, 5}));
    }

    /**
     * 20. 有效的括号
     * https://leetcode.cn/problems/valid-parentheses/
     * 给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串 s ，判断字符串是否有效。
     * <p>
     * 有效字符串需满足：
     * <p>
     * 左括号必须用相同类型的右括号闭合。
     * 左括号必须以正确的顺序闭合。
     * 每个右括号都有一个对应的相同类型的左括号。
     * 示例 1：
     * <p>
     * 输入：s = "()"
     * 输出：true
     * 示例 2：
     * <p>
     * 输入：s = "()[]{}"
     * 输出：true
     * 示例 3：
     * <p>
     * 输入：s = "(]"
     * 输出：false
     * 解题思路：单调栈变形
     *
     * @param s
     * @return
     */
    public boolean isValid(String s) {
        Deque<Character> stack = new ArrayDeque<>();
        for (char ch : s.toCharArray()) {
            if (ch == '(' || ch == '[' || ch == '{') {
                stack.push(ch);
            } else {
                if (!stack.isEmpty() && getCharacter(ch) == stack.peekFirst()) {
                    stack.pop();
                } else {
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }

    private Character getCharacter(Character ch) {
        switch (ch) {
            case ')':
                return '(';
            case ']':
                return '[';
            default:
                return '{';
        }
    }

    @Test
    public void testIsValid() {
        Assert.assertEquals(false, isValid("("));
        Assert.assertEquals(true, isValid("()"));
        Assert.assertEquals(true, isValid("()[]{}"));
        Assert.assertEquals(false, isValid("(]"));
    }

    /**
     * 394. 字符串解码
     * https://leetcode.cn/problems/decode-string/description/?envType=study-plan-v2&envId=top-100-liked
     * 给定一个经过编码的字符串，返回它解码后的字符串。
     * <p>
     * 编码规则为: k[encoded_string]，表示其中方括号内部的 encoded_string 正好重复 k 次。注意 k 保证为正整数。
     * <p>
     * 你可以认为输入字符串总是有效的；输入字符串中没有额外的空格，且输入的方括号总是符合格式要求的。
     * <p>
     * 此外，你可以认为原始数据不包含数字，所有的数字只表示重复的次数 k ，例如不会出现像 3a 或 2[4] 的输入。
     * <p>
     * <p>
     * <p>
     * 示例 1：
     * <p>
     * 输入：s = "3[a]2[bc]"
     * 输出："aaabcbc"
     * 示例 2：
     * <p>
     * 输入：s = "3[a2[c]]"
     * 输出："accaccacc"
     * 示例 3：
     * <p>
     * 输入：s = "2[abc]3[cd]ef"
     * 输出："abcabccdcdcdef"
     * 示例 4：
     * <p>
     * 输入：s = "abc3[cd]xyz"
     * 输出："abccdcdcdxyz"
     * 解题思路：
     * 1.用2个栈保存结果
     * 1.1 multiStack保存积累的乘数结果
     * 1.2 resStack保存执行出的字符结果，每次新生成的结果都append到后面
     * 2.分4种case处理：
     * 2.1 [说明是新token的开始，之前的乘数、结果都压入栈
     * 2.2 ]说明token结束，乘数取出计算出当前出结果，append到res
     * 2.3 数字，压入到乘数结果中
     * 2.4 单个字符，压入到字符结果中
     *
     * @param s
     * @return
     */
    public String decodeString(String s) {
        StringBuilder res = new StringBuilder();
        Deque<Integer> multiStack = new LinkedList<>();
        Deque<StringBuilder> resStack = new LinkedList<>();
        int multi = 0;
        for (char ch : s.toCharArray()) {
            if (ch == '[') {
                multiStack.push(multi);
                resStack.push(res);
                multi = 0;
                res = new StringBuilder();
            } else if (ch == ']') {
                StringBuilder tmp = new StringBuilder();
                int curMulti = multiStack.pop();
                for (int i = 0; i < curMulti; i++) {
                    tmp.append(res);
                }
                res = resStack.pop().append(tmp);
            } else if (Character.isDigit(ch)) {
                multi = multi * 10 + Integer.parseInt(String.valueOf(ch));
            } else {
                res.append(ch);
            }
        }
        return res.toString();
    }

    @Test
    public void testDecodeString() {
        Assert.assertEquals("abcbcabcbc", decodeString("2[a2[bc]]"));
        Assert.assertEquals("aaabcbc", decodeString("3[a]2[bc]"));
        Assert.assertEquals("accaccacc", decodeString("3[a2[c]]"));
        Assert.assertEquals("abcabccdcdcdef", decodeString("2[abc]3[cd]ef"));
        Assert.assertEquals("abccdcdcdxyz", decodeString("abc3[cd]xyz"));
    }
}
