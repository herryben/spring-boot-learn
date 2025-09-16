package com.learn.array;

import com.learn.utils.Utils;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArraySolution {
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

        int pt1 = fast;
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

    /**
     * TODO 128. 最长连续序列
     * https://leetcode.cn/problems/longest-consecutive-sequence/?envType=study-plan-v2&envId=top-100-liked
     * 给定一个未排序的整数数组 nums ，找出数字连续的最长序列（不要求序列元素在原数组中连续）的长度。
     * <p>
     * 请你设计并实现时间复杂度为 O(n) 的算法解决此问题。
     * <p>
     * 示例 1：
     * <p>
     * 输入：nums = [100,4,200,1,3,2]
     * 输出：4
     * 解释：最长数字连续序列是 [1, 2, 3, 4]。它的长度为 4。
     * 示例 2：
     * <p>
     * 输入：nums = [0,3,7,2,5,8,4,6,0,1]
     * 输出：9
     *
     * @param nums
     * @return
     */
    public int longestConsecutive(int[] nums) {
        return 0;
    }

    @Test
    public void testLongestConsecutive() {
        Assert.assertEquals(4, findDuplicate(new int[]{100, 4, 200, 1, 3, 2}));
        Assert.assertEquals(9, findDuplicate(new int[]{0, 3, 7, 2, 5, 8, 4, 6, 0, 1}));
    }

    /**
     * 53. 最大子数组和
     * https://leetcode.cn/problems/maximum-subarray/?envType=study-plan-v2&envId=top-100-liked
     * 给你一个整数数组 nums ，请你找出一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
     * <p>
     * 子数组 是数组中的一个连续部分。
     * 示例 1：
     * <p>
     * 输入：nums = [-2,1,-3,4,-1,2,1,-5,4]
     * 输出：6
     * 解释：连续子数组 [4,-1,2,1] 的和最大，为 6 。
     * 示例 2：
     * <p>
     * 输入：nums = [1]
     * 输出：1
     * 示例 3：
     * <p>
     * 输入：nums = [5,4,-1,7,8]
     * 输出：23
     * <p>
     * 解题思路：
     * 1. kadane算法标准流程
     *
     * @param nums
     * @return
     */
    public int maxSubArray(int[] nums) {
        int max = Integer.MIN_VALUE;
        int cur = 0;
        for (int num : nums) {
            cur = num + Math.max(cur, 0);
            max = Math.max(max, cur);
        }
        return max;
    }

    @Test
    public void testMaxSubArray() {
        Assert.assertEquals(6, maxSubArray(new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4}));
        Assert.assertEquals(1, maxSubArray(new int[]{1}));
        Assert.assertEquals(23, maxSubArray(new int[]{5, 4, -1, 7, 8}));
    }

    /**
     * 16. 最接近的三数之和
     * https://leetcode.cn/problems/3sum-closest/solutions/301382/zui-jie-jin-de-san-shu-zhi-he-by-leetcode-solution/
     * 给你一个长度为 n 的整数数组 nums 和 一个目标值 target。请你从 nums 中选出三个整数，使它们的和与 target 最接近。
     * <p>
     * 返回这三个数的和。
     * <p>
     * 假定每组输入只存在恰好一个解。
     * <p>
     * <p>
     * <p>
     * 示例 1：
     * <p>
     * 输入：nums = [-1,2,1,-4], target = 1
     * 输出：2
     * 解释：与 target 最接近的和是 2 (-1 + 2 + 1 = 2)。
     * 示例 2：
     * <p>
     * 输入：nums = [0,0,0], target = 1
     * 输出：0
     * 解释：与 target 最接近的和是 0（0 + 0 + 0 = 0）。
     * 解题思路：排序+双指针
     *
     * @param nums
     * @param target
     * @return
     */
    public int threeSumClosest(int[] nums, int target) {
        // write your code here.
        Arrays.sort(nums);
        int ans = nums[0] + nums[1] + nums[2];
        for (int i = 0; i <= nums.length - 3; i++) {
            int left = i + 1, right = nums.length - 1;
            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];
                if (sum == target) {
                    return target;
                } else if (sum > target) {
                    right--;
                } else {
                    left++;
                }

                if (Math.abs(sum - target) < Math.abs(ans - target)) {
                    ans = sum;
                }
            }
        }

        return ans;
    }

    @Test
    public void testThreeSumClosest() {
        Assert.assertEquals(-2, threeSumClosest(new int[]{4, 0, 5, -5, 3, 3, 0, -4, -5}, -2));
        Assert.assertEquals(2, threeSumClosest(new int[]{-1, 2, 1, -4}, 1));
        Assert.assertEquals(0, threeSumClosest(new int[]{0, 0, 0}, 0));
    }

    /**
     * 15. 三数之和
     * https://leetcode.cn/problems/3sum/submissions/441931731/
     * 给你一个整数数组 nums ，判断是否存在三元组 [nums[i], nums[j], nums[k]] 满足 i != j、i != k 且 j != k ，同时还满足 nums[i] + nums[j] + nums[k] == 0 。请你返回所有和为 0 且不重复的三元组。
     * <p>
     * 注意：答案中不可以包含重复的三元组。
     * <p>
     * <p>
     * <p>
     * <p>
     * <p>
     * 示例 1：
     * <p>
     * 输入：nums = [-1,0,1,2,-1,-4]
     * 输出：[[-1,-1,2],[-1,0,1]]
     * 解释：
     * nums[0] + nums[1] + nums[2] = (-1) + 0 + 1 = 0 。
     * nums[1] + nums[2] + nums[4] = 0 + 1 + (-1) = 0 。
     * nums[0] + nums[3] + nums[4] = (-1) + 2 + (-1) = 0 。
     * 不同的三元组是 [-1,0,1] 和 [-1,-1,2] 。
     * 注意，输出的顺序和三元组的顺序并不重要。
     * 示例 2：
     * <p>
     * 输入：nums = [0,1,1]
     * 输出：[]
     * 解释：唯一可能的三元组和不为 0 。
     * 示例 3：
     * <p>
     * 输入：nums = [0,0,0]
     * 输出：[[0,0,0]]
     * 解释：唯一可能的三元组和为 0 。
     *
     * @param nums
     * @return
     */
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        Arrays.sort(nums);
        for (int i = 0; i < nums.length; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            int left = i + 1, right = nums.length - 1;
            while (left < right) {
                if (nums[i] + nums[left] + nums[right] > 0) {
                    right--;
                    while (left < right && nums[right] == nums[right + 1]) {
                        right--;
                    }
                } else if (nums[i] + nums[left] + nums[right] < 0) {
                    left++;
                    while (left < right && nums[left - 1] == nums[left]) {
                        left++;
                    }
                } else {
                    List<Integer> list = new ArrayList<>();
                    list.add(nums[i]);
                    list.add(nums[left]);
                    list.add(nums[right]);
                    ans.add(list);
                    left++;
                    right--;
                }
            }
        }

        return ans;
    }

    @Test
    public void testThreeSum() {
        Assert.assertEquals(true, Utils.isEqualArray(new int[][]{{-1, -1, 2}, {-1, 0, 1}}, Utils.convertToArray(threeSum(new int[]{-1, 0, 1, 2, -1, -4}))));
        Assert.assertEquals(true, Utils.isEqualArray(new int[][]{}, Utils.convertToArray(threeSum(new int[]{0, 1, 1}))));
        Assert.assertEquals(true, Utils.isEqualArray(new int[][]{{0, 0, 0}}, Utils.convertToArray(threeSum(new int[]{0, 0, 0}))));
    }

    /**
     * 11. 盛最多水的容器
     * https://leetcode.cn/problems/container-with-most-water/description/
     * 给定一个长度为 n 的整数数组 height 。有 n 条垂线，第 i 条线的两个端点是 (i, 0) 和 (i, height[i]) 。
     * <p>
     * 找出其中的两条线，使得它们与 x 轴共同构成的容器可以容纳最多的水。
     * <p>
     * 返回容器可以储存的最大水量。
     * <p>
     * 说明：你不能倾斜容器。
     * 解题思路：双指针法
     * 一句话概括：我们left++和right--都是为了尝试取到更多的水，如果短的板不动的话，取到的水永远不会比上次多。
     */
    public int maxArea(int[] height) {
        int ans = Integer.MIN_VALUE;
        int left = 0, right = height.length - 1;
        while (left < right) {
            ans = Math.max(ans, Math.min(height[left], height[right]) * (right - left));
            if (height[left] < height[right]) {
                left++;
            } else {
                right--;
            }
        }
        return ans;
    }

    @Test
    public void testMaxArea() {
        Assert.assertEquals(49, maxArea(new int[]{1, 8, 6, 2, 5, 4, 8, 3, 7}));
        Assert.assertEquals(1, maxArea(new int[]{1, 1}));
    }
}
