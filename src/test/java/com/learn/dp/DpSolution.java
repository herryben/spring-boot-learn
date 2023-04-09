package com.learn.dp;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

@Slf4j
public class DpSolution {
    /**
     * 70. 爬楼梯
     * https://leetcode.cn/problems/climbing-stairs/description/
     * 假设你正在爬楼梯。需要 n 阶你才能到达楼顶。
     * 每次你可以爬 1 或 2 个台阶。你有多少种不同的方法可以爬到楼顶呢？
     * 示例 1：
     * 输入：n = 2
     * 输出：2
     * 解释：有两种方法可以爬到楼顶。
     * 1. 1 阶 + 1 阶
     * 2. 2 阶
     * 示例 2：
     * 输入：n = 3
     * 输出：3
     * 解释：有三种方法可以爬到楼顶。
     * 1. 1 阶 + 1 阶 + 1 阶
     * 2. 1 阶 + 2 阶
     * 3. 2 阶 + 1 阶
     * 解题思路：
     *  dp[i]:爬到第i层的方法
     *  dp[i] = dp[i -1] + dp[i -2]
     *  爬到第i层的方法=爬到第i-1层的方法 + 爬到第i-2层的方法
     * @param n
     * @return
     */
    public int climbStairs(int n) {
        int[] dp = new int[n + 1];
        dp[0] = 1;
        dp[1] = 1;
        for (int i = 2; i <= n; i++) {
            // 跳1+跳2级
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        return dp[n];
    }

    public int climbStairsCompress(int n) {
        int dp_i_1 = 1, dp_i_2 = 1;
        for (int i = 2; i <= n; i++) {
            int dp = dp_i_1 + dp_i_2;
            dp_i_2 = dp_i_1;
            dp_i_1 = dp;
        }
        return dp_i_1;
    }

    @Test
    public void testClimbStairs() {
        Assert.assertEquals(climbStairs(3), 3);
        Assert.assertEquals(climbStairsCompress(3), 3);
    }

    /**
     * 518. 零钱兑换 II
     * https://leetcode.cn/problems/coin-change-ii/description/
     * 给你一个整数数组 coins 表示不同面额的硬币，另给一个整数 amount 表示总金额。
     * 请你计算并返回可以凑成总金额的硬币组合数。如果任何硬币组合都无法凑出总金额，返回 0 。
     * 假设每一种面额的硬币有无限个。
     * 题目数据保证结果符合 32 位带符号整数。
     * 示例 1：
     * 输入：amount = 5, coins = [1, 2, 5]
     * 输出：4
     * 解释：有四种方式可以凑成总金额：
     * 5=5
     * 5=2+2+1
     * 5=2+1+1+1
     * 5=1+1+1+1+1
     * 解题方法：
     *  dp[i][j]:用i种硬币凑成j的方法组合数
     *  d[i][j] = dp[i-1][j] + dp[i][j-coins[i-1]]
     *  i-1枚硬币凑成j的方法 + i枚硬币凑成j-去除这枚硬币的方法
     * @param amount
     * @param coins
     * @return
     */
    public int change(int amount, int[] coins) {
        int[][] dp = new int[coins.length + 1][amount + 1];
        for (int i = 0; i < coins.length + 1; i++) {
            dp[i][0] = 1;
        }
        for (int i = 1; i <= coins.length; i++) {
            for (int j = 1; j <= amount; j++) {
                if (j - coins[i - 1] < 0) {
                    // 硬币不够用了
                    dp[i][j] = dp[i - 1][j];
                } else {
                    // 硬币够用
                    dp[i][j] = dp[i - 1][j] + dp[i][j - coins[i - 1]];
                }
            }
        }
        return dp[coins.length][amount];
    }

    public int changeCompress(int amount, int[] coins) {
        int[] dp = new int[amount + 1];
        dp[0] = 1;
        for (int i = 1; i <= coins.length; i++) {
            for (int j = 0; j <= amount; j++) {
                if (j - coins[i - 1] >= 0) {
                    dp[j] = dp[j] + dp[j - coins[i - 1]];
                }
            }
        }
        return dp[amount];
    }

    @Test
    public void testChange() {
        Assert.assertEquals(change(5, new int[]{1, 2, 5}), 4);
        Assert.assertEquals(changeCompress(5, new int[]{1, 2, 5}), 4);
    }

    /**
     * 322. 零钱兑换
     * https://leetcode.cn/problems/coin-change/description/
     * 给你一个整数数组 coins ，表示不同面额的硬币；以及一个整数 amount ，表示总金额。
     * 计算并返回可以凑成总金额所需的 最少的硬币个数 。如果没有任何一种硬币组合能组成总金额，返回 -1 。
     * 你可以认为每种硬币的数量是无限的。
     * 示例 1：
     * 输入：coins = [1, 2, 5], amount = 11
     * 输出：3
     * 解释：11 = 5 + 5 + 1
     * 示例 2：
     * 输入：coins = [2], amount = 3
     * 输出：-1
     * 示例 3：
     * 输入：coins = [1], amount = 0
     * 输出：0
     */
    public int coinChange(int[] coins, int amount) {
        // dp[i][j]：使用i中硬币,凑成j的最小个数
        int[][] dp = new int[coins.length + 1][amount + 1];
        // 因为是 初始化为amount + 1即可
        for (int i = 0; i <= coins.length; i++) {
            for (int j = 0; j <= amount; j++) {
                dp[i][j] = amount + 1;
            }
        }
        // 初始状态用任意枚硬币凑成0的个数都是0
        for (int i = 0; i <= coins.length; i++) {
            dp[i][0] = 0;
        }

        for (int j = 0; j <= amount; j++) {
            for (int i = 1; i <= coins.length; i++) {
                if (j - coins[i - 1] >= 0) {
                    // 如果余额还能大于等于0，则选用i-1枚硬币凑够j和i枚硬币凑够余额的2者最小（因为是无限硬币）
                    dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - coins[i - 1]] + 1);
                } else {
                    // 如果余额不够了，只能集成上一个状态
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }
        return dp[coins.length][amount] == amount + 1 ? -1 : dp[coins.length][amount];
    }

    @Test
    public void testCoinChange() {
        Assert.assertEquals(coinChange(new int[]{1, 2, 5}, 11), 3);
        Assert.assertEquals(coinChange(new int[]{2}, 3), -1);
        Assert.assertEquals(coinChange(new int[]{1}, 0), 0);
    }

    /**
     * 解题思路：
     * dp[i]=Math.min(dp[i], dp[i-coin]+1);
     * 当前硬币最小和之前硬币最小+1取最小值
     *
     * @param coins
     * @param amount
     * @return
     */
    public int coinChangeOfficial(int[] coins, int amount) {
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, amount + 1);
        dp[0] = 0;
        for (int i = 1; i <= amount; i++) {
            for (int coin : coins) {
                if (i - coin >= 0) {
                    dp[i] = Math.min(dp[i], dp[i - coin] + 1);
                }
            }
        }
        return dp[amount] == amount + 1 ? -1 : dp[amount];
    }

