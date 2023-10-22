package com.learn.interval;

import com.learn.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
@Slf4j
public class IntervalsSolution {
    /**
     * 56. 合并区间
     * https://leetcode.cn/problems/merge-intervals/
     * 以数组 intervals 表示若干个区间的集合，其中单个区间为 intervals[i] = [starti, endi] 。请你合并所有重叠的区间，并返回 一个不重叠的区间数组，该数组需恰好覆盖输入中的所有区间 。
     * 示例 1：
     * <p>
     * 输入：intervals = [[1,3],[2,6],[8,10],[15,18]]
     * 输出：[[1,6],[8,10],[15,18]]
     * 解释：区间 [1,3] 和 [2,6] 重叠, 将它们合并为 [1,6].
     * 示例 2：
     * <p>
     * 输入：intervals = [[1,4],[4,5]]
     * 输出：[[1,5]]
     * 解释：区间 [1,4] 和 [4,5] 可被视为重叠区间。
     * 结题思路：先排序，当前头小于等于最后一个的尾就是重叠
     * @param intervals
     * @return
     */
    public int[][] merge(int[][] intervals) {
        List<int[]> res = new ArrayList<>();
        // 先排序，start为排序key
        Arrays.sort(intervals, (o1, o2) -> {
            return o1[0] - o2[0];
        });
        res.add(new int[]{intervals[0][0], intervals[0][1]});
        for (int i = 1; i < intervals.length; i++) {
            int[] current = intervals[i];
            int[] last = res.get(res.size() - 1);
            // 当前头小于等于最后一个尾
            if (current[0] <= last[1]) {
                // 相当于合并
                last[1] = Math.max(current[1], last[1]);
            } else {
                res.add(new int[]{current[0], current[1]});
            }
        }
        return res.toArray(new int[][]{});
    }

    @Test
    public void testMerge() {
        Assert.assertTrue(Utils.isEqualArray(new int[][]{{1, 6}, {8, 10}, {15, 18}}, merge(new int[][]{{1, 3}, {2, 6}, {8, 10}, {15, 18}})));
        Assert.assertTrue(Utils.isEqualArray(new int[][]{{1, 5}}, merge(new int[][]{{1, 4}, {4, 5}})));
    }

