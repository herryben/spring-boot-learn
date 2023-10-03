package com.learn.array;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @author herryhaorui@didiglobal.com
 * @desc
 * @date 2023/7/8 3:53 下午
 * int[] diff = new int[nums.length];
 * // 构造差分数组
 * diff[0] = nums[0];
 * for (int i = 1; i < nums.length; i++) {
 *     diff[i] = nums[i] - nums[i - 1];
 * }
 *
 * int[] res = new int[diff.length];
 * // 根据差分数组构造结果数组
 * res[0] = diff[0];
 * for (int i = 1; i < diff.length; i++) {
 *     res[i] = res[i - 1] + diff[i];
 * }
 *
 * 这样构造差分数组diff，就可以快速进行区间增减的操作，
 * 如果你想对区间nums[i..j]的元素全部加 3，那么只需要让diff[i] += 3，然后再让diff[j+1](j+1在0..nums.length范围内) -= 3即可：
 * 单点更新，范围查询，就用线段树。范围更新，单独查询，就用差分数组。
 */
public class DiffArray {
    /**
     * 1109. 航班预订统计
     * https://leetcode.cn/problems/corporate-flight-bookings/description/
     * 这里有 n 个航班，它们分别从 1 到 n 进行编号。
     *
     * 有一份航班预订表 bookings ，表中第 i 条预订记录 bookings[i] = [firsti, lasti, seatsi] 意味着在从 firsti 到 lasti （包含 firsti 和 lasti ）的 每个航班 上预订了 seatsi 个座位。
     *
     * 请你返回一个长度为 n 的数组 answer，里面的元素是每个航班预定的座位总数。
     * 示例 1：
     *
     * 输入：bookings = [[1,2,10],[2,3,20],[2,5,25]], n = 5
     * 输出：[10,55,45,25,25]
     * 解释：
     * 航班编号        1   2   3   4   5
     * 预订记录 1 ：   10  10
     * 预订记录 2 ：       20  20
     * 预订记录 3 ：       25  25  25  25
     * 总座位数：      10  55  45  25  25
     * 因此，answer = [10,55,45,25,25]
     * 示例 2：
     *
     * 输入：bookings = [[1,2,10],[2,2,15]], n = 2
     * 输出：[10,25]
     * 解释：
     * 航班编号        1   2
     * 预订记录 1 ：   10  10
     * 预订记录 2 ：       15
     * 总座位数：      10  25
     * 因此，answer = [10,25]
     * 解题思路：基础数据为0的差分数组
     * @param bookings
     * @param n
     * @return
     */
    public int[] corpFlightBookings(int[][] bookings, int n) {
        int[] diff = new  int[n];
        for(int[] book : bookings) {
            int i = book[0] - 1;
            int j_1 = book[1];
            int val = book[2];
            diff[i] += val;
            if (j_1 < n) {
                diff[j_1] -= val;
            }
        }
        for (int i = 1; i < n; i++) {
            // 既是差分数据，又是原数组
            diff[i] = diff[i - 1] + diff[i];
        }
        return diff;
    }

    @Test
    public void testCorpFlightBookings() {
        Assert.assertEquals(true, Arrays.equals(new int[]{10,55,45,25,25}, corpFlightBookings(new int[][] {{1,2,10},{2,3,20},{2,5,25}}, 5)));
        Assert.assertEquals(true, Arrays.equals(new int[]{10,25}, corpFlightBookings(new int[][] {{1,2,10}, {2,2,15}}, 2)));
    }

    /**
     * 1094. 拼车
     * https://leetcode.cn/problems/car-pooling/description/
     * 车上最初有 capacity 个空座位。车 只能 向一个方向行驶（也就是说，不允许掉头或改变方向）
     *
     * 给定整数 capacity 和一个数组 trips ,  trip[i] = [numPassengersi, fromi, toi] 表示第 i 次旅行有 numPassengersi 乘客，接他们和放他们的位置分别是 fromi 和 toi 。这些位置是从汽车的初始位置向东的公里数。
     *
     * 当且仅当你可以在所有给定的行程中接送所有乘客时，返回 true，否则请返回 false。
     * 示例 1：
     *
     * 输入：trips = [[2,1,5],[3,3,7]], capacity = 4
     * 输出：false
     * 示例 2：
     *
     * 输入：trips = [[2,1,5],[3,3,7]], capacity = 5
     * 输出：true
     * 解题思路：利用差分数组模拟上下车，最后车上的容量大于给定的则肯定不能完成
     * @param trips
     * @param capacity
     * @return
     */
    public boolean carPooling(int[][] trips, int capacity) {
        int max = 0;
        for(int[] trip : trips) {
            int start = trip[1];
            int end = trip[2];
            max = Math.max(max, start);
            max = Math.max(max, end);
        }
        int[] diff = new int[++max];

        for(int[] trip : trips) {
            int i = trip[1];
            int j_1 = trip[2];
            int val = trip[0];
            diff[i] += val;
            if (j_1 < max) {
                diff[j_1] -= val;
            }
        }

        for (int i = 1; i < max; i++) {
            diff[i] += diff[i - 1];
        }
        for (int i = 0; i < max; i++) {
            if (capacity < diff[i]) {
                return false;
            }
        }
        return true;
    }

    @Test
    public void testCarPooling() {
        Assert.assertEquals(false, carPooling(new int[][] {{2,1,5}, {3,3,7}}, 4));
        Assert.assertEquals(true, carPooling(new int[][] {{2,1,5}, {3,3,7}}, 5));
    }

    /**
     * 解题思路：用贪心模拟整个过程，如果不够的话就能下多少下多少，如果还是上不了则说明肯定不能完成
     * 用堆加速乘客下车
     * @param trips
     * @param capacity
     * @return
     */
    public boolean carPooling2(int[][] trips, int capacity) {
        // 以下车时间进行排序
        PriorityQueue<int[]> queue = new PriorityQueue<>(Comparator.comparingInt(o -> o[2]));
        // 以上车时间进行排序
        Arrays.sort(trips, Comparator.comparingInt(o -> o[1]));
        for(int[] trip: trips) {
            capacity -= trip[0];
            if (capacity < 0) {
                while (!queue.isEmpty() && trip[1] >= queue.peek()[2]) {
                    capacity += queue.poll()[0];
                }
                if (capacity < 0) {
                    return false;
                }
            }
            queue.offer(trip);
        }
        return true;
    }

    @Test
    public void testCarPooling2() {
        Assert.assertEquals(false, carPooling2(new int[][] {{2,1,5}, {3,3,7}}, 4));
        Assert.assertEquals(true, carPooling2(new int[][] {{2,1,5}, {3,3,7}}, 5));
    }
}