    @Test
    public void testCoinChangeOfficial() {
        Assert.assertEquals(coinChangeOfficial(new int[]{1, 2, 5}, 11), 3);
        Assert.assertEquals(coinChangeOfficial(new int[]{2}, 3), -1);
        Assert.assertEquals(coinChangeOfficial(new int[]{1}, 0), 0);
    }

    /**
     * 300. 最长递增子序列
     * 给你一个整数数组 nums ，找到其中最长严格递增子序列的长度。
     * 子序列 是由数组派生而来的序列，删除（或不删除）数组中的元素而不改变其余元素的顺序。例如，[3,6,2,7] 是数组 [0,3,1,6,2,2,7] 的子序列。
     * 示例 1：
     * 输入：nums = [10,9,2,5,3,7,101,18]
     * 输出：4
     * 解释：最长递增子序列是 [2,3,7,101]，因此长度为 4 。
     * 示例 2：
     * 输入：nums = [0,1,0,3,2,3]
     * 输出：4
     * 示例 3：
     * 输入：nums = [7,7,7,7,7,7,7]
     * 输出：1
     * 解题思路：
     * 定义 dp[i]为考虑前 i 个元素，以第 i 个数字结尾的最长上升子序列的长度，注意 nums[i] 必须被选取。
     *
     * @param nums
     * @return
     */
    public int lengthOfLIS(int[] nums) {
        int[] dp = new int[nums.length];
        // 初始状态下每个长度肯定包含自己
        Arrays.fill(dp, 1);
        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < i; j++) {
                // 当前元素比之前的元素大才更新（这样才能递增）
                if (nums[i] > nums[j]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
        }
        // 返回最大的那个
        return Arrays.stream(dp).max().getAsInt();
    }

