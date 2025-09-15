package com.learn.sort;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

@Slf4j
public class SortSolution {
    /**
     * 875. 爱吃香蕉的珂珂
     * 珂珂喜欢吃香蕉。这里有 n 堆香蕉，第 i 堆中有 piles[i] 根香蕉。警卫已经离开了，将在 h 小时后回来。
     * 珂珂可以决定她吃香蕉的速度 k （单位：根/小时）。每个小时，她将会选择一堆香蕉，从中吃掉 k 根。如果这堆香蕉少于 k 根，她将吃掉这堆的所有香蕉，然后这一小时内不会再吃更多的香蕉。
     * 珂珂喜欢慢慢吃，但仍然想在警卫回来前吃掉所有的香蕉。
     * 返回她可以在 h 小时内吃掉所有香蕉的最小速度 k（k 为整数）。
     * 示例 1：
     * 输入：piles = [3,6,7,11], h = 8
     * 输出：4
     * 示例 2：
     * 输入：piles = [30,11,23,4,20], h = 5
     * 输出：30
     * 示例 3：
     * 输入：piles = [30,11,23,4,20], h = 6
     * 输出：23
     * 解题思路：
     * 二分查找左边界
     * 查找区间[1, max]
     * 因为是求最小速度，所以是leftBound
     * @param piles
     * @param h
     * @return
     */
    public int minEatingSpeed(int[] piles, int h) {
        // 可以拆分，所以左边界从最小的1开始
        int low = 1, high = Arrays.stream(piles).max().getAsInt() + 1;
        while (low < high) {
            int mid = low + (high - low) / 2;
            int needHours = needHours(piles, mid);
            if (needHours > h) {
                low = mid + 1;
            } else if (needHours < h) {
                high = mid;
            } else {
                high = mid;
            }
        }
        return low;
    }

    // 小技巧：(pile + speed - 1) / speed 向上去整
    public int needHours(int[] piles, int speed) {
        int needHour = 0;
        for (int pile : piles) {
            needHour += (pile + speed - 1) / speed;
        }
        return needHour;
    }

    @Test
    public void testMinEatingSpeed() {
        Assert.assertEquals(4, minEatingSpeed(new int[]{3, 6, 7, 11}, 8));
        Assert.assertEquals(30, minEatingSpeed(new int[]{30, 11, 23, 4, 20}, 5));
        Assert.assertEquals(23, minEatingSpeed(new int[]{30, 11, 23, 4, 20}, 6));
        Assert.assertEquals(2, minEatingSpeed(new int[]{312884470}, 312884469));
    }

    /**
     * 1011. 在 D 天内送达包裹的能力
     * 传送带上的包裹必须在 days 天内从一个港口运送到另一个港口。
     * 传送带上的第 i 个包裹的重量为 weights[i]。每一天，我们都会按给出重量（weights）的顺序往传送带上装载包裹。我们装载的重量不会超过船的最大运载重量。
     * 返回能在 days 天内将传送带上的所有包裹送达的船的最低运载能力。
     * 示例 1：
     * 输入：weights = [1,2,3,4,5,6,7,8,9,10], days = 5
     * 输出：15
     * 解释：
     * 船舶最低载重 15 就能够在 5 天内送达所有包裹，如下所示：
     * 第 1 天：1, 2, 3, 4, 5
     * 第 2 天：6, 7
     * 第 3 天：8
     * 第 4 天：9
     * 第 5 天：10
     * 示例 2：
     * 输入：weights = [3,2,2,4,1,4], days = 3
     * 输出：6
     * 解释：
     * 船舶最低载重 6 就能够在 3 天内送达所有包裹，如下所示：
     * 第 1 天：3, 2
     * 第 2 天：2, 4
     * 第 3 天：1, 4
     * 请注意，货物必须按照给定的顺序装运，因此使用载重能力为 14 的船舶并将包装分成 (2, 3, 4, 5), (1, 6, 7), (8), (9), (10) 是不允许的
     * 示例 3：
     * 输入：weights = [1,2,3,1,1], days = 4
     * 输出：3
     * 解释：
     * 第 1 天：1
     * 第 2 天：2
     * 第 3 天：3
     * 第 4 天：1, 1
     *
     * @param weights
     * @param days
     * @return
     */
    public int shipWithinDays(int[] weights, int days) {
        // 因为不可以拆分，所以最小值肯定是重量里面的最大值，最大值不可能超过重量的总和，因为是开区间所以要+1
        int low = Arrays.stream(weights).max().getAsInt(), high = Arrays.stream(weights).sum() + 1;
        while (low < high) {
            int mid = low + (high - low) / 2;
            int needDays = needDays(weights, mid);
            if (needDays > days) {
                low = mid + 1;
            } else if (needDays < days) {
                high = mid;
            } else {
                high = mid;
            }
        }
        return low;
    }

