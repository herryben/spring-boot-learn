package com.learn.common;

import org.junit.Assert;
import org.junit.Test;

public class CommonSolution {
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
}