    @Test
    public void testLengthOfLIS() {
        Assert.assertEquals(4, lengthOfLIS(new int[]{10, 9, 2, 5, 3, 7, 101, 18}));
        Assert.assertEquals(4, lengthOfLIS(new int[]{0, 1, 0, 3, 2, 3}));
        Assert.assertEquals(1, lengthOfLIS(new int[]{7, 7, 7, 7, 7, 7, 7}));
    }

    @Test
    public void testLis() {
        Assert.assertEquals(4, lengthOfLISGreedy(new int[]{10, 9, 2, 5, 3, 7, 101, 18}));
        Assert.assertEquals(4, lengthOfLISGreedy(new int[]{0, 1, 0, 3, 2, 3}));
        Assert.assertEquals(1, lengthOfLISGreedy(new int[]{7, 7, 7, 7, 7, 7, 7}));
    }

    public int lengthOfLISGreedy(int[] datas) {
        int[] queue = new int[datas.length];
        int max = 0;
        queue[max] = datas[max];
        for (int i = 1; i < datas.length; i++) {
            if (datas[i] > queue[max]) {
                queue[++max] = datas[i];
            } else {
                queue[lowerBound(queue, datas[i], max + 1)] = datas[i];
            }
        }
        return max + 1;
    }

    public int lowerBound(int[] datas, int target, int length) {
        int low = 0, high = length;
        while (low < high) {
            int mid = low + (high - low) / 2;
            if (datas[mid] > target) {
                high = mid;
            } else if (datas[mid] < target) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }
        return low;
    }

    /**
     * 0-1背包
     * 给你一个可装载重量为 W 的背包和 N 个物品，每个物品有重量和价值两 个属性。其中第 i 个物品的重量为 wt[i] ，
     * 价值为 val[i] ，现在让你用 这个背包装物品，最多能装的价值是多少?
     * N = 3, W = 4
     * wt = [2, 1, 3]
     * val = [4, 2, 3]
     * 算法返回6
     * 解题思路：dp[i][j] i件物品重量为j时的最大价值
     */
    public int knapsack(int W, int N, int[] wt, int[] val) {
        int[][] dp = new int[N + 1][W + 1];
        for (int i = 1; i <= N; i++) {
            for (int w = 1; w <= W; w++) {
                if (w - wt[i - 1] < 0) {
                    // 背包剩余重量不够 这种情况下只能选择不装入背包
                    dp[i][w] = dp[i - 1][w];
                } else {
                    // 不装入和装入，两者之间选最大值
                    dp[i][w] = Math.max(dp[i - 1][w], dp[i - 1][w - wt[i - 1]] + val[i - 1]);
                }
            }
        }
        return dp[N][W];
    }

    @Test
    public void testKnapsack() {
        Assert.assertEquals(6, knapsack(4, 3, new int[]{2, 1, 3}, new int[]{4, 2, 3}));
    }

    /**
     * 有⼀个背包，最⼤容量为 amount ，有⼀系列物品 coins ，每个物品的重
     * 量为 coins[i] ，每个物品的数量⽆限。请问有多少种⽅法，能够把背包恰
     * 好装满？
     * 结题思路：就是换硬币
     * dp[i][j] 的定义如下：
     * 若只使⽤前 i 个物品，当背包容量为 j 时，有 dp[i][j] 种⽅法可以装满背包。
     *
     * @return
     */
    public int completeNapsack(int amount, int[] wt) {
        int N = wt.length;
        int[][] dp = new int[N+1][amount+1];
        // i个物品容量为0时，只有1种凑法就是啥也不用
        for (int i = 0; i <= N; i++) {
            dp[i][0] = 1;
        }
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j<=amount; j++) {
                if (j >= wt[i - 1]) {
                    dp[i][j] = dp[i - 1][j] + dp[i][j - wt[i - 1]];
                } else {
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }
        return dp[N][amount];
    }

    @Test
    public void testCompleteNapsack() {
        Assert.assertEquals(4, completeNapsack(5, new int[]{1, 2, 5}));
        Assert.assertEquals(0, completeNapsack(3, new int[]{2}));
    }