    /**
     * 就是简单模拟
     *
     * @param weights
     * @param cap
     * @return
     */
    public int needDays(int[] weights, int cap) {
        int needDays = 1, current = 0;
        for (int weight : weights) {
            // 提前判断，如果满了就新增一天
            if (current + weight > cap) {
                needDays++;
                current = 0;
            }
            current += weight;
        }
        return needDays;
    }

    @Test
    public void testShipWithinDays() {
        Assert.assertEquals(15, shipWithinDays(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}, 5));
        Assert.assertEquals(6, shipWithinDays(new int[]{3, 2, 2, 4, 1, 4}, 3));
        Assert.assertEquals(3, shipWithinDays(new int[]{1, 2, 3, 1, 1}, 4));
    }

    /**
     * 2389. 和有限的最长子序列
     * https://leetcode.cn/problems/longest-subsequence-with-limited-sum/description/
     * 给你一个长度为 n 的整数数组 nums ，和一个长度为 m 的整数数组 queries 。
     * 返回一个长度为 m 的数组 answer ，其中 answer[i] 是 nums 中 元素之和小于等于 queries[i] 的 子序列 的 最大 长度  。
     * 子序列 是由一个数组删除某些元素（也可以不删除）但不改变剩余元素顺序得到的一个数组。
     * 实例1：
     * 输入：nums = [4,5,2,1], queries = [3,10,21]
     * 输出：[2,3,4]
     * 解释：queries 对应的 answer 如下：
     * - 子序列 [2,1] 的和小于或等于 3 。可以证明满足题目要求的子序列的最大长度是 2 ，所以 answer[0] = 2 。
     * - 子序列 [4,5,1] 的和小于或等于 10 。可以证明满足题目要求的子序列的最大长度是 3 ，所以 answer[1] = 3 。
     * - 子序列 [4,5,2,1] 的和小于或等于 21 。可以证明满足题目要求的子序列的最大长度是 4 ，所以 answer[2] = 4 。
     * 示例 2：
     * 输入：nums = [2,3,4,5], queries = [1]
     * 输出：[0]
     * 解释：空子序列是唯一一个满足元素和小于或等于 1 的子序列，所以 answer[0] = 0 。
     * 解题思路：本质上是对前缀和数组进行二分查找
     * 1. 排序
     *  1.1 虽然是子序列，这道题的顺序不影响结果
     * 2. 前缀和
     * 3. 二分搜索
     *  3.1因为是求最长，所以是upperBound
     * @param nums
     * @param queries
     * @return
     */
    public int[] answerQueries(int[] nums, int[] queries) {
        Arrays.sort(nums);
        int[] prefixSum = new int[nums.length];
        prefixSum[0] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            prefixSum[i] = prefixSum[i - 1] + nums[i];
        }
        int[] answer = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            answer[i] = upperBound(prefixSum, queries[i]);
        }
        return answer;
    }

    public int upperBound(int[] data, int target) {
        int low = 0, high = data.length;
        while (low < high) {
            int mid = low + (high - low) / 2;
            if (target > data[mid]) {
                low = mid + 1;
            } else if (target < data[mid]) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }
        return high;
    }

    @Test
    public void testAnswerQueries() {
        Assert.assertEquals(true, Arrays.equals(new int[]{2, 3, 4}, answerQueries(new int[]{4, 5, 2, 1}, new int[]{3, 10, 21})));
        Assert.assertEquals(true, Arrays.equals(new int[]{0}, answerQueries(new int[]{2, 3, 4, 5}, new int[]{1})));
    }

    /**
     * 215. 数组中的第K个最大元素
     * https://leetcode.cn/problems/kth-largest-element-in-an-array/description/
     * 示例 1:
     * 输入: [3,2,1,5,6,4], k = 2
     * 输出: 5
     * 示例 2:
     * 输入: [3,2,3,1,2,4,5,5,6], k = 4
     * 输出: 4
     * 给定整数数组 nums 和整数 k，请返回数组中第 k 个最大的元素。
     * 请注意，你需要找的是数组排序后的第 k 个最大的元素，而不是第 k 个不同的元素。
     * 你必须设计并实现时间复杂度为 O(n) 的算法解决此问题。
     * TODO 347. 前 K 个高频元素
     * TODO 4. 寻找两个正序数组的中位数
     * @param nums
     * @param k
     * @return
     */
    public int findKthLargest(int[] nums, int k) {
        // 这里有一个转换 kTh就是nums.length - k
        return quickSort(nums, nums.length - k, 0, nums.length - 1);
    }

    public int quickSort(int[] nums, int kTh, int left, int right) {
        if (left == right) {
            // 左右一样，说明已经是最终结果了，直接返回
            // 例如：1 2 3，上次循环idx=1,kTth=0,本次循环到达这个分支
            return nums[left];
        } else {
            int idx = partition(nums, left, right);
            if (idx == kTh) {
                return nums[kTh];
            } else if (idx > kTh) {
                // 如果idx > kTh 则查找[left, idx - 1]即缩小范围1
                return quickSort(nums, kTh, left, idx - 1);
            } else {
                // 如果idx < kTh 则查找[idx + 1, right]即缩小范围1
                return quickSort(nums, kTh, idx + 1, right);
            }
        }
    }

    public int partition(int[] nums, int left, int right) {
        Random random = new Random();
        // nextInt[0, bound)
        int idx = left + random.nextInt(right - left + 1), i = left, j = right;
        // 直接交换哨兵和最左侧
        int pivot = nums[idx];
        nums[idx] = nums[left];
        nums[left] = pivot;
        while (i < j) {
            // 从最右侧开始查找比哨兵小的数
            while (i < j && pivot <= nums[j]) {
                j--;
            }
            // 这里是填坑
            nums[i] = nums[j];
            // 从最左侧开始查找比哨兵大于等于的数
            while (i < j && pivot > nums[i]) {
                i++;
            }
            // 这里是填坑
            nums[j] = nums[i];
        }
        nums[i] = pivot;
        return i;
    }


    @Test
    public void testFindKthLargest() {
        Assert.assertEquals(5, findKthLargest(new int[]{3, 2, 1, 5, 6, 4}, 2));
        Assert.assertEquals(4, findKthLargest(new int[]{3, 2, 3, 1, 2, 4, 5, 5, 6}, 4));
    }

    @Test
    public void testRandom() {
        Random random = new Random();
        int left = 0, right = 2;
        for (int i = 0; i < 20; i++) {
            log.info("nextInt={}", (left + random.nextInt(right - left)));
        }
    }

    /**
     * 剑指 Offer II 072. 求平方根 https://leetcode.cn/problems/jJ0w9p/
     * 69. x 的平方根 https://leetcode.cn/problems/sqrtx/
     * 给你一个非负整数 x ，计算并返回 x 的 算术平方根 。
     * 由于返回类型是整数，结果只保留 整数部分 ，小数部分将被 舍去 。
     * 注意：不允许使用任何内置指数函数和算符，例如 pow(x, 0.5) 或者 x ** 0.5 。
     * 解题思路：upper bound 寻找最接近x的数
     *
     * @param x
     * @return
     */
    public int mySqrt(int x) {
        int ans = 0, left = 1, right = x;
        // 2端都是闭区间
        while (left <= right) {
            // upper bound的形式，因为我们要找的ans要是最接近于x的最大的数，利用upper bound
            int mid = left + (right - left) / 2;
            if (mid <= x / mid) {
                // 只要mid <= x/mid，left左边界就会一直向右移动，ans就会一直更新（变大）
                // 直到不在满足mid <= x/mid的条件为止，ans就会停止更新，永远停在满足mid<=x/mid条件下面的最后一次更新
                // 即满足ans * ans <= mid的最大值。
                ans = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return ans;
    }

    @Test
    public void testMySqrt() {
        Assert.assertEquals(2, mySqrt(8));
        Assert.assertEquals(2, mySqrt(5));
        Assert.assertEquals(3, mySqrt(9));
    }

    /**
     * 35. 搜索插入位置
     * https://leetcode.cn/problems/search-insert-position/?envType=study-plan-v2&envId=top-100-liked
     * 给定一个排序数组和一个目标值，在数组中找到目标值，并返回其索引。如果目标值不存在于数组中，返回它将会被按顺序插入的位置。
     * <p>
     * 请必须使用时间复杂度为 O(log n) 的算法。
     * <p>
     * <p>
     * <p>
     * 示例 1:
     * <p>
     * 输入: nums = [1,3,5,6], target = 5
     * 输出: 2
     * 示例 2:
     * <p>
     * 输入: nums = [1,3,5,6], target = 2
     * 输出: 1
     * 示例 3:
     * <p>
     * 输入: nums = [1,3,5,6], target = 7
     * 输出: 4
     * nums 为 无重复元素 的 升序 排列数组
     * 解题思路：因为是找最大，所以用的是upperBound，又因为无重复元素，所以找到时直接返回
     * @param nums
     * @param target
     * @return
     */
    public int searchInsert(int[] nums, int target) {
        int left = 0, right = nums.length;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] > target) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }

    @Test
    public void testSearchInsert() {
        Assert.assertEquals(1, searchInsert(new int[]{1, 3}, 2));
        Assert.assertEquals(2, searchInsert(new int[]{1, 3, 5, 6}, 5));
        Assert.assertEquals(1, searchInsert(new int[]{1, 3, 5, 6}, 2));
        Assert.assertEquals(4, searchInsert(new int[]{1, 3, 5, 6}, 7));
    }

    /**
     * 4. 搜索二维矩阵
     * 给你一个满足下述两条属性的 m x n 整数矩阵：
     * <p>
     * 每行中的整数从左到右按非严格递增顺序排列。
     * 每行的第一个整数大于前一行的最后一个整数。
     * 给你一个整数 target ，如果 target 在矩阵中，返回 true ；否则，返回 false 。
     * <p>
     * <p>
     * <p>
     * 示例 1：
     * <p>
     * <p>
     * 输入：matrix = [[1,3,5,7],[10,11,16,20],[23,30,34,60]], target = 3
     * 输出：true
     * 示例 2：
     * <p>
     * <p>
     * 输入：matrix = [
     * [1,3,5,7],
     * [10,11,16,20],
     * [23,30,34,60]
     * ], target = 13
     * 输出：false
     * 解题思路：搜索二叉树
     * 从右上角开始，往左下角搜索
     * @param matrix
     * @param target
     * @return
     */
    public boolean searchMatrix(int[][] matrix, int target) {
        int x = 0;
        int y = matrix[0].length - 1;
        while (0 <= x && x < matrix.length && 0 <= y && y < matrix[0].length) {
            if (matrix[x][y] == target) {
                return true;
            } else if (matrix[x][y] > target) {
                // 数字大了就回去点
                y--;
            } else {
                // 数字笑了就往下一行
                x++;
            }
        }
        return false;
    }

    @Test
    public void testSearchMatrix() {
        Assert.assertEquals(true, searchMatrix(new int[][]{{1, 3, 5, 7}, {10, 11, 16, 20}, {23, 30, 34, 60}}, 3));
        Assert.assertEquals(false, searchMatrix(new int[][]{{1, 3, 5, 7}, {10, 11, 16, 20}, {23, 30, 34, 60}}, 13));
    }

    public boolean searchMatrixBinary(int[][] matrix, int target) {
        int n = matrix.length, m = matrix[0].length;
        int left = 0, right = n * m - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            int num = matrix[mid / m][mid % m];
            if (num == target) {
                return true;
            } else if (num < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return false;
    }

    @Test
    public void testSearchMatrixBinary() {
        Assert.assertEquals(false, searchMatrixBinary(new int[][]{{1, 1}}, 2));
        Assert.assertEquals(true, searchMatrixBinary(new int[][]{{1, 3, 5, 7}, {10, 11, 16, 20}, {23, 30, 34, 60}}, 3));
        Assert.assertEquals(false, searchMatrixBinary(new int[][]{{1, 3, 5, 7}, {10, 11, 16, 20}, {23, 30, 34, 60}}, 13));
    }

    /**
     * 34. 在排序数组中查找元素的第一个和最后一个位置
     * https://leetcode.cn/problems/find-first-and-last-position-of-element-in-sorted-array/?envType=study-plan-v2&envId=top-100-liked
     * 给你一个按照非递减顺序排列的整数数组 nums，和一个目标值 target。请你找出给定目标值在数组中的开始位置和结束位置。
     * <p>
     * 如果数组中不存在目标值 target，返回 [-1, -1]。
     * <p>
     * 你必须设计并实现时间复杂度为 O(log n) 的算法解决此问题。
     * <p>
     * <p>
     * <p>
     * 示例 1：
     * <p>
     * 输入：nums = [5,7,7,8,8,10], target = 8
     * 输出：[3,4]
     * 示例 2：
     * <p>
     * 输入：nums = [5,7,7,8,8,10], target = 6
     * 输出：[-1,-1]
     * 示例 3：
     * <p>
     * 输入：nums = [], target = 0
     * 输出：[-1,-1]
     * 解题思路：
     * 1. 2次rightBound查找
     *
     * @param nums
     * @param target
     * @return
     */
    public int[] searchRange(int[] nums, int target) {
        if (nums.length == 0) {
            return new int[]{-1, -1};
        }
        int low = rightBound(nums, target - 1);
        int hi = rightBound(nums, target);
        return low == hi ? new int[]{-1, -1} : new int[]{low, hi - 1};
    }

    public int rightBound(int[] nums, int target) {
        int left = 0, right = nums.length;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] > target) {
                right = mid;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else if (nums[mid] == target) {
                left = mid + 1;
            }
        }
        return left;
    }

    @Test
    public void testSearchRange() {
        Assert.assertEquals(true, Arrays.equals(new int[]{3, 4}, searchRange(new int[]{5, 7, 7, 8, 8, 10}, 8)));
        Assert.assertEquals(true, Arrays.equals(new int[]{-1, -1}, searchRange(new int[]{5, 7, 7, 8, 8, 10}, 6)));
        Assert.assertEquals(true, Arrays.equals(new int[]{-1, -1}, searchRange(new int[]{}, 0)));
    }

    /**
     * 33. 搜索旋转排序数组
     * https://leetcode.cn/problems/search-in-rotated-sorted-array/?envType=study-plan-v2&envId=top-100-liked
     * 整数数组 nums 按升序排列，数组中的值 互不相同 。
     * <p>
     * 在传递给函数之前，nums 在预先未知的某个下标 k（0 <= k < nums.length）上进行了 旋转，使数组变为 [nums[k], nums[k+1], ..., nums[n-1], nums[0], nums[1], ..., nums[k-1]]（下标 从 0 开始 计数）。例如， [0,1,2,4,5,6,7] 在下标 3 处经旋转后可能变为 [4,5,6,7,0,1,2] 。
     * <p>
     * 给你 旋转后 的数组 nums 和一个整数 target ，如果 nums 中存在这个目标值 target ，则返回它的下标，否则返回 -1 。
     * <p>
     * 你必须设计一个时间复杂度为 O(log n) 的算法解决此问题。
     * 示例 1：
     * <p>
     * 输入：nums = [4,5,6,7,0,1,2], target = 0
     * 输出：4
     * 示例 2：
     * <p>
     * 输入：nums = [4,5,6,7,0,1,2], target = 3
     * 输出：-1
     * 示例 3：
     * <p>
     * 输入：nums = [1], target = 0
     * 输出：-1
     *
     * @param nums
     * @param target
     * @return
     */
    public int search(int[] nums, int target) {
        int left = 0, right = nums.length;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                return mid;
            } else if (nums[left] <= nums[mid]) { // 左半部分是递增序列
                if (nums[left] <= target && target < nums[mid]) { // 目标在左半部分
                    right = mid;
                } else {
                    left = mid + 1;
                }
            } else { // 右半部分是递增序列
                if (nums[mid] < target && target <= nums[right]) { // 目标在右半部分
                    left = mid + 1;
                } else {
                    right = mid;
                }
            }
        }
        return -1;
    }

    @Test
    public void testSearch() {
        Assert.assertEquals(4, search(new int[]{4, 5, 6, 7, 0, 1, 2}, 0));
        Assert.assertEquals(-1, search(new int[]{4, 5, 6, 7, 0, 1, 2}, 3));
        Assert.assertEquals(-1, search(new int[]{1}, 0));
    }

    /**
     * 153. 寻找旋转排序数组中的最小值
     * https://leetcode.cn/problems/find-minimum-in-rotated-sorted-array/description/?envType=study-plan-v2&envId=top-100-liked
     * 已知一个长度为 n 的数组，预先按照升序排列，经由 1 到 n 次 旋转 后，得到输入数组。例如，原数组 nums = [0,1,2,4,5,6,7] 在变化后可能得到：
     * 若旋转 4 次，则可以得到 [4,5,6,7,0,1,2]
     * 若旋转 7 次，则可以得到 [0,1,2,4,5,6,7]
     * 注意，数组 [a[0], a[1], a[2], ..., a[n-1]] 旋转一次 的结果为数组 [a[n-1], a[0], a[1], a[2], ..., a[n-2]] 。
     * <p>
     * 给你一个元素值 互不相同 的数组 nums ，它原来是一个升序排列的数组，并按上述情形进行了多次旋转。请你找出并返回数组中的 最小元素 。
     * <p>
     * 你必须设计一个时间复杂度为 O(log n) 的算法解决此问题。
     * <p>
     * <p>
     * <p>
     * 示例 1：
     * <p>
     * 输入：nums = [3,4,5,1,2]
     * 输出：1
     * 解释：原数组为 [1,2,3,4,5] ，旋转 3 次得到输入数组。
     * 示例 2：
     * <p>
     * 输入：nums = [4,5,6,7,0,1,2]
     * 输出：0
     * 解释：原数组为 [0,1,2,4,5,6,7] ，旋转 4 次得到输入数组。
     * 示例 3：
     * <p>
     * 输入：nums = [11,13,15,17]
     * 输出：11
     * 解释：原数组为 [11,13,15,17] ，旋转 4 次得到输入数组。
     * 解题思路：官方题解
     * 1.当mid < high时，说明[mid, high]时递增序列，最小值一定在左半部分，mid有可能是最小值
     * 2.当mid > high时，说明[0, mid]先升后降，最小值一定在右半部分
     * 3.由于数组不包含重复元素，并且只要当前的区间长度不为1，mid和high不会重合
     *
     * @param nums
     * @return
     */
    public int findMin(int[] nums) {
        int left = 0, right = nums.length - 1;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] < nums[right]) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return nums[left];
    }

    @Test
    public void testFindMin() {
        Assert.assertEquals(1, findMin(new int[]{3, 4, 5, 1, 2}));
        Assert.assertEquals(0, findMin(new int[]{4, 5, 6, 7, 0, 1, 2}));
        Assert.assertEquals(11, findMin(new int[]{11, 13, 15, 17}));
    }
}
