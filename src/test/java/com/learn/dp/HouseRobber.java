package com.learn.dp;

import org.junit.Assert;
import org.junit.Test;

public class HouseRobber {
    /**
     * 198. 打家劫舍
     * https://leetcode.cn/problems/house-robber/
     * 你是一个专业的小偷，计划偷窃沿街的房屋。每间房内都藏有一定的现金，影响你偷窃的唯一制约因素就是相邻的房屋装有相互连通的防盗系统，如果两间相邻的房屋在同一晚上被小偷闯入，系统会自动报警。
     * <p>
     * 给定一个代表每个房屋存放金额的非负整数数组，计算你 不触动警报装置的情况下 ，一夜之内能够偷窃到的最高金额。
     * 示例 1：
     * <p>
     * 输入：[1,2,3,1]
     * 输出：4
     * 解释：偷窃 1 号房屋 (金额 = 1) ，然后偷窃 3 号房屋 (金额 = 3)。
     * 偷窃到的最高金额 = 1 + 3 = 4 。
     * 示例 2：
     * <p>
     * 输入：[2,7,9,3,1]
     * 输出：12
     * 解释：偷窃 1 号房屋 (金额 = 2), 偷窃 3 号房屋 (金额 = 9)，接着偷窃 5 号房屋 (金额 = 1)。
     * 偷窃到的最高金额 = 2 + 9 + 1 = 12 。
     * 解题思路：dp[i] 为到底i个最多能抢到的钱
     * @param nums
     * @return
     */
    public int rob(int[] nums) {
        int[] dp = new int[nums.length + 1];
        dp[1] = nums[0];
        for (int i = 2; i <= nums.length; i++) {
            // Math.max(选前一个（不抢当前）, 选前前一个加自己（抢当前）)
            dp[i] = Math.max(dp[i - 1], dp[i - 2] + nums[i - 1]);
        }
        return dp[nums.length];
    }

    @Test
    public void testRob() {
        Assert.assertEquals(3, rob(new int[]{2, 3}));
        Assert.assertEquals(1, rob(new int[]{1}));
        Assert.assertEquals(4, rob(new int[]{1, 2, 3, 1}));
        Assert.assertEquals(12, rob(new int[]{2, 7, 9, 3, 1}));
    }

    /**
     * 213. 打家劫舍 II
     * https://leetcode.cn/problems/house-robber-ii/
     * 你是一个专业的小偷，计划偷窃沿街的房屋，每间房内都藏有一定的现金。这个地方所有的房屋都 围成一圈 ，这意味着第一个房屋和最后一个房屋是紧挨着的。同时，相邻的房屋装有相互连通的防盗系统，如果两间相邻的房屋在同一晚上被小偷闯入，系统会自动报警 。
     * <p>
     * 给定一个代表每个房屋存放金额的非负整数数组，计算你 在不触动警报装置的情况下 ，今晚能够偷窃到的最高金额。
     * 示例 1：
     * <p>
     * 输入：nums = [2,3,2]
     * 输出：3
     * 解释：你不能先偷窃 1 号房屋（金额 = 2），然后偷窃 3 号房屋（金额 = 2）, 因为他们是相邻的。
     * 示例 2：
     * <p>
     * 输入：nums = [1,2,3,1]
     * 输出：4
     * 解释：你可以先偷窃 1 号房屋（金额 = 1），然后偷窃 3 号房屋（金额 = 3）。
     * 偷窃到的最高金额 = 1 + 3 = 4 。
     * 示例 3：
     * <p>
     * 输入：nums = [1,2,3]
     * 输出：3
     * 解题思路：环状排列 意味着第一个房子和最后一个房子中 只能选择一个偷窃，因此可以把此 环状排列房间 问题约化为两个 单排排列房间 子问题
     * 环状排列 + 2个单排dp
     * @param nums
     * @return
     */
    public int rob2(int[] nums) {
        if (nums.length == 1) {
            return nums[0];
        }
        if (nums.length == 2) {
            return Math.max(nums[0], nums[1]);
        }
        return Math.max(rob(nums, 0, nums.length - 2),
                rob(nums, 1, nums.length - 1));
    }

    public int rob(int[] nums, int start, int end) {
       int dpi = 0, dp1 = nums[start], dp2 = 0;
        for (int i = start + 1; i <= end; i++) {
            dpi = Math.max(dp1, dp2 + nums[i]);
            dp2 = dp1;
            dp1 = dpi;
        }
        return dpi;
    }

    @Test
    public void testRob2() {
        Assert.assertEquals(3, rob2(new int[]{2, 3, 2}));
        Assert.assertEquals(4, rob2(new int[]{1, 2, 3, 1}));
        Assert.assertEquals(3, rob2(new int[]{1, 2, 3}));
    }

    /**
     * TODO 2560. 打家劫舍 IV
     * https://leetcode.cn/problems/house-robber-iv/
     * 沿街有一排连续的房屋。每间房屋内都藏有一定的现金。现在有一位小偷计划从这些房屋中窃取现金。
     * <p>
     * 由于相邻的房屋装有相互连通的防盗系统，所以小偷 不会窃取相邻的房屋 。
     * <p>
     * 小偷的 窃取能力 定义为他在窃取过程中能从单间房屋中窃取的 最大金额 。
     * <p>
     * 给你一个整数数组 nums 表示每间房屋存放的现金金额。形式上，从左起第 i 间房屋中放有 nums[i] 美元。
     * <p>
     * 另给你一个整数 k ，表示窃贼将会窃取的 最少 房屋数。小偷总能窃取至少 k 间房屋。
     * <p>
     * 返回小偷的 最小 窃取能力。
     * 示例 1：
     * <p>
     * 输入：nums = [2,3,5,9], k = 2
     * 输出：5
     * 解释：
     * 小偷窃取至少 2 间房屋，共有 3 种方式：
     * - 窃取下标 0 和 2 处的房屋，窃取能力为 max(nums[0], nums[2]) = 5 。
     * - 窃取下标 0 和 3 处的房屋，窃取能力为 max(nums[0], nums[3]) = 9 。
     * - 窃取下标 1 和 3 处的房屋，窃取能力为 max(nums[1], nums[3]) = 9 。
     * 因此，返回 min(5, 9, 9) = 5 。
     * 示例 2：
     * <p>
     * 输入：nums = [2,7,9,3,1], k = 2
     * 输出：2
     * 解释：共有 7 种窃取方式。窃取能力最小的情况所对应的方式是窃取下标 0 和 4 处的房屋。返回 max(nums[0], nums[4]) = 2 。
     *
     * @param nums
     * @param k
     * @return
     */
    public int minCapability(int[] nums, int k) {
        return 0;
    }

    @Test
    public void testMinCapability() {
        Assert.assertEquals(5, minCapability(new int[]{2, 3, 5, 9}, 2));
        Assert.assertEquals(5, minCapability(new int[]{2, 7, 9, 3, 1}, 2));
    }
}