    /**
     * 416.分割等和子集
     * 给定一个只包含正整数的非空数组，是否可以将这个数组分割成两个子集，使得两个子集的元素和相等。
     * 注意：
     * 1.每个数组中的元素不会超过100
     * 2.数组的大小不会超过200
     * 示例1：
     *  输入：[1, 5, 11, 5]
     *  输出：true
     *  解释：数组可以分割成[1, 5, 5] 和 [11]
     * 示例2：
     *  输入：[1, 2, 3, 5]
     *  输出：false
     *  解释：数组不能分割成两个元素和相等的子集
     * 解题思路：
     *  用 num / 2 转化成0/1背包思路
     */
    public boolean canPartition (int[] nums) {
        int sum = Arrays.stream(nums).sum();
        int n = sum % 2;
        if (n == 1) {
            return false;
        }
        boolean[][] dp = new boolean[nums.length+1][n + 1];
        for (int i = 0; i <= nums.length; i++) {
            dp[i][0] = true;
        }
        for (int i = 1; i <= nums.length; i++) {
            for (int j = 1; j <= n; j++) {
                if (j >= nums[i-1]) {
                    dp[i][j] = dp[i-1][j] || dp[i-1][j-nums[i-1]];
                } else {
                    dp[i][j] = dp[i-1][j];
                }
            }
        }
        return dp[nums.length][n];
    }

    @Test
    public void testCanPartition() {
        Assert.assertEquals(true, canPartition(new int[]{1, 5, 11, 5}));
        Assert.assertEquals(false, canPartition(new int[]{1, 2, 3, 5}));
    }

