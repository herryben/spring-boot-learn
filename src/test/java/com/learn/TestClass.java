package com.learn;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * @author herryhaorui@didiglobal.com
 * @desc
 * @date 2023/10/18 1:47 下午
 */
public class TestClass {

    /**
     * 给定长度为n的数组，每个元素代表一个木头的长度，木头可以任意截断，从这堆木头中截出至少k个相同长度为m的木块。已知k，求max(m)。
     * 输入两行，第一行n,k，第二行为数组序列。输出最大值。
     * <p>
     * 输入:
     * n=5 k=5
     * arr=[4,7,2,10,5]
     * 输出:4
     * 解释：最多可以把它分成5段长度为4的木头
     * 解题思路：类似珂珂吃香蕉
     */
    public int maxNum(int[] nums, int k) {
        int left = 1, right = Arrays.stream(nums).max().getAsInt();
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (valid(nums, k, mid)) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }

        return right - 1;
    }

    public boolean valid(int[] nums, int k, int m) {
        int sum = 0;
        for (int num : nums) {
            sum += num / m;
        }

        return sum >= k;
    }

    @Test
    public void testClass() {
        Assert.assertEquals(4, maxNum(new int[]{4, 7, 2, 10, 5}, 5));
        Assert.assertEquals(5, maxNum(new int[]{5, 7, 5, 10, 5}, 5));
    }
}