    /**
     * 435. 无重叠区间
     * https://leetcode.cn/problems/non-overlapping-intervals/description/
     * 给定一个区间的集合 intervals ，其中 intervals[i] = [starti, endi] 。返回 需要移除区间的最小数量，使剩余区间互不重叠 。
     * 示例 1:
     * <p>
     * 输入: intervals = [[1,2],[2,3],[3,4],[1,3]]
     * 输出: 1
     * 解释: 移除 [1,3] 后，剩下的区间没有重叠。
     * 示例 2:
     * <p>
     * 输入: intervals = [ [1,2], [1,2], [1,2] ]
     * 输出: 2
     * 解释: 你需要移除两个 [1,2] 来使剩下的区间没有重叠。
     * 示例 3:
     * <p>
     * 输入: intervals = [ [1,2], [2,3] ]
     * 输出: 0
     * 解释: 你不需要移除任何区间，因为它们已经是无重叠的了。
     * 解题思路：
     * 1. 算出不重合的数量
     * 2. 总数- 不重合的数量
     * @param intervals
     * @return
     */
    public int eraseOverlapIntervals(int[][] intervals) {
        // 先排序，start为排序key
        Arrays.sort(intervals, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[0] - o2[0];
            }
        });
        // 不重合的数量
        int count = 1;
        int end = intervals[0][1];
        for (int i = 1; i < intervals.length; i++) {
            int[] interval = intervals[i];
            if (interval[0] >= end) {
                // 如果区间不重合计算+1，重新开始选取
                count++;
                end = interval[1];
            }
        }
        return intervals.length - count;
    }

    @Test
    public void testEraseOverlapIntervals() {
        Assert.assertEquals(1, eraseOverlapIntervals(new int[][]{{1, 2}, {2, 3}, {3, 4}, {1, 3}}));
        Assert.assertEquals(2, eraseOverlapIntervals(new int[][]{{1, 2}, {1, 2}, {1, 2}}));
        Assert.assertEquals(0, eraseOverlapIntervals(new int[][]{{1, 2}, {2, 3}}));
    }

    /**
     * 面试题 16.06. 最小差
     * https://leetcode.cn/problems/smallest-difference-lcci/
     * 给定两个整数数组a和b，计算具有最小差绝对值的一对数值（每个数组中取一个值），并返回该对数值的差
     * 示例：
     * <p>
     * 输入：{1, 3, 15, 11, 2}, {23, 127, 235, 19, 8}
     * 输出：3，即数值对(11, 8)
     * 提示：
     * <p>
     * 1 <= a.length, b.length <= 100000
     * -2147483648 <= a[i], b[i] <= 2147483647
     * 正确结果在区间 [0, 2147483647] 内
     * 解题思路：
     * 1. 贪心
     * 2. 先对2个数组排序
     * 3. 再用双指针，谁小谁往前
     * 需要注意补码、反码相关内容
     * https://www.404bugs.com/index.php/details/1079094626572095488
     * https://blog.csdn.net/qq_48052049/article/details/125994544
     * @param a
     * @param b
     * @return
     */
    public int smallestDifference(int[] a, int[] b) {
        // 这俩用long来解决abs溢出问题
        long diff = Integer.MAX_VALUE;
        Arrays.sort(a);
        Arrays.sort(b);
        int i = 0, j = 0;
        while (i < a.length && j < b.length) {
            diff = Math.min(Math.abs(diff), Math.abs(a[i] - b[j]));
            // 贪心 谁小谁往前走
            if (a[i] < b[j]) {
                i++;
            } else {
                j++;
            }
        }
        return (int) diff;
    }

    @Test
    public void testSmallestDifference() {
        Assert.assertEquals(1, smallestDifference(new int[]{-2147483648, 1}, new int[]{2147483647, 0}));
        Assert.assertEquals(3, smallestDifference(new int[]{1, 3, 15, 11, 2}, new int[]{23, 127, 235, 19, 8}));
    }

    /**
     * 452. 用最少数量的箭引爆气球
     * 有一些球形气球贴在一堵用 XY 平面表示的墙面上。墙面上的气球记录在整数数组 points ，其中points[i] = [xstart, xend] 表示水平直径在 xstart 和 xend之间的气球。你不知道气球的确切 y 坐标。
     * <p>
     * 一支弓箭可以沿着 x 轴从不同点 完全垂直 地射出。在坐标 x 处射出一支箭，若有一个气球的直径的开始和结束坐标为 xstart，xend， 且满足  xstart ≤ x ≤ xend，则该气球会被 引爆 。可以射出的弓箭的数量 没有限制 。 弓箭一旦被射出之后，可以无限地前进。
     * <p>
     * 给你一个数组 points ，返回引爆所有气球所必须射出的 最小 弓箭数 。
     * 示例 1：
     * <p>
     * 输入：points = [[10,16],[2,8],[1,6],[7,12]]
     * 输出：2
     * 解释：气球可以用2支箭来爆破:
     * -在x = 6处射出箭，击破气球[2,8]和[1,6]。
     * -在x = 11处发射箭，击破气球[10,16]和[7,12]。
     * 示例 2：
     * <p>
     * 输入：points = [[1,2],[3,4],[5,6],[7,8]]
     * 输出：4
     * 解释：每个气球需要射出一支箭，总共需要4支箭。
     * 示例 3：
     * <p>
     * 输入：points = [[1,2],[2,3],[3,4],[4,5]]
     * 输出：2
     * 解释：气球可以用2支箭来爆破:
     * - 在x = 2处发射箭，击破气球[1,2]和[2,3]。
     * - 在x = 4处射出箭，击破气球[3,4]和[4,5]。
     * 解题思路
     * 1. 计算不重合的数量
     * @param points
     * @return
     */
    public int findMinArrowShots(int[][] points) {
        Arrays.sort(points, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[0] - o2[0];
            }
        });
        int count = 1;
        int end = points[0][1];
        for (int i = 1; i < points.length; i++) {
            int[] point = points[i];
            if (point[0] > end) {
                count++;
                end = point[1];
            }
        }
        return count;
    }

    @Test
    public void testFindMinArrowShots() {
        Assert.assertEquals(2, findMinArrowShots(new int[][]{{10, 16}, {2, 8}, {1, 6}, {7, 12}}));
        Assert.assertEquals(4, findMinArrowShots(new int[][]{{1, 2}, {3, 4}, {5, 6}, {7, 8}}));
        Assert.assertEquals(2, findMinArrowShots(new int[][]{{1, 2}, {2, 3}, {3, 4}, {4, 5}}));
    }
}