    /**
     * 1143. 最长公共子序列
     * https://leetcode.cn/problems/longest-common-subsequence/
     * 给定两个字符串 text1 和 text2，返回这两个字符串的最长 公共子序列 的长度。如果不存在 公共子序列 ，返回 0 。
     * 一个字符串的 子序列 是指这样一个新的字符串：它是由原字符串在不改变字符的相对顺序的情况下删除某些字符（也可以不删除任何字符）后组成的新字符串。
     * 例如，"ace" 是 "abcde" 的子序列，但 "aec" 不是 "abcde" 的子序列。
     * 两个字符串的 公共子序列 是这两个字符串所共同拥有的子序列。
     * 示例 1：
     * 输入：text1 = "abcde", text2 = "ace"
     * 输出：3
     * 解释：最长公共子序列是 "ace" ，它的长度为 3 。
     * 示例 2：
     * 输入：text1 = "abc", text2 = "abc"
     * 输出：3
     * 解释：最长公共子序列是 "abc" ，它的长度为 3 。
     * 示例 3：
     * 输入：text1 = "abc", text2 = "def"
     * 输出：0
     * 解释：两个字符串没有公共子序列，返回 0 。
     * 解题思路：
     * dp[i][j]:s1[0..i-1] s2[0..j-1]它们的LCS的长度是dp[i][j]
     * s1[i] == s2[j]=
     * dp[i][j] = dp[i-1][j-1] + 1
     * s1 s2都前进一步
     * s1[i] != s2[j]
     * dp[i][j] = Math.math(dp[i-1][j], dp[i][j-1]);
     * s1退后一步或者s2退后一步
     *
     * @param text1
     * @param text2
     * @return
     */
    public int longestCommonSubsequence(String text1, String text2) {
        int[][] dp = new int[text1.length() + 1][text2.length() + 1];
        for (int i = 1; i <= text1.length(); i++) {
            for (int j = 1; j <= text2.length(); j++) {
                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        return dp[text1.length()][text2.length()];
    }

    @Test
    public void testLongestCommonSubsequence() {
        Assert.assertEquals(3, longestCommonSubsequence("abcde", "ace"));
        Assert.assertEquals(3, longestCommonSubsequence("abc", "abc"));
        Assert.assertEquals(0, longestCommonSubsequence("abc", "def"));
    }

    /**
     * 516. 最长回文子序列
     * 给你一个字符串 s ，找出其中最长的回文子序列，并返回该序列的长度。
     * 子序列定义为：不改变剩余字符顺序的情况下，删除某些字符或者不删除任何字符形成的一个序列。
     * 示例 1：
     * 输入：s = "bbbab"
     * 输出：4
     * 解释：一个可能的最长回文子序列为 "bbbb" 。
     * 示例 2：
     * 输入：s = "cbbd"
     * 输出：2
     * 解释：一个可能的最长回文子序列为 "bb" 。
     * 解题思路：dp[i][j]:s[i..j]的最长回文长度
     *
     * @param s
     * @return
     */
    public int longestPalindromeSubseq(String s) {
        int len = s.length();
        int[][] dp = new int[len + 1][len + 1];
        for (int i = 0; i <= len; i++) {
            dp[i][i] = 1;
        }
        // 倒着算
        for (int i = len; i > 0; i--) {
            for (int j = i + 1; j <= len; j++) {
                if (s.charAt(i - 1) == s.charAt(j - 1)) {
                    dp[i][j] = dp[i + 1][j - 1] + 2;
                } else {
                    dp[i][j] = Math.max(dp[i + 1][j], dp[i][j - 1]);
                }
            }
        }
        return dp[1][len];
    }

    @Test
    public void testLongestPalindromeSubseq() {
        Assert.assertEquals(4, longestPalindromeSubseq("bbbab"));
        Assert.assertEquals(2, longestPalindromeSubseq("cbbd"));
    }

    /**
     * 解题思路：
     * dp[i]: s[0..i-1]的最长回文长度
     *
     * @param s
     * @return
     */
    public int longestPalindromeSubseq2(String s) {
        int len = s.length();
        int[] dp = new int[len + 1];
        dp[1] = 1;
        Arrays.fill(dp, 1);
        for (int i = len; i > 0; i--) {
            int pre = 0;
            for (int j = i + 1; j <= len; j++) {
                int tmp = dp[j];
                if (s.charAt(i - 1) == s.charAt(j - 1)) {
                    dp[j] = pre + 2;
                } else {
                    dp[j] = Math.max(dp[j], dp[j - 1]);
                }
                pre = tmp;
            }
        }
        return dp[len];
    }

    @Test
    public void testLongestPalindromeSubseq2() {
        Assert.assertEquals(4, longestPalindromeSubseq2("bbbab"));
        Assert.assertEquals(2, longestPalindromeSubseq2("cbbd"));
    }

    /**
     * 647. 回文子串
     * https://leetcode.cn/problems/palindromic-substrings/description/
     * 给你一个字符串 s ，请你统计并返回这个字符串中 回文子串 的数目。
     * 回文字符串 是正着读和倒过来读一样的字符串。
     * 子字符串 是字符串中的由连续字符组成的一个序列。
     * 具有不同开始位置或结束位置的子串，即使是由相同的字符组成，也会被视作不同的子串。
     * 示例 1：
     * 输入：s = "abc"
     * 输出：3
     * 解释：三个回文子串: "a", "b", "c"
     * 示例 2：
     * 输入：s = "aaa"
     * 输出：6
     * 解释：6个回文子串: "a", "a", "a", "aa", "aa", "aaa"
     * 解题思路：
     * 中心扩展法，分奇数、偶数扩展
     *
     * @param s
     * @return
     */
    public int countSubstrings(String s) {
        int ans = 0;
        for (int center = 0; center < s.length(); center++) {
            ans += expendCountSubstrings(s, center, center) + expendCountSubstrings(s, center, center + 1);
        }
        return ans;
    }

    private int expendCountSubstrings(String s, int left, int right) {
        int ans = 0;
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            left--;
            right++;
            ans++;
        }
        return ans;
    }

    @Test
    public void testCountSubstrings() {
        Assert.assertEquals(3, countSubstrings("abc"));
        Assert.assertEquals(6, countSubstrings("aaa"));
    }

    /**
     * 解题思路
     * 中心扩展法 枚举所有中心点
     * 由此我们可以看出长度为 n 的字符串会生成 2n−1 组回文中心
     * 其中l=i/2, r=l+ i mod 2
     *
     * @param s
     * @return
     */
    public int countSubstrings2(String s) {
        int ans = 0;
        for (int center = 0; center < 2 * s.length() - 1; center++) {
            int left = center / 2, right = left + center % 2;
            while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
                left--;
                right++;
                ans++;
            }
        }
        return ans;
    }

    @Test
    public void testCountSubstrings2() {
        Assert.assertEquals(3, countSubstrings2("abc"));
        Assert.assertEquals(6, countSubstrings2("aaa"));
    }

    /**
     * dp法
     * dp[i][j] = s[i..j]的回文子串个数
     *
     * @param s
     * @return
     */
    public int countSubstringsDp(String s) {
        int len = s.length(), ans = 0;
        int[][] dp = new int[len][len];
        for (int i = len - 1; i >= 0; i--) {
            // 中心分奇偶数 所以从i开始
            for (int j = i; j < len; j++) {
                if (s.charAt(i) == s.charAt(j)) {
                    if (j <= i + 1) {
                        // "a" 和 "aa" 的情况
                        dp[i][j] = 1;
                    } else {
                        dp[i][j] = dp[i + 1][j - 1];
                    }
                }
                ans += dp[i][j];
            }
        }
        return Arrays.stream(dp).flatMapToInt(Arrays::stream).sum();
    }

