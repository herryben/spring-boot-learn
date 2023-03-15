package com.learn.dp;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

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
                    dp[i][j] = dp[i - 1][j];
                } else {
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
                // 当前元素比之前的元素大才更新
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
     * 01背包
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
}