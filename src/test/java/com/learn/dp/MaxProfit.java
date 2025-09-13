package com.learn.dp;

import org.junit.Assert;
import org.junit.Test;

public class MaxProfit {
    /**
     * 121. 买卖股票的最佳时机
     * https://leetcode.cn/problems/best-time-to-buy-and-sell-stock/
     * 给定一个数组 prices ，它的第 i 个元素 prices[i] 表示一支给定股票第 i 天的价格。
     * <p>
     * 你只能选择 某一天 买入这只股票，并选择在 未来的某一个不同的日子 卖出该股票。设计一个算法来计算你所能获取的最大利润。
     * <p>
     * 返回你可以从这笔交易中获取的最大利润。如果你不能获取任何利润，返回 0 。
     * 示例 1：
     * <p>
     * 输入：[7,1,5,3,6,4]
     * 输出：5
     * 解释：在第 2 天（股票价格 = 1）的时候买入，在第 5 天（股票价格 = 6）的时候卖出，最大利润 = 6-1 = 5 。
     * 注意利润不能是 7-1 = 6, 因为卖出价格需要大于买入价格；同时，你不能在买入前卖出股票。
     * 示例 2：
     * <p>
     * 输入：prices = [7,6,4,3,1]
     * 输出：0
     * 解释：在这种情况下, 没有交易完成, 所以最大利润为 0。
     * 解题思路：就是普通贪心+模拟
     * 相当于维护了一个长度的 从小到大的单调栈
     *
     * @param prices
     * @return
     */
    public int maxProfit(int[] prices) {
        int ans = 0, cur = prices[0];
        for (int price : prices) {
            ans = Math.max(ans, price - cur);
            cur = Math.min(cur, price);
        }
        return ans;
    }

    @Test
    public void testMaxProfit() {
        Assert.assertEquals(5, maxProfit(new int[]{7, 1, 5, 3, 6, 4}));
        Assert.assertEquals(0, maxProfit(new int[]{7, 6, 4, 3, 1}));
    }

    /**
     * TODO 123. 买卖股票的最佳时机 III
     * https://leetcode.cn/problems/best-time-to-buy-and-sell-stock-iii/
     * 给定一个数组，它的第 i 个元素是一支给定的股票在第 i 天的价格。
     * <p>
     * 设计一个算法来计算你所能获取的最大利润。你最多可以完成 两笔 交易。
     * <p>
     * 注意：你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。
     * 示例 1:
     * <p>
     * 输入：prices = [3,3,5,0,0,3,1,4]
     * 输出：6
     * 解释：在第 4 天（股票价格 = 0）的时候买入，在第 6 天（股票价格 = 3）的时候卖出，这笔交易所能获得利润 = 3-0 = 3 。
     * 随后，在第 7 天（股票价格 = 1）的时候买入，在第 8 天 （股票价格 = 4）的时候卖出，这笔交易所能获得利润 = 4-1 = 3 。
     * 示例 2：
     * <p>
     * 输入：prices = [1,2,3,4,5]
     * 输出：4
     * 解释：在第 1 天（股票价格 = 1）的时候买入，在第 5 天 （股票价格 = 5）的时候卖出, 这笔交易所能获得利润 = 5-1 = 4 。
     * 注意你不能在第 1 天和第 2 天接连购买股票，之后再将它们卖出。
     * 因为这样属于同时参与了多笔交易，你必须在再次购买前出售掉之前的股票。
     * 示例 3：
     * <p>
     * 输入：prices = [7,6,4,3,1]
     * 输出：0
     * 解释：在这个情况下, 没有交易完成, 所以最大利润为 0。
     * 示例 4：
     * <p>
     * 输入：prices = [1]
     * 输出：0
     *
     * @param prices
     * @return
     */
    public int maxProfit3(int[] prices) {
        return 0;
    }

    @Test
    public void testMaxProfit3() {
        Assert.assertEquals(6, maxProfit3(new int[]{3, 3, 5, 0, 0, 3, 1, 4}));
        Assert.assertEquals(4, maxProfit3(new int[]{1, 2, 3, 4, 5}));
        Assert.assertEquals(0, maxProfit3(new int[]{7, 6, 4, 3, 1}));
    }

    /**
     * TODO 给你一个整数数组 prices ，其中 prices[i] 表示某支股票第 i 天的价格。
     * <p>
     * 在每一天，你可以决定是否购买和/或出售股票。你在任何时候 最多 只能持有 一股 股票。你也可以先购买，然后在 同一天 出售。
     * <p>
     * 返回 你能获得的 最大 利润 。
     * 示例 1：
     * <p>
     * 输入：prices = [7,1,5,3,6,4]
     * 输出：7
     * 解释：在第 2 天（股票价格 = 1）的时候买入，在第 3 天（股票价格 = 5）的时候卖出, 这笔交易所能获得利润 = 5 - 1 = 4 。
     * 随后，在第 4 天（股票价格 = 3）的时候买入，在第 5 天（股票价格 = 6）的时候卖出, 这笔交易所能获得利润 = 6 - 3 = 3 。
     * 总利润为 4 + 3 = 7 。
     * 示例 2：
     * <p>
     * 输入：prices = [1,2,3,4,5]
     * 输出：4
     * 解释：在第 1 天（股票价格 = 1）的时候买入，在第 5 天 （股票价格 = 5）的时候卖出, 这笔交易所能获得利润 = 5 - 1 = 4 。
     * 总利润为 4 。
     * 示例 3：
     * <p>
     * 输入：prices = [7,6,4,3,1]
     * 输出：0
     * 解释：在这种情况下, 交易无法获得正利润，所以不参与交易可以获得最大利润，最大利润为 0 。
     *
     * @param prices
     * @return
     */
    public int maxProfit2(int[] prices) {
        return 0;
    }