    @Test
    public void testCountSubstringsDp() {
        Assert.assertEquals(3, countSubstringsDp("abc"));
        Assert.assertEquals(6, countSubstringsDp("aaa"));
    }

    /**
     * 解题思路：
     * Manacher马拉车
     * f[i] 我们用 f(i) 来表示以 s 的第 i 位为回文中心，可以拓展出的最大回文半径，那么 f(i)−1就是以 i 为中心的最大回文串长度
     *
     * @param s
     * @return
     */
    public int countSubstringsManacher(String s) {
        int len = s.length();
        StringBuilder sb = new StringBuilder("$#");
        for (int i = 0; i < len; ++i) {
            sb.append(s.charAt(i));
            sb.append('#');
        }
        len = sb.length();
        sb.append('!');

        int[] f = new int[len];
        int iMax = 0, rMax = 0, ans = 0;
        for (int i = 1; i < len; i++) {
            // 初始化f[i]
            if (i <= rMax) {
                // 对称位置和rMax-i取最小
                f[i] = Math.min(f[2 * iMax - i], rMax - i);
            } else {
                f[i] = 1;
            }
            // 中心拓展
            while (sb.charAt(i + f[i]) == sb.charAt(i - f[i])) {
                ++f[i];
            }
            // 动态维护 iMax 和 rMax
            if (i + f[i] > rMax) {
                iMax = i;
                rMax = i + f[i];
            }
            // 统计答案, 当前贡献为 (f[i] - 1) / 2 上取整,f[i] - 1即是回文字符串长度
            // 左一半加上有一半
            ans += f[i] / 2;
        }
        return ans;
    }

    @Test
    public void testCountSubstringsManacher() {
        Assert.assertEquals(3, countSubstringsManacher("abc"));
        Assert.assertEquals(6, countSubstringsManacher("aaa"));
    }

    /**
     * 5. 最长回文子串
     * 给你一个字符串 s，找到 s 中最长的回文子串。
     * 如果字符串的反序与原始字符串相同，则该字符串称为回文字符串。
     * 示例 1：
     * 输入：s = "babad"
     * 输出："bab"
     * 解释："aba" 同样是符合题意的答案。
     * 示例 2：
     * 输入：s = "cbbd"
     * 输出："bb"
     * 解题思路：中心扩展法
     *
     * @param s
     * @return
     */
    public String longestPalindrome(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        int start = 0, end = 0;
        for (int i = 0; i < s.length(); i++) {
            int len = Math.max(expendLongestPalindrome(s, i, i), expendLongestPalindrome(s, i, i + 1));
            if (end - start < len) {
                // 兼容bb 和 aba2种情况
                start = i - (len - 1) / 2;
                end = i + len / 2;
            }
        }
        // substring左闭右开
        return s.substring(start, end + 1);
    }

