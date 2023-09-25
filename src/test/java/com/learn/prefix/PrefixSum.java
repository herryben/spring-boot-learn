package com.learn.prefix;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author herryhaorui@didiglobal.com
 * @desc
 * @date 2023/7/7 8:37 下午
 */
public class PrefixSum {
    @Test
    public void testNumArray() {
        NumArray numArray = new NumArray(new int[] {-2, 0, 3, -5, 2, -1});
        Assert.assertEquals(1, numArray.sumRange(0, 2)); // return 1 ((-2) + 0 + 3)
        Assert.assertEquals(-1, numArray.sumRange(2, 5)); // return -1 (3 + (-5) + 2 + (-1))
        Assert.assertEquals(-3, numArray.sumRange(0, 5)); // return -3 ((-2) + 0 + 3 + (-5) + 2 + (-1))
    }

    @Test
    public void testNumMatrix() {
        NumMatrix numMatrix = new NumMatrix(new int[][]{{3,0,1,4,2},{5,6,3,2,1},{1,2,0,1,5},{4,1,0,1,7},{1,0,3,0,5}});
        Assert.assertEquals(8, numMatrix.sumRegion(2, 1, 4, 3)); // return 8 (红色矩形框的元素总和)
        Assert.assertEquals(11, numMatrix.sumRegion(1, 1, 2, 2)); // return 11 (绿色矩形框的元素总和)
        Assert.assertEquals(12, numMatrix.sumRegion(1, 2, 2, 4)); // return 12 (蓝色矩形框的元素总和)
    }

    /**
     * 560. 和为 K 的子数组
     * 给你一个整数数组 nums 和一个整数 k ，请你统计并返回 该数组中和为 k 的连续子数组的个数 。
     * https://leetcode.cn/problems/subarray-sum-equals-k/description/
     * 示例 1：
     *
     * 输入：nums = [1,1,1], k = 2
     * 输出：2
     * 示例 2：
     *
     * 输入：nums = [1,2,3], k = 3
     * 输出：2
     * 解题思路：前缀和
     * 求i..j的区间和为preSum[j + 1] - preSum[i]
     * 即最终结果求：preSum[j + 1] - preSum[i] = k
     * 简单移项可得符合条件的下标 i 需要满足 preSum[j + 1] - k = preSum[i]
     * 因为前缀和是从0开始加到j，所以只需要算1次即可，举例：1, 1的前缀和为2；1, 1, -1的前缀和也为2
     * 从0~nums.length保证了i~j递增，不会算到>j之后的结果
     * @param nums
     * @param k
     * @return
     */
    public int subarraySum(int[] nums, int k) {
        int sum = 0, ans = 0;
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, 1);
        for(int num : nums) {
            sum += num;
            int diff = sum - k;
            if (map.containsKey(diff)) {
                ans += map.get(diff);
            }
            map.compute(sum, (key, val) -> val == null ? 1 : ++val);
        }
        return ans;
    }

    @Test
    public void testSubarraySum() {
        Assert.assertEquals(2, subarraySum(new int[] {1,1,1}, 2));
        Assert.assertEquals(2, subarraySum(new int[] {1,2,3}, 3));
    }

    /**
     * 解题思路：
     * 双指针枚举
     * @param nums
     * @param k
     * @return
     */
    public int subarraySum2(int[] nums, int k) {
        int ans = 0;
        // 枚举所有数
        for (int i = 0; i < nums.length; i++) {
            int sum = 0;
            // 枚举[0, i]
            for (int j = i; j >= 0; j--) {
                sum += nums[j];
                if (sum == k) {
                    ans++;
                }
            }
        }
        return ans;
    }

    @Test
    public void testSubarraySum2() {
        Assert.assertEquals(2, subarraySum2(new int[] {1,1,1}, 2));
        Assert.assertEquals(2, subarraySum2(new int[] {1,2,3}, 3));
    }
}