    @Test
    public void testMaxProfit2() {
        Assert.assertEquals(7, maxProfit2(new int[]{7, 1, 5, 3, 6, 4}));
        Assert.assertEquals(4, maxProfit2(new int[]{1, 2, 3, 4, 5}));
        Assert.assertEquals(0, maxProfit2(new int[]{7, 6, 4, 3, 1}));
    }

    /**
     * TODO 188. 买卖股票的最佳时机 IV
     * https://leetcode.cn/problems/best-time-to-buy-and-sell-stock-iv/
     * 给定一个整数数组 prices ，它的第 i 个元素 prices[i] 是一支给定的股票在第 i 天的价格，和一个整型 k 。
     * <p>
     * 设计一个算法来计算你所能获取的最大利润。你最多可以完成 k 笔交易。也就是说，你最多可以买 k 次，卖 k 次。
     * <p>
     * 注意：你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。
     * 示例 1：
     * <p>
     * 输入：k = 2, prices = [2,4,1]
     * 输出：2
     * 解释：在第 1 天 (股票价格 = 2) 的时候买入，在第 2 天 (股票价格 = 4) 的时候卖出，这笔交易所能获得利润 = 4-2 = 2 。
     * 示例 2：
     * <p>
     * 输入：k = 2, prices = [3,2,6,5,0,3]
     * 输出：7
     * 解释：在第 2 天 (股票价格 = 2) 的时候买入，在第 3 天 (股票价格 = 6) 的时候卖出, 这笔交易所能获得利润 = 6-2 = 4 。
     * 随后，在第 5 天 (股票价格 = 0) 的时候买入，在第 6 天 (股票价格 = 3) 的时候卖出, 这笔交易所能获得利润 = 3-0 = 3 。
     *
     * @param prices
     * @return
     */
    public int maxProfit4(int[] prices) {
        return 0;
    }

    @Test
    public void testMaxProfit4() {
        Assert.assertEquals(2, maxProfit4(new int[]{2, 4, 1}));
        Assert.assertEquals(7, maxProfit4(new int[]{3, 2, 6, 5, 0, 3}));
    }

    /**
     * TODO 309. 最佳买卖股票时机含冷冻期
     * https://leetcode.cn/problems/best-time-to-buy-and-sell-stock-with-cooldown/
     * 给定一个整数数组prices，其中第  prices[i] 表示第 i 天的股票价格 。​
     * <p>
     * 设计一个算法计算出最大利润。在满足以下约束条件下，你可以尽可能地完成更多的交易（多次买卖一支股票）:
     * <p>
     * 卖出股票后，你无法在第二天买入股票 (即冷冻期为 1 天)。
     * 注意：你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。
     * 示例 1:
     * <p>
     * 输入: prices = [1,2,3,0,2]
     * 输出: 3
     * 解释: 对应的交易状态为: [买入, 卖出, 冷冻期, 买入, 卖出]
     * 示例 2:
     * <p>
     * 输入: prices = [1]
     * 输出: 0
     *
     * @param prices
     * @return
     */
    public int maxProfit5(int[] prices) {
        return 0;
    }

    @Test
    public void testMaxProfit5() {
        Assert.assertEquals(3, maxProfit5(new int[]{1, 2, 3, 0, 2}));
        Assert.assertEquals(0, maxProfit5(new int[]{1}));
    }

    /**
     * TODO 714. 买卖股票的最佳时机含手续费
     * https://leetcode.cn/problems/best-time-to-buy-and-sell-stock-with-transaction-fee/
     * 给定一个整数数组 prices，其中 prices[i]表示第 i 天的股票价格 ；整数 fee 代表了交易股票的手续费用。
     * <p>
     * 你可以无限次地完成交易，但是你每笔交易都需要付手续费。如果你已经购买了一个股票，在卖出它之前你就不能再继续购买股票了。
     * <p>
     * 返回获得利润的最大值。
     * <p>
     * 注意：这里的一笔交易指买入持有并卖出股票的整个过程，每笔交易你只需要为支付一次手续费。
     * 示例 1：
     * <p>
     * 输入：prices = [1, 3, 2, 8, 4, 9], fee = 2
     * 输出：8
     * 解释：能够达到的最大利润:
     * 在此处买入 prices[0] = 1
     * 在此处卖出 prices[3] = 8
     * 在此处买入 prices[4] = 4
     * 在此处卖出 prices[5] = 9
     * 总利润: ((8 - 1) - 2) + ((9 - 4) - 2) = 8
     * 示例 2：
     * <p>
     * 输入：prices = [1,3,7,5,10,3], fee = 3
     * 输出：6
     *
     * @param prices
     * @return
     */
    public int maxProfit6(int[] prices, int fee) {
        return 0;
    }

    @Test
    public void testMaxProfit6() {
        Assert.assertEquals(8, maxProfit6(new int[]{1, 3, 2, 8, 4, 9}, 2));
        Assert.assertEquals(6, maxProfit6(new int[]{1, 3, 7, 5, 10, 3}, 8));
    }
}