    private int expendLongestPalindrome(String s, int left, int right) {
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            left--;
            right++;
        }
        // 符合条件后又向两边扩扩了，所以(right - left + 1) - 2 = right - left - 1
        return right - left - 1;
    }

    @Test
    public void testLongestPalindrome() {
        String res = longestPalindrome("babad");
        Assert.assertEquals(true, StringUtils.equals("bab", res) || StringUtils.equals("aba", res));
        Assert.assertEquals(true, StringUtils.equals("bb", longestPalindrome("cbbd")));
    }

    public String longestPalindromeDp(String s) {
        int len = s.length();
        boolean[][] dp = new boolean[len][len];
        int start = 0, maxLen = 0;
        for (int i = len - 1; i >= 0; i--) {
            for (int j = i; j < len; j++) {
                if (s.charAt(i) == s.charAt(j)) {
                    if (j <= i + 1) {
                        dp[i][j] = true;
                    } else {
                        dp[i][j] = dp[i + 1][j - 1];
                    }
                }
                if (dp[i][j] && j - i + 1 > maxLen) {
                    maxLen = j - i + 1;
                    start = i;
                }
            }
        }
        return s.substring(start, start + maxLen);
    }

    @Test
    public void testLongestPalindromeDp() {
        String res = longestPalindromeDp("babad");
        Assert.assertEquals(true, StringUtils.equals("bab", res) || StringUtils.equals("aba", res));
        Assert.assertEquals(true, StringUtils.equals("bb", longestPalindromeDp("cbbd")));
    }

    public String longestPalindromeManacher(String s) {
        int len = s.length();
        StringBuilder sb = new StringBuilder("$#");
        for (int i = 0; i < len; i++) {
            sb.append(s.charAt(i)).append("#");
        }
        len = sb.length();
        sb.append("!");
        int[] f = new int[len];
        int rMax = 0, iMax = 0, start = 0, end = 0;
        for (int i = 1; i < len; i++) {
            // 都是通过i推理出来的 所以肯定和i有关系
            f[i] = i <= rMax ? Math.min(rMax - i, f[2 * iMax - i]) : 1;
            // 中心扩展
            while (sb.charAt(i + f[i]) == sb.charAt(i - f[i])) {
                f[i]++;
            }
            // 最右端又往远了，更新中心和半径
            if (i + f[i] > rMax) {
                iMax = i;
                rMax = i + f[i];
            }

            // 回文子串长度
            if (2 * f[i] - 1 > end - start) {
                // (f[i] - 1) 计算出的间隔
                start = i - (f[i] - 1);
                end = i + (f[i] - 1);
            }
        }
        StringBuilder ans = new StringBuilder();
        for (int i = start; i <= end; i++) {
            if (sb.charAt(i) != '#') {
                ans.append(sb.charAt(i));
            }
        }
        return ans.toString();
    }

    @Test
    public void testLongestPalindromeManacher() {
        String res = longestPalindromeManacher("babad");
        log.info("testLongestPalindromeManacher res={}", res);
        Assert.assertEquals(true, StringUtils.equals("bab", res) || StringUtils.equals("aba", res));
        Assert.assertEquals(true, StringUtils.equals("bb", longestPalindromeManacher("cbbd")));
    }

    /**
     * TODO 55. 跳跃游戏
     * https://leetcode.cn/problems/jump-game/
     * 给定一个非负整数数组 nums ，你最初位于数组的 第一个下标 。
     * 数组中的每个元素代表你在该位置可以跳跃的最大长度。
     * 判断你是否能够到达最后一个下标。
     * 示例 1：
     *  输入：nums = [2,3,1,1,4]
     *  输出：true
     *  解释：可以先跳 1 步，从下标 0 到达下标 1, 然后再从下标 1 跳 3 步到达最后一个下标。
     * 示例 2：
     *  输入：nums = [3,2,1,0,4]
     *  输出：false
     *  解释：无论怎样，总会到达下标为 3 的位置。但该下标的最大跳跃长度是 0 ， 所以永远不可能到达最后一个下标。
     *
     * @param nums
     * @return
     */
    public boolean canJump(int[] nums) {
        return false;
    }

    @Test
    public void testCanJump() {
        Assert.assertEquals(true, canJump(new int[]{2, 3, 1, 1, 4}));
        Assert.assertEquals(false, canJump(new int[]{3, 2, 1, 0, 4}));
    }

    /**
     * TODO 45. 跳跃游戏 II
     * https://leetcode.cn/problems/jump-game-ii/description/
     * 给定一个长度为 n 的 0 索引整数数组 nums。初始位置为 nums[0]。
     * 每个元素 nums[i] 表示从索引 i 向前跳转的最大长度。换句话说，如果你在 nums[i] 处，你可以跳转到任意 nums[i + j] 处:
     * 0 <= j <= nums[i]
     * i + j < n
     * 返回到达 nums[n - 1] 的最小跳跃次数。生成的测试用例可以到达 nums[n - 1]。
     * 示例 1:
     *  输入: nums = [2,3,1,1,4]
     *  输出: 2
     *  解释: 跳到最后一个位置的最小跳跃数是 2。
     *      从下标为 0 跳到下标为 1 的位置，跳 1 步，然后跳 3 步到达数组的最后一个位置。
     * 示例 2:
     *  输入: nums = [2,3,0,1,4]
     *  输出: 2
     *
     * @param nums
     * @return
     */
    public int jump(int[] nums) {
        return 0;
    }

    @Test
    public void testJump() {
        Assert.assertEquals(2, jump(new int[]{2, 3, 1, 1, 4}));
        Assert.assertEquals(2, jump(new int[]{3, 2, 1, 0, 4}));
    }
}